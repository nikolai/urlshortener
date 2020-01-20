package org.sng.shortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sng.shortener.controllers.ParamNames;
import org.sng.shortener.json.GetStatisticResponse;
import org.sng.shortener.model.AllowedRedirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sng.shortener.controllers.ParamNames.ACCOUNT_ID;
import static org.sng.shortener.controllers.PathMappings.ACCOUNT;
import static org.sng.shortener.controllers.PathMappings.REGISTER;
import static org.sng.shortener.controllers.PathMappings.STATISTIC;
import static org.sng.shortener.services.AccountService.PASSWORD_LENGTH;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiControllerTest {

    private static final String PASSWORD = "password";
    private static final String SUCCESS = "success";
    private static final String SHORT_URL = "shortUrl";

    private static final String TEST_LONG_URL = "http://ya.ru";

    private String newAccountId;
    private static long accountCount;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void beforeTest() {
        newAccountId = "acc" + accountCount++;
    }

    @Test
    public void open_account() throws Exception {
        Assert.assertTrue(createAccountOk(newAccountId).length() >= PASSWORD_LENGTH);
    }

    @Test
    public void register_url() throws Exception {
        String pass = createAccountOk(newAccountId);
        new URL(shortenOk(newAccountId, pass, TEST_LONG_URL));
    }

    @Test
    public void default_redirect_test() throws Exception {
        String pass = createAccountOk(newAccountId);
        URL shortUrl = new URL(shortenOk(newAccountId, pass, TEST_LONG_URL));
        checkRedirect(TEST_LONG_URL, shortUrl, 302);
    }

    @Test
    public void redirect_301_test() throws Exception {
        String pass = createAccountOk(newAccountId);
        int redirectType = AllowedRedirect.PERMANENTLY_301.getCode();
        URL shortUrl = new URL(shortenOk(newAccountId, pass, TEST_LONG_URL, redirectType));
        checkRedirect(TEST_LONG_URL, shortUrl, redirectType);
    }

    @Test
    public void statistics_test() throws Exception {
        String pass = createAccountOk(newAccountId);
        Map<String, Long> expectedStats = new HashMap<>();

        URL shortUrl = new URL(shortenOk(newAccountId, pass, TEST_LONG_URL));
        int useRedirectCount = 5;
        for (int i = 0; i < useRedirectCount; i++) {
            checkRedirect(TEST_LONG_URL, shortUrl);
        }
        expectedStats.put(TEST_LONG_URL, (long)useRedirectCount);

        String anotherLongUrl = "http://google.com";
        URL anotherShortUrl = new URL(shortenOk(newAccountId, pass, anotherLongUrl));
        checkRedirect(anotherLongUrl, anotherShortUrl);
        expectedStats.put(anotherLongUrl, 1L);

        Map<String, Long> realStats = fetchStats(newAccountId, pass);
        assertThat(realStats.entrySet(), equalTo(expectedStats.entrySet()));
    }

    private Map<String, Long> fetchStats(String user, String password) throws Exception {
        String contentAsString = fetchStatsRequest(user, password)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper jackson = new ObjectMapper();
        return jackson.readValue(contentAsString, GetStatisticResponse.class);
    }

    private ResultActions fetchStatsRequest(String user, String password) throws Exception {
        return mockMvc.perform(get(STATISTIC + "/" + user)
                .with(user(user).password(password)))
                .andDo(print());
    }

    private void checkRedirect(String expectedLocation, URL shortUrl) throws Exception {
        checkRedirect(expectedLocation, shortUrl, AllowedRedirect.TEMPORARILY_302.getCode());
    }
    private void checkRedirect(String expectedLocation, URL shortUrl, int redirectType) throws Exception {
        mockMvc.perform(get(shortUrl.toURI()))
                .andDo(print())
                .andExpect(status().is(redirectType))
                .andExpect(header().stringValues("Location", expectedLocation));
    }

    private ResultActions createAccountPost(String accountId) throws Exception {
        return mockMvc.perform(post(ACCOUNT)
                .param(ACCOUNT_ID, accountId))
                .andDo(print());
    }

    private String createAccountOk(String accountId) throws Exception {
        String contentAsString = createAccountPost(accountId)
                .andExpect(status().isOk())
                .andExpect(jsonPath(SUCCESS, is(true)))
                .andExpect(jsonPath(PASSWORD).exists())
                .andReturn().getResponse().getContentAsString();

        return JsonPath.parse(contentAsString).read(PASSWORD, String.class);
    }

    private String shortenOk(String user, String password, String url) throws Exception {
        return shortenOk(user, password, url, null);
    }

    private String shortenOk(String user, String password, String url, Integer redirectType) throws Exception {
        String contentAsString = shortenPost(user, password, url, redirectType)
                .andExpect(status().isOk())
                .andExpect(jsonPath(SHORT_URL).isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        return JsonPath.parse(contentAsString).read(SHORT_URL, String.class);
    }

    private ResultActions shortenPost(String user, String password, String url, Integer redirectType) throws Exception {
        MockHttpServletRequestBuilder reqBuilder = post(REGISTER).with(user(user).password(password));
        if (redirectType != null) {
            reqBuilder.param(ParamNames.REDIRECT_TYPE, String.valueOf(redirectType));
        }
        reqBuilder.param(ParamNames.URL, url);
        return mockMvc.perform(reqBuilder).andDo(print());
    }
}
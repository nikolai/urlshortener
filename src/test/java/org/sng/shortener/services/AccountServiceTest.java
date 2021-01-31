package org.sng.shortener.services;


import org.junit.jupiter.api.Test;
import org.sng.shortener.exceptions.AccountAlreadyExists;
import org.sng.shortener.model.Account;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AccountServiceTest {

    @Test
    void register() throws AccountAlreadyExists {
        RandomGeneratorService rndGen = new RandomGeneratorService();
        AccountService accService = new AccountService(rndGen);

        String existAccId = "a153";
        Account existedAcc = new Account(existAccId, "secret");
        // условие выполнения теста (предусловие)
        accService.getRegisteredAccounts().put(existAccId, existedAcc);

        assertThatExceptionOfType(AccountAlreadyExists.class).isThrownBy(
            ()-> accService.register(existAccId)
        );
    }
}
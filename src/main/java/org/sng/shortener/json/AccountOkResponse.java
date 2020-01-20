package org.sng.shortener.json;

public class AccountOkResponse extends ResponseBase {

    private final String password;

    public AccountOkResponse(String password) {
        super(true, "Your account is opened");
		this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
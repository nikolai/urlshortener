package org.sng.shortener.services;

import org.sng.shortener.model.Account;
import org.sng.shortener.exceptions.AccountAlreadyExists;
import org.sng.shortener.exceptions.AccountAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class AccountService {
    public static final int PASSWORD_LENGTH = 8;
    private final ConcurrentHashMap<String, Account> registeredAccounts = new ConcurrentHashMap<>();

	@Autowired
    private RandomGeneratorService generatorService;

    public String register(String accountId) throws AccountAlreadyExists {
		String password = generatorService.generateString(PASSWORD_LENGTH);
		Account account = new Account(accountId, password);
        Account existed = registeredAccounts.putIfAbsent(accountId, account);
		if (existed != null) {
			throw new AccountAlreadyExists(accountId);
		}
		return password;
	}


	public void check(String name, String password) throws AccountAuthException {
		Account account = registeredAccounts.get(name);
		if (account != null && account.getPassword().equals(password)) {
			// passed!
            return;
		}
		throw new AccountAuthException();
	}
}
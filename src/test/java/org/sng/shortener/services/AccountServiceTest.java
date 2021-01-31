package org.sng.shortener.services;


import org.junit.jupiter.api.Test;
import org.sng.shortener.exceptions.AccountAlreadyExists;
import org.sng.shortener.model.Account;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AccountServiceTest {

    @Test
    void register() {
        // теперь можно заменить внутренние ссылки на тестовые объекты!
        RandomGeneratorService rndGen = new RandomGeneratorService();
        AccountService accService = new AccountService(rndGen);

        // теперь можно наполнить тестовые данные!
        String existAccId = "a153";
        Account existedAcc = new Account(existAccId, "secret");
        // условие выполнения теста (предусловие)
        accService.getRegisteredAccounts().put(existAccId, existedAcc);

        // ожидаемое поведение - генерация исключения AccountAlreadyExists
        assertThatExceptionOfType(AccountAlreadyExists.class).isThrownBy(
            ()-> accService.register(existAccId)
        );
    }
}
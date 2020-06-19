package com.bolife.online.service;

import java.util.List;
import java.util.Map;

import com.bolife.online.entity.Account;

public interface AccountService {
    public List<Account> getAllAccount();

    public Account getAccountByUsername(String username);

    Account getAccountById(int authorId);

    boolean updateAccount(Account currentAccount);

    Map<String, Object> getAccounts(int page, int accountPageSize);

    int addAccount(Account account);

    boolean deleteAccount(int id);

    boolean disabledAccount(int id);

    boolean abledAccount(int id);
}

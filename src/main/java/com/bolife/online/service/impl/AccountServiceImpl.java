package com.bolife.online.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolife.online.entity.Account;
import com.bolife.online.mapper.AccountMapper;
import com.bolife.online.service.AccountService;
import com.bolife.online.util.FinalDefine;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("all")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAllAccount() {
        return accountMapper.getAccounts();
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    @Override
    public Account getAccountById(int authorId) {
        return accountMapper.getAccountById(authorId);
    }

    @Override
    public boolean updateAccount(Account currentAccount) {
        return accountMapper.updateAccountById(currentAccount);
    }

    @Override
    public Map<String, Object> getAccounts(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = accountMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("accounts", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("accounts", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Account> accounts = accountMapper.getAccounts();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("accounts", accounts);
        return data;
    }

    @Override
    public boolean deleteAccount(int id) {
        return accountMapper.deleteAccount(id) > 0;
    }

    @Override
    public boolean disabledAccount(int id) {
        return accountMapper.updateState(id, 1) > 0;
    }

    @Override
    public boolean abledAccount(int id) {
        return accountMapper.updateState(id, 0) > 0;
    }

    @Override
    public int addAccount(Account account) {
        account.setAvatarImgUrl(FinalDefine.DEFAULT_AVATAR_IMG_URL);
        return accountMapper.insertAccount(account);
    }
}

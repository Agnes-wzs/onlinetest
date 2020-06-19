package com.bolife.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bolife.online.entity.Account;

@Mapper
public interface AccountMapper {
    public List<Account> getAccounts();

    Account getAccountByUsername(@Param("username") String username);

    Account getAccountById(@Param("id") int id);

    boolean updateAccountById(@Param("account") Account account);

    int getCount();

    int insertAccount(@Param("account") Account account);

    int deleteAccount(@Param("id") int id);

    int updateState(@Param("id") int id, @Param("state") int state);
}

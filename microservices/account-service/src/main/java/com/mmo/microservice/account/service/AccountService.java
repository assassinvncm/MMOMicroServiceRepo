package com.mmo.microservice.account.service;

import com.mmo.microservice.account.dto.AccountRequest;
import com.mmo.microservice.account.dto.AccountResponse;

public interface AccountService {

    /**
     * Add user facebook
     * @param in
     * @return
     */
    public AccountResponse createAccount(AccountRequest in);

    public String getFacebookID(String token);

}

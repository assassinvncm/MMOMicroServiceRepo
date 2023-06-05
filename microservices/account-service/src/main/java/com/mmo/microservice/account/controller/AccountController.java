package com.mmo.microservice.account.controller;

import com.mmo.microservice.account.dto.AccountRequest;
import com.mmo.microservice.account.dto.AccountResponse;
import com.mmo.microservice.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Validated @RequestBody AccountRequest dto){
        return accountService.createAccount(dto);
    }

}

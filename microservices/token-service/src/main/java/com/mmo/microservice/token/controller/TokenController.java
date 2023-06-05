package com.mmo.microservice.token.controller;

import com.mmo.microservice.token.dto.TokenReqDTO;
import com.mmo.microservice.token.dto.TokenRespDTO;
import com.mmo.microservice.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import main.java.com.mmo.microservice.token.dto.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/v1/{uid}")
    public TokenRespDTO getUserAccessTokenByUID(@PathVariable(value = "uid") Long uid){
//        logger.info("Start action listPage");
        return tokenService.getUserAccessTokenByUID(uid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/v1/pageToken")
    public String getPageAccessTokenByPageID(@RequestBody TokenReqDTO inDto){
//        logger.info("Start action listPage");
        return tokenService.getPageAccessTokenByPageID(inDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/v1/exchange")
    public String getExchangeToken(@RequestParam(name = "token") String token
            , @RequestParam(name = "grantType") String grantType
            , @RequestParam(name = "clientId") String clientId
            , @RequestParam(name = "clientSecret") String clientSecret){
//        logger.info("Start action listPage");
        return tokenService.getExchangeToken(token, grantType, clientId, clientSecret);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/v1")
    public void saveToken(@Validated @RequestBody TokenDTO dto){
        tokenService.saveToken(dto);
    }
}

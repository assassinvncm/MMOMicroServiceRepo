package com.mmo.microservice.account.service.impl;

import com.mmo.microservice.account.config.ApplicationProperties;
import com.mmo.microservice.account.dto.AccountRequest;
import com.mmo.microservice.account.dto.AccountResponse;
import com.mmo.microservice.account.dto.TokenDTO;
import com.mmo.microservice.account.event.SaveTokenEvent;
import com.mmo.microservice.account.model.Account;
import com.mmo.microservice.account.repository.AccountRepository;
import com.mmo.microservice.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final WebClient.Builder webClientBuilder;

    private final ApplicationProperties applicationProperties;

    private final AccountRepository accountRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final KafkaTemplate<String, SaveTokenEvent> kafkaTemplate;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest in) {
        Optional.ofNullable(in.getAccess_token())
                .orElseThrow(() -> new IllegalArgumentException("Access token can not null"));
        String token = webClientBuilder.build().get()
                .uri("http://token-service/api/token/v1/exchange", uriBuilder
                        -> uriBuilder.queryParam("grantType", applicationProperties.APP_PROP_EXCHANGE_GRANT_TYPE)
                        .queryParam("clientId", applicationProperties.APP_PROP_EXCHANGE_CLIENT_ID)
                        .queryParam("clientSecret", applicationProperties.APP_PROP_EXCHANGE_CLIENT_SECRET)
                        .queryParam("token", in.getAccess_token()).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String fb_id = getFacebookID(token);

        Optional.ofNullable(accountRepository.getFbUserByFBID(fb_id))
                .ifPresent(v -> {
                    throw new IllegalArgumentException("This facebook account are already!");
                });

        Account ufb = accountRepository.save(Account.builder()
                .username(in.getUsername())
                .created_date(new Date())
                .type(in.getType())
                .fb_id(fb_id)
                .build());

        log.info("Asynchorus save token");
//        webClientBuilder.build().post()
//                .uri("http://token-service/api/token/v1")
//                .body(BodyInserters.fromValue(TokenDTO.builder()
//                        .type("USER")
//                        .token(token)
//                        .created_date(new Date())
//                        .r_user_fb_id(ufb.getId())
//                        .build()))
//                .retrieve()
//                .bodyToMono(Void.class);
//        applicationEventPublisher.publishEvent(new SaveTokenEvent(this, "USER", token, new Date(), null, ufb.getId()));
        kafkaTemplate.send("createTokenTopic", SaveTokenEvent.builder()
                .type("USER")
                .token(token)
                .created_date(new Date())
                .r_user_fb_id(ufb.getId())
                .build());
        return null;
    }

    @Override
    public String getFacebookID(String token) {
        String rs = "";
        String resp = webClientBuilder.build().get()
                .uri("https://graph.facebook.com/v16.0/me", uriBuilder
                        -> uriBuilder.queryParam("fields", "id")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsresp = new JSONObject(resp);
        if(jsresp.has("id")) {
            rs = jsresp.getString("id");
        }
        return rs;
    }
}

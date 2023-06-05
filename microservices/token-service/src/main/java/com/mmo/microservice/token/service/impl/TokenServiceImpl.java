package com.mmo.microservice.token.service.impl;

import com.mmo.microservice.token.dto.TokenReqDTO;
import com.mmo.microservice.token.dto.TokenRespDTO;
import com.mmo.microservice.token.model.Token;
import com.mmo.microservice.token.repository.TokenRepo;
import com.mmo.microservice.token.service.TokenService;
import com.mmo.microservice.token.util.URLRequestConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.java.com.mmo.microservice.token.dto.TokenDTO;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final TokenRepo tokenRepo;
    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public TokenRespDTO getUserAccessTokenByUID(Long u_id) {
        return modelMapper.map(tokenRepo.getFBUserToken(u_id), TokenRespDTO.class);
    }

    @Override
    public String getPageAccessTokenByPageID(TokenReqDTO inDto) {
        String page_access_token = "";
        String rs = webClientBuilder.build().get()
                .uri(URLRequestConstant.GET_PAGE_ACCESS_TOKEN_PAGE_ID+inDto.getFb_page_id()
                        , uriBuilder -> uriBuilder.queryParam("access_token", inDto.getToken())
                                .queryParam("fields", "name,access_token").build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsPAResp = new JSONObject(rs);
        if(jsPAResp.has("access_token")) {
            page_access_token = jsPAResp.getString("access_token");
        }
        return page_access_token;
    }

    @Override
    public String getExchangeToken(String token, String grantType, String clientId, String clientSecret) {
        String exchange_access_token = "";
        String rs = webClientBuilder.build().get()
                .uri("https://graph.facebook.com/v16.0/oauth/access_token", uriBuilder
                        -> uriBuilder.queryParam("grant_type", grantType)
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("fb_exchange_token", token)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsPAResp = new JSONObject(rs);
        if(jsPAResp.has("access_token")) {
            exchange_access_token = jsPAResp.getString("access_token");
        }
        return exchange_access_token;
    }

    @Override
    public void saveToken(TokenDTO dto) {
        log.info("Start save token successfully!");
        tokenRepo.save(modelMapper.map(dto, Token.class));
        log.info("Save token successfully!");
    }
}

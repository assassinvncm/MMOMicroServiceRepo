package com.mmo.microservice.token.service;

import com.mmo.microservice.token.dto.TokenReqDTO;
import com.mmo.microservice.token.dto.TokenRespDTO;
import com.mmo.microservice.token.model.Token;
import main.java.com.mmo.microservice.token.dto.TokenDTO;

public interface TokenService {

    /**
     * Get user access token
     * @param p_id
     * @return
     */
    TokenRespDTO getUserAccessTokenByUID(Long p_id);

    /**
     * Get page access token
     * @param inDto
     * @return
     */
    String getPageAccessTokenByPageID(TokenReqDTO inDto);

    /**
     * Get long time token from facebook client access token
     * @param token
     * @param grantType
     * @param clientId
     * @param clientSecret
     * @return
     */
    String getExchangeToken(String token, String grantType, String clientId, String clientSecret);

    /**
     * Save token
     * @param dto
     */
    void saveToken(TokenDTO dto);
}

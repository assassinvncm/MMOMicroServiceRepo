package com.mmo.microservice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private long id;
    private String fb_id;
    private long r_user_id;
    private String username;
    private String access_token;
    private String type;
}

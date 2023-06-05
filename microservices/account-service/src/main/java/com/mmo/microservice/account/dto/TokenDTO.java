package com.mmo.microservice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String type;
    private String token;
    private Date valid_from;
    private Date valid_to;
    private Date created_date;
    private Long r_page_id;
    private Long r_user_fb_id;
}

package com.mmo.microservice.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRespDTO {
    private long id;
    private String type;
    private String token;
    private Date valid_from;
    private Date valid_to;
    private Date created_date;
}

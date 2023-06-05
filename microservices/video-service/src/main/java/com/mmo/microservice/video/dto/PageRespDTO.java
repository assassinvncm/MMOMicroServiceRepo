package com.mmo.microservice.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRespDTO {

    private long id;
    private String fb_page_id;
    private String page_name;
    private Long u_fb_id;
    private Date created_date;
}

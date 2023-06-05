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
public class ReelVideoDTO {
    private String video_id;
    private Long p_id;
    private String description;
    private String title;
    private String state;
    private String phase;
    private int status;
    private String source_path;
    private String vid_name;
    private Date created_date;
    private String error_des;
}

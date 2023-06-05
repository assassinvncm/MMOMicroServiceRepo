package com.mmo.microservice.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadReelsDTO {
    private String video_id;
    private Long pid;
    private Long uid;

    public UploadReelsDTO(String video_id, Long pid) {
        super();
        this.video_id = video_id;
        this.pid = pid;
    }

}

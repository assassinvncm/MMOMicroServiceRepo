package com.mmo.microservice.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadVideoDTO {
    private String video_id;
    private String description;
    private String title;
    private String path;
    private String acc_id;
}

package com.mmo.microservice.video.controller;

import com.mmo.microservice.video.config.ApplicationProperties;
import com.mmo.microservice.video.dto.DownloadPushDTO;
import com.mmo.microservice.video.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;
    private final ApplicationProperties applicationProperties;

    @RequestMapping(value = "/tiktok/v1", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> downloadTiktokVideo(@Validated @RequestBody DownloadPushDTO dto){
        return ResponseEntity.ok(downloadService.downloadByID(dto, applicationProperties.APP_PROP_EXCHANGE_SAVE_PATH, applicationProperties.APP_PROP_TIKTOK_URL, applicationProperties.APP_PROP_SNAPTIK_URL));
    }
}

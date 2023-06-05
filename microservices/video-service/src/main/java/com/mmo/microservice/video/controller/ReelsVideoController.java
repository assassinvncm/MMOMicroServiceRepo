package com.mmo.microservice.video.controller;

import com.mmo.microservice.video.dto.UploadReelsDTO;
import com.mmo.microservice.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fb/video")
@RequiredArgsConstructor
public class ReelsVideoController {

    private final VideoService videoService;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseEntity<?> listVid(@PathVariable(value = "pid") Long pid){
//        logger.info("Start action listPage");
        return ResponseEntity.ok(videoService.getVidByPageID(pid));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/publish/v1", method = RequestMethod.POST)
    public ResponseEntity<?> getStatus( @Validated @RequestBody UploadReelsDTO dto){
        return ResponseEntity.ok(videoService.publishVidByID(dto));
    }
}

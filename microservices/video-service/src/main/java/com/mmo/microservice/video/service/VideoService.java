package com.mmo.microservice.video.service;

import com.mmo.microservice.video.dto.DownloadVideoDTO;
import com.mmo.microservice.video.dto.ReelRespDTO;
import com.mmo.microservice.video.dto.ReelVideoDTO;
import com.mmo.microservice.video.dto.UploadReelsDTO;
import com.mmo.microservice.video.model.r_reel_vid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface VideoService {
    public ReelRespDTO saveDownloadVid(DownloadVideoDTO dto, Long pid);
    List<ReelRespDTO> saveLstVideoDownload(List<DownloadVideoDTO> dto, Long p_id);
    public List<ReelVideoDTO> getVidByPageID(Long pid);
    public ReelRespDTO publishVidByID(UploadReelsDTO dto);
    public r_reel_vid save(r_reel_vid i);
    String initCreateReels(String page_access_token);
    boolean uploadLocalReels(String page_access_token, String video_id, byte[] videoData) throws IOException;
    String checkUploadSessionStatus(String page_access_token, String video_id);
    boolean publishReels(String page_access_token, String video_id,String description, String tittle) throws UnsupportedEncodingException;
}

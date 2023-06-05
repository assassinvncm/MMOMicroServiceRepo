package com.mmo.microservice.video.service;

import com.mmo.microservice.video.dto.DownloadPushDTO;
import com.mmo.microservice.video.dto.ReelRespDTO;

import java.util.List;

public interface DownloadService {
    public List<ReelRespDTO> downloadByID(DownloadPushDTO dto, String save_path, String tiktok_url, String snaptik_url);
}

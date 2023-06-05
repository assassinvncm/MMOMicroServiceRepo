package com.mmo.microservice.video.service.impl;

import com.mmo.microservice.video.dto.*;
import com.mmo.microservice.video.model.r_reel_vid;
import com.mmo.microservice.video.repository.RReelVidRepo;
import com.mmo.microservice.video.service.VideoService;
import com.mmo.microservice.video.util.CacheUtils;
import com.mmo.microservice.video.util.FileUtils;
import com.mmo.microservice.video.util.URLRequestConstant;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final RReelVidRepo rReelVidRepo;
    private final WebClient.Builder webClientBuilder;
    private final ModelMapper modelMapper;

    @Override
    public ReelRespDTO saveDownloadVid(DownloadVideoDTO dto, Long pid) {
        rReelVidRepo.save(new r_reel_vid(dto.getVideo_id(), pid, dto.getDescription(), dto.getTitle(), 0, dto.getPath(), dto.getVideo_id(), new Date(), dto.getAcc_id()));
//		rs = new ReelRespDTO(200, dto.getVideo_id(), String.valueOf(true));
        return new ReelRespDTO(200, dto.getVideo_id(), String.valueOf(true));
    }

    @Override
    public List<ReelRespDTO> saveLstVideoDownload(List<DownloadVideoDTO> dto, Long p_id) {
//        logger.info("Start saveLstVideoDownload");
        List<ReelRespDTO> rs = new ArrayList<>();
        PageRespDTO page = webClientBuilder.build().get()
                .uri("http://page-service/api/page/"+p_id)
                .retrieve()
                .bodyToMono(PageRespDTO.class)
                .block();
        dto.stream().forEach(d -> {
            ReelRespDTO r = saveDownloadVid(d, p_id);
            rs.add(r);
        });

//        logger.info("End saveLstVideoDownload");
        return rs;
    }

    @Override
    public List<ReelVideoDTO> getVidByPageID(Long pid) {
        return rReelVidRepo.getVidByPageID(pid)
                .stream()
                .map(entity -> modelMapper.map(entity, ReelVideoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReelRespDTO publishVidByID(UploadReelsDTO dto) {
        ReelRespDTO rs = null;
        r_reel_vid rrv = rReelVidRepo.getById(dto.getVideo_id());
        PageRespDTO page = webClientBuilder.build().get()
                .uri("http://page-service/api/page/"+dto.getPid())
                .retrieve()
                .bodyToMono(PageRespDTO.class)
                .block();
        try {
            String token = CacheUtils.getToken(dto.getPid());
            if(token==null) {
                TokenRespDTO u = webClientBuilder.build().get()
                        .uri("http://token-service/api/token/"+page.getU_fb_id())
                        .retrieve()
                        .bodyToMono(TokenRespDTO.class)
                        .block();
                String page_access_token = webClientBuilder.build().post()
                        .uri("http://token-service/api/token/")
                        .body(BodyInserters.fromValue(TokenReqDTO.builder().token(u.getToken()).fb_page_id(page.getFb_page_id())))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
                token = page_access_token;
                CacheUtils.putToken(page.getId(), page_access_token);
            }

            if(!StringUtils.isEmpty(token)) {
                boolean isUploadLocalReels = true;
                String video_id = rrv.getUpload_id();
                video_id = initCreateReels(token);
                rrv.setUpload_id(video_id);
//				rrv = new r_reel_vid(video_id, dto.getPid(), rrv.getDescription(), rrv.getTitle(), "INIT", "start", 0, rrv.getSource_path(), rrv.getVideo_id(), new Date());
                isUploadLocalReels = uploadLocalReels(token, video_id, FileUtils.readFileFromPath(rrv.getSource_path()));
                if(isUploadLocalReels) {
                    boolean isPublishReels = publishReels(token, video_id, rrv.getDescription(), "");
                    if(isPublishReels) {
                        rrv.setState("PUBLISHED");
                        rrv.setPhase("finish");
                        rrv.setStatus(1);
                        rs = new ReelRespDTO(200, video_id, String.valueOf(isPublishReels));
                        FileUtils.deleteFileFromPath(rrv.getSource_path());
                    }else {
                        rrv.setState("UPLOAD");
                        rrv.setStatus(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            rrv.setStatus(2);
            rrv.setError_des(e.getMessage());
        }finally {
            save(rrv);
        }
//		logger.info("End uploadReelByPath");
        return rs;
    }

    @Override
    public r_reel_vid save(r_reel_vid i) {
        return rReelVidRepo.save(i);
    }

    @Override
    public String initCreateReels(String page_access_token) {
        String video_id = "";
        String initCreateReelResp = webClientBuilder.build().post()
                .uri(URLRequestConstant.POST_CREATE_PUBLISH_REEL, uriBuilder
                        -> uriBuilder.queryParam("access_token", page_access_token)
                        .queryParam("upload_phase", "start")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsInitCreateResp = new JSONObject(initCreateReelResp);
        if(jsInitCreateResp.has("video_id")) {
            video_id = jsInitCreateResp.getString("video_id");
        }
        return video_id;
    }

    @Override
    public boolean uploadLocalReels(String page_access_token, String video_id, byte[] videoData) throws IOException {
        boolean rs = false;
        // Tạo header cho request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "OAuth "+page_access_token);
        headers.add("offset", "0");
        headers.add("file_size", String.valueOf(videoData.length));
        headers.add("Content-Type", "application/octet-stream");
        String uploadLocalReelResp = webClientBuilder.build().post()
                .uri(URLRequestConstant.POST_UPLOAD_LOCAL_REEL+video_id)
                .headers(h -> headers.putAll(headers))
                .body(BodyInserters.fromValue(videoData))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsUploadLocalReelResp = new JSONObject(uploadLocalReelResp);
        if(jsUploadLocalReelResp.has("success")) {
            rs = jsUploadLocalReelResp.getBoolean("success");
        }
        return rs;
    }

    @Override
    public String checkUploadSessionStatus(String page_access_token, String video_id) {
        String getPAResp = webClientBuilder.build().get()
                .uri(URLRequestConstant.GET_UPLOAD_STATUS_REEL+video_id, uriBuilder
                        -> uriBuilder
                        .queryParam("fields", "status")
                        .queryParam("access_token", page_access_token)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return getPAResp;
    }

    @Override
    public boolean publishReels(String page_access_token, String video_id, String description, String tittle) {
        boolean rs = false;

//		// Tạo header cho request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String publishReelResp = webClientBuilder.build().post()
                .uri(URLRequestConstant.POST_CREATE_PUBLISH_REEL, uriBuilder
                        -> {
                    try {
                        return uriBuilder.queryParam("access_token", page_access_token)
                        .queryParam("video_id", video_id)
                        .queryParam("upload_phase", "finish")
                        .queryParam("video_state", "PUBLISHED")
                        .queryParam("description", description)
                        .queryParam("title", URLEncoder.encode(tittle, StandardCharsets.UTF_8.toString())).build();
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .headers(h -> headers.putAll(headers))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsPublishReelResp = new JSONObject(publishReelResp);
        if(jsPublishReelResp.has("success")) {
            rs = jsPublishReelResp.getBoolean("success");
        }
        return rs;
    }
}

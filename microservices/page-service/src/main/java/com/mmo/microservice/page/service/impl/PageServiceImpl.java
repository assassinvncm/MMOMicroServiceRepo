package com.mmo.microservice.page.service.impl;

import com.mmo.microservice.page.dto.PageRespDTO;
import com.mmo.microservice.page.dto.TokenDTO;
import com.mmo.microservice.page.model.Page;
import com.mmo.microservice.page.repository.PageRepository;
import com.mmo.microservice.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public PageRespDTO getPageByID(Long id) {
        return modelMapper.map(pageRepository.getOne(id), PageRespDTO.class);
    }

    @Override
    public void saveListPageByAccountToken(String token, Long account_id) {
        String getPAResp = webClientBuilder.build().get()
                .uri("https://graph.facebook.com/v16.0/me/accounts",uriBuilder
                        -> uriBuilder.queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsPAResp = new JSONObject(getPAResp);
        if(jsPAResp.has("data")) {
            JSONArray dataArr = jsPAResp.getJSONArray("data");
            for (int i=0; i < dataArr.length(); i++) {
                JSONObject pageATInfor = dataArr.getJSONObject(i);
                String page_name = pageATInfor.getString("name");
                String page_token = pageATInfor.getString("access_token");
                String page_id = pageATInfor.getString("id");
                Page rp = pageRepository.save(Page.builder().page_name(page_name).u_fb_id(account_id).fb_page_id(page_id).created_date(new Date()).build());

                //Call create token
                webClientBuilder.build().post()
                        .uri("http://localhost:8082/api/token/v1")
                        .body(BodyInserters.fromValue(TokenDTO.builder()
                                .type("PAGE")
                                .token(token)
                                .created_date(new Date())
                                .r_page_id(rp.getId())
                                .build()))
                        .retrieve()
                        .bodyToMono(Void.class);
            }
        }

    }
}

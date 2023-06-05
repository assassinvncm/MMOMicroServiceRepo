package com.mmo.microservice.page.service;

import com.mmo.microservice.page.dto.PageRespDTO;

public interface PageService {

    /**
     * Get page by id
     * @param id
     * @return
     */
    public PageRespDTO getPageByID(Long id);

    public void saveListPageByAccountToken(String token, Long account_id);
}

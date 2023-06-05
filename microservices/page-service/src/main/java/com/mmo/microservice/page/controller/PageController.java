package com.mmo.microservice.page.controller;

import com.mmo.microservice.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/page")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/v1/{pid}", method = RequestMethod.GET)
    public ResponseEntity<?> getPageById(@PathVariable(value = "pid") Long pid){
//        logger.info("Start action listPage");
        return ResponseEntity.ok(pageService.getPageByID(pid));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping("/v1/saveList")
    public void saveListPageByUATK(@RequestParam("access_token") String access_token, @RequestParam("account_id") Long account_id){

    }
}

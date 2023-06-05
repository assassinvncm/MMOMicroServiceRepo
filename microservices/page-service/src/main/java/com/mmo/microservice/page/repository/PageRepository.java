package com.mmo.microservice.page.repository;

import com.mmo.microservice.page.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {
    @Query("SELECT r FROM Page r  WHERE r.u_fb_id = :id")
    List<Page> getPageByUID(@Param("id") long id);

    @Modifying
    @Query("delete from Page rp where rp.u_fb_id = :id")
    void deleteTokenByUFBID(@Param("id") long id);
}

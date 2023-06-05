package com.mmo.microservice.video.repository;

import com.mmo.microservice.video.model.r_reel_vid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RReelVidRepo extends JpaRepository<r_reel_vid, String> {
    @Query("SELECT r FROM r_reel_vid r WHERE r.p_id = :pid")
    List<r_reel_vid> getVidByPageID(@Param("pid") long pid);

    @Query("SELECT r.video_id FROM r_reel_vid r WHERE r.p_id = :pid and acc_id = :acc_id")
    List<String> getVidByPageIDAndAccID(@Param("pid") long pid, @Param("acc_id") String acc_id);

    @Modifying
    @Query("UPDATE r_reel_vid rrv set status = 9 where rrv.p_id = :pid and rrv.status = 0")
    void updateToSchedule(@Param("pid") long pid);
}

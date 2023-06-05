package com.mmo.microservice.video.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "r_reel_vid")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class r_reel_vid {
    @Id
    private String video_id;
    private Long p_id;
    private String description;
    private String title;
    private String state;
    private String phase;
    private int status;
    private String source_path;
    private String vid_name;
    private Date created_date;
    private String error_des;
    private String upload_id;
    private String acc_id;

    public r_reel_vid(String video_id, Long p_id, String description, String title, int status, String source_path,
                      String vid_name, Date created_date, String acc_id) {
        super();
        this.video_id = video_id;
        this.p_id = p_id;
        this.description = description;
        this.title = title;
        this.status = status;
        this.source_path = source_path;
        this.vid_name = vid_name;
        this.created_date = created_date;
        this.acc_id = acc_id;
    }
}

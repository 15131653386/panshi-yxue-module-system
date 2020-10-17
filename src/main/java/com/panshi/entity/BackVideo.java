package com.panshi.entity;

import com.alibaba.druid.filter.AutoLoad;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackVideo {
    private String id;
    private String title;
    private String intro;
    private String coverUrl;
    private String videoUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd" )
    private Date createAt;
    private String userId;
    private String cid;
    private String grpId;
    private User user;
    private Category category;
    private Group group;
    private List<Like> li;
    private Integer like;
    private Play play;

}

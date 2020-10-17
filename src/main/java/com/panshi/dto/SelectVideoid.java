package com.panshi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectVideoid {
                private String id;
                private String videoTitle;
                private String cover;
                private String path;
                private String uploadTime;
                private String description;
                 private Integer likeCount;
                 private String cateName;
                 private String categoryId;
                 private String userId;
                 private String userPicImg;
                private String userName;
                private Integer playCount;
                private Boolean isAttention;
                private List<SelectVideo> videoList;
}

package com.panshi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yx_img")
public class Img implements Serializable {
    @TableId(type = IdType.UUID)
    private String id;
    private String imgUrl;
    private String intro;
    private Date publishTime;
    private String userId;
}

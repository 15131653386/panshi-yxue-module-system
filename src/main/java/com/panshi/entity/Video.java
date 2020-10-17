package com.panshi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
@Document(indexName = "yingx",type = "video")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yx_video")
public class Video implements Serializable {
    @TableId(type = IdType.UUID)
    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String intro;
    @Field(type = FieldType.Keyword)
    private String coverUrl;
    @Field(type = FieldType.Keyword)
    private String videoUrl;
    @Field(type = FieldType.Date)
    private Date createAt;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Keyword)
    private String cid;
    @Field(type = FieldType.Keyword)
    private String grpId;

}

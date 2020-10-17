package com.panshi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yx_user")
public class User implements Serializable {
    @TableId(type = IdType.UUID)
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "手机号")
    private String mobile;
    @Excel(name = "姓名")
    private String sign;
    @Excel(name = "头像",type = 2,width = 40,height = 40,imageType = 1,savePath = "D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\")
    private String headShow;
    @Excel(name = "账号状态",replace={"正常_t","冻结_f"})
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern ="yyyy-MM-dd" )
    @Excel(name = "创建时间",format = "yyyy-MM-dd")
    private Date regTime;
    @Excel(name = "学分")
    private Double score;
    @Excel(name = "微信")
    private String wechat;
    private String city;
    private String sex;
}

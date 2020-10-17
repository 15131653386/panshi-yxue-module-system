package com.panshi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yx_category")
public class Category implements Serializable {
    @TableId(type = IdType.UUID)
    @Excel(name = "编号")
    private String id;
    @Excel(name = "类别名称")
    private String name;
    @Excel(name = "级别")
    private String level;
    @Excel(name = "父类编号")
    private String pId;
}

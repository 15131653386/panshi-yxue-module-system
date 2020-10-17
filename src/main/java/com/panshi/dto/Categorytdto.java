package com.panshi.dto;

import com.panshi.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorytdto implements Serializable {
    private String id;
    private String cateName;
    private String levels;
    private String parentId;
    private List<Categorytdto> categoryList;
}

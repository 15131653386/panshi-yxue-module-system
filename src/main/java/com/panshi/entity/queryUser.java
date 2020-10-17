package com.panshi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class queryUser {
    private String month;
    private Integer boys;
    private Integer girls;
}

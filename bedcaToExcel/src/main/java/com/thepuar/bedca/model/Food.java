package com.thepuar.bedca.model;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.List;

@Data
public class Food {

    private String f_id;
    private String f_ori_name;
    private List<Valores> valores;
}

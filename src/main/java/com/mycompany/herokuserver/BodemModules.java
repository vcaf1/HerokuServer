/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

/**
 *
 * @author Victoria
 */
import java.util.ArrayList;
import java.util.List;

public class BodemModules {
    private List<BodemModule> bodemModuleList;

    public List<BodemModule> getBodemModuleList() {
        if(bodemModuleList == null) {
            bodemModuleList = new ArrayList<>();
        }
        return bodemModuleList;
    }

    public void setBodemModuleList(List<BodemModule> bodemModuleList) {
        this.bodemModuleList = bodemModuleList;
    }
}

package com.mycompany.herokuserver;

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
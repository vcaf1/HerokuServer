/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victoria
 */
public class LuchtModules {
    private List<LuchtModule> luchtModuleList;
     
    public List<LuchtModule> getLuchtModuleList() {
        if(luchtModuleList == null) {
            luchtModuleList = new ArrayList<>();
        }
        return luchtModuleList;
    }
  
    public void setLuchtModuleList(List<LuchtModule> luchtModuleList) {
        this.luchtModuleList = luchtModuleList;
    }
}

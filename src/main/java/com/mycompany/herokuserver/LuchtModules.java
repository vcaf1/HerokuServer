package com.mycompany.herokuserver;

import java.util.ArrayList;
import java.util.List;

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

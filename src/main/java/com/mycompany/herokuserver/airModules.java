package com.mycompany.herokuserver;

import java.util.ArrayList;
import java.util.List;

public class airModules {
    private List<airModule> airModuleList;
     
    public List<airModule> getAirModuleList() {
        if(airModuleList == null) {
            airModuleList = new ArrayList<>();
        }
        return airModuleList;
    }
  
    public void setAirModuleList(List<airModule> airModuleList) {
        this.airModuleList = airModuleList;
    }
}


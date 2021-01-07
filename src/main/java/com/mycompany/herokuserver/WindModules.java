package com.mycompany.herokuserver;

import java.util.ArrayList;
import java.util.List;

public class WindModules {
    private List<WindModule> windModuleList;
     
    public List<WindModule> getWindModuleList() {
        if(windModuleList == null) {
            windModuleList = new ArrayList<>();
        }
        return windModuleList;
    }
  
    public void setWindModuleList(List<WindModule> windModuleList) {
        this.windModuleList = windModuleList;
    }
}
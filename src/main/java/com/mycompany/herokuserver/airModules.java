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


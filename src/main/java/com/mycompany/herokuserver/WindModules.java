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
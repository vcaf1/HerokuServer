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
public class GasModules {
   private List<GasModule> gasModuleList;
     
    public List<GasModule> getGasModuleList() {
        if(gasModuleList == null) {
            gasModuleList = new ArrayList<>();
        }
        return gasModuleList;
    }
  
    public void setGastModuleList(List<GasModule> gasModuleList) {
        this.gasModuleList = gasModuleList;
    }
}

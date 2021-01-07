package com.mycompany.herokuserver;

import java.util.ArrayList;
import java.util.List;

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

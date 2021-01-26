package com.mycompany.herokuserver;

import java.util.Objects;

public class GasModule extends Modules{
    private  int ppmWaarde;
    private String moduleNaam,  moduleDatum;

  GasModule() {}

  GasModule(int ppmWaarde) {
        this.ppmWaarde = ppmWaarde;
  }
    public GasModule(int id, int ppmWaarde) {
        super();
        this.id = id;
        this.ppmWaarde = ppmWaarde;
    }
    public GasModule(int id, String moduleNaam,int ppmWaarde) {
        super();
        this.id = id;
        this.moduleNaam = moduleNaam;
        this.ppmWaarde = ppmWaarde;
    }
    public GasModule(int id, String moduleNaam,int ppmWaarde, String moduleDatum) {
        super();
        this.id = id;
        this.moduleNaam = moduleNaam;
        this.ppmWaarde = ppmWaarde;
        this.moduleDatum = moduleDatum;
    }

    public String getModuleNaam() {
        return moduleNaam;
    }

    public int getPpmWaarde() {
        return ppmWaarde;
    }

    public void setPpmWaarde(int ppmWaarde) {
        this.ppmWaarde = ppmWaarde;
    }

    public String getModuleDatum() {
        return moduleDatum;
    }

    public void setModuleDatum(String moduleDatum) {
        this.moduleDatum = moduleDatum;
    }

    public void setModuleNaam(String moduleNaam) {
        this.moduleNaam = moduleNaam;
    }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

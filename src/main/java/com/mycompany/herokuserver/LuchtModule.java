package com.mycompany.herokuserver;

import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
 

class LuchtModule extends Modules{

  private  int valueTem,valueHum;
    private String moduleNaam;

  LuchtModule() {}

  LuchtModule(int valueTem, int valueHum) {
        this.valueTem = valueTem;
        this.valueHum = valueHum;
  }
    public LuchtModule(int id, int valueTem, int valueHum) {
        super();
        this.id = id;
        this.valueTem = valueTem;
        this.valueHum = valueHum;
    }
    public LuchtModule(int id, String moduleNaam, int valueTem, int valueHum) {
        super();
        this.id = id;
        this.moduleNaam = moduleNaam;
        this.valueTem = valueTem;
        this.valueHum = valueHum;
    }

    public String getModuleNaam() {
        return moduleNaam;
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


    public int getValueTem() {
        return valueTem;
    }

    public void setValueTem(int valueTem) {
        this.valueTem = valueTem;
    }

    public int getValueHum() {
        return valueHum;
    }

    public void setValueHum(int valueHum) {
        this.valueHum = valueHum;
    }
}
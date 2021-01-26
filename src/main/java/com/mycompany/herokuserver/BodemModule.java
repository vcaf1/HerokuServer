package com.mycompany.herokuserver;
import java.util.Objects;

public class BodemModule extends Modules {

    private  int valueSoil;

    BodemModule() {}

    BodemModule(int valueSoil) {
        this.valueSoil = valueSoil;

    }
    public BodemModule(int id, int valueSoil) {
        super();
        this.id = id;
        this.valueSoil = valueSoil;

    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getValueSoil(){return valueSoil;}
    public void setValueSoil(int valueSoil){this.valueSoil = valueSoil;}

}

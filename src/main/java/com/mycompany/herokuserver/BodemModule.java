/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;
import java.util.Objects;
/**
 *
 * @author Victoria
 */
public class BodemModule extends Modules {

    private  double valueSoil;

    BodemModule() {}

    BodemModule(double valueSoil) {
        this.valueSoil = valueSoil;

    }
    public BodemModule(int id, double valueSoil) {
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
    public double getValueSoil(){return valueSoil;}
    public void setValueSoil(double valueSoil){this.valueSoil = valueSoil;}



    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof BodemModule))
            return false;
        BodemModule bodemModule = (BodemModule) o;
        return Objects.equals(this.id, bodemModule.id) && Objects.equals(this.valueSoil, bodemModule.valueSoil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.valueSoil);
    }

    @Override
    public String toString() {
        return "BodemModule{" + "id=" + this.id + ", Soilmoisture ='" + this.valueSoil  + '}';
    }
}

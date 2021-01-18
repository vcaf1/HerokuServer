package com.mycompany.herokuserver;
import java.util.Objects;

public class BodemModule extends Modules {

    private  int valueSoil;

    BodemModule() {}

    BodemModule(int valueSoil) {
        this.valueSoil = valueSoil;

    }

    public int getValueSoil(){return valueSoil;}
    public void setValueSoil(int valueSoil){this.valueSoil = valueSoil;}



    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof BodemModule))
            return false;
        BodemModule bodemModule = (BodemModule) o;
        return Objects.equals(this.valueSoil, bodemModule.valueSoil);
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.valueSoil);
    }

    @Override
    public String toString() {
        return "BodemModule{"  + " Soilmoisture ='" + this.valueSoil  + '}';
    }
}

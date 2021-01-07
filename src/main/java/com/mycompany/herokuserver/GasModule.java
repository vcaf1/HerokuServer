package com.mycompany.herokuserver;

import java.util.Objects;

public class GasModule extends Modules{
    private  int waarde;

  GasModule() {}

  GasModule(int waarde) {
        this.waarde = waarde;
  }
    public GasModule(int id, int waarde) {
        super();
        this.id = id;
        this.waarde = waarde;
    }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

    public int getWaarde() {
        return waarde;
    }

    public void setWaarde(int waarde) {
        this.waarde = waarde;
    }

    @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof GasModule))
      return false;
    GasModule gasmodule = (GasModule) o;
    return Objects.equals(this.id, gasmodule.id) && Objects.equals(this.waarde, gasmodule.waarde);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.waarde);
  }

  @Override
  public String toString() {
    return "WindModule{" + "id=" + this.id + ", GasWaarde='" + this.waarde + '\'' + '}';
  }
}

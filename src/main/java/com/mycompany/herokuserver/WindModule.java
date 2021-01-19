package com.mycompany.herokuserver;

import java.util.Objects;

public class WindModule extends Modules{
    
  private  int valueWindR,valueWindS;
  private String dbValueWindRS, dbID;
  
    WindModule(){
        
    }

  WindModule(int valueWindR, int valueWindS) {
        this.valueWindR = valueWindR;
        this.valueWindS = valueWindS;
  }
  
    public WindModule(int id, int valueWindR, int valueWindS) {
        super();
        this.id = id;
        this.valueWindR = valueWindR;
        this.valueWindS = valueWindS;
    }
  
    public WindModule(String dbID, String dbValueWindRS) {
        super();
        this.dbID = dbID;
        this.dbValueWindRS = dbValueWindRS;
    }

    public String getDbID() {
        return dbID;
    }

    public void setDbID(String dbID) {
        this.dbID = dbID;
    }

    public String getDbValueWindRS() {
        return dbValueWindRS;
    }

    public void setDbValueWindRS(String dbValueWindRS) {
        this.dbValueWindRS = dbValueWindRS;
    }

    public int getValueWindR() {
        return valueWindR;
    }

    public void setValueWindR(int valueWindR) {
        this.valueWindR = valueWindR;
    }

    public int getValueWindS() {
        return valueWindS;
    }

    public void setValueWindS(int valueWindS) {
        this.valueWindS = valueWindS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof WindModule))
      return false;
    WindModule windmodule = (WindModule) o;
    return Objects.equals(this.id, windmodule.id) && Objects.equals(this.valueWindR, windmodule.valueWindS)
        && Objects.equals(this.valueWindR, windmodule.valueWindS);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.valueWindR, this.valueWindS);
  }

  @Override
  public String toString() {
    return "WindModule{" + "id=" + this.id + ", WindRichting='" + this.valueWindR + '\'' + ", WindSnelheid='" + this.valueWindS + '\'' + '}';
  }
}

package com.mycompany.herokuserver;

public class airModule extends Modules {

    private String airdetails;

    public airModule (){
        
    }

    public airModule( int id, String airdetails) {
        super();
        this.id = id;
        this.airdetails = airdetails;
    }

//    public airModule(String airmoduleID, String airdetails) {
//        this.airmoduleID = airmoduleID;
//        this.airdetails = airdetails;
//    }

    public String getAirdetails() {
        return airdetails;
    }

    public void setAirdetails(String airdetails) {
        this.airdetails = airdetails;
    }
//
//    public String getAirmoduleID() {
//        return airmoduleID;
//    }
//
//    public void setAirmoduleID(String airmoduleID) {
//        this.airmoduleID = airmoduleID;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof airModule)) {
//            return false;
//        }
//        airModule aimodule = (airModule) o;
//        return Objects.equals(this.id, aimodule.id) && Objects.equals(this.airdetails, aimodule.airdetails);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(this.id, this.airdetails);
//    }
//
//    @Override
//    public String toString() {
//        return "airModule{" + "id=" + this.id + ", airDetails='" + this.airdetails + '\'' + '}';
//    }
}

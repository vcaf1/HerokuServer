/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

/**
 *
 * @author Victoria
 */
public class airModule extends Module {

    private String airdetails;

    public airModule (){
        
    }

    public airModule( String id, String airdetails) {
        super(id);
        this.airdetails = airdetails;
    }

//    public airModule(String airmoduleID, String airdetails) {
//        this.airmoduleID = airmoduleID;
//        this.airdetails = airdetails;
//    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

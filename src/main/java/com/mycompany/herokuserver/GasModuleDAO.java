/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Victoria
 */
public class GasModuleDAO {

    private static GasModules list = new GasModules();

    static {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from gasmodules");
            while (result.next()) {
                GasModule dbgasmodule = new GasModule(result.getInt("Gmod_id"),result.getInt("waarde"));
                list.getGasModuleList().add(dbgasmodule);
            }
            System.out.println("Added the data from the ElephantSQL database from the Gasmodule");
            }catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
     
    

    public GasModules getAllGasModules() {
        return list;
    }

    public void addLuchtModule(GasModule gasModule) {
        list.getGasModuleList().add(gasModule);
    }
}

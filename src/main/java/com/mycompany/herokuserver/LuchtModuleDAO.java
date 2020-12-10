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
import org.springframework.stereotype.Repository;

/**
 *
 * @author Victoria
 */
@Repository
public class LuchtModuleDAO {

    private static LuchtModules list = new LuchtModules();

    static {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from luchtmodules");
            while (result.next()) {
                LuchtModule dbluchtmodule = new LuchtModule(result.getInt("lumod_id"),result.getInt("temperatuur"), result.getInt("vochtigheid"));
                list.getLuchtModuleList().add(dbluchtmodule);
            }
            System.out.println("Added the data from the ElephantSQL databse from the Luchtmodule");
            }catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
     
    

    public LuchtModules getAllLuchtModules() {
        return list;
    }

    public void addLuchtModule(LuchtModule luchtModule) {
        list.getLuchtModuleList().add(luchtModule);
    }
}

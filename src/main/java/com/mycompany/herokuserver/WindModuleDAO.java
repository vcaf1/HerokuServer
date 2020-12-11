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
public class WindModuleDAO {
    
/*the Result statement for WindModule for the database
    Fix the atributes for the windmodule so that the data can be set
    in the ServerController have to fix the Wind attributes and wind databse so the right infor is shown 
    for the windmodule*/
    private static WindModules list = new WindModules();

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
                WindModule dbwindmodule = new WindModule();
                list.getWindModuleList().add(dbwindmodule);
            }
            System.out.println("Added the data from the ElephantSQL database from the Windmodule");
            }catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
     
    

    public WindModules getAllWindModules() {
        return list;
    }

    public void addWindModule(WindModule windModule) {
        list.getWindModuleList().add(windModule);
    }
}


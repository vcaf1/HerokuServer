package com.mycompany.herokuserver;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BodemDAO {

    private static BodemModules list = new BodemModules();

    static {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from bodemmodules");
            while (result.next()) {
                BodemModule dbBodemmodule = new BodemModule(result.getInt("soil_id"),result.getDouble("soilValue"));
                list.getBodemModuleList().add(dbBodemmodule);
            }
            System.out.println("Added the data from the ElephantSQL database from the Luchtmodule");
        }catch (SQLException se) {
            System.out.println(se.getMessage());
        }
    }



    public BodemModules getAllBodemModules() {
        return list;
    }

    public void addBodemModule(BodemModule bodemModule) {
        list.getBodemModuleList().add(bodemModule);
    }
}

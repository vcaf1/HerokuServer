/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/module")
/**
 *
 * @author Victoria
 */
public class ServerController {

    @Autowired
    private LuchtModuleDAO luchtModuleDao;
    private WindModuleDAO windModuleDao;
    private ArrayList<LuchtModule> luchtlijst = new ArrayList<>();
    private ArrayList<WindModule> windlijst = new ArrayList<>();
    private LuchtModules luchtlist = new LuchtModules();
    private WindModules windlist = new WindModules();

    //private DataSource dataSource;
    private int counter = 0;

    @GetMapping(path = "/lucht", produces = "application/json")
    public LuchtModules getLuchtModules() {

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
                LuchtModule dbluchtmodule = new LuchtModule(result.getInt("lumod_id"), result.getInt("temperatuur"), result.getInt("vochtigheid"));
                luchtlist.getLuchtModuleList().add(dbluchtmodule);
            }
            System.out.println("Added the data from the ElephantSQL databse from the Luchtmodule");
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }

        return luchtlist;
        //return luchtModuleDao.getAllLuchtModules();
    }

    /*
    @GetMapping(path = "/wind", produces = "application/json")
    public WindModules getWindModules() {

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
                WindModule dbwindmodule = new WindModule(result.getInt("lumod_id"), result.getInt("temperatuur"), result.getInt("vochtigheid"));
                windlist.getWindModuleList().add(dbwindmodule);
            }
            System.out.println("Added the data from the ElephantSQL databse from the Windmodule");
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }

        return windlist;
        //return windModuleDao.getAllWindModules();
    }
    */

    @PostMapping(path = "/kpn/luchtmodule", produces = "application/json")
    public LuchtModules addKPNLuchtModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the LuchtModule");
        System.out.println(json);
        LuchtModule Lumodule = new LuchtModule();
        try {

            int Payloadplace = json.indexOf("vs");
            int StartofPayload = Payloadplace + 7;
            int EndOfPayload = StartofPayload + 2;
            String HumidityHex = json.substring(StartofPayload, EndOfPayload);
            System.out.println(HumidityHex);
            int StartofPayloadTeam = EndOfPayload + 2;
            int EndOfPayloadTem = StartofPayloadTeam + 2;
            String TemperatuurHex = json.substring(StartofPayloadTeam, EndOfPayloadTem);
            System.out.println(TemperatuurHex);

            Integer HumidityDec = Integer.parseInt(HumidityHex, 16);
            Integer TemperatuurDec = Integer.parseInt(TemperatuurHex, 16);

            Lumodule.setValueHum(HumidityDec);
            Lumodule.setValueTem(TemperatuurDec);
            luchtlijst.add(Lumodule);

            LuchtModule dbluchtmodule = new LuchtModule(HumidityDec, TemperatuurDec);
            try {
                Class.forName("org.postgresql.Driver");
            } catch (java.lang.ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            try {
                /*
                String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/kdftqapz";
                String username = "kdftqapz";
                String password = "mjF8vF1uOBKwJjPfb3h_eyzGnpQLFkg4";
                Connection con = DriverManager.getConnection(url,username,password);
                 */
                String insertStatement = "insert into luchtmodules (temperatuur,vochtigheid) values('" + dbluchtmodule.getValueTem() + "','" + dbluchtmodule.getValueHum() + "')";
                int result = stat.executeUpdate(insertStatement);

            } catch (SQLException se) {
                System.out.println(se.getMessage());
            } finally {
                //It's important to close the statement when you are done with it
                stat.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Couldn't find the vs atribute");

        }
        int moduleHumidity = luchtlijst.get(counter).getValueHum();
        int moduleTemperatuur = luchtlijst.get(counter).getValueTem();

        System.out.println("Humidity: " + moduleHumidity);
        System.out.println("Temperatuur: " + moduleTemperatuur);
        counter++;
        return luchtModuleDao.getAllLuchtModules();

    }
/*
    @PostMapping(path = "/kpn/windmodule", produces = "application/json")
    public WindModules addKPNWindModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the WindModule");
        System.out.println(json);
        WindModule Wmodule = new WindModule();
        try {

            int Payloadplace = json.indexOf("vs");
            int StartofPayload = Payloadplace + 7;
            int EndOfPayload = StartofPayload + 2;
            String HumidityHex = json.substring(StartofPayload, EndOfPayload);
            System.out.println(HumidityHex);
            int StartofPayloadTeam = EndOfPayload + 2;
            int EndOfPayloadTem = StartofPayloadTeam + 2;
            String TemperatuurHex = json.substring(StartofPayloadTeam, EndOfPayloadTem);
            System.out.println(TemperatuurHex);

            Integer HumidityDec = Integer.parseInt(HumidityHex, 16);
            Integer TemperatuurDec = Integer.parseInt(TemperatuurHex, 16);

            Wmodule.setValueHum(HumidityDec);
            Wmodule.setValueTem(TemperatuurDec);
            windlijst.add(Wmodule);

            WindModule dbwindmodule = new WindModule();
            try {
                Class.forName("org.postgresql.Driver");
            } catch (java.lang.ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            try {
                /*
                String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/kdftqapz";
                String username = "kdftqapz";
                String password = "mjF8vF1uOBKwJjPfb3h_eyzGnpQLFkg4";
                Connection con = DriverManager.getConnection(url,username,password);
                Connection con = DBCPDataSource.getConnection();
                Statement stat = con.createStatement();
                String insertStatement = "insert into luchtmodules (temperatuur,vochtigheid) values('" + dbwindmodule.getValueTem() + "','" + dbwindmodule.getValueHum() + "')";
                int result = stat.executeUpdate(insertStatement);

            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Couldn't find the vs atribute");

        }
        int moduleHumidity = windlijst.get(counter).getValueHum();
        int moduleTemperatuur = windlijst.get(counter).getValueTem();

        System.out.println("Humidity: " + moduleHumidity);
        System.out.println("Temperatuur: " + moduleTemperatuur);
        counter++;
        return windModuleDao.getAllWindModules();

    }
    */
    
    
    
    
    
    
    
    
    
    
    /*
     @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }
     */

}

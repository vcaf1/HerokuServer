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
    private Statement stat;
    private DBCPDataSource db;

    //private DataSource dataSource;
    private int luchtcounter = 0;
    private int windcounter = 0;

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
            con.close();
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
    public String addKPNLuchtModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the LuchtModule");
        System.out.println(json);
        LuchtModule Lumodule = new LuchtModule();
        try {

            int Payloadplace = json.indexOf("vs");
            int StartofPayloadHum = Payloadplace + 7;
            int EndOfPayloadHum = StartofPayloadHum + 2;
            String HumidityHex = json.substring(StartofPayloadHum, EndOfPayloadHum);
            System.out.println(HumidityHex);
            int StartofPayloadTem = EndOfPayloadHum + 2;
            int EndOfPayloadTem = StartofPayloadTem + 2;
            String TemperatuurHex = json.substring(StartofPayloadTem, EndOfPayloadTem);
            System.out.println(TemperatuurHex);
            int StartofPayloadId = EndOfPayloadTem;
            int EndOfPayloadTId = StartofPayloadId + 2;
            String IdHex = json.substring(StartofPayloadId, EndOfPayloadTId);
            System.out.println(IdHex);

            Integer HumidityDec = Integer.parseInt(HumidityHex, 16);
            Integer TemperatuurDec = Integer.parseInt(TemperatuurHex, 16);
            Integer IdDec = Integer.parseInt(IdHex, 16);

            Lumodule.setId(IdDec);
            Lumodule.setValueHum(HumidityDec);
            Lumodule.setValueTem(TemperatuurDec);
            luchtlijst.add(Lumodule);

            LuchtModule dbluchtmodule = new LuchtModule(IdDec, HumidityDec, TemperatuurDec);
            try {
                Class.forName("org.postgresql.Driver");
            } catch (java.lang.ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            /*
                String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/kdftqapz";
                String username = "kdftqapz";
                String password = "mjF8vF1uOBKwJjPfb3h_eyzGnpQLFkg4";
                Connection con = DriverManager.getConnection(url,username,password);
             */
            //String insertStatement = "insert into smartfarm.airdata (temperatuur,vochtigheid) values('" + dbluchtmodule.getValueTem() + "','" + dbluchtmodule.getValueHum() + "')";
            String insertStatementTemperatuur = "insert into smartfarm.airmodule (air_id,air_details) values('Lu" + dbluchtmodule.getId()+ "T','" + dbluchtmodule.getValueTem()+ "')";
            String insertStatementVochtigheid = "insert into smartfarm.airmodule (air_id,air_details) values('Lu" + dbluchtmodule.getId()+ "V','" + dbluchtmodule.getValueHum()+ "')";
            int resultT = stat.executeUpdate(insertStatementTemperatuur);
            int resultV = stat.executeUpdate(insertStatementVochtigheid);

            int moduleHumidity = luchtlijst.get(luchtcounter).getValueHum();
            int moduleTemperatuur = luchtlijst.get(luchtcounter).getValueTem();
            int moduleId = luchtlijst.get(luchtcounter).getId();

            System.out.println("Humidity: " + moduleHumidity);
            System.out.println("Temperatuur: " + moduleTemperatuur);
            System.out.println("LuchtModuleId: " + moduleId);
            luchtcounter++;
            con.close();
            return "Data has been sent";
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Couldn't find the vs atribute");
            return "Data has not been sent";
//        } finally {
//            //It's important to close the statement when you are done with it
//            try {
//                stat.close();
//            } catch (SQLException se) {
//                //do something
//                System.out.println(se.getMessage());
//                System.out.println("Something went wrong performing the finally block");
//
//            }
        }

    }
    
    @PostMapping(path = "/kpn/windmodule", produces = "application/json")
    public String addKPNWindModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the WindModule");
        System.out.println(json);
        WindModule Wmodule = new WindModule();
        try {

            int Payloadplace = json.indexOf("vs");
            int StartofPayloadWindR = Payloadplace + 7;
            int EndOfPayloadWindR = StartofPayloadWindR + 2;
            String WindRHex = json.substring(StartofPayloadWindR, EndOfPayloadWindR);
            System.out.println(WindRHex);
            int StartofPayloadWindS = EndOfPayloadWindR + 2;
            int EndOfPayloadWindS = StartofPayloadWindS + 2;
            String WindSHex = json.substring(StartofPayloadWindS, EndOfPayloadWindS);
            System.out.println(WindSHex);
            int StartofPayloadId = EndOfPayloadWindS;
            int EndOfPayloadTId = StartofPayloadId + 2;
            String IdHex = json.substring(StartofPayloadId, EndOfPayloadTId);
            System.out.println(IdHex);

            Integer WindRDec = Integer.parseInt(WindRHex, 16);
            Integer WindSDec = Integer.parseInt(WindSHex, 16);
            Integer IdDec = Integer.parseInt(IdHex, 16);

            Wmodule.setValueWindR(WindRDec);
            Wmodule.setValueWindS(WindSDec);
            Wmodule.setId(IdDec);
            windlijst.add(Wmodule);

            WindModule dbwindmodule = new WindModule(IdDec,WindRDec,WindSDec);
            try {
                Class.forName("org.postgresql.Driver");
            } catch (java.lang.ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            /*
                String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/kdftqapz";
                String username = "kdftqapz";
                String password = "mjF8vF1uOBKwJjPfb3h_eyzGnpQLFkg4";
                Connection con = DriverManager.getConnection(url,username,password);
             */
            //String insertStatement = "insert into windmodules (windrichting,windsnelheid) values('" + dbwindmodule.getValueWindR()+ "','" + dbwindmodule.getValueWindS()+ "')";
            String insertStatementWindRichting = "insert into smartfarm.windmodule (wind_id,wind_details) values('W" + dbwindmodule.getId()+ "R','" + dbwindmodule.getValueWindR()+ "')";
            String insertStatementWindSnelheid = "insert into smartfarm.windmodule (wind_id,wind_details) values('W" + dbwindmodule.getId()+ "S','" + dbwindmodule.getValueWindS()+ "')";
            int resultR = stat.executeUpdate(insertStatementWindRichting);
            int resultS = stat.executeUpdate(insertStatementWindSnelheid);

            int moduleWindR = windlijst.get(windcounter).getValueWindR();
            int moduleWindS = windlijst.get(windcounter).getValueWindS();
            int moduleId = windlijst.get(windcounter).getId();

            System.out.println("Windrichting: " + moduleWindR);
            System.out.println("Windsnelheid: " + moduleWindS);
            System.out.println("WindModuleId: " + moduleId);
            windcounter++;
            con.close();
            return "Data has been sent";
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Couldn't find the vs atribute");
            return "Data has not been sent";
//        } finally {
//            //It's important to close the statement when you are done with it
//            try {
//                stat.close();
//            } catch (SQLException se) {
//                //do something
//                System.out.println(se.getMessage());
//                System.out.println("Something went wrong performing the finally block");
//
//            }
        }

    }

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

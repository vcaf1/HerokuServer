/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
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
@RequestMapping(path = "/lucht")
/**
 *
 * @author Victoria
 */
public class ServerController {

    @Autowired
    private LuchtModuleDAO luchtModuleDao;
    private ArrayList<LuchtModule> lijst = new ArrayList<>();

    //private DataSource dataSource;
    private int counter = 0;

    @GetMapping(path = "/", produces = "application/json")
    public LuchtModules getLuchtModules() {
        return luchtModuleDao.getAllLuchtModules();
    }

    @PostMapping("/start")
    public String create() {
        return "start";
    }

    @PostMapping(path = "/module", produces = "application/json")
    public LuchtModules addModule(@RequestBody LuchtModule module) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Saving person information");
        //Generate resource id
        Integer id = luchtModuleDao.getAllLuchtModules().getLuchtModuleList().size() + 1;
        module.setId(id);

        //add resource
        luchtModuleDao.addLuchtModule(module);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(module.getId())
                .toUri();
        System.out.println(luchtModuleDao.getAllLuchtModules().toString());
        return luchtModuleDao.getAllLuchtModules();

    }

    @PostMapping(path = "/kpn", produces = "application/json")
    public LuchtModules addKPN(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN");
        System.out.println(json);
        LuchtModule module = new LuchtModule();
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

            module.setValueHum(HumidityDec);
            module.setValueTem(TemperatuurDec);
            lijst.add(module);

            LuchtModule dbluchtmodule = new LuchtModule(counter, HumidityDec, TemperatuurDec);
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
                 */
                Connection con = DBCPDataSource.getConnection();
                Statement stat = con.createStatement();
                String insertStatement = "insert into luchtmodules (temperatuur,vochtigheid) values('" + dbluchtmodule.getValueTem() + "','" + dbluchtmodule.getValueHum() + "')";
                int result = stat.executeUpdate(insertStatement);

            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Couldn't find the vs atribute");

        }
        int moduleHumidity = lijst.get(counter).getValueHum();
        int moduleTemperatuur = lijst.get(counter).getValueTem();

        System.out.println("Humidity: " + moduleHumidity);
        System.out.println("Temperatuur: " + moduleTemperatuur);
        counter++;
        return luchtModuleDao.getAllLuchtModules();

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

package com.mycompany.herokuserver;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.json.simple.JSONObject;

@RestController
@RequestMapping(path = "/module")

public class ServerController {

    @Autowired
    private LuchtModuleDAO luchtModuleDao;
    private WindModuleDAO windModuleDao;
    private GasModuleDAO gasModuleDao;
    private BodemDAO bodemModuleDao;
    private ArrayList<LuchtModule> luchtlijst = new ArrayList<>();
    private ArrayList<airModule> airlijst = new ArrayList<>();
    private ArrayList<WindModule> windlijst = new ArrayList<>();
    private ArrayList<GasModule> gaslijst = new ArrayList<>();
    private ArrayList<BodemModule> bodemlijst = new ArrayList<>();
    private List<airModule> airModuleList;
    private airModules airlist = new airModules();
    private LuchtModules luchtlist = new LuchtModules();
    private WindModules windlist = new WindModules();
    private GasModules gaslist = new GasModules();
    private BodemModules bodemList = new BodemModules();
    private Statement stat;
    private DBCPDataSource db;

    //private DataSource dataSource;
    private int luchtcounter = 0;
    private int aircounter = 0;
    private int windcounter = 0;
    private int gascounter = 0;
    private int bodemcounter = 0;

    @GetMapping(path = "/lucht", produces = "application/json")
    public JSONObject getAirModules() {
    JSONObject jo = new JSONObject();
    Collection<JSONObject> items = new ArrayList<JSONObject>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from smartfarm.airmodule");
            while (result.next()) {
                airModule dbairmodule = new airModule(result.getString("air_id"),result.getString("air_details")); 
                JSONObject item1 = new JSONObject();
                item1.put("id", dbairmodule.getId());
                item1.put("air_details", dbairmodule.getAirdetails());
                items.add(item1);
                jo.put("aoColumnDefs",items);
    System.out.println(jo.toString());
                airlist.getAirModuleList().add(dbairmodule);
//                airModuleList.add(dbairmodule);
                airlijst.add(dbairmodule);
            }
            System.out.println("Added the data from the ElephantSQL databse from the AirModule");
            con.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
    System.out.println(jo.toString());

        System.out.println(airlijst);
        System.out.println(airlist);
        System.out.println(airModuleList);
        return jo;
        //return luchtModuleDao.getAllLuchtModules();
    }
    
    @PostMapping(path = "/kpn/airmodule", produces = "application/json")
    public String addKPNAirModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the AirModule");
        System.out.println(json);
        airModule Airmodule = new airModule();
        LuchtModule Lumodule = new LuchtModule();
        try {
            int Payloadplace = json.indexOf("vs");
            int StartofPayloadHum = Payloadplace + 8;
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
            
            String IdDecStr = "Lu"+IdDec.toString();
            String AirDetailsDecStr = HumidityDec.toString()+ TemperatuurDec.toString();

            Airmodule.setId(IdDecStr);
            Airmodule.setAirdetails(AirDetailsDecStr);
            airlijst.add(Airmodule);

            airModule dbairmodule = new airModule(IdDecStr, AirDetailsDecStr);
            luchtModuleDao.addLuchtModule(Lumodule);
            
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
            String insertStatementAirMod = "insert into smartfarm.airmodule (air_id,air_details) values('" + dbairmodule.getId()+ "','" + dbairmodule.getAirdetails()+ "')";
            int result = stat.executeUpdate(insertStatementAirMod);

            String moduleAirdetails = airlijst.get(aircounter).getAirdetails();
            String moduleId = airlijst.get(aircounter).getId();

            System.out.println("Air details: " + moduleAirdetails);
            System.out.println("AirModuleId: " + moduleId);
            aircounter++;
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
    
    @PostMapping(path = "/kpn/gasmodule", produces = "application/json")
    public String addKPNGasModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the GasModule");
        System.out.println(json);
        GasModule Gmodule = new GasModule();
        try {

            int Payloadplace = json.indexOf("vs");
            int StartofPayloadGasWaarde = Payloadplace + 5;
            int EndOfPayloadGasWaarde = StartofPayloadGasWaarde + 4;
            String GasWaardeHex = json.substring(StartofPayloadGasWaarde, EndOfPayloadGasWaarde);
            System.out.println(GasWaardeHex);
            int StartofPayloadId = EndOfPayloadGasWaarde;
            int EndOfPayloadTId = StartofPayloadId + 2;
            String IdHex = json.substring(StartofPayloadId, EndOfPayloadTId);
            System.out.println(IdHex);

            Integer GasWaardeDec = Integer.parseInt(GasWaardeHex, 16);
            Integer IdDec = Integer.parseInt(IdHex, 16);
            
            Gmodule.setWaarde(GasWaardeDec);
            Gmodule.setId(IdDec);
            gaslijst.add(Gmodule);

            GasModule dbgasmodule = new GasModule(IdDec,GasWaardeDec);
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

            // Huidige datum van het systeem gebruiken om naar de database door sturen als Timestamp
            Date datum = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
            String formattedDatum = formatter.format(datum);

            String insertStatementGasWaarde = "INSERT INTO \"smartfarm\".\"gasmodule\" (module_naam, module_waarde, module_timestamp) VALUES ('mq135', " + dbgasmodule.getWaarde() + ", '" + formattedDatum + "'" + ");";

            //String insertStatement = "insert into windmodules (windrichting,windsnelheid) values('" + dbwindmodule.getValueWindR()+ "','" + dbwindmodule.getValueWindS()+ "')";
            //String insertStatementGasWaarde = "insert into smartfarm.gasdata (gas_id,gas_details) values('mq" + dbgasmodule.getId()+ "','" + dbgasmodule.getWaarde()+ "')";
//            String insertStatementGasTimeStamp = "insert into smartfarm.gasmodule (gas_id,gas_details) values('mq" + dbgasmodule.getId()+ "','" + dbgasmodule.getModuleTimeStamp()+ "')";
            int resultG = stat.executeUpdate(insertStatementGasWaarde);
//            int resultGTS = stat.executeUpdate(insertStatementGasTimeStamp);

            int moduleGasW = gaslijst.get(gascounter).getWaarde();
            int moduleId = gaslijst.get(gascounter).getId();

            System.out.println("GasWaarde: " + moduleGasW);
            System.out.println("GasModuleId: " + moduleId);
            gascounter++;
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
    @PostMapping(path = "/kpn/bodemmodule", produces = "application/json")
    public String addKPNBodemModule(@RequestBody String json) {
        //Just has a Sysout stmt, a real world application would save this record to the database
        System.out.println("Data sent from KPN for the BodemModule");
        System.out.println(json);
        BodemModule Bomodule = new BodemModule();
        try {

            int Payloadplace = json.indexOf("vs");
            int StartofPayloadSoil = Payloadplace + 5;
            int EndOfPayloadSoil = StartofPayloadSoil + 4;
            String SoilHex = json.substring(StartofPayloadSoil, EndOfPayloadSoil);
            System.out.println(SoilHex);
            int StartofPayloadId = EndOfPayloadSoil;
            int EndOfPayloadId = StartofPayloadId + 2;
            String IdHex = json.substring(StartofPayloadId, EndOfPayloadId);
            System.out.println(IdHex);

            Integer SoilDec = Integer.parseInt(SoilHex, 16);

            Integer IdDec = Integer.parseInt(IdHex, 16);

            Bomodule.setId(IdDec);
            Bomodule.setValueSoil(SoilDec);
            bodemlijst.add(Bomodule);

            BodemModule dbBodemmodule = new BodemModule(IdDec, SoilDec);
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
            String insertStatementBodem = "insert into smartfarm.soildata (soil_id,soil_value) values('BoVh" + dbBodemmodule.getId()+ "','" + dbBodemmodule.getValueSoil()+ "')";
            int resultT = stat.executeUpdate(insertStatementBodem);

            double moduleSoilmoisture = bodemlijst.get(bodemcounter).getValueSoil();
            int moduleId = bodemlijst.get(bodemcounter).getId();

            System.out.println("Soilmoisture: " + moduleSoilmoisture);
            System.out.println("BodemModuleId: " + moduleId);
            bodemcounter++;
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

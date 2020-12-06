/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

}

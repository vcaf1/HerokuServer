/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.herokuserver;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Victoria
 */
@Repository
public class LuchtModuleDAO {
 
    private static LuchtModules list = new LuchtModules();
     
    static
    {
        list.getLuchtModuleList().add(new LuchtModule(1, 12, 33));
        list.getLuchtModuleList().add(new LuchtModule(2, 32, 36));
        list.getLuchtModuleList().add(new LuchtModule(3, 22, 40));
    }
     
    public LuchtModules getAllLuchtModules() 
    {
        return list;
    }
     
    public void addLuchtModule(LuchtModule luchtModule) {
        list.getLuchtModuleList().add(luchtModule);
    }
}
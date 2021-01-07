package com.mycompany.herokuserver;

public class Module {
    String id;
    
    Module (){
        
    }
    
    public Module (String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}

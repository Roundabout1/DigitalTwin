package com.mycompany.digitaltwin;
/*
счётчик для выдачи ID 
*/
public class IDcounter {
    private int id;

    public IDcounter() {
        this.id = -1;
    }

    public int useID(){
        this.id++;
        return id;
    }
}


package com.mycompany.digitaltwin;

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


package com.mycompany.digitaltwin;
/*
представление личности
*/
public class Person {
    private final int id;
    private String name;
    //private Money budget;

    public Person(){
        this.id = 0;
        //this.budget = new Money(0);
    }

    public Person(int id) {
        this.id = id;
        //this.budget = new Money(0);
    }

    public Person(int id, String name) {
        this.id = id;
        //this.budget = new Money(0);
        this.name = name;
    }

    public Person(int id, int money){
        this.id = id;
        //this.budget = new Money(money);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

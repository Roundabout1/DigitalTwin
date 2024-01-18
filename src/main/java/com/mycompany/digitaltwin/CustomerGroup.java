package com.mycompany.digitaltwin;

import java.util.Arrays;

public class CustomerGroup{
    private final int ID;
    private int size;
    Customer[] customers;
    //в минутах
    private int arrivingTime;
    private Money budget;

    public CustomerGroup(int ID, int size, int arrivingTime, Customer[] customers) {
        this.ID = ID;
        this.size = size;
        this.customers = customers;
        this.budget = new Money(0);
        for (Customer customer : customers) {
            this.budget.add(customer.getMoney());
        }
        this.arrivingTime = arrivingTime;
    }

    @Override
    public String toString() {
        return "CustomerGroup{" +
                "ID=" + ID +
                ", size=" + size +
                ", customers=" + Arrays.toString(customers) +
                ", arrivingTime=" + arrivingTime +
                ", budget=" + budget +
                '}';
    }

    public int getID() {
        return ID;
    }

    public int getSize() {
        return size;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public int getArrivingTime() {
        return arrivingTime;
    }

    public Money getBudget() {
        return budget;
    }

    public Pizza[] favouritePizza(){
        Pizza[] pizzas = new Pizza[customers.length];
        for(int i = 0; i < customers.length; i++){
            pizzas[i] = customers[i].getFavourite();
        }
        return pizzas;
    }
}


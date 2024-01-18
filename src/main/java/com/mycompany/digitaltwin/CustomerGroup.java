package com.mycompany.digitaltwin;

import java.util.Arrays;
/*
группа клиентов, которая одновременно заходит и одновременно выходит, а также делает заказ для всех, находящейся в этой группе людей
минимальный размер группы равен 1, то есть считается, что каждый клиент-одиночка это группа, состоящая из одного человека 
*/
public class CustomerGroup{
    private final int ID;
    // количество людей в группе, минимальный размер - единица
    private int size;
    Customer[] customers;
    // время прибытия в пиццерию, в минутах, относительно начала дня
    private int arrivingTime;
    // деньги, которые группа может потратить на заказ
    private Money budget;

    public CustomerGroup(int ID, int size, int arrivingTime, Customer[] customers) {
        this.ID = ID;
        this.size = size;
        this.customers = customers;
        // бюджет группы - это сумма денег всех клиентов
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


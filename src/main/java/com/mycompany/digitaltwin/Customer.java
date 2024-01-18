package com.mycompany.digitaltwin;
/*
посетитель пиццерии
*/
public class Customer{
    private int id;
    private Money money;
    // максимальное время ожидание заказа (в минутах)
    private int patience;
    private Pizza favourite;

    public Customer(int id, Money money, Pizza favourite) {
        this.id = id;
        this.money = money;
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", money=" + money +
                ", patience=" + patience +
                ", favourite=" + favourite +
                '}';
    }

    public int getId() {
        return id;
    }

    public Money getMoney() {
        return money;
    }

    public int getPatience() {
        return patience;
    }

    public Pizza getFavourite() {
        return favourite;
    }
}
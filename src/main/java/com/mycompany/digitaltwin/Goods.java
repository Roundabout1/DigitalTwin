package com.mycompany.digitaltwin;

/*
товар, который продаётся, на данный момент это только пицца
*/
public class Goods {
    private Money price;
    private Sold object;
    private double popularity;
    private int time;
  
    public Goods(Pizza pizza, Money price){
        this.object = pizza;
        this.price = price;
        this.popularity = pizza.getPopularity();
        this.time = pizza.getMinutes();
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Sold getObject() {
        return object;
    }

    public void setObject(Sold object) {
        this.object = object;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "price=" + price +
                ", object=" + object +
                '}';
    }
}


package com.mycompany.digitaltwin;

import java.util.ArrayList;

public class Order {
    private int ID;;
    private ArrayList<Goods> goods;
    private CustomerGroup group;
    private Money cost;
    private int minutes;
    public Order(int ID, CustomerGroup group, ArrayList<Goods> goods) {
        this.ID = ID;
        this.group = group;
        this.goods = goods;
        this.cost = new Money();
        this.minutes = 0;
        for(int i = 0; i < goods.size(); i++){
            this.minutes += goods.get(i).getTime();
            this.cost.add(goods.get(i).getPrice());
        }
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Goods> getGoods() {
        return goods;
    }

    public CustomerGroup getGroup() {
        return group;
    }

    public Money getCost() {
        return cost;
    }

    public int getMinutes() {
        return minutes;
    }
}
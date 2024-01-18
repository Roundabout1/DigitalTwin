package com.mycompany.digitaltwin;

import java.util.ArrayList;

/*
заказ клиента или клиентов 
*/
public class Order {
    private int ID;
    // заказанные товары (пиццы)
    private ArrayList<Goods> goods;
    // заказывающая группа, может состоять из одного человека
    private CustomerGroup group;
    // стоимость заказа
    private Money cost;
    // время, необходимое для выполнения заказа
    private int minutes;
    public Order(int ID, CustomerGroup group, ArrayList<Goods> goods) {
        this.ID = ID;
        this.group = group;
        this.goods = goods;
        
        // стоимость и время на приготвления заказа, считаются как сумма, соответствующих значений у товаров, входящих в заказ
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
package com.mycompany.digitaltwin;

// ингридиент - это то, из чего состоит пицца
public class Ingredient implements Sold{
    private int id;
    private String name;
    //единица измерения
    private String unit;
    private Money min_cost;
    private Money max_cost;
    //количество (в ед. измерениях) за покупку 1 шт.
    private int num_unit;

    public Ingredient(int id, String name, String unit, Money min_cost, Money max_cost, int num_unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.min_cost = min_cost;
        this.max_cost = max_cost;
        this.num_unit = num_unit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", min_cost=" + min_cost +
                ", max_cost=" + max_cost +
                ", num_unit=" + num_unit +
                '}';
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Money getMin_cost() {
        return min_cost;
    }

    public void setMin_cost(Money min_cost) {
        this.min_cost = min_cost;
    }

    public Money getMax_cost() {
        return max_cost;
    }

    public void setMax_cost(Money max_cost) {
        this.max_cost = max_cost;
    }

    public int getNum_unit() {
        return num_unit;
    }

    public void setNum_unit(int num_unit) {
        this.num_unit = num_unit;
    }
}

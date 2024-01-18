package com.mycompany.digitaltwin;

import java.util.ArrayList;

/*
рецепт пиццы
*/
public class Pizza implements Sold{
    private final int id;
    // название пиццы
    private final String name;
    // время приготовления пиццы в минутах
    private int minutes;
    // вероятность того, что пицца окажется любимой у клиента
    private double popularity;
    // необходимые ингридиенты для приготовления пиццы
    private ArrayList<Ingredient> ingredients;
    // количество каждого ингридиента, которое должно содержаться в пицце
    private ArrayList<Double> numIngredient;

    public Pizza(int id, String name, int minutes, double popularity, ArrayList<Ingredient> ingredients, ArrayList<Double> numIngredient) {
        this.id = id;
        this.name = name;
        this.minutes = minutes;
        this.popularity = popularity;
        this.ingredients = ingredients;
        this.numIngredient = numIngredient;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getPopularity() {
        return popularity;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Double> getNumIngredient() {
        return numIngredient;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minutes=" + minutes +
                ", popularity=" + popularity +
                ", ingredients=" + ingredients +
                ", numIngredient=" + numIngredient +
                '}';
    }
}

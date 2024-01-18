package com.mycompany.digitaltwin;

import java.util.ArrayList;

public class Pizza implements Sold{
    private final int id;
    private final String name;
    private int minutes;
    private double popularity;
    private ArrayList<Ingredient> ingredients;
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

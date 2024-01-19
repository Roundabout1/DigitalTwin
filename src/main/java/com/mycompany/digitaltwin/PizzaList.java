package com.mycompany.digitaltwin;

import java.util.Random;
import java.util.ArrayList;

/*
список пиццы
*/
public class PizzaList {
    private ArrayList<Pizza> pizzas;
    // этот массив предназначен для выбора случайной пиццы на основе её популярности
    private ArrayList<Double> borderLines;
    
    public PizzaList() {
        pizzas = new ArrayList<>();
        borderLines = new ArrayList<>();
    }
    
    public void add(Pizza pizza) {
        pizzas.add(pizza);
        if (borderLines.isEmpty()) {
            borderLines.add(pizza.getPopularity());
        } else {
            int lastIndex = borderLines.size() - 1;
            double border = borderLines.get(lastIndex) + pizza.getPopularity();
            if (border > 1.0) {
                throw new IllegalArgumentException("Сумма популярности пицц первышает 1.0");
            } else {
                borderLines.add(border);
            }
        }
    }
    
    public Pizza get(int i) {
       return pizzas.get(i); 
    }
    
    public int size() {
        return pizzas.size();
    }
    
    // возвращает случайую пиццу, на основе её популярности
    public Pizza randomPizza(Random random) {
        double pick = random.nextDouble();
        for (int i = 0; i < size(); i++) {
            if (pick < borderLines.get(i)) {
                return get(i);
            }
        }
        // если функция вернула null, то значит что-то пошло не так, либо список с пиццами пустой
        return null;
    }
}

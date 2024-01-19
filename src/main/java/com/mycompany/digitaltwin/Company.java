package com.mycompany.digitaltwin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
общие свединия о пиццерии, как о предприятии
*/
public class Company {
    private int id;
    // бюджет
    private Money budget;
    // расходы на рекламу
    private Money advertising;
    // прирост узнаваемости пиццерии за счёт рекламы в день
    private double adGrow;
    private Place place;
    // НДС
    private double VAT;
    ArrayList<Employee> employees;
    // запасы товаров и ингридиентов
    private ArrayList<Double> reserves;
    // продоваемые товары
    ArrayList<Goods> goods;
    public Company() {
        this.id = 0;
    }

    public Company(File input, Place place, ArrayList<Employee> employees, PizzaList pizzas, int numIngredient) {
        this.place = place;
        this.employees = employees;
        Scanner in = null;
        try {
            in = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        budget = new Money(in.nextInt());
        VAT = in.nextDouble();
        advertising = new Money(in.nextInt());
        adGrow = in.nextDouble();
        reserves = new ArrayList<>();
        goods = new ArrayList<>();
        for(int i = 0; i < pizzas.size(); i++){
            goods.add(new Goods(pizzas.get(i), new Money(0)));
        }

        for(int i = 0; i < numIngredient; i++){
            reserves.add(0.0);
        }

    }

    public ArrayList<Goods> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<Goods> goods) {
        this.goods = goods;
    }

    public Money getBudget() {
        return budget;
    }

    public void setBudget(Money budget) {
        this.budget = budget;
    }

    public Money getAdvertising() {
        return advertising;
    }

    public void setAdvertising(Money advertising) {
        this.advertising = advertising;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Double> getReserves() {
        return reserves;
    }

    public void setReserves(ArrayList<Double> reserves) {
        this.reserves = reserves;
    }

    public double getAdGrow() {
        return adGrow;
    }

    public void setAdGrow(double adGrow) {
        this.adGrow = adGrow;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    //сколько человек узнало о пиццерии
    public void increaseAdGrow(double people){
        this.place.setPasserProbability(place.getPasserProbability() + people/(double) place.getPopulation());
    }

    public void increaseAdGrow(int people){
        increaseAdGrow((double) people);
    }

    public void increaseAdGrow(){
        increaseAdGrow(adGrow);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", budget=" + budget +
                ", advertising=" + advertising +
                ", adGrow=" + adGrow +
                ", place=" + place +
                ", VAT=" + VAT +
                ", employees=" + employees +
                '}';
    }

    public void paySalary(){
        for(Employee e : employees){
            this.budget.minus(e.getSalary());
        }
    }
}

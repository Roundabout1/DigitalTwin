package com.mycompany.digitaltwin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Place {
    private String name;
    private int population;
    private int weekDayPasser;
    private int weekendDayPasser;
    private int deliveryNum;
    private Money cost;
    private int numSeat;
    private double passerProbability;
    private double deliveryProbability;
    private Money customerMinBudget;
    private Money customerMaxBudget;

    public Place(File input){
        Scanner in = null;
        try {
            in = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        population = in.nextInt();
        cost = new Money(in.nextInt());
        numSeat = in.nextInt();
        passerProbability = in.nextDouble();
        weekDayPasser = in.nextInt();
        weekendDayPasser = in.nextInt();
        deliveryProbability = in.nextDouble();
        deliveryNum = in.nextInt();
        customerMinBudget = new Money(in.nextInt());
        customerMaxBudget = new Money(in.nextInt());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeekDayPasser() {
        return weekDayPasser;
    }

    public void setWeekDayPasser(int weekDayPasser) {
        this.weekDayPasser = weekDayPasser;
    }

    public int getWeekendDayPasser() {
        return weekendDayPasser;
    }

    public void setWeekendDayPasser(int weekendDayPasser) {
        this.weekendDayPasser = weekendDayPasser;
    }

    public int getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(int deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Money getCost() {
        return cost;
    }

    public void setCost(Money cost) {
        this.cost = cost;
    }

    public int getNumSeat() {
        return numSeat;
    }

    public void setNumSeat(int numSeat) {
        this.numSeat = numSeat;
    }

    public double getPasserProbability() {
        return passerProbability;
    }

    public void setPasserProbability(double passerProbability) {
        this.passerProbability = passerProbability;
    }

    public double getDeliveryProbability() {
        return deliveryProbability;
    }

    public void setDeliveryProbability(double deliveryProbability) {
        this.deliveryProbability = deliveryProbability;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Money getCustomerMinBudget() {
        return customerMinBudget;
    }

    public void setCustomerMinBudget(Money customerMinBudget) {
        this.customerMinBudget = customerMinBudget;
    }

    public Money getCustomerMaxBudget() {
        return customerMaxBudget;
    }

    public void setCustomerMaxBudget(Money customerMaxBudget) {
        this.customerMaxBudget = customerMaxBudget;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", weekDayPasser=" + weekDayPasser +
                ", weekendDayPasser=" + weekendDayPasser +
                ", deliveryNum=" + deliveryNum +
                ", cost=" + cost +
                ", numSeat=" + numSeat +
                ", passerProbability=" + passerProbability +
                ", deliveryProbability=" + deliveryProbability +
                ", customerMinBudget=" + customerMinBudget +
                ", customerMaxBudget=" + customerMaxBudget +
                '}';
    }
}
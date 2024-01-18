package com.mycompany.digitaltwin;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
/*
главный класс, который инициализирует программу
*/
public class DigitalTwin {

        public static void main(String[] args) {
        File placeFile = new File("Data/Input/Place");
        Place place = new Place(placeFile);
        System.out.println(place.toString());

        ArrayList<Employee> employees = new ArrayList<>();
        IDcounter idcnt = new IDcounter();
        File employeesFile = new File("Data/Input/employees");
        Scanner empScan = null;
        try {
            empScan = new Scanner(employeesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Job jobs[] = Job.values();
        for(int i = 0; i < jobs.length; i++){
            int numPosition = empScan.nextInt();
            int salary = empScan.nextInt();
            for(int j = 0; j < numPosition; j++){
                employees.add(new Employee(idcnt.useID(), salary, jobs[i]));
            }
        }
        for(Employee i : employees){
            System.out.println(i);
        }

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        IDcounter ingIdCnt = new IDcounter();
        File ingredientFile = new File("Data/Input/Ingredients");
        Scanner ingredientIn = null;
        try {
            ingredientIn = new Scanner(ingredientFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int numIng = ingredientIn.nextInt();
        ingredientIn.nextLine();
        for(int i = 0; i < numIng; i++){
            String name = ingredientIn.nextLine();
            String unit = ingredientIn.nextLine();
            Money min_cost = new Money(ingredientIn.nextInt());
            Money max_cost = new Money(ingredientIn.nextInt());
            int num_unit = ingredientIn.nextInt();
            try {
                ingredientIn.nextLine();
            }catch (NoSuchElementException e){}
            Ingredient ingredient = new Ingredient(ingIdCnt.useID(), name, unit, min_cost, max_cost, num_unit);
            ingredients.add(ingredient);
        }
        System.out.println(ingredients);
        ArrayList<Pizza> pizzas = new ArrayList<>();
        File pizzaFile = new File("Data/Input/Pizza");
        IDcounter pizzaID = new IDcounter();
        Scanner pizzaIn = null;
        try {
            pizzaIn = new Scanner(pizzaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int pizzaNum = pizzaIn.nextInt();
        for(int i = 0; i < pizzaNum; i++){
            pizzaIn.nextLine();
            String name = pizzaIn.nextLine();
            System.out.println(name);
            int time = pizzaIn.nextInt();
            double popularity = pizzaIn.nextDouble();
            int pizzaIngNum = pizzaIn.nextInt();
            ArrayList<Ingredient> pizzaIng = new ArrayList<>();
            for(int j = 0; j < pizzaIngNum; j++){
                pizzaIng.add(ingredients.get(pizzaIn.nextInt()));
                //System.out.println(pizzaIn.next());
            }
            ArrayList<Double> numIngredient = new ArrayList<>();
            for(int j = 0; j < pizzaIngNum; j++){
                numIngredient.add(pizzaIn.nextDouble());
                //System.out.println(pizzaIn.next());
            }
            pizzas.add(new Pizza(pizzaID.useID(), name, time, popularity, pizzaIng, numIngredient));
        }
        System.out.println(pizzas);

        File companyFile = new File("Data/Input/Company");
        Company company = new Company(companyFile, place, employees, pizzas, ingredients.size());
        System.out.println(company);

        File confFile = new File("Data/Input/conf");
        Scanner confIn = null;
        try {
            confIn = new Scanner(confFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int days = confIn.nextInt();
        double margin = confIn.nextDouble();
        int groupSize = confIn.nextInt();
        int workingTime = confIn.nextInt();
        double recommendationChance = confIn.nextDouble();
        double customerDistribution = confIn.nextDouble();
        int waiterTime = confIn.nextInt();
        Simulation simulation = new Simulation(company, pizzas, ingredients);
        simulation.run(days, margin, groupSize, workingTime, recommendationChance, customerDistribution, waiterTime);
    }
}

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
        // загрузка данных о месте
        File placeFile = new File("Data/Input/Place");
        Place place = new Place(placeFile);
        System.out.println(place.toString());
        
        // загрузка данных о работниках
        ArrayList<Employee> employees = new ArrayList<>();
        IDcounter idcnt = new IDcounter();
        File employeesFile = new File("Data/Input/employees");
        Scanner empScan = null;
        try {
            empScan = new Scanner(employeesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // расшифровка должностей работников
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
        
        // загрузка ингридиентов
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        IDcounter ingIdCnt = new IDcounter();
        File ingredientFile = new File("Data/Input/Ingredients");
        Scanner ingredientIn = null;
        try {
            ingredientIn = new Scanner(ingredientFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // количество ингридиентов
        int numIng = ingredientIn.nextInt();
        ingredientIn.nextLine();
        for(int i = 0; i < numIng; i++){
            // название ингридиента
            String name = ingredientIn.nextLine();
            // название единицы измерения ингридиента
            String unit = ingredientIn.nextLine();
            // минимально возможная стоимость ингридиента на ранке
            Money min_cost = new Money(ingredientIn.nextInt());
            // максимально возможная стоимость ингридиента на рынке
            Money max_cost = new Money(ingredientIn.nextInt());
            // количество игридиента в соответствующих единицах измерениях, содержащихся в одной штуке
            int num_unit = ingredientIn.nextInt();
            try {
                ingredientIn.nextLine();
            }catch (NoSuchElementException e){}
            Ingredient ingredient = new Ingredient(ingIdCnt.useID(), name, unit, min_cost, max_cost, num_unit);
            ingredients.add(ingredient);
        }
        System.out.println(ingredients);
        
        // загрузка списка пицц
        ArrayList<Pizza> pizzas = new ArrayList<>();
        File pizzaFile = new File("Data/Input/Pizza");
        IDcounter pizzaID = new IDcounter();
        Scanner pizzaIn = null;
        try {
            pizzaIn = new Scanner(pizzaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // количество видов пицц в списке
        int pizzaNum = pizzaIn.nextInt();
        for(int i = 0; i < pizzaNum; i++){
            pizzaIn.nextLine();
            // название пиццы
            String name = pizzaIn.nextLine();
            System.out.println(name);
            // время пригтовления пиццы
            int time = pizzaIn.nextInt();
            // вероятность того, что пицца окажется любимой у клиента
            double popularity = pizzaIn.nextDouble();
            // количество видов ингридиентов из которых состоит пицца
            int pizzaIngNum = pizzaIn.nextInt();
            // добавление ингридиентов через их индекс
            ArrayList<Ingredient> pizzaIng = new ArrayList<>();
            for(int j = 0; j < pizzaIngNum; j++){
                pizzaIng.add(ingredients.get(pizzaIn.nextInt()));
                //System.out.println(pizzaIn.next());
            }
            // количество каждого ингридиента, которое должно содержаться в пицце
            ArrayList<Double> numIngredient = new ArrayList<>();
            for(int j = 0; j < pizzaIngNum; j++){
                numIngredient.add(pizzaIn.nextDouble());
                //System.out.println(pizzaIn.next());
            }
            pizzas.add(new Pizza(pizzaID.useID(), name, time, popularity, pizzaIng, numIngredient));
        }
        System.out.println(pizzas);
        
        // загрузка данных о предприятии, а также передача уже существующих данные туда
        File companyFile = new File("Data/Input/Company");
        Company company = new Company(companyFile, place, employees, pizzas, ingredients.size());
        System.out.println(company);
        
        // загрузка дополнительных параметров для симуляции
        File confFile = new File("Data/Input/conf");
        Scanner confIn = null;
        try {
            confIn = new Scanner(confFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // количество дней на протяжении которых будет работать пиццерия
        int days = confIn.nextInt();
        // наценка на товары
        double margin = confIn.nextDouble();
        // максимальный размер группы, которая может сформироваться из клиентов, группы заходят в пиццерию одновременно и уходят одновременно
        int groupSize = confIn.nextInt();
        // количество минут на протяжении которых работает пиццерия
        int workingTime = confIn.nextInt();
        // шанс того, что клиент порекомендует пиццерию своим друзьям
        double recommendationChance = confIn.nextDouble();
        // 
        double customerDistribution = confIn.nextDouble();
        // время, затраченное на обслуживание клиента или группы клиентов одним официантом/кассиром
        int waiterTime = confIn.nextInt();
        
        // инициализация и запуск симуляции
        Simulation simulation = new Simulation(company, pizzas, ingredients);
        simulation.run(days, margin, groupSize, workingTime, recommendationChance, customerDistribution, waiterTime);
    }
}

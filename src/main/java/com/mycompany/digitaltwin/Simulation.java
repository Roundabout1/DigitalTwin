package com.mycompany.digitaltwin;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    //public static int workingTime = 480;
    //public static int stdDeviation = 120;
    //public static double recommendationChance = 0.1;
    //отклонение от ожидаемого количества клиентов
    //public static double customerDistribution = 0.05;
    //public static int waiterTime = 3;
    private Company company;
    private ArrayList<Pizza> pizzas;
    private ArrayList<Money> prices;
    private ArrayList<Ingredient> ingredients;
    private Random random;
    public Simulation(Company company, ArrayList<Pizza> pizzas, ArrayList<Ingredient> ingredients){
        this.company = company;
        this.pizzas = pizzas;
        this.ingredients = ingredients;
        this.prices = new ArrayList<>(ingredients.size());
        for(int i = 0; i < ingredients.size(); i++){
            this.prices.add(new Money(0));
        }
        this.random = new Random();
    }

    public void run(int days, double margin, int groupSize, int workingTime, double recommendationChance, double customerDistribution, int waiterTime){
        Place place = company.getPlace();
        File outputFile = new File(FilePathes.OUTPUT_PATH);
        outputFile.delete();
        outputFile = new File(FilePathes.OUTPUT_PATH);
        /*try {
            FileWriter ingPrices = new FileWriter("Data/output/IngredientPrices", true);
            FileWriter pizPrices = new FileWriter("Data/output/PizzaPrices", true);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            FileWriter fstream1 = new FileWriter(FilePathes.OUTPUT_FILE);// конструктор с одним параметром - для перезаписи
            BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }

        for(int day = 0; day < days; day++){
            Money[] buyCost = new Money[company.getGoods().size()];
            //каждые 4 недели меняется цена на ингредиенты и соответственно на пиццу
            //также выплачивается зарплата всем сотрудникам
            if(day%28 == 0){
                company.paySalary();
                //меняются цены на ингредиенты
                for(int i = 0; i < prices.size(); i++){
                    Ingredient ingredient = ingredients.get(i);
                    int min = ingredient.getMin_cost().getRub();
                    int max = ingredient.getMax_cost().getRub();
                    Money priceForNumUnits = new Money(random.nextInt(max - min) + min, random.nextInt(99));
                    Money priceForOneUnit = new Money(priceForNumUnits.toDouble()/ingredient.getNum_unit());
                    this.prices.set(i, priceForOneUnit);

                }
                //System.out.println(this.prices);
                //меняются цены на пиццу
                for(int i = 0; i < pizzas.size(); i++){
                    Money buyCostCur = new Money(0);
                    Pizza pizza = pizzas.get(i);
                    for(int j = 0; j < pizza.getIngredients().size(); j++){
                        Ingredient ing = pizza.getIngredients().get(j);
                        double num = pizza.getNumIngredient().get(j);
                        /*if(i == 1) {
                            System.out.println(ing.getName());
                            System.out.println(prices.get(ing.getId()));
                            System.out.println(num);
                            System.out.println(ing.getId());
                        }*/
                        buyCostCur.add(new Money(num*prices.get(ing.getId()).toDouble()));
                    }
                    buyCost[i] = buyCostCur;
                    buyCostCur.add(new Money(buyCostCur.toDouble()*company.getVAT()));
                    company.getGoods().get(i).setPrice(new Money(buyCostCur.toDouble() + buyCostCur.toDouble()*margin));
                }
                //System.out.println(company.getGoods());

            }
            //в начале каждой недели закупаемся
            if(day%7 == 0){
                /*double approximateCustomerNum = 7*(company.getAdGrow() + (
                        place.getPasserProbability()*(place.getWeekDayPasser() +
                        place.getWeekendDayPasser()) +
                                place.getDeliveryProbability()*place.getDeliveryNum()
                ));*/
                double approximateCustomerNum = 7*(company.getAdGrow() + (
                        place.getPasserProbability()*(place.getWeekDayPasser() +
                                place.getWeekendDayPasser())
                ));
                approximateCustomerNum += (approximateCustomerNum - company.getAdGrow())* recommendationChance;
                approximateCustomerNum += approximateCustomerNum*customerDistribution;
                //System.out.println((int)approximateCustomerNum);

                double[] numBuy = new double[pizzas.size()];
                for(int i = 0; i < numBuy.length; i++){
                    Pizza curPizza = pizzas.get(i);
                    numBuy[i] = approximateCustomerNum*curPizza.getPopularity();
                   // System.out.println(numBuy[i]);
                    Money totalCost = new Money(0);
                    for(int j = 0; j < curPizza.getIngredients().size(); j++){
                        Ingredient curIngredient = curPizza.getIngredients().get(j);
                        double curNumIngredient = curPizza.getNumIngredient().get(j);
                        double curReserve = company.getReserves().get(curIngredient.getId());
                        double desirableNumber = curNumIngredient*numBuy[i];
                        double finalNumber = Math.max(0.0, desirableNumber-curReserve);
                        //System.out.printf("%f  %f\n", desirableNumber, curReserve);
                        company.getReserves().set(curIngredient.getId(), curReserve+finalNumber);
                        Money curCost = new Money(finalNumber*prices.get(curIngredient.getId()).toDouble());
                        totalCost.add(curCost);
                    }
                    company.getBudget().minus(totalCost);
                    //System.out.printf("total cost = %f\n",totalCost.toDouble());
                }
            }

            //генерируем клиентов
            IDcounter cntCustomer = new IDcounter();
            //int todayCustomerDeliverySize = (int)Math.round(place.getDeliveryNum()*place.getDeliveryProbability());
            int todayCustomerPasserSize;
            if(day%7 < 5){
                todayCustomerPasserSize = (int)Math.round(place.getWeekDayPasser() * place.getPasserProbability());
            }else {
                todayCustomerPasserSize = (int)Math.round(place.getWeekendDayPasser() * place.getPasserProbability());
            }
            todayCustomerPasserSize = distribute(todayCustomerPasserSize, customerDistribution);
            //todayCustomerDeliverySize = distribute(todayCustomerDeliverySize);
            //System.out.printf("size = %d %d\n", todayCustomerPasserSize, todayCustomerDeliverySize);
            ArrayList<Customer> customers = new ArrayList<>();
            for(int i = 0; i < todayCustomerPasserSize; i++){
                Money money = new Money(random.nextDouble()*
                        (place.getCustomerMaxBudget().toDouble()-place.getCustomerMinBudget().toDouble())
                        + place.getCustomerMinBudget().toDouble());
                ArrayList<Integer> indexes = new ArrayList<>();
                for(int j = 0; j < pizzas.size(); j++){
                    if(random.nextDouble() <= pizzas.get(j).getPopularity()){
                        indexes.add(j);
                    }
                }
                Pizza favourite;
                if(indexes.size() != 0){
                    favourite = pizzas.get(random.nextInt(indexes.size()));
                }else{
                    favourite = pizzas.get(random.nextInt(pizzas.size()));
                }
                customers.add(new Customer(cntCustomer.useID(), money, favourite));
                //System.out.printf("Customer %d %s\n", i, customers.get(i).toString());

            }

            ArrayList<CustomerGroup> groups = new ArrayList<>();
            IDcounter groupCnt = new IDcounter();
            int index = 0;
            while (index < todayCustomerPasserSize){
                int nextIndex = Math.min(todayCustomerPasserSize, index + random.nextInt(groupSize)+1);
                int arriving = (int)(random.nextGaussian()*workingTime/6) + workingTime/2;
                arriving = Math.max(0, arriving);
                arriving = Math.min(workingTime-1, arriving);
                Customer[] curGroup = new Customer[nextIndex-index];
                for(int i = 0; i < nextIndex-index; i++){
                    curGroup[i] = customers.get(i+index);
                }
                groups.add(new CustomerGroup(groupCnt.useID(), nextIndex - index, arriving, curGroup));
                index = nextIndex;
                //System.out.println(arriving);
            }
            groups.sort(new CustomerGroupComparator());
            /*for(int i = 0; i < groups.size(); i++)
                System.out.println(groups.get(i).getArrivingTime());*/
            ArrayList<Employee> waiters = new ArrayList<>();
            ArrayList<Employee> cooks = new ArrayList<>();
            for(Employee i : company.getEmployees()){
                if(i.getJob() == Job.waiter)
                    waiters.add(i);
                if(i.getJob() == Job.cook)
                    cooks.add(i);
            }
            TaskManager waiterManager = new TaskManager(waiters);
            TaskManager cookManager = new TaskManager(cooks);
            int freeSeat = place.getNumSeat();
            ArrayDeque<Pair> seatQueue = new ArrayDeque<>();
            for(int i = 0; i < groups.size(); i++){
                CustomerGroup group = groups.get(i);
                int curTime = group.getArrivingTime();
                while (seatQueue.size() > 0 && seatQueue.getFirst().end < curTime){
                    freeSeat += seatQueue.getFirst().seats;
                    seatQueue.removeFirst();
                }
                if(freeSeat < group.getSize()){
                    continue;
                }
                Pizza[] groupPizzas = group.favouritePizza();
                ArrayList<Goods> goods = new ArrayList<>();

                for(int j = 0; j < groupPizzas.length; j++){
                    goods.add(company.getGoods().get(groupPizzas[j].getId()));
                }
                Order order = new Order(i, group, goods);
                if(order.getCost().toDouble() > group.getBudget().toDouble()){
                    continue;
                }
                /*if(order.getMinutes() + curTime >= workingTime || order.getCost().toDouble() > group.getBudget().toDouble()){
                    continue;
                }*/
                int wt = waiterManager.addTask(curTime, waiterTime, workingTime);
                int ct = cookManager.addTask(wt, order.getMinutes(), workingTime);
                //System.out.printf("%d %d\n", wt, ct);
                if(wt == -1 || ct == -1){
                    continue;
                }
                freeSeat -= group.getSize();
                seatQueue.addLast(new Pair(group.getSize(), ct));
                company.getBudget().add(order.getCost());
                for(int j = 0; j < group.getSize(); j++){
                    if(random.nextDouble() <= recommendationChance) {
                        company.increaseAdGrow(1);
                    }
                }
                for(int j = 0; j < groupPizzas.length; j++){
                    Pizza p = groupPizzas[j];
                    for(int k = 0; k < p.getNumIngredient().size(); k++){
                        Ingredient ing = p.getIngredients().get(k);
                        //System.out.println(company.getReserves().get(ing.getId()).toString());
                        company.getReserves().set(ing.getId(), company.getReserves().get(ing.getId()) - p.getNumIngredient().get(k));
                        //System.out.println(company.getReserves().get(ing.getId()).toString());
                    }
                }
                //System.out.printf("%s %d %d %d %d\n", company.getBudget().toString(), curTime, freeSeat, wt, ct);
            }
            company.increaseAdGrow();
            System.out.printf("results = %d %s %f %d\n", day, company.getBudget().toString(), place.getPasserProbability(),
                    todayCustomerPasserSize);
            try(FileWriter writer = new FileWriter(outputFile, true))
            {
                // запись всей строки
                String newBudget =  company.getBudget().toString().replace(',','.');
                String text = "" + (day+1) + " " + newBudget + " " + place.getPasserProbability() + " " + todayCustomerPasserSize + "\n";
                writer.write(text);

                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        File htmlFile = new File("Chart/index.html");
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int distribute(int size, double customerDistribution){
        int value = (int)Math.round(size*customerDistribution);
        //System.out.println(value);
        return size + random.nextInt(2*value+1) - value;
    }

}

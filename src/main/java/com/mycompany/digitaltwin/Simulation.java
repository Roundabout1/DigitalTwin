package com.mycompany.digitaltwin;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
/*
симуляции бизнеса
*/
public class Simulation {
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
    
    /*
    запуск симуляции
    days - количество дней на протяжении которых будет работать симуляция
    margin - наценка на товары
    groupSize - максимальный размер группы, которая может сформироваться из клиентов, группы заходят в пиццерию одновременно и уходят одновременно
    workingTime - количество минут на протяжении которых работает пиццерия
    recommendationChance - шанс того, что клиент порекомендует пиццерию своим друзьям
    customerDistribution - отклонение от ожидаемого количества клиентов
    waiterTime - время, затраченное на обслуживание клиента или группы клиентов одним официантом/кассиром
    */
    public void run(int days, double margin, int groupSize, int workingTime, double recommendationChance, double customerDistribution, int waiterTime){
        Place place = company.getPlace();
        // файл, в который будет записываться результат за каждый день симуляции 
        File outputFile = new File(FilePathes.OUTPUT_PATH);
        // Удаление файла с данными, которые были получены до симуляции
        outputFile.delete();
        outputFile = new File(FilePathes.OUTPUT_PATH);
   

        try {
            FileWriter fstream1 = new FileWriter(FilePathes.OUTPUT_FILE);// конструктор с одним параметром - для перезаписи
            BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }
        
        for(int day = 0; day < days; day++){
            // стоимость покупки продоваемых товаров
            Money[] buyCost = new Money[company.getGoods().size()];
            // каждые 4 недели меняется цена на ингредиенты и соответственно на пиццу
            // также выплачивается зарплата всем сотрудникам
            if(day%28 == 0){
                company.paySalary();
                // меняются цены на ингредиенты
                for(int i = 0; i < prices.size(); i++){
                    Ingredient ingredient = ingredients.get(i);
                    int min = ingredient.getMin_cost().getRub();
                    int max = ingredient.getMax_cost().getRub();
                    // цены генерируются случайным образом
                    Money priceForNumUnits = new Money(random.nextInt(max - min) + min, random.nextInt(99));
                    // цена за 1 единицу измерения (например, из цены за 1000 грамм муки выводится цена за 1 грамм муки
                    Money priceForOneUnit = new Money(priceForNumUnits.toDouble()/ingredient.getNum_unit());
                    this.prices.set(i, priceForOneUnit);

                }
                //System.out.println(this.prices);
                // меняются цены на пиццу из-за новых цен на ингридиенты на рынке
                for(int i = 0; i < pizzas.size(); i++){
                    Money buyCostCur = new Money(0);
                    Pizza pizza = pizzas.get(i);
                    for(int j = 0; j < pizza.getIngredients().size(); j++){
                        Ingredient ing = pizza.getIngredients().get(j);
                        double num = pizza.getNumIngredient().get(j);
                        buyCostCur.add(new Money(num*prices.get(ing.getId()).toDouble()));
                    }
                    buyCost[i] = buyCostCur;
                    buyCostCur.add(new Money(buyCostCur.toDouble()*company.getVAT()));
                    company.getGoods().get(i).setPrice(new Money(buyCostCur.toDouble() + buyCostCur.toDouble()*margin));
                }
                //System.out.println(company.getGoods());

            }
            // в начале каждой недели закупаемся ингридиентами
            if(day%7 == 0){
                /*double approximateCustomerNum = 7*(company.getAdGrow() + (
                        place.getPasserProbability()*(place.getWeekDayPasser() +
                        place.getWeekendDayPasser()) +
                                place.getDeliveryProbability()*place.getDeliveryNum()
                ));*/
                // предположительное количество посетителей на следующей недели
                double approximateCustomerNum = 7*(company.getAdGrow() + (
                        place.getPasserProbability()*(place.getWeekDayPasser() +
                                place.getWeekendDayPasser())
                ));
                approximateCustomerNum += (approximateCustomerNum - company.getAdGrow())* recommendationChance;
                approximateCustomerNum += approximateCustomerNum*customerDistribution;
                //System.out.println((int)approximateCustomerNum);
                
                // предположительное количество покупок каждой из пицц
                double[] numBuy = new double[pizzas.size()];
                for(int i = 0; i < numBuy.length; i++){
                    Pizza curPizza = pizzas.get(i);
                    numBuy[i] = approximateCustomerNum*curPizza.getPopularity();
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

            // генерируем клиентов
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
            ArrayList<Customer> customers = new ArrayList<>();
            for(int i = 0; i < todayCustomerPasserSize; i++){
                Money money = new Money(random.nextDouble()*
                        (place.getCustomerMaxBudget().toDouble()-place.getCustomerMinBudget().toDouble())
                        + place.getCustomerMinBudget().toDouble());
                // индесы с любимыми пиццами 
                ArrayList<Integer> favouritePizzaIndexes = new ArrayList<>();
                // выбор любимой пиццы (неправильная генерация любимой пиццы, нужно пофиксить, возможно придётся поменять формат входных данных) 
                
                for(int j = 0; j < pizzas.size(); j++){
                    if(random.nextDouble() <= pizzas.get(j).getPopularity()){
                        favouritePizzaIndexes.add(j);
                    }
                }
                Pizza favourite;
                if(favouritePizzaIndexes.size() != 0){
                    favourite = pizzas.get(random.nextInt(favouritePizzaIndexes.size()));
                }else{
                    favourite = pizzas.get(random.nextInt(pizzas.size()));
                }
                customers.add(new Customer(cntCustomer.useID(), money, favourite));
                //System.out.printf("Customer %d %s\n", i, customers.get(i).toString());

            }
            
            // генерация групп, состоящих из клиентов, сгенерированных ранее (1 человек считается за группу тоже)
            ArrayList<CustomerGroup> groups = new ArrayList<>();
            IDcounter groupCnt = new IDcounter();
            int index = 0;
            while (index < todayCustomerPasserSize) {
                int nextIndex = Math.min(todayCustomerPasserSize, index + random.nextInt(groupSize)+1);
                // время прибытия, переменная подчиняется нормальному распределению
                int arrivingTime = (int)(random.nextGaussian()*workingTime/6) + workingTime/2;
                arrivingTime = Math.max(0, arrivingTime);
                arrivingTime = Math.min(workingTime-1, arrivingTime);
                Customer[] curGroup = new Customer[nextIndex-index];
                for(int i = 0; i < nextIndex-index; i++){
                    curGroup[i] = customers.get(i+index);
                }
                groups.add(new CustomerGroup(groupCnt.useID(), nextIndex - index, arrivingTime, curGroup));
                index = nextIndex;
                //System.out.println(arriving);
            }
            // сортировка по времени приытия
            groups.sort(new CustomerGroupComparator());
            
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
            // обслуживание групп в порядке их прибывания, распределение заказов в зависимости между поварами и официантами 
            for(int i = 0; i < groups.size(); i++) {
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
                // нету свободного повара, либо официанта, способного обслужить группу, до того, как у группы закончится терпение
                if(wt == -1 || ct == -1){
                    continue;
                }
                freeSeat -= group.getSize();
                seatQueue.addLast(new Pair(group.getSize(), ct));
                company.getBudget().add(order.getCost());
                // шанс увеличить известность пиццерии, за счёт того, что кто-то из группы порекомендует пиццерию
                for(int j = 0; j < group.getSize(); j++){
                    if(random.nextDouble() <= recommendationChance) {
                        company.increaseAdGrow(1);
                    }
                }
                // вычитание ингридиентов из запасов для приготвления пиццы
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
            
            // запись результатов в файл
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
    
    // сгенерировать количество клиентов с учётом отклонения от ожидаемого количества клиентов 
    public int distribute(int size, double customerDistribution){
        int value = (int)Math.round(size*customerDistribution);
        return size + random.nextInt(2*value+1) - value;
    }

}

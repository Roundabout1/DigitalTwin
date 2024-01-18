package com.mycompany.digitaltwin;

/*
статический класс для хранения путей к файлам в одном месте
 */
public final class FilePathes {
    // корневая папка с входными и выходными данными
    public static final String DATA_ROOT = "data";
    
    // папка со входными данными
    public static final String INPUT_ROOT = DATA_ROOT + "/input";
    
    // данные, связанные с местом пребывания пиццерии
    public static final String PLACE = INPUT_ROOT + "/place";
    // данные о работниках пиццерии
    public static final String EMPLOYEES = INPUT_ROOT + "/employees";
    // данные об ингридиентах из которых состоит пицца 
    public static final String INGREDIENTS = INPUT_ROOT + "/ingredients";
    // рецепты пицц
    public static final String PIZZA = INPUT_ROOT + "/pizza";
    // данные о предприятии
    public static final String COMPANY = INPUT_ROOT + "/company";
    // дополнительные даннные для симуляции
    public static final String CONF = INPUT_ROOT + "/conf";
    
    /* файл с выходными данными: 
    первый столбец - это день
    второй столбец - это оставшееся количесчтво денег
    третий столбец - это вероятность того, что прохожий зайдёт в пиццерию
    четвёртый столбец - это количество посетителей за соответствующий день
    */
    public static final String OUTPUT_FILE = "output.txt";
    public static final String OUTPUT_PATH = DATA_ROOT + "/output/" + OUTPUT_FILE;
    
}
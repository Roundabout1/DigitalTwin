package com.mycompany.digitaltwin;

import java.util.ArrayList;
/*
хранение нагрузки работников
*/
public class TaskManager {
    private ArrayList<Employee> employees;
    // время (в минутах) к которому работник выполнит все свои задачи
    private int[] load;

    public TaskManager(ArrayList<Employee> employees) {
        this.employees = employees;
        this.load = new int[employees.size()];
    }

    public int addTask(int receiveTime, int taskTime, int maxTime){
        int min = 0;
        for(int i = 0; i < load.length; i++){
            if(load[min] > load[i]){
                min = i;
            }
        }
        int res = load[min];
        if(res <= receiveTime){
            res = receiveTime + taskTime;
        }else{
            res += taskTime;
        }
        if(res >= maxTime){
            return -1;
        }
        load[min] = res;
        return res;
    }
}
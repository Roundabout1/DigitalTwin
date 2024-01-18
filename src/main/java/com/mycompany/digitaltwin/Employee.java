package com.mycompany.digitaltwin;
/*
представление о работнике
*/
public class Employee {
    //ежемесячная зарплата
    private Money salary;
    private Person person;
    //должность
    private Job job;
    public Employee(int id, int salary, Job job) {
        this.salary = new Money(salary);
        this.person = new Person(id);
        this.job = job;
    }

    public Employee(int id, Money salary, Job job) {
        this.salary = salary;
        this.person = new Person(id);
        this.job = job;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", person=" + person +
                ", job=" + job +
                '}';
    }

    public Money getSalary() {
        return salary;
    }

    public Person getPerson() {
        return person;
    }

    public Job getJob() {
        return job;
    }
}


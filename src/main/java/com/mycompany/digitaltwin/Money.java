package com.mycompany.digitaltwin;

public class Money {
    private int rub;
    private int kopek;

    Money(int rub, int kopek){
        set(rub, kopek);
    }

    public Money(){
        this.rub = 0;
        this.kopek = 0;
    }

    public Money(int rub) {
        this.rub = rub;
        this.kopek = 0;
    }

    public Money(double money){
        rub = (int) money;
        money -= rub;
        kopek = (int) (money*100);
    }

    @Override
    public String toString() {
        if(kopek < 10){
            return String.format("%d,0%d", rub, kopek);
        }else{
            return  String.format("%d,%d", rub, kopek);
        }
    }

    public void set(int rub, int kopek){
        this.rub = rub + kopek/100;
        this.kopek = kopek%100;
    }

    public void add(Money money){
        int kop = money.kopek + this.kopek;
        this.rub = this.rub + money.rub + kop/100;
        this.kopek = kop%100;
    }

    public void minus(Money money) {
        this.rub = (this.rub + this.kopek / 100) - (money.rub + money.kopek / 100);
        this.kopek = this.kopek%100 - money.kopek%100;
        if(this.kopek < 0){
            this.rub--;
            this.kopek = 100+this.kopek;
        }
    }
    public int getRub(){
        return rub;
    }

    public double toDouble(){
        return (double) rub + (double)(kopek)/100.0;
    }
}

package com.example.ggb;

public class MonthData {
    private String month;
    private String income;
    private String expenditure;
    private String sum;

    public MonthData(String month, String income, String expenditure, String sum){
        this.month=month;
        this.income=income;
        this.expenditure=expenditure;
        this.sum=sum;
    }

    public String getMonth(){
        return this.month;
    }
    public String getIncome(){
        return this.income;
    }
    public String getExpenditure(){
        return this.expenditure;
    }
    public String getSum(){
        return this.sum;
    }
}

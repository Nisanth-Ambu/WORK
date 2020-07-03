package com.example.simpleapp;

public class StoreAllDatabaseInfo {
   private long id;
   private long rate;
   private long day;
   private  long month;
   private long year;
   private String remind;

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public long getId() {
        return id;
    }
 public StoreAllDatabaseInfo insertdata(long id,long rate,long day,long month,long year)
 {
     StoreAllDatabaseInfo str=new StoreAllDatabaseInfo();
    str.setId(id);
    str.setRate(rate);
     str.setDay(day);
    str. setMonth(month);
     str.setYear(year);
     return  str;
 }
    public StoreAllDatabaseInfo insertreminder(long id,String remin,long day,long month,long year)
    {
        StoreAllDatabaseInfo str=new StoreAllDatabaseInfo();
        str.setId(id);
       str.setRemind(remin);
        str.setDay(day);
        str. setMonth(month);
        str.setYear(year);
        return  str;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }
}

package com.example.myapplicatio1;

public class ExampleItem {
    private String Name;
    private String Location;
    private String Ph;
    private String mid;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public ExampleItem(){}
    public ExampleItem(String mid,String name,String location,String ph)
    {this.mid=mid;
        this.Name=name;
        this.Location=location;
        this.Ph=ph;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPh() {
        return Ph;
    }

    public void setPh(String ph) {
        Ph = ph;
    }
}

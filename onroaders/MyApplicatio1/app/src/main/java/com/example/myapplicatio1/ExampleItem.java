package com.example.myapplicatio1;

public class ExampleItem {
    private String Name;
    private String Location;
    private String Ph;

    public ExampleItem(){}
    public ExampleItem(String name,String location,String ph)
    {
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

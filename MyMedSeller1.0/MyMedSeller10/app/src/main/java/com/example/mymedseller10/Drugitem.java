package com.example.test;

public class Drugitem {private int ids;
private String product;
private int price;
private int qty;

public Drugitem(){}
public Drugitem(int id,String pro,int pri,int qty)
{
  setIds(id);
  setProduct(pro);
  setPrice(pri);
  setQty(qty);
}




    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

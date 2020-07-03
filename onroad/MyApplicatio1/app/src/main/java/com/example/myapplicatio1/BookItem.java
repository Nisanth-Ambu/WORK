package com.example.myapplicatio1;

public class BookItem {
    private String Ddate;
    private String Uid;
    private String wid;
    private String jid;
    private String uname;
    private String wname;
    private String ucontact;
    private String wcontact;
    private String loctype;
    private String location;
    private String status;
    public BookItem(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BookItem(String Ddate, String Uid, String wid, String jid, String uname, String wname, String ucontact, String wcontact, String loctype, String location, String status)
    {
        this.Ddate=Ddate;
        this.Uid=Uid;
        this.jid=jid;
        this.location=location;
        this.loctype=loctype;
        this.status=status;
        this.ucontact=ucontact;
        this.uname=uname;
        this.wcontact=wcontact;
        this.wid=wid;
        this.wname=wname;
    }



    public String getDdate() {
        return Ddate;
    }

    public void setDdate(String ddate) {
        Ddate = ddate;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getUcontact() {
        return ucontact;
    }

    public void setUcontact(String ucontact) {
        this.ucontact = ucontact;
    }

    public String getWcontact() {
        return wcontact;
    }

    public void setWcontact(String wcontact) {
        this.wcontact = wcontact;
    }

    public String getLoctype() {
        return loctype;
    }

    public void setLoctype(String loctype) {
        this.loctype = loctype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

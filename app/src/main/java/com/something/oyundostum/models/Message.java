package com.something.oyundostum.models;

public class Message {
    private String gonderen,mesaj,zaman;
    public Message(){}

    public Message(String gonderen, String mesaj, String zaman) {
        this.gonderen = gonderen;
        this.mesaj = mesaj;
        this.zaman = zaman;
    }

    public String getGonderen() {return gonderen;}

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }
}
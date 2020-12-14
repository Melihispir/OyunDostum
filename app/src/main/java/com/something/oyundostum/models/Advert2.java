package com.something.oyundostum.models;

public class Advert2 {
   private  String aciklama;
    private String kullaniciadi;
   private  String oyunismi;

    public Advert2(String aciklama, String kullaniciadi, String oyunismi) {
        this.aciklama = aciklama;
        this.kullaniciadi = kullaniciadi;
        this.oyunismi = oyunismi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getOyunismi() {
        return oyunismi;
    }

    public void setOyunismi(String oyunismi) {
        this.oyunismi = oyunismi;
    }
}

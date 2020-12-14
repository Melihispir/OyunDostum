package com.something.oyundostum.models;

public class ilanlar_veriler {
    private String oyun_adi;
    private String aciklama;
    private String isim_;

    public String getOyun_adi() {
        return oyun_adi;
    }

    public void setOyun_adi(String oyun_adi) {
        this.oyun_adi = oyun_adi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getIsim_() {
        return isim_;
    }

    public void setIsim_(String isim_) {
        this.isim_ = isim_;
    }





    public ilanlar_veriler(String oyun_adi, String aciklama, String isim_, String soyisim) {
        this.oyun_adi = oyun_adi;
        this.aciklama = aciklama;
        this.isim_ = isim_;

    }
}

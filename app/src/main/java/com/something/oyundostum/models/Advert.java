package com.something.oyundostum.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advert {


    @SerializedName("AdvertID")
    @Expose
    private Integer advertID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("SendTime")
    @Expose
    private String sendTime;
    @SerializedName("LeftTime")
    @Expose
    private String leftTime;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("GameID")
    @Expose
    private Integer gameID;



    public Integer getAdvertID() {
        return advertID;
    }

    public void setAdvertID(Integer advertID) {
        this.advertID = advertID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}

package com.kawsarkobir2018.ipl;

import java.io.Serializable;

/**
 * Created by HP-NPC on 15/11/2017.
 */

public class InstallApp implements Serializable{
    public int id;
    public String name;
    public String imgurl;
    public String appurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public int clicked;

    public InstallApp(){

    }

    public InstallApp(String appurl,int clicked, int id, String imgurl, String name) {
        this.id = id;
        this.name = name;
        this.imgurl = imgurl;
        this.appurl = appurl;
        this.clicked = clicked;
    }
}

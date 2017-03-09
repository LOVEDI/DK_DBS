package com.zd.dk_dbs.Entity;

import com.zd.dk_dbs.Entity.Base.BaseEntity;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class Sift extends BaseEntity{
    private String name;
    private String position;//位置
    private String headIcon;//头像
    private String cover;//封面
    private String state;//直播状态

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

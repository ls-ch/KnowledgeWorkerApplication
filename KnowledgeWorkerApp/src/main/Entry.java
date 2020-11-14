/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Time;

/**
 *
 * @author Ian_R
 */
public class Entry {

    protected String desc;
    protected String category;
    protected Time startTime;
    protected Time endTime;
    protected String duration;
    protected int id;
    
    public Entry(
            int id, 
            String desc, 
            String category, 
            Time startTime, 
            Time endTime, 
            String duration) 
    {
        this.desc = desc;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public String getCategory() {
        return category;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDuration() {
        return duration;
    }
}

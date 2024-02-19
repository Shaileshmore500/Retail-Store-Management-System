package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class MonthMaster {
    @Id
    @GeneratedValue
    private int monthMaster_Pid;
    private String Name;
    private Date startDate;
    private Date enddate;
    private int totalDays;

    public int getMonthMaster_Pid() {
        return monthMaster_Pid;
    }

    public void setMonthMaster_Pid(int monthMaster_Pid) {
        this.monthMaster_Pid = monthMaster_Pid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

}

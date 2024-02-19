package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Attendence {
    @Id
    @GeneratedValue
    private int attendence_pid;
    private User user_fid;
    private MonthMaster monthMaster_fid;
    private String attendenceJson;

    public int getAttendence_pid() {
        return attendence_pid;
    }

    public void setAttendence_pid(int attendence_pid) {
        this.attendence_pid = attendence_pid;
    }

    public User getUser_fid() {
        return user_fid;
    }

    public void setUser_fid(User user_fid) {
        this.user_fid = user_fid;
    }

    public MonthMaster getMonthMaster_fid() {
        return monthMaster_fid;
    }

    public void setMonthMaster_fid(MonthMaster monthMaster_fid) {
        this.monthMaster_fid = monthMaster_fid;
    }

    public String getAttendenceJson() {
        return attendenceJson;
    }

    public void setAttendenceJson(String attendenceJson) {
        this.attendenceJson = attendenceJson;
    }

}

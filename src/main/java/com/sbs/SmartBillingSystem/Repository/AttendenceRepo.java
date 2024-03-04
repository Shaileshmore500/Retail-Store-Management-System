package com.sbs.SmartBillingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sbs.SmartBillingSystem.Entity.Attendence;

public interface AttendenceRepo extends JpaRepository<Attendence , Integer>{
     // @Query("select at from Attendence at where CURDATE() between
    // at.monthMaster_fid.startDate and at.monthMaster_fid.startDate.enddate")
    // List<Attendence> getAttendenceByDate();
    // @Query("select at from Attendence at where CURRENT_DATE between
    // at.monthMaster_fid.startDate and at.monthMaster_fid.enddate")
    // List<Attendence> getAttendenceByDate();
    @Query("select at from Attendence at where  at.monthMaster_fid.startDate <=now() and at.monthMaster_fid.enddate >=now()")
    List<Attendence> getAttendenceByDate();
    // where CURDATE() >= at.monthMaster_fid.startDate and CURDATE()
    // <=at.monthMaster_fid.enddate"
    // NOW()
    
}

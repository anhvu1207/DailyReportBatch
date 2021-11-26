package com.example.batchdemo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "st_exchange_calendar")
public class StExchangeCalendar {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
   private Long id;
    @Column(name = "exchange_id")
   private Integer exchangeId;
    @Column(name = "company_id")
   private Integer companyId;
    @Column(name = "exchange_sname")
   private String exchangeSname;
    @Column(name = "front_date")
   private LocalDate frontDate;
    @Column(name = "open_time")
   private Timestamp openTime;
    @Column(name = "close_time")
   private Timestamp closeTime;
    @Column(name = "holiday_flg")
   private Boolean holidayFlg;
    @Column(name = "nationalHolidayFlg")
   private Boolean national_holiday_flg;
    @Column(name = "note")
   private String note;
    @Column(name = "create_uid")
   private Integer createUid;
    @Column(name = "create_date")
   private Timestamp createDate;
    @Column(name = "write_uid")
   private Integer writeUid;
    @Column(name = "write_date")
   private Timestamp writeDate;

}

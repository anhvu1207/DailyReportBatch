package com.example.batchdemo.repository;

import com.example.batchdemo.entity.StExchangeCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StExchangeCalendarRepository extends JpaRepository<StExchangeCalendar,Long> {
}

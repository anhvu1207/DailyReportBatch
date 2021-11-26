package com.example.batchdemo.service;

import com.example.batchdemo.entity.StExchangeCalendar;
import com.example.batchdemo.repository.StExchangeCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReaderService {
    private final StExchangeCalendarRepository stExchangeCalendarRepository;
  public List<StExchangeCalendar> listData (){
      return stExchangeCalendarRepository.findAll();
   }
}

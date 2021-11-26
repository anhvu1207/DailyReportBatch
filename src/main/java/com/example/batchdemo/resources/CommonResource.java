package com.example.batchdemo.resources;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CommonResource {
    @Value("${application.companyId}")
    private int companyId;

}

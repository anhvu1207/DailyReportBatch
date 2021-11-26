package com.example.batchdemo.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Data
@Component
public class EntityManagerService {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
}

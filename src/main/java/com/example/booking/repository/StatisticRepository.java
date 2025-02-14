package com.example.booking.repository;

import com.example.booking.model.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticRepository extends MongoRepository<Statistic, String> {
}

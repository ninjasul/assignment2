package com.assignment.support.repository;

import com.assignment.support.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RegionRepository extends JpaRepository<Region, String> {
}
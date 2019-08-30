package com.assignment.support.repository;

import com.assignment.support.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SupportRepository extends JpaRepository<Support, Long> {
}
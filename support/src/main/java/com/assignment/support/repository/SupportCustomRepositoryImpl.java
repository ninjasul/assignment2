package com.assignment.support.repository;

import com.assignment.support.entity.QRegion;
import com.assignment.support.entity.QSupport;
import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Slf4j
public class SupportCustomRepositoryImpl extends QuerydslRepositorySupport implements SupportCustomRepository {

    private final JPAQueryFactory queryFactory;

    public SupportCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Support.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Support findByRegionName(Region region) {
        return queryFactory.selectFrom(QSupport.support)
                .innerJoin(QRegion.region).on(QSupport.support.region.code.eq(QRegion.region.code))
                .where(QRegion.region.name.eq(region.getName()))
                .fetchOne();
    }
}
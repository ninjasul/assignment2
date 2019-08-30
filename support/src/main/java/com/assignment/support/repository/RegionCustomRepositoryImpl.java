package com.assignment.support.repository;

import com.assignment.support.entity.QRegion;
import com.assignment.support.entity.QSupport;
import com.assignment.support.entity.Region;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class RegionCustomRepositoryImpl extends QuerydslRepositorySupport implements RegionCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QRegion $ = QRegion.region;

    public RegionCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Region.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Region> findBestRegions(int count) {
        return queryFactory.selectFrom($)
                .innerJoin(QSupport.support).on($.code.eq(QSupport.support.region.code))
                .orderBy(QSupport.support.limitAmount.desc(), QSupport.support.avgRate.asc())
                .limit(count)
                .fetch();
    }

    @Override
    public Region findSmallestMaxRateRegion() {
        return queryFactory.selectFrom($)
                .innerJoin(QSupport.support).on($.code.eq(QSupport.support.region.code))
                .orderBy(QSupport.support.maxRate.asc())
                .fetchFirst();
    }
}
package com.assignment.support.repository;

import com.assignment.support.entity.Region;

import java.util.List;

public interface RegionCustomRepository {
    List<Region> findBestRegions(int count);
    Region findSmallestMaxRateRegion();
}
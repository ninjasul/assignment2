package com.assignment.support.repository;

import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;

import java.util.List;

public interface SupportCustomRepository {
    Support findByRegionName(Region region);
}
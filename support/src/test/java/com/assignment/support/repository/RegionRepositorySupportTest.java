package com.assignment.support.repository;

import com.assignment.support.entity.Region;
import com.assignment.support.base.BaseRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionRepositorySupportTest extends BaseRepositoryTest {

    @Autowired
    private RegionCustomRepositoryImpl regionRepositorySupport;


    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findBestRegions_for_zero_region() {
        regionRepositorySupport.findBestRegions(0);
    }

    @Test
    public void findBestRegions() {
        for (int curSize = 1; curSize <= supports.size(); ++curSize ) {
            List<Region> foundRegions = regionRepositorySupport.findBestRegions(curSize);
            List<Region> bestRegions = getSortedBestSupportRegions(curSize);

            assertThat(foundRegions).isNotNull();
            assertThat(foundRegions.size()).isEqualTo(curSize);

            for (int j = 0; j < curSize; ++j) {
                assertThat(foundRegions.get(j).getCode()).isEqualTo(bestRegions.get(j).getCode());
                assertThat(foundRegions.get(j).getName()).isEqualTo(bestRegions.get(j).getName());
            }
        }
    }

    @Test
    public void findSmallestMaxRateRegions() {
        Region foundRegion = regionRepositorySupport.findSmallestMaxRateRegion();
        Region targetRegion = getSortedSmallestMaxRateRegion();

        assertThat(foundRegion).isNotNull();
        assertThat(foundRegion.getCode()).isEqualTo(targetRegion.getCode());
        assertThat(foundRegion.getName()).isEqualTo(targetRegion.getName());
    }
}
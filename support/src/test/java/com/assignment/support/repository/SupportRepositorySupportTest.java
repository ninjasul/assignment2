package com.assignment.support.repository;

import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import com.assignment.support.base.BaseRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupportRepositorySupportTest extends BaseRepositoryTest {

    @Autowired
    private SupportRepositorySupport supportRepositorySupport;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findByRegionName_for_null_region() {
        Region region = new Region(null, null);

        supportRepositorySupport.findByRegionName(region);
    }

    @Test
    public void findByRegionName_for_empty_region() {
        Region region = new Region(null, "");

        Support foundSupport = supportRepositorySupport.findByRegionName(region);

        assertThat(foundSupport).isNull();
    }

    @Test
    public void findByRegionName_for_wrong_region() {
        Region region = new Region(null, "뉴욕시");

        Support foundSupport = supportRepositorySupport.findByRegionName(region);

        assertThat(foundSupport).isNull();
    }

    @Test
    public void findByRegionName() {
        for (int i = 0; i < supports.size(); ++i ) {
            Support foundSupport = supportRepositorySupport.findByRegionName(supports.get(i).getRegion());

            assertThat(foundSupport).isNotNull();
            assertThat(foundSupport.getRegion().getCode()).isEqualTo(supports.get(i).getRegion().getCode());
            assertThat(foundSupport.getRegion().getName()).isEqualTo(supports.get(i).getRegion().getName());
            assertThat(foundSupport.getTarget()).isEqualTo(supports.get(i).getTarget());
            assertThat(foundSupport.getUsage()).isEqualTo(supports.get(i).getUsage());
            assertThat(foundSupport.getLimits()).isEqualTo(supports.get(i).getLimits());
            assertThat(foundSupport.getLimitAmount()).isEqualTo(supports.get(i).getLimitAmount());
            assertThat(foundSupport.getRate()).isEqualTo(supports.get(i).getRate());
            assertThat(foundSupport.getMinRate()).isEqualTo(supports.get(i).getMinRate());
            assertThat(foundSupport.getMaxRate()).isEqualTo(supports.get(i).getMaxRate());
            assertThat(foundSupport.getAvgRate()).isEqualTo(supports.get(i).getAvgRate());
            assertThat(foundSupport.getInstitute()).isEqualTo(supports.get(i).getInstitute());
            assertThat(foundSupport.getMgmt()).isEqualTo(supports.get(i).getMgmt());
            assertThat(foundSupport.getReception()).isEqualTo(supports.get(i).getReception());
        }
    }
}
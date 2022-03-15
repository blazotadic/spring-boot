package com.academy.demo;

import com.academy.demo.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoAcademyApplication.class)
public class CampaignServiceUnitTest {

    @Autowired
    private CampaignService campaignService;


    @Test
    public void injectedCategoryPropertiesTest()
    {
        campaignService.testCategory();
    }
}

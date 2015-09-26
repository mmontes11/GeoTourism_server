package com.mmontes.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static com.mmontes.util.GlobalNames.SPRING_CONFIG_FILE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class TIPServiceTest {

    @Test
    public void createTIPTest(){

    }
}

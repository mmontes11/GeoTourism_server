package com.mmontes.service.test;

import com.mmontes.model.dao.TIPDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.util.GlobalNames.SPRING_CONFIG_FILE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE })
@Transactional
public class TIPServiceTest {

    @Test
    public void createTIPTest(){

    }
}

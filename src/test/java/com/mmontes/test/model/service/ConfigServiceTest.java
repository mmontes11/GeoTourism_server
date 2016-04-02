package com.mmontes.test.model.service;

import com.mmontes.model.service.ConfigService;
import com.mmontes.util.dto.ConfigDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.test.util.Constants.SPRING_CONFIG_TEST_FILE;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE,SPRING_CONFIG_TEST_FILE })
@Transactional
public class ConfigServiceTest {

    @Autowired
    private ConfigService configService;

    @Test
    public void getConfig(){
        ConfigDto configDto = configService.getConfig(false);
        assertNotNull(configDto);
        assertNotNull(configDto.getBbox());
        assertNotNull(configDto.getOsmTypes());

        configDto = configService.getConfig(true);
        assertNotNull(configDto);
        assertNotNull(configDto.getBbox());
        assertNotNull(configDto.getOsmTypes());
    }
}

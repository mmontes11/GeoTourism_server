package com.mmontes.model.service;

import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.OSMTypeDto;

import java.util.List;

public interface ConfigService {
    ConfigDto getConfig();
    String getBBox();
    List<OSMTypeDto> getOSMTypes();
}

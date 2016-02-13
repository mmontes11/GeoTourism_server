package com.mmontes.model.dao;

import com.mmontes.model.entity.Config;
import com.mmontes.model.util.genericdao.GenericDao;

public interface ConfigDao extends GenericDao<Config, Long> {
    Config getConfigBoundingBox();
}

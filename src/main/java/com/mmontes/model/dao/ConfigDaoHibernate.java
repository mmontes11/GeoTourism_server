package com.mmontes.model.dao;

import com.mmontes.model.entity.Config;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("ConfigDao")
public class ConfigDaoHibernate extends GenericDaoHibernate<Config, Long> implements ConfigDao {
    @Override
    public Config getConfigBoundingBox() {
        String queryString = "SELECT c FROM Config c";
        return (Config) getSession().createQuery(queryString).setMaxResults(1).uniqueResult();
    }
}

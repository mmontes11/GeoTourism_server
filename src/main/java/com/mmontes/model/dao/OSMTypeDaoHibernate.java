package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMTypeDao")
@SuppressWarnings("all")
public class OSMTypeDaoHibernate extends GenericDaoHibernate<OSMType, Long> implements OSMTypeDao {

    @Override
    public List<String> findOSMTypeByOSMKey(String OSMKey) {
        String queryString = "SELECT o.value FROM OSMType o WHERE o.osmKey.key = :OSMKey";
        return (List<String>)
                getSession()
                        .createQuery(queryString)
                        .setParameter("OSMKey", OSMKey)
                        .list();
    }

    @Override
    public List<OSMType> find(boolean hasTIPtype) {
        String queryString = "SELECT o FROM OSMType o ";
        if (hasTIPtype){
            queryString += "WHERE o.tipType IS NOT NULL";
        }
        return (List<OSMType>) getSession().createQuery(queryString).list();
    }

    @Override
    public OSMType findByValue(String OSMValue) throws InstanceNotFoundException {
        String queryString = "SELECT o FROM OSMType o WHERE o.value = :OSMValue";
        OSMType osmType=
                (OSMType)
                getSession()
                        .createQuery(queryString)
                        .setParameter("OSMValue", OSMValue)
                        .uniqueResult();
        if (osmType == null){
            throw new InstanceNotFoundException(OSMType.class.getName(),OSMValue);
        }else{
            return osmType;
        }
    }
}

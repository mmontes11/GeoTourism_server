package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMTypeDao")
@SuppressWarnings("all")
public class OSMTypeDaoHibernate extends GenericDaoHibernate<OSMType, Long> implements OSMTypeDao {

    @Override
    public List<OSMType> find(boolean hasTIPtype) {
        String queryString = "SELECT o FROM OSMType o ";
        if (hasTIPtype){
            queryString += "WHERE o.tipType IS NOT NULL";
        }
        return (List<OSMType>) getSession().createQuery(queryString).list();
    }

    @Override
    public List<String> findByKey(String OSMKey) {
        String queryString = "SELECT o.value FROM OSMType o WHERE o.osmKey.key = :OSMKey";
        return (List<String>)
                getSession()
                        .createQuery(queryString)
                        .setParameter("OSMKey", OSMKey)
                        .list();
    }


    @Override
    public OSMType findByKeyValue(String OSMKey,String OSMValue) throws InstanceNotFoundException {
        String queryString = "SELECT o FROM OSMType o WHERE o.value = :OSMValue";
        if (OSMKey != null){
            queryString += "WHERE o.key = :OSMKey ";
        }
        if (OSMValue != null){
            String partialQueryString = "o.value = :OSMValue";
            queryString += OSMKey != null? "AND " : "WHERE ";
            queryString += partialQueryString;
        }
        Query query = getSession().createQuery(queryString);
        if (OSMKey != null){
            query.setParameter("OSMKey",OSMKey);
        }
        if (OSMValue != null){
            query.setParameter("OSMValue",OSMValue);
        }
        OSMType osmType= (OSMType) query.uniqueResult();
        if (osmType == null){
            throw new InstanceNotFoundException(OSMType.class.getName(),OSMValue);
        }else{
            return osmType;
        }
    }
}

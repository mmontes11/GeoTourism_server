package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMTypeDao")
@SuppressWarnings("all")
public class OSMTypeDaoHibernate extends GenericDaoHibernate<OSMType,Long> implements OSMTypeDao {
    @Override
    public List<OSMType> getOSMTypes() {
        String queryString = "SELECT * FROM osmtype o JOIN tiptype t ON o.tiptypeid = t.id ";
        return (List<OSMType>)getSession().createSQLQuery(queryString).addEntity(OSMType.class).list();
    }

    @Override
    public OSMType findByOSMvalueId(Long osmValueId) throws InstanceNotFoundException {
        String queryString = "SELECT o FROM OSMType o WHERE o.osmValue.id = :osmValueId";
        OSMType osmType =
                (OSMType) getSession()
                        .createQuery(queryString)
                        .setParameter("osmValueId",osmValueId)
                        .uniqueResult();
        if (osmType == null){
            throw new InstanceNotFoundException();
        }else{
            return osmType;
        }
    }
}

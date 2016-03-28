package com.mmontes.model.dao;

import com.mmontes.model.entity.Favourite;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository("FavouriteDao")
@SuppressWarnings("all")
public class FavouriteDaoHibernate extends GenericDaoHibernate<Favourite, Long> implements FavouriteDao {

    @Override
    public Integer getMaxNumOfFavs() {
        String queryString = "SELECT MAX(num_favs) FROM (SELECT COUNT(*) AS  num_favs FROM tipuseraccount GROUP BY tipid) AS num_favs_tip";
        BigInteger result = (BigInteger) getSession().createSQLQuery(queryString).uniqueResult();
        return (result == null) ? null : result.intValue();
    }

    @Override
    public Favourite findByTIPIdFBUserId(Long TIPId, Long facebookUserId) {
        String queryString = "SELECT f FROM Favourite f WHERE f.tip.id = :TIPId AND f.userAccount.facebookUserId = :facebookUserId";
        return (Favourite)
                getSession()
                    .createQuery(queryString)
                    .setParameter("TIPId", TIPId)
                    .setParameter("facebookUserId", facebookUserId)
                    .uniqueResult();
    }

    @Override
    public List<UserAccount> getFavouritedBy(Long TIPId) {
        String queryString = "SELECT f.userAccount FROM Favourite f WHERE f.tip.id = :TIPId";
        return (List<UserAccount>)
                getSession()
                    .createQuery(queryString)
                    .setParameter("TIPId", TIPId)
                    .list();
    }
}
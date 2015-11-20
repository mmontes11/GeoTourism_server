package com.mmontes.model.dao;

import com.mmontes.model.entity.Rating;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("RatingDao")
public class RatingDaoHibernate extends GenericDaoHibernate<Rating, Long> implements RatingDao {
    @Override
    public Double getAverageRate(Long TIPId) {
        String queryString = "SELECT avg(r.ratingValue) as float FROM Rating r WHERE r.tip.id = :id";
        return (Double) getSession().createQuery(queryString).setParameter("id",TIPId).uniqueResult();
    }

    @Override
    public Rating getUserTIPRate(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        String queryString = "SELECT r FROM Rating r WHERE r.tip.id = :tipid AND r.userAccount.facebookUserId = :userid";
        Rating rating = (Rating) getSession().createQuery(queryString).setParameter("tipid",TIPId).setParameter("userid",facebookUserId).uniqueResult();

        if (rating == null){
            throw new InstanceNotFoundException(facebookUserId,Rating.class.getName());
        }else{
            return rating;
        }
    }
}

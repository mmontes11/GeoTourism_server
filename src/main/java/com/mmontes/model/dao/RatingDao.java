package com.mmontes.model.dao;

import com.mmontes.model.entity.Rating;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

public interface RatingDao extends GenericDao<Rating,Long> {

    Double getAverageRate(Long TIPId);
    Rating getUserTIPRate(Long TIPId,Long userAccountID) throws InstanceNotFoundException;
}

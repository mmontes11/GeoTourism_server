package com.mmontes.model.service;

import com.mmontes.model.entity.Rating;
import com.mmontes.util.exception.InstanceNotFoundException;

public interface RatingService {

    Double rate(Double ratingValue, Long TIPId, Long facebookUserId) throws InstanceNotFoundException;
    Double getAverageRate(Long TIPId) throws InstanceNotFoundException;
    Double getUserTIPRate(Long TIPId, Long userAccountID) throws InstanceNotFoundException;
    Double deleteUserTIPRate(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;
}

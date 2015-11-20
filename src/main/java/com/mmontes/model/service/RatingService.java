package com.mmontes.model.service;

import com.mmontes.util.exception.InstanceNotFoundException;

public interface RatingService {

    Double rate(Double ratingValue, Long TIPId, Long facebookUserId) throws InstanceNotFoundException;

    Double getAverageRate(Long TIPId);

    Double getUserTIPRate(Long TIPId, Long userAccountID) throws InstanceNotFoundException;
}

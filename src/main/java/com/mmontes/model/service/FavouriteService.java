package com.mmontes.model.service;

import com.mmontes.util.exception.InstanceNotFoundException;

public interface FavouriteService {

    void markAsFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;

    void deleteFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;

    boolean isFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;
}

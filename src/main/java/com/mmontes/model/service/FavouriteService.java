package com.mmontes.model.service;

import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface FavouriteService {

    void markAsFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;

    void deleteFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;

    boolean isFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException;

    List<UserAccountDto> getFavourites(Long TIPId) throws InstanceNotFoundException;
}

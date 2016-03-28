package com.mmontes.model.dao;

import com.mmontes.model.entity.Favourite;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface FavouriteDao extends GenericDao<Favourite, Long> {

    Integer getMaxNumOfFavs();
    Favourite findByTIPIdFBUserId(Long TIPId, Long facebookUserId);
    List<UserAccount> getFavouritedBy(Long TIPId);
}
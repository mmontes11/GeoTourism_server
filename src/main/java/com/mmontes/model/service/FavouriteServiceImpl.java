package com.mmontes.model.service;

import com.mmontes.model.dao.FavouriteDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.Favourite;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service("FavouriteService")
@Transactional
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private FavouriteDao favouriteDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private DtoService dtoService;

    @Override
    public void markAsFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);
        Favourite favourite = favouriteDao.findByTIPIdFBUserId(TIPId,facebookUserId);
        if (favourite == null){
            favourite = new Favourite();
            favourite.setTip(tip);
            favourite.setUserAccount(userAccount);
        }
        favourite.setFavouriteDate(Calendar.getInstance());
        favouriteDao.save(favourite);
    }

    @Override
    public void deleteFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        tipDao.findById(TIPId);
        userAccountDao.findByFBUserID(facebookUserId);
        Favourite favourite = favouriteDao.findByTIPIdFBUserId(TIPId,facebookUserId);
        favouriteDao.remove(favourite.getId());
    }

    @Override
    public boolean isFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        tipDao.findById(TIPId);
        userAccountDao.findByFBUserID(facebookUserId);
        Favourite favourite = favouriteDao.findByTIPIdFBUserId(TIPId,facebookUserId);
        return favourite != null;
    }

    @Override
    public List<UserAccountDto> getFavouritedBy(Long TIPId) throws InstanceNotFoundException {
        tipDao.findById(TIPId);
        return dtoService.ListUserAccount2ListUserAccountDto(favouriteDao.getFavouritedBy(TIPId));
    }
}

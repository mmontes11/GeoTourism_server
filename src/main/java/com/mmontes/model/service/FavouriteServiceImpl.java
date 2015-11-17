package com.mmontes.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("FavouriteService")
@Transactional
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public void markAsFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);
        tip.getFavouritedBy().add(userAccount);
        tipDao.save(tip);
    }

    @Override
    public void deleteFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);
        tip.getFavouritedBy().remove(userAccount);
        tipDao.save(tip);
    }

    @Override
    public boolean isFavourite(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);
        return tip.getFavouritedBy().contains(userAccount);
    }
}

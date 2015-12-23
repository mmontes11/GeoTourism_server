package com.mmontes.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("FavouriteService")
@Transactional
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private DtoService dtoService;

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

    @Override
    public List<UserAccountDto> getFavourites(Long TIPId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        return dtoService.ListUserAccount2ListUserAccountDto(new ArrayList<>(tip.getFavouritedBy()));
    }
}

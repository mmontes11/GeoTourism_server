package com.mmontes.model.service;

import com.mmontes.model.dao.RatingDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.Rating;
import com.mmontes.model.entity.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service("RatingService")
@Transactional
public class RatingServiceImpl implements RatingService{

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public Double rate(Double ratingValue, Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);

        Rating rating = null;
        try {
            rating = ratingDao.getUserTIPRate(TIPId,userAccount.getId());
        } catch (InstanceNotFoundException e) {
            rating = new Rating();
        }
        rating.setTip(tip);
        rating.setUserAccount(userAccount);
        rating.setRatingValue(ratingValue);
        rating.setRatingDate(Calendar.getInstance());

        ratingDao.save(rating);

        tip.getRatings().add(rating);

        tipDao.save(tip);

        return ratingDao.getAverageRate(tip.getId());
    }

    @Override
    public Double getAverageRate(Long TIPId) {
        return ratingDao.getAverageRate(TIPId);
    }
}

package com.mmontes.util.dto;

import com.mmontes.model.entity.*;
import com.mmontes.model.service.FavouriteService;
import com.mmontes.model.service.RatingService;
import com.mmontes.service.FacebookService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dtoService")
public class DtoService {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private FavouriteService favouriteService;

    public DtoService(){
    }

    public TIPDetailsDto TIP2TIPDetailsDto(TIP tip,UserAccount userAccount) throws InstanceNotFoundException {
        TIPDetailsDto tipDetailsDto = new TIPDetailsDto();
        String geom = GeometryConversor.wktFromGeometry(tip.getGeom());

        tipDetailsDto.setId(tip.getId());
        tipDetailsDto.setType(tip.getType().getId());
        tipDetailsDto.setName(tip.getName());
        tipDetailsDto.setDescription(tip.getDescription());
        tipDetailsDto.setGeom(geom);
        tipDetailsDto.setAddress(tip.getAddress());
        tipDetailsDto.setPhotoUrl(tip.getPhotoUrl());
        tipDetailsDto.setInfoUrl(tip.getInfoUrl());
        tipDetailsDto.setGoogleMapsUrl(tip.getGoogleMapsUrl());

        if (userAccount != null){
            tipDetailsDto.setAverageRate(ratingService.getAverageRate(tip.getId()));
            Double ratingValue = ratingService.getUserTIPRate(tip.getId(), userAccount.getFacebookUserId());
            tipDetailsDto.setMyRate(ratingValue);

            List<UserAccountDto> accountDtos = ListUserAccount2ListUserAccountDto(new ArrayList<>(tip.getFavouritedBy()));
            tipDetailsDto.setFavouritedBy(accountDtos);
            tipDetailsDto.setMyFavourite(favouriteService.isFavourite(tip.getId(), userAccount.getFacebookUserId()));

            List<CommentDto> commentDtos = ListComment2ListCommentDto(new ArrayList<>(tip.getComments()));
            tipDetailsDto.setComments(commentDtos);
        }
        return tipDetailsDto;
    }

    public TIPSearchDto TIP2TIPSearchDto(TIP tip){
        Long id = tip.getId();
        String geom = GeometryConversor.wktFromGeometry(tip.getGeom());

        return new TIPSearchDto(id,geom);
    }

    public List<TIPSearchDto> ListTIP2ListSearchDto(List<TIP> tips){
        List<TIPSearchDto> tipSearchDtos = new ArrayList<>();
        for (TIP tip : tips){
            tipSearchDtos.add(TIP2TIPSearchDto(tip));
        }
        return tipSearchDtos;
    }

    public CityDto City2CityDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }

    public List<CityDto> ListCity2ListCityDto(List<City> cities) {
        List<CityDto> cityDtos = new ArrayList<>();
        for (City c : cities) {
            cityDtos.add(City2CityDto(c));
        }
        return cityDtos;
    }

    public UserAccountDto UserAccount2UserAccountDto(UserAccount user){
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setFacebookUserId(user.getFacebookUserId());
        userAccountDto.setFacebookProfilePhotoUrl(user.getFacebookProfilePhotoUrl());
        userAccountDto.setName(user.getName());
        return userAccountDto;
    }

    public List<UserAccountDto> ListUserAccount2ListUserAccountDto(List<UserAccount> users){
        List<UserAccountDto> userAccountDtos = new ArrayList<>();
        for(UserAccount user : users){
            userAccountDtos.add(UserAccount2UserAccountDto(user));
        }
        return userAccountDtos;
    }

    public CommentDto Comment2CommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setUser(UserAccount2UserAccountDto(comment.getUserAccount()));
        return commentDto;
    }

    public List<CommentDto> ListComment2ListCommentDto(List<Comment> comments){
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments){
            commentDtos.add(Comment2CommentDto(comment));
        }
        return commentDtos;
    }
}

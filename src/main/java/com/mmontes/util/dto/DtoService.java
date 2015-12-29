package com.mmontes.util.dto;

import com.mmontes.model.entity.City;
import com.mmontes.model.entity.Comment;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.service.CommentService;
import com.mmontes.model.service.FavouriteService;
import com.mmontes.model.service.RatingService;
import com.mmontes.model.service.RouteService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dtoService")
public class DtoService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private RouteService routeService;

    public DtoService(){
    }

    public TIPDetailsDto TIP2TIPDetailsDto(TIP tip, UserAccount userAccount) throws InstanceNotFoundException {
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

            tipDetailsDto.setFavouritedBy(favouriteService.getFavourites(tip.getId()));
            tipDetailsDto.setMyFavourite(favouriteService.isFavourite(tip.getId(), userAccount.getFacebookUserId()));

            tipDetailsDto.setComments(commentService.getComments(tip.getId()));
        }
        return tipDetailsDto;
    }

    public FeatureSearchDto TIP2FeatureSearchDto(TIP tip){
        Long id = tip.getId();
        String geom = GeometryConversor.wktFromGeometry(tip.getGeom());
        return new FeatureSearchDto(id,geom);
    }

    public List<FeatureSearchDto> ListTIP2ListFeatureSearchDto(List<TIP> tips){
        List<FeatureSearchDto> featureSearchDtos = new ArrayList<>();
        for (TIP tip : tips){
            featureSearchDtos.add(TIP2FeatureSearchDto(tip));
        }
        return featureSearchDtos;
    }

    public TIPMinDto TIP2TIPMinDto(TIP tip){
        TIPMinDto tipMinDto = new TIPMinDto();
        tipMinDto.setId(tip.getId());
        tipMinDto.setName(tip.getName());
        tipMinDto.setGoogleMapsurl(tip.getGoogleMapsUrl());
        return tipMinDto;
    }

    public List<TIPMinDto> ListTIP2ListTIPMinDto(List<TIP> tips){
        List<TIPMinDto> tipMinDtos = new ArrayList<>();
        for (TIP tip : tips){
            tipMinDtos.add(TIP2TIPMinDto(tip));
        }
        return tipMinDtos;
    }

    public RouteDetailsDto Route2RouteDetailsDto(Route route) throws InstanceNotFoundException {
        RouteDetailsDto routeDetailsDto = new RouteDetailsDto();
        routeDetailsDto.setId(route.getId());
        routeDetailsDto.setName(route.getName());
        routeDetailsDto.setDescription(route.getDescription());
        routeDetailsDto.setTravelMode(route.getTravelMode());
        routeDetailsDto.setGeom(GeometryConversor.wktFromGeometry(route.getGeom()));
        routeDetailsDto.setGoogleMapsUrl(route.getGoogleMapsUrl());
        routeDetailsDto.setTips(routeService.getTIPsInOrder(route.getId()));
        return routeDetailsDto;
    }

    public FeatureSearchDto Route2FeatureSearchDto(Route route){
        Long id = route.getId();
        String geom = GeometryConversor.wktFromGeometry(route.getGeom());
        return new FeatureSearchDto(id,geom);
    }

    public List<FeatureSearchDto> ListRoute2ListFeatureSearchDto(List<Route> routes){
        List<FeatureSearchDto> featureSearchDtos = new ArrayList<>();
        for (Route route : routes){
            featureSearchDtos.add(Route2FeatureSearchDto(route));
        }
        return featureSearchDtos;
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
        userAccountDto.setFacebookProfileUrl(user.getFacebookProfileUrl());
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

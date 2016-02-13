package com.mmontes.util.dto;

import com.mmontes.model.entity.City;
import com.mmontes.model.entity.Comment;
import com.mmontes.model.entity.OSMType;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.service.CommentService;
import com.mmontes.model.service.FavouriteService;
import com.mmontes.model.service.RatingService;
import com.mmontes.model.service.RouteService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("DtoService")
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
        String geom = GeometryUtils.WKTFromGeometry(tip.getGeom());

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
        String geom = GeometryUtils.WKTFromGeometry(tip.getGeom());
        String icon = tip.getType().getIcon();
        return new FeatureSearchDto(id,geom,icon);
    }

    public List<FeatureSearchDto> ListTIP2ListFeatureSearchDto(List<TIP> tips){
        List<FeatureSearchDto> featureSearchDtos = new ArrayList<>();
        for (TIP tip : tips){
            featureSearchDtos.add(TIP2FeatureSearchDto(tip));
        }
        return featureSearchDtos;
    }

    public TIPRouteDto TIP2TIPRouteDto(TIP tip){
        TIPRouteDto tipRouteDto = new TIPRouteDto();
        tipRouteDto.setId(tip.getId());
        tipRouteDto.setName(tip.getName());
        tipRouteDto.setGoogleMapsUrl(tip.getGoogleMapsUrl());
        tipRouteDto.setIcon(tip.getType().getIcon());
        tipRouteDto.setGeom(GeometryUtils.WKTFromGeometry(tip.getGeom()));
        return tipRouteDto;
    }

    public List<TIPRouteDto> ListTIP2ListTIPMinDto(List<TIP> tips){
        List<TIPRouteDto> tipRouteDtos = new ArrayList<>();
        for (TIP tip : tips){
            tipRouteDtos.add(TIP2TIPRouteDto(tip));
        }
        return tipRouteDtos;
    }

    public RouteDetailsDto Route2RouteDetailsDto(Route route,Long facebooUserId) throws InstanceNotFoundException {
        RouteDetailsDto routeDetailsDto = new RouteDetailsDto();
        routeDetailsDto.setId(route.getId());
        routeDetailsDto.setName(route.getName());
        routeDetailsDto.setDescription(route.getDescription());
        routeDetailsDto.setTravelMode(route.getTravelMode());
        routeDetailsDto.setGeom(GeometryUtils.WKTFromGeometry(route.getGeom()));
        routeDetailsDto.setGoogleMapsUrl(route.getGoogleMapsUrl());
        routeDetailsDto.setTips(routeService.getTIPsInOrder(route.getId()));
        if (facebooUserId != null){
            routeDetailsDto.setCreator(UserAccount2UserAccountDto(route.getCreator()));
        }
        return routeDetailsDto;
    }

    public FeatureSearchDto Route2FeatureSearchDto(Route route){
        Long id = route.getId();
        String geom = GeometryUtils.WKTFromGeometry(route.getGeom());
        return new FeatureSearchDto(id,geom,null);
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

    public OSMTypeDto OSMType2OSMTypeDto(OSMType osmType){
        OSMTypeDto osmTypeDto = new OSMTypeDto();
        osmTypeDto.setKey(osmType.getKeyName());
        osmTypeDto.setValue(osmType.getValue());
        osmTypeDto.setTipTypeId(osmType.getTIPType().getId());
        return  osmTypeDto;
    }

    public List<OSMTypeDto> ListOSMType2ListOSMTypeDto(List<OSMType> osmTypes){
        List<OSMTypeDto> osmTypeDtos = new ArrayList<>();
        for(OSMType osmType : osmTypes){
            osmTypeDtos.add(OSMType2OSMTypeDto(osmType));
        }
        return osmTypeDtos;
    }
}

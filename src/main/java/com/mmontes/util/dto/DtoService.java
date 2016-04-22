package com.mmontes.util.dto;

import com.mmontes.model.entity.City;
import com.mmontes.model.entity.Comment;
import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.metric.Metric;
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

    public DtoService() {
    }

    public TIPDetailsDto TIP2TIPDetailsDto(TIP tip, UserAccount userAccount) throws InstanceNotFoundException {
        TIPDetailsDto tipDetailsDto = new TIPDetailsDto();
        String geom = GeometryUtils.WKTFromGeometry(tip.getGeom());

        tipDetailsDto.setId(tip.getId());
        Long tipTypeId = tip.getType()!=null? tip.getType().getId() : null;
        tipDetailsDto.setType(tipTypeId);
        tipDetailsDto.setName(tip.getName());
        tipDetailsDto.setDescription(tip.getDescription());
        tipDetailsDto.setGeom(geom);
        tipDetailsDto.setAddress(tip.getAddress());
        tipDetailsDto.setPhotoUrl(tip.getPhotoUrl());
        tipDetailsDto.setInfoUrl(tip.getInfoUrl());
        tipDetailsDto.setGoogleMapsUrl(tip.getGoogleMapsUrl());

        if (userAccount != null) {
            tipDetailsDto.setAverageRate(ratingService.getAverageRate(tip.getId()));
            Double ratingValue = ratingService.getUserTIPRate(tip.getId(), userAccount.getFacebookUserId());
            tipDetailsDto.setMyRate(ratingValue);

            tipDetailsDto.setFavouritedBy(favouriteService.getFavouritedBy(tip.getId()));
            tipDetailsDto.setMyFavourite(favouriteService.isFavourite(tip.getId(), userAccount.getFacebookUserId()));

            tipDetailsDto.setComments(commentService.getComments(tip.getId()));
        }
        return tipDetailsDto;
    }

    public FeatureSearchDto TIP2FeatureSearchDto(TIP tip) {
        Long id = tip.getId();
        String geom = GeometryUtils.WKTFromGeometry(tip.getGeom());
        String icon = tip.getType() != null? tip.getType().getIcon() : null;
        return new FeatureSearchDto(id, geom, icon);
    }

    public List<FeatureSearchDto> ListTIP2ListFeatureSearchDto(List<TIP> tips) {
        List<FeatureSearchDto> featureSearchDtos = new ArrayList<>();
        for (TIP tip : tips) {
            featureSearchDtos.add(TIP2FeatureSearchDto(tip));
        }
        return featureSearchDtos;
    }

    public TIPRouteDto TIP2TIPRouteDto(TIP tip) {
        TIPRouteDto tipRouteDto = new TIPRouteDto();
        tipRouteDto.setId(tip.getId());
        tipRouteDto.setName(tip.getName());
        tipRouteDto.setGoogleMapsUrl(tip.getGoogleMapsUrl());
        tipRouteDto.setIcon(tip.getType().getIcon());
        tipRouteDto.setGeom(GeometryUtils.WKTFromGeometry(tip.getGeom()));
        return tipRouteDto;
    }

    public List<TIPRouteDto> ListTIP2ListTIPMinDto(List<TIP> tips) {
        List<TIPRouteDto> tipRouteDtos = new ArrayList<>();
        for (TIP tip : tips) {
            tipRouteDtos.add(TIP2TIPRouteDto(tip));
        }
        return tipRouteDtos;
    }

    public TIPReviewDto TIP2TIPReviewDto(TIP tip){
        TIPReviewDto tipReviewDto = new TIPReviewDto();
        tipReviewDto.setId(tip.getId());
        tipReviewDto.setIcon(tip.getType().getIcon());
        tipReviewDto.setName(tip.getName());
        tipReviewDto.setGeom(GeometryUtils.WKTFromGeometry(tip.getGeom()));
        tipReviewDto.setCityName(tip.getCity().getName());
        return tipReviewDto;
    }

    public List<TIPReviewDto> ListTIP2ListTIPReviewDto(List<TIP> tips){
        List<TIPReviewDto> tipReviewDtos = new ArrayList<>();
        for(TIP tip : tips){
            tipReviewDtos.add(TIP2TIPReviewDto(tip));
        }
        return tipReviewDtos;
    }

    public RouteDetailsDto Route2RouteDetailsDto(Route route, Long facebooUserId) throws InstanceNotFoundException {
        RouteDetailsDto routeDetailsDto = new RouteDetailsDto();
        routeDetailsDto.setId(route.getId());
        routeDetailsDto.setName(route.getName());
        routeDetailsDto.setDescription(route.getDescription());
        routeDetailsDto.setTravelMode(route.getTravelMode());
        routeDetailsDto.setGeom(GeometryUtils.WKTFromGeometry(route.getGeom()));
        routeDetailsDto.setGoogleMapsUrl(route.getGoogleMapsUrl());
        routeDetailsDto.setTips(routeService.getTIPsInOrder(route.getId()));
        if (facebooUserId != null) {
            routeDetailsDto.setCreator(UserAccount2UserAccountDto(route.getCreator()));
        }
        return routeDetailsDto;
    }

    public FeatureSearchDto Route2FeatureSearchDto(Route route) {
        Long id = route.getId();
        String geom = GeometryUtils.WKTFromGeometry(route.getGeom());
        return new FeatureSearchDto(id, geom, null);
    }

    public List<FeatureSearchDto> ListRoute2ListFeatureSearchDto(List<Route> routes) {
        List<FeatureSearchDto> featureSearchDtos = new ArrayList<>();
        for (Route route : routes) {
            featureSearchDtos.add(Route2FeatureSearchDto(route));
        }
        return featureSearchDtos;
    }

    public IDnameDto City2CityDto(City city) {
        IDnameDto IDnameDto = new IDnameDto();
        IDnameDto.setId(city.getId());
        IDnameDto.setName(city.getName());
        return IDnameDto;
    }

    public List<IDnameDto> ListCity2ListCityDto(List<City> cities) {
        List<IDnameDto> IDnameDtos = new ArrayList<>();
        for (City c : cities) {
            IDnameDtos.add(City2CityDto(c));
        }
        return IDnameDtos;
    }

    public UserAccountDto UserAccount2UserAccountDto(UserAccount user) {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setFacebookUserId(user.getFacebookUserId());
        userAccountDto.setFacebookProfileUrl(user.getFacebookProfileUrl());
        userAccountDto.setFacebookProfilePhotoUrl(user.getFacebookProfilePhotoUrl());
        userAccountDto.setName(user.getName());
        return userAccountDto;
    }

    public List<UserAccountDto> ListUserAccount2ListUserAccountDto(List<UserAccount> users) {
        List<UserAccountDto> userAccountDtos = new ArrayList<>();
        for (UserAccount user : users) {
            userAccountDtos.add(UserAccount2UserAccountDto(user));
        }
        return userAccountDtos;
    }

    public CommentDto Comment2CommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setUser(UserAccount2UserAccountDto(comment.getUserAccount()));
        return commentDto;
    }

    public List<CommentDto> ListComment2ListCommentDto(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(Comment2CommentDto(comment));
        }
        return commentDtos;
    }

    public MetricDto Metric2MetricDto(Metric metric) {
        MetricDto metricDto = new MetricDto();
        metricDto.setId(metric.getId());
        metricDto.setName(metric.getName());
        return metricDto;
    }

    public List<MetricDto> ListMetric2ListMetricDto(List<Metric> metrics){
        List<MetricDto> metricDtos = new ArrayList<>();
        for (Metric metric : metrics){
            metricDtos.add(Metric2MetricDto(metric));
        }
        return metricDtos;
    }

    public TIPtypeDto TIPtype2TIPtypeDto(TIPtype tipType){
        TIPtypeDto tipTypeDetailsDto = new TIPtypeDto();
        tipTypeDetailsDto.setId(tipType.getId());
        tipTypeDetailsDto.setName(tipType.getName());
        tipTypeDetailsDto.setIcon(tipType.getIcon());
        return tipTypeDetailsDto;
    }

    public List<TIPtypeDto> ListTIPtype2TIPtypeDto(List<TIPtype> tipTypes){
        List<TIPtypeDto> tipTypeDtos = new ArrayList<>();
        for (TIPtype tipType : tipTypes){
            tipTypeDtos.add(TIPtype2TIPtypeDto(tipType));
        }
        return tipTypeDtos;
    }

    public OSMTypeDto OSMType2OSMTypeDto(OSMType osmType) {
        OSMTypeDto osmTypeDto = new OSMTypeDto();
        osmTypeDto.setId(osmType.getId());
        if (osmType.getOsmKey() != null){
            osmTypeDto.setKey(osmType.getOsmKey().getKey());
        }
        osmTypeDto.setValue(osmType.getValue());
        if (osmType.getTipType() != null){
            osmTypeDto.setTipTypeID(osmType.getTipType().getId());
        }
        return osmTypeDto;
    }

    public List<OSMTypeDto> ListOSMType2ListOSMTypeDto(List<OSMType> osmTypes) {
        List<OSMTypeDto> osmTypeDtos = new ArrayList<>();
        for (OSMType osmType : osmTypes) {
            osmTypeDtos.add(OSMType2OSMTypeDto(osmType));
        }
        return osmTypeDtos;
    }
}

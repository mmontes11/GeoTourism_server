package com.mmontes.util.dto;

import com.mmontes.model.entity.*;
import com.mmontes.util.GeometryConversor;

import java.util.ArrayList;
import java.util.List;

public class DtoConversor {

    public static TIPDetailsDto TIP2TIPDetailsDto(TIP tip){
        String geom = GeometryConversor.wktFromGeometry(tip.getGeom());

        return new TIPDetailsDto(tip.getId(),tip.getType().getId(),tip.getName(),tip.getDescription(), geom, tip.getAddress(),
                tip.getPhotoUrl(), tip.getInfoUrl(), tip.getGoogleMapsUrl());
    }

    public static TIPSearchDto TIP2TIPSearchDto(TIP tip){
        Long id = tip.getId();
        String geom = GeometryConversor.wktFromGeometry(tip.getGeom());

        return new TIPSearchDto(id,geom);
    }

    public static List<TIPSearchDto> ListTIP2ListSearchDto(List<TIP> tips){
        List<TIPSearchDto> tipSearchDtos = new ArrayList<>();
        for (TIP tip : tips){
            tipSearchDtos.add(DtoConversor.TIP2TIPSearchDto(tip));
        }
        return tipSearchDtos;
    }

    public static CityDto City2CityDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }

    public static List<CityDto> ListCity2ListCityDto(List<City> cities) {
        List<CityDto> cityDtos = new ArrayList<>();
        for (City c : cities) {
            cityDtos.add(DtoConversor.City2CityDto(c));
        }
        return cityDtos;
    }

    public static UserDto User2UserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setFacebookUserId(user.getFacebookUserId());
        userDto.setFacebookProfilePhotoUrl(user.getFacebookProfilePhotoUrl());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        return null;
    }

    public static List<UserDto> ListUser2ListUserDto(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            userDtos.add(DtoConversor.User2UserDto(user));
        }
        return userDtos;
    }

    public static CommentDto Comment2CommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setUser(DtoConversor.User2UserDto(comment.getUser()));
        return commentDto;
    }

    public static List<CommentDto> ListComment2ListCommentDto(List<Comment> comments){
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments){
            commentDtos.add(DtoConversor.Comment2CommentDto(comment));
        }
        return commentDtos;
    }
}

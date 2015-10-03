package com.mmontes.util.dto;

import com.mmontes.model.entity.TIP;
import com.mmontes.model.entity.Comment;
import com.mmontes.model.entity.User;
import com.mmontes.model.util.TIPUtils;
import com.mmontes.util.GeometryConversor;

import java.util.ArrayList;
import java.util.List;

public class DtoConversor {

    public static TIPDto TIP2TIPDto(TIP tip){
        String type = tip.getType();
        String geom = GeometryConversor.wktFromGeometry(tip.getGeom());
        String cityName = tip.getCity().getName();
        String regionName = tip.getCity().getRegion().getName();
        String countryName = tip.getCity().getRegion().getCountry().getName();

        TIPDto tipDto = new TIPDto(tip.getId(),type,tip.getName(),tip.getDescription(), geom, tip.getAddress(),
                                    tip.getPhotoUrl(),tip.getInfoUrl(),tip.getGoogleMapsUrl(),
                                    cityName,regionName,countryName);
        return tipDto;
    }

    public static List<TIPDto> ListTIP2ListTIPDto(List<TIP> TIPs){
        List<TIPDto> TIPDtos = new ArrayList<TIPDto>();
        for(TIP tip : TIPs){
            TIPDtos.add(DtoConversor.TIP2TIPDto(tip));
        }
        return TIPDtos;
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
        List<UserDto> userDtos = new ArrayList<UserDto>();
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
        List<CommentDto> commentDtos = new ArrayList<CommentDto>();
        for (Comment comment : comments){
            commentDtos.add(DtoConversor.Comment2CommentDto(comment));
        }
        return commentDtos;
    }
}

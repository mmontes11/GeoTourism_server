package com.mmontes.model.service;

import com.mmontes.util.dto.CommentDto;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface CommentService {

    List<CommentDto> comment(String commentText,Long facebookUserId, Long TIPId) throws InstanceNotFoundException;
    List<CommentDto> getComments(Long TIPId);
    List<CommentDto> deleteComment(Long commentId,Long TIPId,Long facebookUserId) throws InstanceNotFoundException;
}

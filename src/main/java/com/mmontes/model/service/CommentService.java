package com.mmontes.model.service;

import com.mmontes.util.exception.InstanceNotFoundException;

public interface CommentService {

    void comment(String commentText,Long facebookUserId, Long TIPId) throws InstanceNotFoundException;
}

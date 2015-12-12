package com.mmontes.model.dao;

import com.mmontes.model.entity.Comment;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface CommentDao extends GenericDao<Comment,Long>{

    List getComments(Long TIPId);
}

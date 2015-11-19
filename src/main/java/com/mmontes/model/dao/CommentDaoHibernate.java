package com.mmontes.model.dao;

import com.mmontes.model.entity.Comment;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("CommentDao")
public class CommentDaoHibernate extends GenericDaoHibernate<Comment, Long> implements CommentDao {
}

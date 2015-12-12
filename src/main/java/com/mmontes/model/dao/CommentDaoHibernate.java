package com.mmontes.model.dao;

import com.mmontes.model.entity.Comment;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CommentDao")
public class CommentDaoHibernate extends GenericDaoHibernate<Comment, Long> implements CommentDao {

    @Override
    public List<Comment> getComments(Long TIPId) {
        String queryString = "SELECT c FROM Comment c WHERE c.tip.id = :id ORDER BY c.commentDate DESC";
        return (List<Comment>) getSession().createQuery(queryString).setParameter("id",TIPId).list();
    }
}

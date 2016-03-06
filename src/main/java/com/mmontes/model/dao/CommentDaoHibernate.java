package com.mmontes.model.dao;

import com.mmontes.model.entity.Comment;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository("CommentDao")
@SuppressWarnings("unchecked")
public class CommentDaoHibernate extends GenericDaoHibernate<Comment, Long> implements CommentDao {

    @Override
    public List<Comment> getComments(Long TIPId) {
        String queryString = "SELECT c FROM Comment c WHERE c.tip.id = :id ORDER BY c.commentDate DESC";
        return (List<Comment>) getSession().createQuery(queryString).setParameter("id",TIPId).list();
    }

    @Override
    public Comment findComment(Long commentId, Long TIPId, Long facebookUserId) {
        String queryString = "SELECT c FROM Comment c WHERE c.id = :commentId AND c.tip.id = :TIPId AND c.userAccount.facebookUserId = :facebookUserId";
        return (Comment) getSession()
                            .createQuery(queryString)
                            .setParameter("commentId", commentId)
                            .setParameter("TIPId", TIPId)
                            .setParameter("facebookUserId", facebookUserId)
                            .uniqueResult();
    }
}

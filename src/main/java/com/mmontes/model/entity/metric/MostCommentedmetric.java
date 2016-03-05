package com.mmontes.model.entity.metric;


import com.mmontes.model.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MostCommentedMetric extends Metric {

    @Autowired
    private CommentDao commentDao;

    public MostCommentedMetric() {
    }

    public MostCommentedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public List<List<Double>> getStats() {
        Integer maxNumOfComments = commentDao.getMaxNumOfComments();
        return super.statsDao.getMostCommented(maxNumOfComments);
    }
}

package com.mmontes.model.entity.metric;


import com.mmontes.model.dao.CommentDao;
import com.mmontes.util.dto.LatLngWeight;
import com.mmontes.util.dto.StatsDto;
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
    public StatsDto getStats() {
        Integer max = commentDao.getMaxNumOfComments();
        List<LatLngWeight> data = super.statsDao.getMostCommented();
        return new StatsDto(max,data);
    }
}

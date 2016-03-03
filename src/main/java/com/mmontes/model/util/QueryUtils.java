package com.mmontes.model.util;

import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.util.List;
import java.util.Map;

public class QueryUtils {

    public static String getINvalues(List values) {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            Object o = values.get(i);
            if (o instanceof String) {
                ids.append("'").append(o.toString()).append("'");
            } else {
                ids.append(o.toString());
            }
            if (i != (values.size() - 1)) {
                ids.append(",");
            }
        }
        return "(" + ids.toString() + ")";
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> query2MapList(Query query) {
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

package com.mmontes.model.util;

import java.util.List;
import java.util.StringJoiner;

public class QueryUtils {

    public static String getINvalues (List values){
        StringJoiner joiner = new StringJoiner(",");
        for (Object o : values){
            joiner.add(o.toString());
        }
        return "(" + joiner.toString() + ")";
    }
}

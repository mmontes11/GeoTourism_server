package com.mmontes.model.util;

import java.util.List;
import java.lang.StringBuilder;

public class QueryUtils {

    public static String getINvalues (List values){
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i<values.size(); i++){
            Object o = values.get(i);
            if (o instanceof String){
                ids.append("'").append(o.toString()).append("'");
            }else{
                ids.append(o.toString());
            }
            if (i != (values.size()-1)){
                ids.append(",");
            }
        }
        return "(" + ids.toString() + ")";
    }
}

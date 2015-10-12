package com.mmontes.model.util;

import static com.mmontes.util.Constants.HOTEL_DISCRIMINATOR;
import static com.mmontes.util.Constants.MONUMENT_DISCRIMINATOR;
import static com.mmontes.util.Constants.NATURAL_SPACE_DISCRIMINATOR;
import static com.mmontes.util.Constants.RESTAURANT_DISCRIMINATOR;

import com.mmontes.model.dao.CityDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TIPUtils {

    public static boolean isValidType(String type){
        List<String> types = new ArrayList<String>() {{
            add(MONUMENT_DISCRIMINATOR);
            add(NATURAL_SPACE_DISCRIMINATOR);
            add(HOTEL_DISCRIMINATOR);
            add(RESTAURANT_DISCRIMINATOR);
        }};
        return types.contains(type);
    }
}

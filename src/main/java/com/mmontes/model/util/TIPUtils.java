package com.mmontes.model.util;

import static com.mmontes.util.Constants.HOTEL_DISCRIMINATOR;
import static com.mmontes.util.Constants.MONUMENT_DISCRIMINATOR;
import static com.mmontes.util.Constants.NATURAL_SPACE_DISCRIMINATOR;
import static com.mmontes.util.Constants.RESTAURANT_DISCRIMINATOR;

import com.mmontes.model.entity.TIP.*;

import java.util.ArrayList;
import java.util.List;

public class TIPUtils {

    public static String getType(TIP tip) {
        if (tip instanceof Monument) {
            return MONUMENT_DISCRIMINATOR;
        }
        if (tip instanceof NaturalSpace) {
            return NATURAL_SPACE_DISCRIMINATOR;
        }
        if (tip instanceof Hotel) {
            return HOTEL_DISCRIMINATOR;
        }
        if (tip instanceof Restaurant) {
            return RESTAURANT_DISCRIMINATOR;
        }
        return null;
    }

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

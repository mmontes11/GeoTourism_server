package com.mmontes.model.util;

import static com.mmontes.util.GlobalNames.HOTEL_DISCRIMINATOR;
import static com.mmontes.util.GlobalNames.MONUMENT_DISCRIMINATOR;
import static com.mmontes.util.GlobalNames.NATURAL_SPACE_DISCRIMINATOR;
import static com.mmontes.util.GlobalNames.RESTAURANT_DISCRIMINATOR;

import com.mmontes.model.entity.TIP.*;

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
}

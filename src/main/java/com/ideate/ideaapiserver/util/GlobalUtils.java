package com.ideate.ideaapiserver.util;

import java.util.UUID;

public class GlobalUtils {

    public static String makeUUID(int digit) {
        String random = UUID.randomUUID().toString();
        random = random.replaceAll("-", "");
        random = random.substring(0, digit);
        return random;
    }

}

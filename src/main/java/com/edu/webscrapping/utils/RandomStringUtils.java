package com.edu.webscrapping.utils;

import java.security.SecureRandom;

public class RandomStringUtils {

    public static String generateRandomFilePrefix (String prefix, String fileType) {

        SecureRandom random = new SecureRandom();
        random.generateSeed(32);

        return prefix + random.nextLong() + "." + fileType;

    }


}

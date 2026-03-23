package sn.dev.ct.immosen.util;

import java.util.Random;

public class RandomStringGenerator {
    public static String generateRandomString(int length) {
        String availableValues = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@#$*";
        Random random = new Random();
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < length; i++) {
            value.append(availableValues.charAt(random.nextInt(availableValues.length())));
        }
        return value.toString();
    }
}

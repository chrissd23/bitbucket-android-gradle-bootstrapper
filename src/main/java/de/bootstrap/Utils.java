package de.bootstrap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    public static String getPatternString(String regEx, String responseBody) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(responseBody);
        String string = null;
        while (matcher.find()) {
            string = matcher.group();
        }
        return string;
    }


}

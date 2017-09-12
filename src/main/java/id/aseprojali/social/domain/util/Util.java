package id.aseprojali.social.domain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by avew on 9/12/17.
 */
public class Util {

    public static String getMentionFromWords(String words) {
        String pattern = "\\S+[a-z0-9]@[a-z0-9\\.]+";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(words);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}

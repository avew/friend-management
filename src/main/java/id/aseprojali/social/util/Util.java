package id.aseprojali.social.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}

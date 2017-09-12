package id.aseprojali.social.domain.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by avew on 9/12/17.
 */
public class UtilTest {
    @Test
    public void getMentionFromWords() throws Exception {
        String words = "Hello World! kate@example.com";
        String mentionFromWords = Util.getMentionFromWords(words);
        Assert.assertEquals(mentionFromWords, "kate@example.com");
    }

}
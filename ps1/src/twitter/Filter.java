package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    /**
     * Find tweets written by a particular user.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equalsIgnoreCase(username)) {
                result.add(tweet);
            }
        }
        return result;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if (!timestamp.isBefore(timespan.getStart()) && !timestamp.isAfter(timespan.getEnd())) {
                result.add(tweet);
            }
        }
        return result;
    }

    /**
     * Find tweets that contain certain words.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            for (String word : words) {
                if (tweet.getText().toLowerCase().contains(word.toLowerCase())) {
                    result.add(tweet);
                    break;  // Only need to match one word, so break after the first match.
                }
            }
        }
        return result;
    }
}

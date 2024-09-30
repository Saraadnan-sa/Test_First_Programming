package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "meeting @10am", d3);

    // writtenBy() tests

    @Test
    public void testWrittenBySingleAuthor() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
        assertEquals("expected 2 tweets", 2, result.size());
        assertTrue("expected list to contain tweet1", result.contains(tweet1));
        assertTrue("expected list to contain tweet3", result.contains(tweet3));
    }

    @Test
    public void testWrittenByNoMatchingAuthor() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "unknown");
        assertTrue("expected empty list", result.isEmpty());
    }

    // inTimespan() tests

    @Test
    public void testInTimespanAllWithin() {
        Timespan timespan = new Timespan(d1, d3);
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertEquals("expected 3 tweets", 3, result.size());
    }

    @Test
    public void testInTimespanNoneWithin() {
        Timespan timespan = new Timespan(Instant.parse("2016-02-17T12:30:00Z"), Instant.parse("2016-02-17T13:00:00Z"));
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertTrue("expected empty list", result.isEmpty());
    }

    // containing() tests

    @Test
    public void testContainingWord() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        assertEquals("expected 2 tweets", 2, result.size());
    }

    @Test
    public void testContainingNoMatchingWord() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("meeting"));
        assertTrue("expected empty list", result.isEmpty());
    }

    @Test
    public void testContainingCaseInsensitive() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("Rivest"));
        assertEquals("expected 2 tweets", 2, result.size());
    }
}

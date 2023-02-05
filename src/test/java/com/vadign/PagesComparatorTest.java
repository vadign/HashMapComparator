package com.vadign;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PagesComparatorTest {

    @Test
    public void testFindDeletedPages() throws URISyntaxException {
        Map<URI, String> todayState = new HashMap<>();
        Map<URI, String> yesterdayState = new HashMap<>();

        todayState.put(new URI("https://vac.tv/"), "<title> sample vac.tv </title>");
        todayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        todayState.put(new URI("https://zac.tv/"), "<title> sample changed zac.tv </title>");
        todayState.put(new URI("https://tw.tv/"), "<title> sample tw.tv </title>");

        yesterdayState.put(new URI("https://vac.tv/"), "<title> sample changed vac.tv </title>");
        yesterdayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        yesterdayState.put(new URI("https://zac.tv/"), "<title> sample zac.tv </title>");
        yesterdayState.put(new URI("https://go.tv/"), "<title> sample go.com </title>");

        String excepted = """ 
                Исчезли следующие страницы:
                https://go.tv/
                """;

        String actual = String.valueOf(PagesComparator.findDeletedPages(yesterdayState, todayState));

        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void testFindNewPages() throws URISyntaxException {
        Map<URI, String> todayState = new HashMap<>();
        Map<URI, String> yesterdayState = new HashMap<>();

        todayState.put(new URI("https://vac.tv/"), "<title> sample vac.tv </title>");
        todayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        todayState.put(new URI("https://zac.tv/"), "<title> sample changed zac.tv </title>");
        todayState.put(new URI("https://tw.tv/"), "<title> sample tw.tv </title>");

        yesterdayState.put(new URI("https://vac.tv/"), "<title> sample changed vac.tv </title>");
        yesterdayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        yesterdayState.put(new URI("https://zac.tv/"), "<title> sample zac.tv </title>");
        yesterdayState.put(new URI("https://go.tv/"), "<title> sample go.com </title>");

        String excepted = """ 
                Появились следующие новые страницы:
                https://tw.tv/
                """;

        String actual = String.valueOf(PagesComparator.findNewPages(yesterdayState, todayState));

        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void testFindChangedPages() throws URISyntaxException {
        Map<URI, String> todayState = new HashMap<>();
        Map<URI, String> yesterdayState = new HashMap<>();

        todayState.put(new URI("https://vac.tv/"), "<title> sample vac.tv </title>");
        todayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        todayState.put(new URI("https://zac.tv/"), "<title> sample changed zac.tv </title>");
        todayState.put(new URI("https://tw.tv/"), "<title> sample tw.tv </title>");

        yesterdayState.put(new URI("https://vac.tv/"), "<title> sample changed vac.tv </title>");
        yesterdayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        yesterdayState.put(new URI("https://zac.tv/"), "<title> sample zac.tv </title>");
        yesterdayState.put(new URI("https://go.tv/"), "<title> sample go.com </title>");

        String excepted = """ 
                Изменились следующие страницы:
                https://vac.tv/
                https://zac.tv/
                """;

        String actual = String.valueOf(PagesComparator.findChangedPages(yesterdayState, todayState));

        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void testFinalLetter() throws URISyntaxException {
        Map<URI, String> todayState = new HashMap<>();
        Map<URI, String> yesterdayState = new HashMap<>();

        todayState.put(new URI("https://vac.tv/"), "<title> sample vac.tv </title>");
        todayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        todayState.put(new URI("https://zac.tv/"), "<title> sample changed zac.tv </title>");
        todayState.put(new URI("https://tw.tv/"), "<title> sample tw.tv </title>");

        yesterdayState.put(new URI("https://vac.tv/"), "<title> sample changed vac.tv </title>");
        yesterdayState.put(new URI("https://dac.tv/"), "<title> sample dac.tv </title>");
        yesterdayState.put(new URI("https://zac.tv/"), "<title> sample zac.tv </title>");
        yesterdayState.put(new URI("https://go.tv/"), "<title> sample go.com </title>");

        String excepted = """
                Здравствуйте, дорогая и.о. секретаря

                За последние сутки во ввереных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы:
                https://go.tv/
                Появились следующие новые страницы:
                https://tw.tv/
                Изменились следующие страницы:
                https://vac.tv/
                https://zac.tv/

                C уважением,
                автоматизированная система мониторинга.""";

        String actual = """
                Здравствуйте, дорогая и.о. секретаря

                За последние сутки во ввереных Вам сайтах произошли следующие изменения:
                                
                """ + PagesComparator.findDeletedPages(yesterdayState, todayState) +
                PagesComparator.findNewPages(yesterdayState, todayState) +
                PagesComparator.findChangedPages(yesterdayState, todayState) +
                """
                                        
                        C уважением,
                        автоматизированная система мониторинга.""";

        Assert.assertEquals(excepted, actual);
    }
}

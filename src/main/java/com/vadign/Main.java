package com.vadign;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<URI, String> todayState = new HashMap<>();
        Map<URI, String> yesterdayState = new HashMap<>();

        String finalLetter = """
                Здравствуйте, дорогая и.о. секретаря

                За последние сутки во ввереных Вам сайтах произошли следующие изменения:
                                
                """ + PagesComparator.findDeletedPages(yesterdayState, todayState) +
                PagesComparator.findNewPages(yesterdayState, todayState) +
                PagesComparator.findChangedPages(yesterdayState, todayState) +
                """
                                        
                        C уважением,
                        автоматизированная система мониторинга.
                        """;

        System.out.println(finalLetter);
    }
}
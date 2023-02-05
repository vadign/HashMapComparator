package com.vadign;

import java.net.URI;
import java.util.Map;

public class PagesComparator {

    static public StringBuilder findDeletedPages(Map<URI, String> yesterdayState, Map<URI, String> todayState) {
        StringBuilder deletedPages = new StringBuilder("Исчезли следующие страницы:\n");

        yesterdayState.forEach((uri, s) -> {
            if (!(todayState.containsKey(uri))) {
                deletedPages.append(uri.toString()).append("\n");
            }
        });

        return deletedPages;
    }

    static public StringBuilder findNewPages(Map<URI, String> yesterdayState, Map<URI, String> todayState) {
        StringBuilder newPages = new StringBuilder("Появились следующие новые страницы:\n");

        todayState.forEach(((uri, s) -> {
            if (!(yesterdayState.containsKey(uri))) {
                newPages.append(uri.toString()).append("\n");
            }
        }));

        return newPages;
    }

    static public StringBuilder findChangedPages(Map<URI, String> yesterdayState, Map<URI, String> todayState) {
        StringBuilder changedPages = new StringBuilder("Изменились следующие страницы:\n");

        todayState.forEach(((uri, s) -> {
            if (yesterdayState.containsKey(uri)) {
                if (!(yesterdayState.get(uri).equals(todayState.get(uri)))) {
                    changedPages.append(uri.toString()).append("\n");
                }
            }
        }));

        return changedPages;
    }

}
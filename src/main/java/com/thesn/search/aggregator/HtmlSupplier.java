package com.thesn.search.aggregator;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class HtmlSupplier {

    private static final Map<String, String> resources;

    static {
        resources = new HashMap<>();
        registerHtml("index.html");
    }

    public static String getHtml(String name) {
        return resources.get(name);
    }

    private static void registerHtml(String path) {
        try {
            resources.put(path,
                    new BufferedReader(
                        new InputStreamReader(
                                new ClassPathResource(path).getInputStream()
                        )
                    ).lines().collect(Collectors.joining("\n"))
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

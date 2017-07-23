package com.thesn.search.aggregator.tasks;

import java.util.Optional;

public interface Task {
    Optional<String> find(String query);
}

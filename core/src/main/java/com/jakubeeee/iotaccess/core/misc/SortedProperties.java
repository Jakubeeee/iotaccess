package com.jakubeeee.iotaccess.core.misc;

import java.util.*;

import static java.util.Collections.enumeration;
import static java.util.Collections.unmodifiableSet;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

public final class SortedProperties extends Properties {

    @Override
    public Set<Object> keySet() {
        return unmodifiableSet(new TreeSet<>(super.keySet()));
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return super.entrySet().stream()
                .sorted(comparing(entry -> entry.getKey().toString()))
                .collect(toCollection(LinkedHashSet::new));
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return enumeration(new TreeSet<>(super.keySet()));
    }

}
package io.dropwizard.metrics5;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class TaggedMetric<Child extends Metric> {
    protected final String name;
    protected final List<String> tagNames;

    protected final ConcurrentMap<List<String>, Child> children = new ConcurrentHashMap<>();


    protected TaggedMetric(Builder b) {
        if (b.name.isEmpty()) throw new IllegalStateException("Name hasn't been set.");
        name = b.name;
        tagNames = Arrays.asList(b.tagNames);
    }

    public Child tags(String... tagValues) {
        if (tagValues.length != tagNames.size()) {
            throw new IllegalArgumentException("Incorrect number of tags.");
        }
        for (String label : tagValues) {
            if (label == null) {
                throw new IllegalArgumentException("Tag cannot be null.");
            }
        }
        List<String> key = Arrays.asList(tagValues);
        Child c = children.get(key);
        if (c != null) {
            return c;
        }
        Child c2 = newChild();
        Child tmp = children.putIfAbsent(key, c2);
        return tmp == null ? c2 : tmp;
    }

    protected abstract Child newChild();

    public abstract static class Builder<B extends Builder<B, C>, C extends TaggedMetric> {
        String name = "";
        String[] tagNames = new String[]{};

        public B name(String name) {
            this.name = name;
            return (B) this;
        }

        public B tagNames(String... tagNames) {
            this.tagNames = tagNames;
            return (B) this;
        }

        public abstract C create();
    }

}

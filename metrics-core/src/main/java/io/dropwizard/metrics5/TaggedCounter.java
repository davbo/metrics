package io.dropwizard.metrics5;

public class TaggedCounter extends TaggedMetric<Counter> {

    TaggedCounter(Builder b) {
        super(b);
    }

    public static class Builder extends TaggedMetric.Builder<Builder, TaggedCounter> {
        @Override
        public TaggedCounter create() {
            return new TaggedCounter(this);
        }
    }

    public static Builder build() {
        return new Builder();
    }

    @Override
    protected Counter newChild() {
        return new Counter();
    }
}

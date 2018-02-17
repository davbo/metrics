package io.dropwizard.metrics5;

import java.util.concurrent.atomic.DoubleAdder;

public class TaggedGauge extends TaggedMetric<TaggedGauge.Child> {
    protected TaggedGauge(Builder b) {
        super(b);
    }

    public static class Builder extends TaggedMetric.Builder<Builder, TaggedGauge> {
        @Override
        public TaggedGauge create() {
            return new TaggedGauge(this);
        }
    }

    public static Builder build() {
        return new Builder();
    }

    @Override
    protected Child newChild() {
        return new Child();
    }

    public static class Child implements Gauge<Double> {
        private final DoubleAdder value = new DoubleAdder();

        /**
         * Increment the gauge by 1.
         */
        public void inc() {
            inc(1);
        }

        /**
         * Increment the gauge by the given amount.
         */
        public void inc(double amt) {
            value.add(amt);
        }

        /**
         * Decrement the gauge by 1.
         */
        public void dec() {
            dec(1);
        }

        /**
         * Decrement the gauge by the given amount.
         */
        public void dec(double amt) {
            value.add(-amt);
        }

        /**
         * Set the gauge to the given value.
         */
        public void set(double val) {
            synchronized (this) {
                value.reset();
                // If get() were called here it'd see an invalid value, so use a lock.
                // inc()/dec() don't need locks, as all the possible outcomes
                // are still possible if set() were atomic so no new races are introduced.
                value.add(val);
            }
        }

        @Override
        public Double getValue() {
            return value.doubleValue();
        }
    }
}

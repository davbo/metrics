package io.dropwizard.metrics5;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TaggedGaugeTest {


    @Test
    public void buildsATaggedGauge() {
        TaggedGauge taggedGauge = TaggedGauge.build().name("MyGauge").tagNames("foo", "bar", "baz").create();
        TaggedGauge.Child gauge = taggedGauge.tags("f", "o", "o");
        gauge.set(100);


        assertThat(taggedGauge.tags("f", "o", "o").getValue()).isEqualTo(100);
        assertThat(taggedGauge.tags("1", "2", "3").getValue()).isEqualTo(0);
    }
}
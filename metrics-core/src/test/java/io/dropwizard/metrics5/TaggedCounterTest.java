package io.dropwizard.metrics5;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TaggedCounterTest {

    @Test
    public void buildsATaggedCounter() {
        TaggedCounter taggedCounter = TaggedCounter.build().name("MyCounter").tagNames("foo", "bar", "baz").create();

        Counter counter = taggedCounter.tags("f", "o", "o");
        counter.inc();
        counter.inc();

        assertThat(taggedCounter.tags("f", "o", "o").getCount()).isEqualTo(2);
        assertThat(taggedCounter.tags("a", "a", "a").getCount()).isEqualTo(0);
    }
}
package com.example.chaosemeraldservice.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class EmeraldTest {

    @Test
    public void shouldConstructAndGetValues() {
        Emerald emerald = new Emerald(100L, Colour.BLUE);

        assertThat(emerald.getPowerLevel(), is(100L));
        assertThat(emerald.getColour(), is(Colour.BLUE));
    }

    @Test
    public void shouldBeEqual() {
        Emerald actual = new Emerald(200L, Colour.GREEN);
        Emerald emerald = new Emerald(200L, Colour.GREEN);

        assertThat(emerald.equals(actual), is(true));
        assertThat(emerald.hashCode(), is(actual.hashCode()));
    }

    @Test
    public void shouldNotBeEqual() {
        Emerald actual = new Emerald(300L, Colour.PINK);
        Emerald emerald = new Emerald(200L, Colour.GREEN);

        assertThat(emerald.equals(actual), is(false));
        assertThat(emerald.hashCode(), is(not(actual.hashCode())));
    }

}
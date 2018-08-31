package com.example.chaosemeraldservice;

import com.example.chaosemeraldservice.model.Colour;
import com.example.chaosemeraldservice.model.Emerald;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChaosEmeraldServiceApplicationTests {

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

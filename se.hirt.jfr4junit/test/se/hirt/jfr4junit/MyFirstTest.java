package se.hirt.jfr4junit;

import static org.junit.gen5.api.Assertions.assertEquals;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(JFRJUnit5Extension.class)
public class MyFirstTest {
	@Test
	void okTest() {
		assertEquals(2, 1 + 1);
		sleep(200);
	}

	@Test
	void failedTest() {
		assertEquals(2, 1 + 2);
		sleep(200);		
	}
	
	@Test
	void myExceptionTest() throws IllegalArgumentException {
		sleep(200);
		throw new IllegalArgumentException("Blahblahblah");
	}
	
	@Test
	void myLongTest() throws IllegalArgumentException {
		sleep(2000);
	}

	@Test
	void anotherLongTest() throws IllegalArgumentException {
		sleep(2000);
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Don't care
		}
	}
}

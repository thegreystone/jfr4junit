package se.hirt.jfr4junit.jdk7;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Producer;

@SuppressWarnings("deprecation")
public class JFR7Utils {
	public final static Producer PRODUCER;

	static {
		URI producerURI = URI.create("http://hirt.se/junit/");
		PRODUCER = new Producer("JFR Producer for JUnit tests", "These events are produced by the JFRUnit5Extension.",
				producerURI);
		PRODUCER.register();
	}

	public static EventToken register(Class<? extends InstantEvent> clazz) {
		try {
			return PRODUCER.addEvent(clazz);
		} catch (InvalidEventDefinitionException | InvalidValueException e) {
			Logger.getLogger(JFR7Utils.class.getName()).log(Level.SEVERE, "Failed to register the event class "
					+ clazz.getName() + ". Event will not be available. Please check your configuration.", e);
		}
		return null;
	}
}

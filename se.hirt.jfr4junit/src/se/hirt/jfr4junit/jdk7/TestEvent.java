package se.hirt.jfr4junit.jdk7;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.TimedEvent;
import com.oracle.jrockit.jfr.ValueDefinition;

@SuppressWarnings("deprecation")
@EventDefinition(path = "junit/testevent", name = "TestEvent", description = "An event wrapping the execution of a JUnit test.", stacktrace = true, thread = true)
public class TestEvent extends TimedEvent {
	private final static EventToken TOKEN;

    @ValueDefinition(name = "Display Name", description = "The display name for the test.")
	private String displayName;
	
	static {
		TOKEN = JFR7Utils.register(TestEvent.class);
	}
	
	public TestEvent() {
		super(TOKEN);
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}

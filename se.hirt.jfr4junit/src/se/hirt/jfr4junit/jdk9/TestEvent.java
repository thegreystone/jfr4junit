package se.hirt.jfr4junit.jdk9;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

@Label("TestEvent")
@Description("An event wrapping the execution of a JUnit test.")
@Category("junit/")
public class TestEvent extends Event {
	@Label("Display Name")
	@Description("The display name for the test.")
	private String displayName;
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}	
}

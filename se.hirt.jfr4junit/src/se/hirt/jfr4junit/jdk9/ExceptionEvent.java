package se.hirt.jfr4junit.jdk9;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
@Label("ExceptionEvent")
@Description("An event wrapping the execution of a JUnit test throwing an exception an exception.")
@Category("/junit/")
public class ExceptionEvent extends Event {
	@Label("Display Name")
	@Description("The display name for the test.")
	private String displayName;
	
	@Label("Exception Message")
	@Description("The exception message for the exception.")
	private String exceptionMessage;
	
	@Label("Exception class")
	@Description("The class of the exception.")
	private Class<?> exceptionClass;

	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Class<?> getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(Class<?> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}	
}

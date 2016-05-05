package se.hirt.jfr4junit.jdk7;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.TimedEvent;
import com.oracle.jrockit.jfr.ValueDefinition;
import com.oracle.jrockit.jfr.ContentType;

@SuppressWarnings("deprecation")
@EventDefinition(path = "junit/exceptionevent", name = "ExceptionEvent", description = "An event wrapping the execution of a JUnit test throwing an exception an exception.", stacktrace = true, thread = true)
public class ExceptionEvent extends TimedEvent {
	private final static EventToken TOKEN;

	static {
		TOKEN = JFR7Utils.register(ExceptionEvent.class);
	}
	
    @ValueDefinition(name = "Display Name", description = "The display name for the test.")
	private String displayName;
    
    @ValueDefinition(name = "Exception Message", description = "The exception message for the exception.")
	private String exceptionMessage;
		
    @ValueDefinition(name = "Exception class", description = "The class of the exception.", contentType = ContentType.None)
    private String exceptionClass;
    
    public ExceptionEvent() {
    	super(TOKEN);
    }
   
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

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
    
}

package se.hirt.jfr4junit.jdk7;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.gen5.api.extension.TestExtensionContext;
import se.hirt.jfr4junit.JFREmitter;

@SuppressWarnings("deprecation")
public class JDK78EventEmitter implements JFREmitter {
	private final Map<String, TestEvent> eventMap = new ConcurrentHashMap<>();
	private final Map<String, ExceptionEvent> errorMap = new ConcurrentHashMap<>();

	@Override
	public void startEvent(TestExtensionContext ctx) {
		System.out.println("78 - start");
		TestEvent testEvent = new TestEvent();
		ExceptionEvent exceptionEvent = new ExceptionEvent();

		eventMap.put(ctx.getUniqueId(), testEvent);
		errorMap.put(ctx.getUniqueId(), exceptionEvent);

		testEvent.setDisplayName(ctx.getDisplayName());
		exceptionEvent.setDisplayName(ctx.getDisplayName());

		testEvent.begin();
		exceptionEvent.begin();
	}

	@Override
	public void endEvent(TestExtensionContext ctx) {
		System.out.println("78 - end");
		TestEvent testEvent = eventMap.get(ctx.getUniqueId());
		if (testEvent == null) {
			// Exception event...
			return;
		}
		testEvent.commit();
		testEvent.end();
		removeEvent(ctx);
	}

	@Override
	public void endFail(TestExtensionContext ctx, Throwable t) {
		System.out.println("78 - fail");
		ExceptionEvent exceptionEvent = errorMap.get(ctx.getUniqueId());
		if (exceptionEvent == null) {
			// Exception event...
			return;
		}
		exceptionEvent.setExceptionMessage(t.getMessage());
		exceptionEvent.setExceptionClass(exceptionEvent.getClass().getName());
		exceptionEvent.commit();
		exceptionEvent.end();
		errorMap.remove(ctx.getUniqueId());
		TestEvent testEvent = eventMap.get(ctx.getUniqueId());
		// End but not commit. Want either or, not both.
		removeEvent(ctx);
		testEvent.end();
	}

	private void removeEvent(TestExtensionContext ctx) {
		eventMap.remove(ctx.getUniqueId());
	}
}

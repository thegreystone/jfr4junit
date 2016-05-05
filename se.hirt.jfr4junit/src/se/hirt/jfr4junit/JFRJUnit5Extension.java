package se.hirt.jfr4junit;

import org.junit.gen5.api.extension.AfterEachExtensionPoint;
import org.junit.gen5.api.extension.BeforeEachExtensionPoint;
import org.junit.gen5.api.extension.ExceptionHandlerExtensionPoint;
import org.junit.gen5.api.extension.TestExtensionContext;

import se.hirt.jfr4junit.jdk7.JDK78EventEmitter;
import se.hirt.jfr4junit.jdk9.JDK9EventEmitter;

public class JFRJUnit5Extension implements BeforeEachExtensionPoint, AfterEachExtensionPoint, ExceptionHandlerExtensionPoint {
	private static final JFREmitter EMITTER;
	static {
		// Check if there is a JDK 7 or 8 implementation available...
		JFREmitter emitter = null;
		try {
			Class.forName("com.oracle.jrockit.jfr.TimedEvent");
			emitter = new JDK78EventEmitter();
		} catch (ClassNotFoundException e) {
			try {
				Class.forName("jdk.jfr.ValueDescriptor");
				emitter = new JDK9EventEmitter();
			} catch (ClassNotFoundException e1) {
				emitter = new NullEmitter();		
			}
		}
		EMITTER = emitter;
	}
	
	@Override
	public void beforeEach(TestExtensionContext ctx) throws Exception {
		System.out.println("Before:" + ctx);
		EMITTER.startEvent(ctx);
	}

	@Override
	public void afterEach(TestExtensionContext ctx) throws Exception {
		System.out.println("After:" + ctx);
		EMITTER.endEvent(ctx);		
	}

	@Override
	public void handleException(TestExtensionContext ctx, Throwable e) throws Throwable {
		System.out.println("Exception:" + ctx);		
		EMITTER.endFail(ctx, e);		
	}
}

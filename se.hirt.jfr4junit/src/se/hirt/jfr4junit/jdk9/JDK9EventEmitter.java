package se.hirt.jfr4junit.jdk9;

import org.junit.gen5.api.extension.TestExtensionContext;

import se.hirt.jfr4junit.JFREmitter;

public class JDK9EventEmitter implements JFREmitter {

	@Override
	public void startEvent(TestExtensionContext ctx) {
		// <TBD>
	}

	@Override
	public void endEvent(TestExtensionContext ctx) {
		// <TBD>
	}

	@Override
	public void endFail(TestExtensionContext ctx, Throwable t) {
		// <TBD>
	}
}

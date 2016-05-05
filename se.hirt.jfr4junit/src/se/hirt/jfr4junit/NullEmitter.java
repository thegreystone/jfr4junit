package se.hirt.jfr4junit;

import org.junit.gen5.api.extension.TestExtensionContext;

public class NullEmitter implements JFREmitter {

	@Override
	public void startEvent(TestExtensionContext ctx) {
	}

	@Override
	public void endEvent(TestExtensionContext ctx) {
	}

	@Override
	public void endFail(TestExtensionContext ctx, Throwable t) {
	}
}

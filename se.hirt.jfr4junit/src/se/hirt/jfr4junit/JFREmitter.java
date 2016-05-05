package se.hirt.jfr4junit;

import org.junit.gen5.api.extension.TestExtensionContext;

public interface JFREmitter {
	void startEvent(TestExtensionContext ctx);
	void endEvent(TestExtensionContext ctx);
	void endFail(TestExtensionContext ctx, Throwable t);
}

/*
 *  Copyright (C) 2016 Marcus Hirt
 *                     www.hirt.se
 *
 * This software is free:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESSED OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright (C) Marcus Hirt, 2016
 */
package se.hirt.jfr4junit;

import java.util.logging.Logger;

import org.junit.gen5.api.extension.AfterEachCallback;
import org.junit.gen5.api.extension.BeforeEachCallback;
import org.junit.gen5.api.extension.ExceptionHandler;
import org.junit.gen5.api.extension.TestExtensionContext;

/**
 * The JUnit5 extension class.
 * 
 * @author Marcus Hirt
 */
@SuppressWarnings("unchecked")
public class JFRExtension implements BeforeEachCallback, AfterEachCallback, ExceptionHandler {
	private static final JFREmitter EMITTER;

	static {
		// Check if there is a JDK 7 or 8 implementation available...
		JFREmitter emitter = null;
		try {
			Class.forName("com.oracle.jrockit.jfr.TimedEvent");
			Class<? extends JFREmitter> clazz = (Class<? extends JFREmitter>) Class.forName("se.hirt.jfr4junit.jdk7.JDK78EventEmitter");
			emitter = clazz.newInstance();
			getLogger().info("Using JDK7 and 8 compatible event emitter.");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			try {
				Class.forName("jdk.jfr.ValueDescriptor");
				Class<? extends JFREmitter> clazz = (Class<? extends JFREmitter>) Class.forName("se.hirt.jfr4junit.jdk9.JDK9EventEmitter");
				emitter = clazz.newInstance();
				getLogger().info("Using JDK9 compatible event emitter.");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
				emitter = new NullEmitter();
				getLogger().info("Could not find relevant JFR classes. Using NULL emitter. No events will be recorded from the JFRExtension.");
			}
		}
		EMITTER = emitter;
	}

	@Override
	public void beforeEach(TestExtensionContext ctx) throws Exception {
		EMITTER.startEvent(ctx);
	}

	@Override
	public void afterEach(TestExtensionContext ctx) throws Exception {
		EMITTER.endEvent(ctx);
	}

	@Override
	public void handleException(TestExtensionContext ctx, Throwable e) throws Throwable {
		EMITTER.endFail(ctx, e);
	}
	
	private static Logger getLogger() {
		return Logger.getLogger(JFRExtension.class.getName());
	}
}

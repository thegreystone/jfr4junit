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
		ExceptionEvent exceptionEvent = errorMap.get(ctx.getUniqueId());
		if (exceptionEvent == null) {
			// Exception event...
			return;
		}
		exceptionEvent.setExceptionMessage(t.getMessage());
		exceptionEvent.setExceptionClass(exceptionEvent.getClass());
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

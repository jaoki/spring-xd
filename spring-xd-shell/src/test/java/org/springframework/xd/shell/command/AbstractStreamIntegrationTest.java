/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.shell.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;

import org.springframework.xd.shell.AbstractShellIntegrationTest;
import org.springframework.xd.shell.command.fixtures.Disposable;
import org.springframework.xd.shell.command.fixtures.FileSink;
import org.springframework.xd.shell.command.fixtures.FileSource;
import org.springframework.xd.shell.command.fixtures.HttpSource;
import org.springframework.xd.shell.command.fixtures.ImapSource;
import org.springframework.xd.shell.command.fixtures.MailSink;
import org.springframework.xd.shell.command.fixtures.MailSource;
import org.springframework.xd.shell.command.fixtures.TailSource;
import org.springframework.xd.shell.command.fixtures.TcpSink;
import org.springframework.xd.shell.command.fixtures.TcpSource;

/**
 * Provides an @After JUnit lifecycle method that will destroy the definitions that were created by the test.
 * 
 * @author Andy Clement
 * @author Mark Pollack
 * @author Eric Bottard
 */
public abstract class AbstractStreamIntegrationTest extends AbstractShellIntegrationTest {

	private StreamCommandTemplate streamOps;

	private List<Disposable> disposables = new ArrayList<Disposable>();

	private MetricsTemplate metrics;

	public AbstractStreamIntegrationTest() {
		streamOps = new StreamCommandTemplate(getShell());
		metrics = new MetricsTemplate(getShell());
		disposables.add(metrics);
	}

	protected MetricsTemplate metrics() {
		return metrics;
	}

	protected StreamCommandTemplate stream() {
		return streamOps;
	}

	@After
	public void after() {
		stream().destroyCreatedStreams();
		cleanUpDisposables();
	}

	private void cleanUpDisposables() {
		Collections.reverse(disposables);
		for (Disposable disposable : disposables) {
			disposable.cleanup();
		}
	}

	protected TcpSource newTcpSource() {
		return new TcpSource();
	}

	protected TcpSink newTcpSink() {
		TcpSink tcpSink = new TcpSink();
		disposables.add(tcpSink);
		return tcpSink;
	}

	protected FileSink newFileSink() {
		FileSink fileSink = new FileSink();
		disposables.add(fileSink);
		return fileSink;
	}

	protected FileSource newFileSource() {
		FileSource fileSource = new FileSource();
		disposables.add(fileSource);
		return fileSource;
	}

	protected TailSource newTailSource() {
		TailSource tailSource = new TailSource();
		disposables.add(tailSource);
		return tailSource;
	}

	protected MailSource newMailSource() {
		MailSource mailSource = new MailSource();
		disposables.add(mailSource);
		return mailSource;
	}

	protected ImapSource newImapSource() {
		ImapSource imapSource = new ImapSource();
		disposables.add(imapSource);
		return imapSource;
	}

	protected MailSink newMailSink() {
		MailSink mailSink = new MailSink();
		disposables.add(mailSink);
		return mailSink;
	}

	protected HttpSource newHttpSource() {
		return new HttpSource(getShell());
	}

	protected HttpSource newHttpSource(int port) {
		return new HttpSource(getShell(), port);
	}

}

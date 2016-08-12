package br.ufal.ic.mwsn;

import java.util.UUID;

public class DataFrame {
	private UUID id;
	private String header;
	private String body;

	public DataFrame(String header, String body) {
		super();
		this.header = header;
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public UUID getId() {
		return id;
	}

}

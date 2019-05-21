package de.etas.tef.shell;

public interface ShellContentHandler
{
	void handleInput(String content);
	void handleError(String content);
}

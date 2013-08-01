package com.kafeblog.roo.portletmvc;

import org.springframework.roo.model.JavaType;

/**
 * Interface of commands that are available via the Roo shell.
 *
 * @since 1.1.1
 */
public interface PortletmvcOperations {

	boolean isSetupCommandAvailable();
	void performSetupCommand();
	
	boolean isControllerCommandAvailable();
	void performControllerCommand(final JavaType controllerClass);
}
package com.kafeblog.roo.portletmvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.springframework.roo.classpath.operations.AbstractOperations;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.process.manager.MutableFile;
import org.springframework.roo.project.Path;
import org.springframework.roo.project.PathResolver;
import org.springframework.roo.project.ProjectOperations;
import org.springframework.roo.support.util.FileUtils;

/**
 * Implementation of {@link PortletmvcOperations} interface.
 *
 * @since 1.1.1
 */
@Component
@Service
public class PortletmvcOperationsImpl extends AbstractOperations implements PortletmvcOperations {
	@Reference private PathResolver pathResolver;
	private Logger log = Logger.getLogger(getClass().getName());
    
	private static final char SEPARATOR = File.separatorChar;

    @Reference private FileManager fileManager;
    
    /**
     * Get a reference to the ProjectOperations from the underlying OSGi container. Make sure you
     * are referencing the Roo bundle which contains this service in your add-on pom.xml.
     */
    @Reference private ProjectOperations projectOperations;

    
    





	@Override
	public boolean isSetupCommandAvailable() {
    	// web.xml should existed
		String path = pathResolver.getFocusedIdentifier(Path.SRC_MAIN_WEBAPP, "WEB-INF/web.xml");
    	boolean ok = fileManager.exists(path);
    	
    	// webmvc-config.xml should existed
    	path = pathResolver.getFocusedIdentifier(Path.SRC_MAIN_WEBAPP, "WEB-INF/spring/webmvc-config.xml");
    	ok = ok && fileManager.exists(path);
    	
    	// portlet.xml should NOT existed
    	path = pathResolver.getFocusedIdentifier(Path.SRC_MAIN_WEBAPP, "WEB-INF/portlet.xml");
    	ok = ok && !fileManager.exists(path);
    	
    	return ok;
	}




	@Override
	public void performSetupCommand() {
    	// Copy portlet.xml
    	// Add portlet configuration to context
    	// create other required files for portlet
		log.info("Perform Setup Command");
		log.info("Copy portlet.xml");
		
		log.info("Perform Setup Command done");
	}




	@Override
	public boolean isControllerCommandAvailable() {
		boolean ok = fileManager.exists(pathResolver.getFocusedIdentifier(Path.SRC_MAIN_WEBAPP, "WEB-INF/portlet.xml"));
		log.info("isControllerCommandAvailable: " + ok);
		return ok;
	}




	@Override
	public void performControllerCommand(final JavaType controllerClass) {
		// Create controller
		// Create views
		log.info("Perform Controller Command");
	}
}
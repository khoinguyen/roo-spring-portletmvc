package com.kafeblog.roo.portletmvc;

import java.util.logging.Logger;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.project.Path;
import org.springframework.roo.project.PathResolver;
import org.springframework.roo.shell.CliAvailabilityIndicator;
import org.springframework.roo.shell.CliCommand;
import org.springframework.roo.shell.CliOption;
import org.springframework.roo.shell.CommandMarker;
import org.springframework.roo.shell.converters.StaticFieldConverter;

/**
 * This addons provide following commands:
 * + portlet setup			Setup PortletMVC, included: Maven, portlet.xml and other required
 * + portlet controller		Create portlet scaffold, included Controller, Views
 *   
 * @since 1.1.1
 */
@Component // Use these Apache Felix annotations to register your commands class in the Roo container
@Service
public class PortletmvcCommands implements CommandMarker { // All command types must implement the CommandMarker interface
	@Reference private FileManager fileManager;
	@Reference private PathResolver pathResolver;
    /**
     * Get hold of a JDK Logger
     */
    private Logger log = Logger.getLogger(getClass().getName());

    /**
     * Get a reference to the PortletmvcOperations from the underlying OSGi container
     */
    @Reference private PortletmvcOperations operations; 
    
    /**
     * Get a reference to the StaticFieldConverter from the underlying OSGi container;
     * this is useful for 'type save' command tab completions in the Roo shell
     */
    @Reference private StaticFieldConverter staticFieldConverter;

    /**
     * The activate method for this OSGi component, this will be called by the OSGi container upon bundle activation 
     * (result of the 'addon install' command) 
     * 
     * @param context the component context can be used to get access to the OSGi container (ie find out if certain bundles are active)
     */
    protected void activate(ComponentContext context) {
        staticFieldConverter.add(PortletmvcPropertyName.class);
    }

    /**
     * The deactivate method for this OSGi component, this will be called by the OSGi container upon bundle deactivation 
     * (result of the 'addon remove' command) 
     * 
     * @param context the component context can be used to get access to the OSGi container (ie find out if certain bundles are active)
     */
    protected void deactivate(ComponentContext context) {
        staticFieldConverter.remove(PortletmvcPropertyName.class);
    }
    
    // *************************************************************************
    //    Example 1 Printing colored messages to the shell
    // *************************************************************************
    
    /**
     * This method is optional. It allows automatic command hiding in situations when the command should not be visible.
     * For example the 'entity' command will not be made available before the user has defined his persistence settings 
     * in the Roo shell or directly in the project.
     * 
     * You can define multiple methods annotated with {@link CliAvailabilityIndicator} if your commands have differing
     * visibility requirements.
     * 
     * @return true (default) if the command should be visible at this stage, false otherwise
     */
    @CliAvailabilityIndicator("portlet setup")
    public boolean isSetupCommandAvailable() {
    	log.info("Check available for portlet setup");
    	return operations.isSetupCommandAvailable();
    }
    
    @CliCommand(value = "portlet setup", help = "Setup PortletMVC to web component")
    public void performSetupCommand() {
    	operations.performSetupCommand();	
    }
    
    /**
     * Define when "portlet controller" command should be visible in the Roo shell. 
     * Only available if persistence.xml & portlet.xml existed
     * 
     * @return true (default) if the command should be visible at this stage, false otherwise
     */
    @CliAvailabilityIndicator("portlet controller")
    public boolean isControllerCommandAvailable() {
        return operations.isControllerCommandAvailable();
    }
    
    /**
     * Replace existing MVC tagx files in the target project
     */
    @CliCommand(value = "portlet controller", help="Replace default Roo MVC tags used for scaffolding")
    public void performControllerCommand(
    		@CliOption(key = "class", mandatory = true, help = "The class of the controller") final JavaType controllerClass
    		) {
        operations.performControllerCommand(controllerClass);
    }
}
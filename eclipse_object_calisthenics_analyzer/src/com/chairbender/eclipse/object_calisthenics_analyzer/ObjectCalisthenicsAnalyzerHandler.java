package com.chairbender.eclipse.object_calisthenics_analyzer;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.PlatformUI;

import com.chairbender.eclipse.object_calisthenics_analyzer.views.CalisthenicsView;
import com.chairbender.object_calisthenics_analyzer.ObjectCalisthenicsAnalyzer;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ObjectCalisthenicsAnalyzerHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ObjectCalisthenicsAnalyzerHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {		            
        //TODO: Implement wizard for picking a project, then run the inspection and report the
		//results
		return null;		            		           
    }
		
}


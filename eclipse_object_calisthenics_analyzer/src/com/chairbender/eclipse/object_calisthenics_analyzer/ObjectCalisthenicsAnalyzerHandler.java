package com.chairbender.eclipse.object_calisthenics_analyzer;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.chairbender.object_calisthenics_analyzer.ObjectCalisthenicsAnalyzer;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ParseException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

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
		 IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		    if (window != null)
		    {
		        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		        Object firstElement = selection.getFirstElement();
		        if (firstElement instanceof IAdaptable)
		        {
		            IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
		            IPath path = project.getFullPath();
		            
		            try {
						ViolationMonitor results = ObjectCalisthenicsAnalyzer.analyze(path.toFile(), "UTF-8");
						results.getAllViolations();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		            		           
		        }
		    }
		
		
		MessageDialog.openInformation(
				window.getShell(),
				"eclipse-object-calisthenics-analyzer",
				"Hello, Eclipse world");
		return null;
	}
}

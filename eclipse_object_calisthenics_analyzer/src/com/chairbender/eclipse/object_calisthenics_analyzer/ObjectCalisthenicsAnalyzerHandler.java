package com.chairbender.eclipse.object_calisthenics_analyzer;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.chairbender.eclipse.object_calisthenics_analyzer.views.CalisthenicsView;
import com.chairbender.object_calisthenics_analyzer.ObjectCalisthenicsAnalyzer;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ParseException;

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
		//create a wizard to choose the project to analyze
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		ContainerSelectionDialog dialog =
				     new ContainerSelectionDialog(shell, null, false, "Select Folder Containing Source Code to Analyze");
	 	dialog.open();
	 	Object[] result = dialog.getResult();
	 	if (result.length > 0 && result[0] instanceof Path) {
	 		Path selection = (Path) result[0];
	 		//Run a new analysis, using the path as the file
	 		ViolationMonitor results;
			try {
				results = ObjectCalisthenicsAnalyzer.analyze(ResourcesPlugin.getWorkspace().getRoot().getLocation().append(selection).toFile(), "UTF-8");
				CalisthenicsView resultsView = (CalisthenicsView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(CalisthenicsView.ID);				
				resultsView.updateResults(results);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 	}

		return null;		            		           
    }
		
}


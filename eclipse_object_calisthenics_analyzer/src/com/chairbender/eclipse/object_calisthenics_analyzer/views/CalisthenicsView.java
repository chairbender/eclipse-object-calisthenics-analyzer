package com.chairbender.eclipse.object_calisthenics_analyzer.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;

import com.chairbender.eclipse.object_calisthenics_analyzer.provider.content.CalisthenicsReportContentProvider;
import com.chairbender.object_calisthenics_analyzer.ObjectCalisthenicsAnalyzer;
import com.chairbender.object_calisthenics_analyzer.violation.Violation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.chairbender.object_calisthenics_analyzer.violation.model.ViolationCategory;
import com.github.javaparser.ParseException;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class CalisthenicsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.chairbender.eclipse.object_calisthenics_analyzer.views.CalisthenicsView";	
	private ListViewer listViewer;

	@Override
	public void createPartControl(Composite parent) {		
		//create a list viewer and add all violations to it
		try {
			ViolationMonitor results = ObjectCalisthenicsAnalyzer.analyze(new File("C:/Programming/slackbot-resistance"), "UTF-8");
			
			//create a list viewer and add all violations to it
			listViewer = new ListViewer(parent);
			listViewer.setContentProvider(new CalisthenicsReportContentProvider());
			listViewer.setLabelProvider(new LabelProvider());
			getSite().setSelectionProvider(listViewer);
			listViewer.setInput(results);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setFocus() {
		listViewer.getControl().setFocus();
		
	}
}

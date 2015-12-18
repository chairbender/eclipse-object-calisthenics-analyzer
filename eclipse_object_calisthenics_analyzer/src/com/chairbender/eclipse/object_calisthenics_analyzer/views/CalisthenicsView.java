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

public class CalisthenicsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.chairbender.eclipse.object_calisthenics_analyzer.views.CalisthenicsView";	
	private ListViewer listViewer;
	
	public void updateResults(ViolationMonitor results) {			
		listViewer.setInput(results);
	}

	@Override
	public void createPartControl(Composite parent) {		
		listViewer = new ListViewer(parent);
		listViewer.setContentProvider(new CalisthenicsReportContentProvider());
		listViewer.setLabelProvider(new LabelProvider());
		getSite().setSelectionProvider(listViewer);				
	}

	@Override
	public void setFocus() {
		listViewer.getControl().setFocus();		
	}
}

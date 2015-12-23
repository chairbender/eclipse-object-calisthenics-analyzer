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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.ui.texteditor.GotoLineAction;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.core.resources.IWorkspaceRoot;

//TODO: Improve this UI. Make the rules and violations more visually separated. Let rules and suggestions be on separate lines
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
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				@SuppressWarnings("unused")
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				Object selectedObject = selection.getFirstElement();
				if (selectedObject instanceof Violation) {									
					Violation violation = (Violation) selectedObject;
					//create a relative path because, stupidly, openEditor fails if you use an absolute IPath 					
					IPath path = new Path(violation.getSourceFile().getAbsolutePath());
					IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
					path = path.makeRelativeTo(workspacePath);
					IFile toOpen = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
					try {
						//always open a new tab
						IWorkbenchPage curPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IEditorDescriptor desc = PlatformUI.getWorkbench().
						        getEditorRegistry().getDefaultEditor(toOpen.getName());
						IEditorPart editorPart = IDE.openEditor(curPage, toOpen, true);
						if (editorPart instanceof ITextEditor) {						
							ITextEditor textEditor= (ITextEditor)editorPart;														
							IDocumentProvider provider= textEditor.getDocumentProvider();
							IDocument document= textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
							
							int start= document.getLineOffset(violation.getViolationLocation().getBeginLine()-1);
							textEditor.selectAndReveal(start, 0);
							
							IWorkbenchPage page= editorPart.getSite().getPage();
							page.activate(editorPart);
											
						}					
					} catch (BadLocationException x) {
						// ignore
					} catch (PartInitException e) {
						// ignore;
					}
				}
			}			
		});
	}

	@Override
	public void setFocus() {
		listViewer.getControl().setFocus();		
	}
}

package com.chairbender.eclipse.object_calisthenics_analyzer.provider.content;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;

/**
 * content provider for a list of violations
 *
 */
public class CalisthenicsReportContentProvider implements IStructuredContentProvider {
	private ViolationMonitor violations; 	
	private Viewer viewer;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
		if (newInput instanceof ViolationMonitor) {
			this.violations = (ViolationMonitor) newInput;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement == violations) {
			return violations.getAllViolations().values().toArray();
		} else {
			return new Object[0];
		}
	}

}

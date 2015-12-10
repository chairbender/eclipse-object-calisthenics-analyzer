package com.chairbender.eclipse.object_calisthenics_analyzer.provider.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.chairbender.object_calisthenics_analyzer.violation.Violation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.chairbender.object_calisthenics_analyzer.violation.model.ViolationCategory;

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
			Map<ViolationCategory,List<Violation>> violationMap = violations.getAllViolations();
			List<ViolationCategory> orderedViolations = new ArrayList<ViolationCategory>(violationMap.keySet());
			Collections.sort(orderedViolations);
			List<Object> result = new ArrayList<Object>();			
			for (ViolationCategory category : orderedViolations) {
				result.add(category.getRuleInfo().describe());
				for (Violation violation : violationMap.get(category)) {
					result.add(violation.toString());
				}
			}
			return result.toArray();
		} else {
			return new Object[0];
		}
	}

}

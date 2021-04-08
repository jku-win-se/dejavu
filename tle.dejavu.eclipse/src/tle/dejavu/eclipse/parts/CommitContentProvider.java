package tle.dejavu.eclipse.parts;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import tle.dejavu.eclipse.visualizer.CommitPool;

public class CommitContentProvider implements IStructuredContentProvider {

	private CommitPool commitPool;
	private String selected = "";

	public CommitContentProvider(CommitPool commitPool) {
		this.commitPool = commitPool;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return commitPool.get(selected).toArray();
	}

	public void setSelection(String selected) {
		this.selected = selected;

	}

}

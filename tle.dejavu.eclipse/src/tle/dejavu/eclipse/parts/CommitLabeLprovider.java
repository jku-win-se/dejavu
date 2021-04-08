package tle.dejavu.eclipse.parts;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.swt.graphics.Image;

import tle.dejavu.eclipse.visualizer.CommitEntry;
import tle.dejavu.eclipse.visualizer.util.FormatUtil;

public class CommitLabeLprovider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object entry, int col) {

		CommitEntry elem = (CommitEntry) entry;
		switch (col) {

		case 0:
			long time = elem.getCommitTime();
			return FormatUtil.formatTimestamp(time);

		case 1:
			return elem.getCommitterName();
		case 2:

			String msg = elem.getMessage();
			int brk = msg.indexOf("\n");
			if (brk > 0) {
				msg = msg.substring(0, brk);
			}

			return msg;
		}

		return entry.toString();
	}

}

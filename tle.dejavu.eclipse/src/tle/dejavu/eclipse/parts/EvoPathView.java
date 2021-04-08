package tle.dejavu.eclipse.parts;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.gef.dot.internal.ui.DotGraphView;
import org.eclipse.gef.graph.Node;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.framework.Bundle;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tle.dejavu.eclipse.visualizer.CommitEntry;
import tle.dejavu.eclipse.visualizer.CommitPool;

import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

public class EvoPathView {
	private static final String TLE_PROJECT = "TLEScenarios";

	@Inject
	ESelectionService selectionService;

	Device device = Display.getCurrent();
	Font font = new Font(device, "Arial", 12, 0);

	private Button btnOpenSelection;

	private IFile fileToOpen;
	private Text txt_filename;
	private Text txt_location;

	private CCombo cmb_scenarios;
	private List<IFile> scenarios = new ArrayList<IFile>();

	private CommitPool commitPool;

	private CommitContentProvider cb;

	private Link lblJira;

	public static final transient Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls()
			.setDateFormat(DateFormat.LONG).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
			.setVersion(1.0).serializeSpecialFloatingPointValues().serializeSpecialFloatingPointValues().create();

	private static final String ROOT_NAME = "apache-cassandra";

	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		System.out.println("Enter in SampleE4View postConstruct");
		parent.setLayout(new GridLayout(1, false));

		Bundle bundle = Platform.getBundle("net.mv.mona.evopathviz.eclipse");
		URL fileURL = bundle.getEntry("gitstore/git_cassandra.txt");

		try {

			List<String> lines = Files.readAllLines(Paths.get(FileLocator.resolve(fileURL).toURI()));

			String joined = String.join("", lines);

			commitPool = GSON.fromJson(joined, CommitPool.class);

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}

		sashForm = new SashForm(parent, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		composite = new Composite(sashForm, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(2, false));

		composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setLayout(new GridLayout(1, false));
		composite_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));

		lblNewLabel_2 = new Label(composite_1, SWT.FLAT);
		lblNewLabel_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Noto Sans Balinese", 16, SWT.NORMAL));
		lblNewLabel_2.setText("DejaVu");

		cmb_scenarios = new CCombo(composite, SWT.BORDER);
		cmb_scenarios.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(cmb_scenarios);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("Filename");

		txt_filename = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).span(1, 1).applyTo(txt_filename);

		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setText("Location");

		txt_location = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).span(1, 1).applyTo(txt_location);

		btnOpenSelection = new Button(composite, SWT.PUSH);
		btnOpenSelection.setText("Inspect Selected Element");
		btnOpenSelection.setEnabled(false);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(btnOpenSelection);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setText("Related Github Commits:");
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(lblNewLabel_3);

		Composite jira = new Composite(composite, SWT.FLAT);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(jira);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(jira);

		lblNewLabel_4 = new Label(jira, SWT.NONE);
		lblNewLabel_4.setText("Related Jira Issues:");
		GridDataFactory.fillDefaults().grab(true, false).span(1, 1).applyTo(lblNewLabel_4);

		lblJira = new Link(jira, SWT.NONE);
		lblJira.setText("<a>no related issue...</a>");
		GridDataFactory.fillDefaults().grab(true, false).span(1, 1).applyTo(lblJira);

		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		// table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(table);

		cb = new CommitContentProvider(commitPool);

		TableViewerColumn col1 = new TableViewerColumn(tableViewer, SWT.FLAT);
		col1.getColumn().setWidth(150);

		TableViewerColumn col2 = new TableViewerColumn(tableViewer, SWT.FLAT);
		col2.getColumn().setWidth(250);

		TableViewerColumn col3 = new TableViewerColumn(tableViewer, SWT.FLAT);
		col3.getColumn().setWidth(550);

		tableViewer.setContentProvider(cb);
		tableViewer.setLabelProvider(new CommitLabeLprovider());
		tableViewer.setInput("");

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				openGitBrowser();

			}
		});

		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				setSelectedCommit();

			}
		});

		lblJira.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				openJiraBrowser();

			}
		});

		btnOpenSelection.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openSelectedFile();
			}

		});

		sashForm.setWeights(new int[] { 1 });

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();

		IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = ws.getProject("test");
		System.out.println("Pro: " + project.exists());
		IResource[] files = null;
		try {
			files = project.getFolder("src").getFolder("test").members();
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		updateScenarios();

	}

	protected void setSelectedCommit() {
		Object selected = ((StructuredSelection) tableViewer.getSelection()).getFirstElement();
		if (selected == null) {
			return;
		}
		CommitEntry entry = ((CommitEntry) selected);

		List<String> issues = new ArrayList(entry.getJiraids());

		if (issues.size() > 0) {
			lblJira.setText("<a>" + issues.get(0) + "</a>");
		} else {
			lblJira.setText("no jira lik available");
		}

	}

	protected void openGitBrowser() {

		Object selected = ((StructuredSelection) tableViewer.getSelection()).getFirstElement();
		if (selected == null) {
			return;
		}
		String commitid = ((CommitEntry) selected).getCommitId();

		String url = "https://github.com/apache/cassandra/commit/" + commitid;
		BrowserWrapper.opoen(url);

	}

	protected void openJiraBrowser() {

		Object selected = ((StructuredSelection) tableViewer.getSelection()).getFirstElement();
		if (selected == null) {
			return;
		}
		Object commitid = new ArrayList(((CommitEntry) selected).getJiraids()).get(0);

		String url = commitPool.getJiraURL() + commitid.toString();
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				BrowserWrapper.opoen(url);

			}
		});

	}

	private void updateScenarios() {
		IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = ws.getProject(TLE_PROJECT);

		try {
			project.accept(new IResourceProxyVisitor() {

				@Override
				public boolean visit(IResourceProxy proxy) throws CoreException {
					if (proxy.getType() == IResource.FILE) {
						if (proxy.getName().endsWith(".dot")) {
							scenarios.add((IFile) proxy.requestResource());
						}

					}
					return true;
				}
			}, IResource.DEPTH_ONE);

		} catch (CoreException e) {
			e.printStackTrace();
		}

		for (IFile f : scenarios) {
			cmb_scenarios.add(f.getName());
		}
		cmb_scenarios.setText("select change scenario");
		cmb_scenarios.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openSelectedScenario();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	protected void openSelectedScenario() {
		IFile selection = scenarios.get(cmb_scenarios.getSelectionIndex());
		openScenarioFile(selection);
	}

	@Inject
	public void updateSelection(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Object p) {
		if (p != null) {

			if (p instanceof StructuredSelection) {
				Object selected = ((StructuredSelection) p).getFirstElement();
				// System.out.println(selected.getClass());

				if (selected instanceof org.eclipse.gef.graph.Node) {
					org.eclipse.gef.graph.Node node = (Node) selected;
					// System.out.println(node.getAttributes());
					triggerSelection(node);

				}
			}

		}
	}

	private void triggerSelection(Node node) {
		Object box = node.getAttributes().get("node-shape");
		if (box instanceof VBox) {
			Object text = findTextChild((VBox) box, 0, new AtomicInteger(0));
			Object text2 = findTextChild((VBox) box, 2, new AtomicInteger(0));
			String txt = "undef";
			String version = "undef";
			if (text instanceof javafx.scene.text.Text) {
				txt = ((javafx.scene.text.Text) text).getText();

			}

			if (text2 instanceof javafx.scene.text.Text) {
				version = ((javafx.scene.text.Text) text2).getText();
			}
			setItemSelected(txt, version);
		}
	}

	private void setItemSelected(String filename, String version) {
		txt_filename.setText(filename);
		findFile(filename, version);

		if (fileToOpen != null) {
			txt_location.setEnabled(true);
			txt_location.setText(fileToOpen.getParent().getFullPath().toString());

		} else {
			txt_location.setText("elemnt not found in workspace!");
			txt_location.setEnabled(false);
		}

		btnOpenSelection.setEnabled(fileToOpen != null);
		loadGitInfo(filename);

	}

	private void loadGitInfo(String filename) {

		String nm = filename + ".java";
		Set commits = commitPool.get(nm);
		cb.setSelection(nm);
		System.out.println("FOUND :" + commits.size());
		tableViewer.refresh();
		tableViewer.getTable().getParent().layout();

	}

	String VIEW_ID = "org.eclipse.gef.dot.internal.ui.DotGraphView";
	private SashForm sashForm;
	private Composite composite;
	private Label lblNewLabel_2;
	private Composite composite_1;
	private Table table;
	private TableViewer tableViewer;
	private Label lblNewLabel_3;
	private Label lblNewLabel_4;

	private void openScenarioFile(IFile file) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();

		IViewDescriptor[] views = workbench.getViewRegistry().getViews();

		try {

			IViewPart viewer = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(VIEW_ID);
			Method method = viewer.getClass().getDeclaredMethod("updateGraph", IFile.class);
			method.setAccessible(true);
			method.invoke(viewer, file);

		} catch (PartInitException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void openSelectedFile() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		FileEditorInput fep = new FileEditorInput(fileToOpen);
		try {
			page.openEditor(fep, "org.eclipse.jdt.ui.CompilationUnitEditor");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Object findTextChild(VBox box, int debth, AtomicInteger count) {
		if (box instanceof VBox) {
			ObservableList<javafx.scene.Node> children = ((VBox) box).getChildren();
			for (javafx.scene.Node n : children) {
				// System.out.println(n.getClass());
				if (n instanceof VBox) {
					if (count.get() == debth) {
						return findTextChild((VBox) n, debth, count);
					}
					count.getAndIncrement();

				} else if (n instanceof HBox) {
					Object found = findTextChild((HBox) n, debth, count);
					if (found != null) {
						return found;
					}
					continue;
				} else if (n instanceof javafx.scene.text.Text) {
					return n;
				} else {
					System.out.println(n.getClass());
				}
			}
			return null;
		}
		return box;
	}

	private Object findTextChild(HBox box, int debth, AtomicInteger count) {
		if (box instanceof HBox) {
			ObservableList<javafx.scene.Node> children = ((HBox) box).getChildren();
			for (javafx.scene.Node n : children) {
				// System.out.println(n.getClass());
				if (n instanceof VBox) {
					return findTextChild((VBox) n, debth, count);
				} else if (n instanceof HBox) {
					return findTextChild((HBox) n, debth, count);
				} else if (n instanceof javafx.scene.text.Text) {
					return n;
				} else {
					System.out.println(n.getClass());
				}
			}
			// System.out.println("BOX found");
			return null;
		}
		return box;
	}

	private void findFile(String filename, String version) {
		IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();

		String projectname = ROOT_NAME + "-" + version;

		IProject project = ws.getProject(projectname);
		fileToOpen = null;
		try {
			project.accept(new IResourceProxyVisitor() {

				@Override
				public boolean visit(IResourceProxy proxy) throws CoreException {
					if (proxy.getName().startsWith(filename)) {
						fileToOpen = (IFile) proxy.requestResource();
						return false;
					}
					return true;
				}
			}, IResource.DEPTH_INFINITE);

		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	@Focus
	public void setFocus() {
		// lblSelectedElement.setFocus();

	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not mix
	 * E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and you
	 * do not receive a ISelection
	 * 
	 * @param s the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {

	}

	/**
	 * This method manages the selection of your current object. In this example we
	 * listen to a single Object (even the ISelection already captured in E3 mode).
	 * <br/>
	 * You should change the parameter type of your received Object to manage your
	 * specific selection
	 * 
	 * @param o : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage your
	 * specific selection
	 * 
	 * @param o : the current array of objects received in case of multiple
	 *          selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

	}
}

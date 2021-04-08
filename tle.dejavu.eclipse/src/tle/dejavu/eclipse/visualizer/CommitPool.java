package tle.dejavu.eclipse.visualizer;

import java.util.Set;

import tle.dejavu.eclipse.visualizer.util.ManagedHashTableSet;

public class CommitPool {

	ManagedHashTableSet<String, CommitEntry> commitPool;
	private String jiraurl;

	public CommitPool() {
		commitPool = new ManagedHashTableSet<String, CommitEntry>();
	}

	public void add(String filename, CommitEntry commitentry) {
		commitPool.add(filename, commitentry);

	}

	public Set<CommitEntry> get(String fn) {
		return commitPool.get(fn);
	}

	public void setJiraURL(String jiraurl) {
		this.jiraurl = jiraurl;

	}

	public String getJiraURL() {
		return jiraurl;
	}

}

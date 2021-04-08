package tle.dejavu.eclipse.visualizer;

import java.util.HashSet;
import java.util.Set;

import tle.dejavu.eclipse.visualizer.util.FormatUtil;

public class CommitEntry {

	private String status;
	private String filename;
	private String timestamp;
	private String fileStatus;
	private String committerName;
	private String commitid;
	private Set<String> files;

	private String message;
	private long committime;
	private Set<String> jiraids;

	public CommitEntry(String commitid) {
		this.commitid = commitid;
		jiraids = new HashSet<String>();
		files = new HashSet<>();

	}

	public String getFilename() {
		return filename;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;

	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public String getCommitterName() {
		return committerName;
	}

	public void setCommitterName(String committerName) {
		this.committerName = committerName;
	}

	public String getCommitId() {
		return commitid;
	}

	public void setMessage(String message) {
		this.message = message;

	}

	public void addFile(String filename) {
		files.add(filename);

	}

	public void setCommitTime(long time) {
		this.committime = time;

	}

	public long getCommitTime() {
		return committime;
	}

	public void setJiraIds(Set<String> ids) {
		jiraids.addAll(ids);

	}

	public String getMessage() {
		return message;
	}

	public Set<String> getJiraids() {
		return jiraids;
	}

}

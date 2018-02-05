package ideas.server;

public class Server {
	private String serverName;
	private String softwareCategory;
	private String softwareName;
	private String softwareVersion;

	public Server(String serverName, String softwareCategory, String softwareName, String softwareVersion) {
		super();
		this.serverName = serverName;
		this.softwareCategory = softwareCategory;
		this.softwareName = softwareName;
		this.softwareVersion = softwareVersion;
	}

	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getSoftwareCategory() {
		return softwareCategory;
	}
	public void setSoftwareCategory(String softwareCategory) {
		this.softwareCategory = softwareCategory;
	}
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
}

package io.spring.stats;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Simon Baslé
 */
@ConfigurationProperties(prefix = "stats")
public class StatsProperties {

	private String groupId;

	private String artifactId;

	private String dependencyId;

	private int year;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getDependencyId() {
		return dependencyId;
	}

	public void setDependencyId(String dependencyId) {
		this.dependencyId = dependencyId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}

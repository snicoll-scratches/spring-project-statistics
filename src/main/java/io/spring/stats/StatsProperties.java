package io.spring.stats;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for project statistics runner.
 *
 * @author Simon Baslé
 */
@ConfigurationProperties(prefix = "stats")
public class StatsProperties {

	/**
	 * GroupId of the module to consider for Maven Central downloads.
	 */
	private String groupId;

	/**
	 * ArtifactId of the module to consider for Maven Central downloads.
	 */
	private String artifactId;

	/**
	 * Dependency id of an entry on start.spring.io to consider for project generation
	 * statistics.
	 */
	private String dependencyId;

	/**
	 * Year to use.
	 */
	private int year = 2020;

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

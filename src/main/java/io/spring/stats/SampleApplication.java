package io.spring.stats;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

@SpringBootApplication
@EnableConfigurationProperties(StatsProperties.class)
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Bean
	public ApplicationRunner statisticsRunner(MavenCentralStatistics mavenCentralStatistics,
			ProjectCreationStatistics projectCreationStatistics, StatsProperties statsProperties) {
		return args -> {
			int year = statsProperties.getYear();

			String groupId = statsProperties.getGroupId();
			String artifactId = statsProperties.getArtifactId();
			if (StringUtils.hasText(groupId) && StringUtils.hasText(artifactId)) {
				System.out.printf("Maven Central statistics for '%s:%s':%n", groupId, artifactId);
				for (int month = 1; month <= 12; month++) {
					System.out.printf("%s-%02d - %s%n", year, month,
							mavenCentralStatistics.getMonthlyDownloadCount(groupId, artifactId, year, month));
				}
			}

			String dependencyId = statsProperties.getDependencyId();
			if (StringUtils.hasText(dependencyId)) {
				System.out.printf("start.spring.io generation statistics for projects with entry '%s':%n",
						dependencyId);
				for (int month = 1; month <= 12; month++) {
					System.out.printf("%s-%02d - %s%n", year, month,
							projectCreationStatistics.getMonthlyProjectCreationCount(dependencyId, year, month));
				}
			}
		};
	}

}

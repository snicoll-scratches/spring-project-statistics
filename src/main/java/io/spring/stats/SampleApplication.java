package io.spring.stats;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Bean
	public ApplicationRunner statisticsRunner(MavenCentralStatistics mavenCentralStatistics,
			ProjectCreationStatistics projectCreationStatistics) {
		return args -> {
			String projectId = "spring-batch";
			String[] artifactIds = {"spring-batch-core"};
			String[] dependencyIds = {"batch"};
			int year = 2020;
			System.out.printf("Maven Central statistics for %s (%s):%n", projectId, String.join(", ", artifactIds));
			for (int month = 1; month <= 12; month++) {
				long total = 0;
				for (String artifactId: artifactIds) {
					total = total + mavenCentralStatistics.getMonthlyDownloadCount(projectId, artifactId, year, month);
				}
				System.out.printf("%s-%02d - %s%n", year, month, total);
			}
			System.out.printf("Project Generation statistics for %s (%s):%n", projectId, String.join(", ", dependencyIds));

			for (int month = 1; month <= 12; month++) {
				int total = 0;
				for (String dependencyId : dependencyIds) {
					total += projectCreationStatistics.getMonthlyProjectCreationCount(dependencyId, year, month);
				}
				System.out.printf("%s-%02d - %s%n", year, month, total);
			}
		};
	}

}

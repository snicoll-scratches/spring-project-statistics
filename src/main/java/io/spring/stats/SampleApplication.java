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
			String artifactId = "spring-batch-core";
			String dependencyId = "batch";
			int year = 2020;
			System.out.printf("Maven Central statistics for %s (%s):%n", projectId, artifactId);
			for (int month = 1; month <= 12; month++) {
				System.out.printf("%s-%02d - %s%n", year, month,
						mavenCentralStatistics.getMonthlyDownloadCount(projectId, artifactId, year, month));
			}
			System.out.printf("Project Generation statistics for %s (%s):%n", projectId, dependencyId);
			for (int month = 1; month <= 12; month++) {
				System.out.printf("%s-%02d - %s%n", year, month,
						projectCreationStatistics.getMonthlyProjectCreationCount(dependencyId, year, month));
			}
		};
	}

}

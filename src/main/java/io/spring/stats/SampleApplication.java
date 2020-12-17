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
			int year = 2020;
			System.out.println("Maven Central statistics for Spring Batch (spring-batch-core):");
			for (int month = 1; month <= 12; month++) {
				System.out.printf("%s-%02d - %s%n", year, month, mavenCentralStatistics.getMonthlyDownloadCount("spring-batch", "spring-batch-core", year, month));
			}
			System.out.println("Project Generation statistics for Spring Batch:");
			for (int month = 1; month <= 12; month++) {
				System.out.printf("%s-%02d - %s%n", year, month, projectCreationStatistics.getMonthlyProjectCreationCount("batch", year, month));
			}
		};
	}

}

package io.spring.stats;

import java.time.LocalDate;
import java.time.Period;

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
			Period quarter = Period.ofMonths(3);
			System.out.println("Maven Central statistics for Spring Batch (spring-batch-core):");
			System.out.println("Q1 - " + mavenCentralStatistics.getDownloadCount(
					"spring-batch", "spring-batch-core", LocalDate.of(2020, 1, 1), quarter));
			System.out.println("Q2 - " + mavenCentralStatistics.getDownloadCount(
					"spring-batch", "spring-batch-core", LocalDate.of(2020, 4, 1), quarter));
			System.out.println("Q3 - " + mavenCentralStatistics.getDownloadCount(
					"spring-batch", "spring-batch-core", LocalDate.of(2020, 7, 1), quarter));
			System.out.println("Q4 - " + mavenCentralStatistics.getDownloadCount(
					"spring-batch", "spring-batch-core", LocalDate.of(2020, 10, 1), quarter));
			System.out.println("Project Generation statistics for Spring Batch:");
			System.out.println("Batch Q1 - " + projectCreationStatistics.getProjectCreationCount(
					"batch", LocalDate.of(2020, 1, 1), quarter));
			System.out.println("Batch Q2 - " + projectCreationStatistics.getProjectCreationCount(
					"batch", LocalDate.of(2020, 4, 1), quarter));
			System.out.println("Batch Q3 - " + projectCreationStatistics.getProjectCreationCount(
					"batch", LocalDate.of(2020, 7, 1), quarter));
			System.out.println("Batch Q4 - " + projectCreationStatistics.getProjectCreationCount(
					"batch", LocalDate.of(2020, 10, 1), quarter));
		};
	}

}

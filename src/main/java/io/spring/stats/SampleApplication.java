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
			LocalDate q1 = LocalDate.of(2020, 1, 1);
			LocalDate q2 = q1.plus(quarter);
			LocalDate q3 = q2.plus(quarter);
			LocalDate q4 = q3.plus(quarter);
			System.out.println("Maven Central statistics for Spring Batch (spring-batch-core):");
			System.out.println("Q1 - " + mavenCentralStatistics.getDownloadCount("spring-batch", "spring-batch-core", q1, quarter));
			System.out.println("Q2 - " + mavenCentralStatistics.getDownloadCount("spring-batch", "spring-batch-core", q2, quarter));
			System.out.println("Q3 - " + mavenCentralStatistics.getDownloadCount("spring-batch", "spring-batch-core", q3, quarter));
			System.out.println("Q4 - " + mavenCentralStatistics.getDownloadCount("spring-batch", "spring-batch-core", q4, quarter));
			System.out.println("Project Generation statistics for Spring Batch:");
			System.out.println("Q1 - " + projectCreationStatistics.getProjectCreationCount("batch", q1, quarter));
			System.out.println("Q2 - " + projectCreationStatistics.getProjectCreationCount("batch", q2, quarter));
			System.out.println("Q3 - " + projectCreationStatistics.getProjectCreationCount("batch", q3, quarter));
			System.out.println("Q4 - " + projectCreationStatistics.getProjectCreationCount("batch", q4, quarter));
		};
	}

}

package io.spring.stats;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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

			System.out.printf("Maven Central statistics for '%s:%s':%n", statsProperties.getGroupId(),
					statsProperties.getArtifactId());
			for (int month = 1; month <= 12; month++) {
				System.out.printf("%s-%02d - %s%n", statsProperties.getYear(), month,
						mavenCentralStatistics.getMonthlyDownloadCount(statsProperties.getGroupId(),
								statsProperties.getArtifactId(), statsProperties.getYear(), month));
			}
			System.out.printf("start.spring.io generation statistics for projects with entry '%s':%n",
					statsProperties.getDependencyId());
			for (int month = 1; month <= 12; month++) {
				System.out.printf("%s-%02d - %s%n", statsProperties.getYear(), month,
						projectCreationStatistics.getMonthlyProjectCreationCount(statsProperties.getDependencyId(),
								statsProperties.getYear(), month));
			}
		};
	}

}

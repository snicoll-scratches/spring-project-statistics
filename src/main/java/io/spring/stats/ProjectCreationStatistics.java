package io.spring.stats;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.stereotype.Component;

/**
 * Provide project generation statistics using our Elasticsearch backend.
 *
 * @author Stephane Nicoll
 */
@Component
public class ProjectCreationStatistics {

	private static final Period MONTHLY = Period.ofMonths(1);

	private final RestHighLevelClient client;

	public ProjectCreationStatistics(RestHighLevelClient client) {
		this.client = client;
	}

	/**
	 * Return the number of projects including the specified {@code dependency} over the
	 * specified period.
	 * @param dependency the id of the dependency
	 * @param year the year
	 * @param month the month of year
	 * @return the number of projects created with the specified dependency for the
	 * specified month
	 * @throws IOException if the backend failed to respond
	 */
	public long getMonthlyProjectCreationCount(String dependency, int year, int month) throws IOException {
		return getProjectCreationCount(dependency, LocalDate.of(year, month, 1), MONTHLY);
	}

	/**
	 * Return the number of projects including the specified {@code dependency} over the
	 * specified period.
	 * @param dependency the id of the dependency
	 * @param start the start period
	 * @param period the period to cover
	 * @return the number of projects created at the specified {@code start} date for the
	 * specified {@code period}
	 * @throws IOException if the backend failed to respond
	 */
	public long getProjectCreationCount(String dependency, LocalDate start, Period period) throws IOException {
		long startTimestamp = toEpoch(start);
		long endTimestamp = toEpoch(start.plus(period));
		CountRequest countRequest = new CountRequest("initializr");
		countRequest.query(query(dependency, startTimestamp, endTimestamp));
		CountResponse count = this.client.count(countRequest, RequestOptions.DEFAULT);
		return count.getCount();
	}

	private QueryBuilder query(String dependency, long from, long to) {
		return QueryBuilders.boolQuery().filter(new MatchQueryBuilder("dependencies.values", dependency))
				.filter(QueryBuilders.rangeQuery("generationTimestamp").gte(from).lt(to));

	}

	private static long toEpoch(LocalDate time) {
		return time.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
	}

}

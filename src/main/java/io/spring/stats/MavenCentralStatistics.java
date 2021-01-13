package io.spring.stats;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.springframework.stereotype.Component;

/**
 * Provide Maven Central download statistics using our Elasticsearch backend.
 *
 * @author Stephane Nicoll
 */
@Component
public class MavenCentralStatistics {

	private static final Period MONTHLY = Period.ofMonths(1);

	private final RestHighLevelClient client;

	public MavenCentralStatistics(RestHighLevelClient client) {
		this.client = client;
	}

	/**
	 * Return the downloads count for the specified module and the specified month
	 * @param groupId the groupId of the module
	 * @param artifactId the artifactId of the module
	 * @param year the year
	 * @param month the month of year
	 * @return the downloads of the specified module for the specified month
	 * @throws IOException if the backend failed to respond
	 */
	public long getMonthlyDownloadCount(String groupId, String artifactId, int year, int month) throws IOException {
		return getDownloadCount(groupId, artifactId, LocalDate.of(year, month, 1), MONTHLY);
	}

	/**
	 * Return the downloads count for the specified module over the specified period.
	 * @param groupId the groupId of the module
	 * @param artifactId the artifactId of the module
	 * @param start the start period (always the first day of the month)
	 * @param period the period to cover
	 * @return the downloads starting at the specified {@code start} date for the
	 * specified {@code period}
	 * @throws IOException if the backend failed to respond
	 */
	public long getDownloadCount(String groupId, String artifactId, LocalDate start, Period period) throws IOException {
		long startTimestamp = toEpoch(start.withDayOfMonth(1));
		long endTimestamp = toEpoch(start.plus(period));
		SearchRequest searchRequest = new SearchRequest("downloads");
		searchRequest.source(new SearchSourceBuilder().query(query(groupId, artifactId, startTimestamp, endTimestamp))
				.aggregation(AggregationBuilders.sum("downloads").field("count")));
		SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
		ParsedSum downloads = response.getAggregations().get("downloads");
		return (long) downloads.getValue();
	}

	private QueryBuilder query(String groupId, String artifactId, long from, long to) {
		return QueryBuilders.boolQuery().filter(new MatchQueryBuilder("groupId", groupId))
				.filter(new MatchQueryBuilder("artifactId", artifactId))
				.filter(QueryBuilders.rangeQuery("from").gte(from)).filter(QueryBuilders.rangeQuery("to").lt(to));

	}

	private static long toEpoch(LocalDate time) {
		return time.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
	}

}

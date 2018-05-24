package com.netcracker.checkapp.server.persistance.customs;

import com.netcracker.checkapp.server.model.DateStats;
import com.netcracker.checkapp.server.model.ShopStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CheckRepositoryImpl implements CheckRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ShopStats> getShopStats() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("username").is(principal.getUsername())),
                Aggregation.group("shortPlace.name")
                        .min("totalSum").as("min")
                        .max("totalSum").as("max")
                        .avg("totalSum").as("avg")
                        .sum("totalSum").as("sum")
        );

        AggregationResults<ShopStats> shopStatsAggregationResults
                = mongoTemplate.aggregate(agg,"checks",ShopStats.class);

        List<ShopStats> shopStats = shopStatsAggregationResults.getMappedResults();

        return shopStats;
    }

    @Override
    public List<DateStats> getDateStats() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // this will aggregate for all years, so fix it with year as parameter!!
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("username").is(principal.getUsername())),
                Aggregation.project("totalSum").and("dateTime").extractMonth().as("month"),
                Aggregation.group("month").sum("totalSum").as("sum")
        );

        AggregationResults<DateStats> dateStatsAggregationResults
                = mongoTemplate.aggregate(agg,"checks",DateStats.class);

        List<DateStats> dateStats = dateStatsAggregationResults.getMappedResults();

        return dateStats;
    }
}

package com.example.demo;

import com.example.demo.model.SportType;
import com.example.demo.report.TopLeagueMarketsReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        printVipMarketReports(context);
    }

    private static void printVipMarketReports(ApplicationContext context) {
        TopLeagueMarketsReport report = context.getBean(TopLeagueMarketsReport.class);
        for (SportType sportType: SportType.values()) {
            try {
                report.printReportToConsole(sportType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

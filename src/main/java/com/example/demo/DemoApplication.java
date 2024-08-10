package com.example.demo;

import com.example.demo.model.League;
import com.example.demo.model.SportType;
import com.example.demo.report.TopLeagueMarketsReport;
import com.example.demo.utils.PerformanceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.net.ssl.SSLHandshakeException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        //printTopLeaguesMarketsReport(context);
        PerformanceUtils.measureRuntime(() -> printTopLeaguesMarketReport(context), "printTopLeaguesMarketReport");
    }

    private static void printTopLeaguesMarketReport(ApplicationContext context) {
        System.out.println("Printing of Top League's Market Report started...");
        TopLeagueMarketsReport report = context.getBean(TopLeagueMarketsReport.class);
        for (SportType sportType: SportType.values()) {
            try {
                report.printReportToConsole(sportType);
            } catch (SSLHandshakeException e) {
                System.out.println("Seems site is unreachable, try to use VPN. " + e.getMessage());
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Printing of Top League's Market Report finished.");
    }
}

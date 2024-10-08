package com.example.demo;

import com.example.demo.model.SportType;
import com.example.demo.report.TopLeagueMarketsReport;
import com.example.demo.utils.PerformanceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.net.ssl.SSLHandshakeException;

@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        //printTopLeaguesMarketsReport(context);
        PerformanceUtils.measureRuntime(() -> printTopLeaguesMarketReport(context), "printTopLeaguesMarketReport");
    }

    private static void printTopLeaguesMarketReport(ApplicationContext context) {
        log.info("Printing of Top League's Market Report started...");
        TopLeagueMarketsReport report = context.getBean(TopLeagueMarketsReport.class);
        for (SportType sportType: SportType.values()) {
            try {
                report.printReportToConsole(sportType);
            } catch (SSLHandshakeException e) {
                log.error("Seems site is unreachable, try to use VPN. " + e.getMessage());
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("Printing of Top League's Market Report finished.");
    }
}

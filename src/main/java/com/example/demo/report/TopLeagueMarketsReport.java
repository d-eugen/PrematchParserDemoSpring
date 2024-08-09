package com.example.demo.report;

import com.example.demo.model.Event;
import com.example.demo.model.EventDetailsResponse;
import com.example.demo.model.League;
import com.example.demo.model.Market;
import com.example.demo.model.Runner;
import com.example.demo.model.SportType;
import com.example.demo.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopLeagueMarketsReport {

    private final SportService sportService;

    @Autowired
    public TopLeagueMarketsReport(SportService sportService) {
        this.sportService = sportService;
    }

    public void printReportToConsole(SportType sportType) throws Exception {
        List<League> topLeagues = sportService.getTopLeagues(sportType);
        for (League league : topLeagues) {
            System.out.println("Processing League: " + league.getName());

            List<Event> events = sportService.fetchFilteredEvents(league.getId());
            for (Event event : events) {
                System.out.println("Processing Event: " + event.getName());

                EventDetailsResponse eventDetails = sportService.fetchEventDetails(event.getId());
                List<Market> markets = eventDetails.getMarkets();

                for (Market market : markets) {
                    System.out.println("Market: " + market.getName());
                    for (Runner runner : market.getRunners()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Name: ").append(runner.getName())
                                .append(", Price: ").append(runner.getPrice())
                                .append(", ID: ").append(runner.getId());
                        System.out.println(sb);
                    }
                }
            }
        }
    }
}

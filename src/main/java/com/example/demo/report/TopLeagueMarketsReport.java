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
    private static final String INDENT = "   ";
    private static final int TOP_MATCHES_LIMIT = 2;

    // Hundreds of markets is inconvenient for testing
    // TODO: remove or set to Zero limiters
    private static final int MARKETS_LIMIT_FOR_READABILITY = 3;
    private static final int RUNNERS_LIMIT_FOR_READABILITY = 3;

    private final SportService sportService;

    @Autowired
    public TopLeagueMarketsReport(SportService sportService) {
        this.sportService = sportService;
    }

    public void printReportToConsole(SportType sportType) throws Exception {
        List<League> topLeagues = sportService.getTopLeagues(sportType);
        for (League league : topLeagues) {
            printIndented(1, sportType.getDisplayName(), league.getName());
            List<Event> topMatches = sportService.fetchTopMatches(league.getId(), TOP_MATCHES_LIMIT);

            for (Event event : topMatches) {
                printIndented(2, event.getName(), (event.getKickoffUtc() + " UTC"), event.getId());
                EventDetailsResponse eventDetails = sportService.fetchEventDetails(event.getId());
                List<Market> markets = eventDetails.getMarkets();

                int marketCount = 0;
                for (Market market : markets) {
                    printIndented(3, market.getName());

                    int runnerCount = 0;
                    for (Runner runner : market.getRunners()) {
                        printIndented(4, runner.getName(), runner.getPrice(), runner.getId());
                        if (++runnerCount >= RUNNERS_LIMIT_FOR_READABILITY) break;
                    }

                    if (++marketCount >= MARKETS_LIMIT_FOR_READABILITY) break;
                }
            }
        }
    }

    private String getIndent(int n) {
        return INDENT.repeat(n);
    }
    private void printIndented(int indentLevel, Object... values) {
        String indent = getIndent(indentLevel);
        StringBuilder formatBuilder = new StringBuilder(indent);
        for (int i = 0; i < values.length; i++) {
            formatBuilder.append("%s");
            if (i < values.length - 1) {
                formatBuilder.append(", ");
            }
        }
        formatBuilder.append("%n");
        System.out.printf(formatBuilder.toString(), values);
    }
}

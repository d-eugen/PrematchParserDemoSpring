package com.example.demo.service;
import com.example.demo.config.ApiConfig;
import com.example.demo.model.Event;
import com.example.demo.model.EventDetailsResponse;
import com.example.demo.model.EventResponse;
import com.example.demo.model.League;
import com.example.demo.model.Market;
import com.example.demo.model.Sport;
import com.example.demo.model.SportType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportService {

    private final FetchDataService fetchDataService;
    private final ApiConfig apiConfig;

    @Autowired
    public SportService(FetchDataService fetchDataService, ApiConfig apiConfig) {
        this.fetchDataService = fetchDataService;
        this.apiConfig = apiConfig;
    }

    public List<Sport> fetchSportsData() throws Exception {
        String url = apiConfig.getSportsUrl();
        return fetchDataService.fetchData(url, new TypeReference<List<Sport>>() {});
    }

    public EventResponse fetchEventsData(long leagueId) throws Exception {
        String url = String.format(apiConfig.getEventsUrl(), leagueId);
        return fetchDataService.fetchData(url, EventResponse.class);
    }

    public EventDetailsResponse fetchEventDetails(long eventId) throws Exception {
        String url = String.format(apiConfig.getEventDetailsUrl(), eventId);
        return fetchDataService.fetchData(url, EventDetailsResponse.class);
    }

    public List<League> getTopLeagues(SportType sportType) throws Exception {
        List<Sport> allSports = fetchSportsData();
        return allSports.stream()
                .filter(sport -> sport.getName().equalsIgnoreCase(sportType.getDisplayName()))
                .flatMap(sport -> sport.getRegions().stream())
                .flatMap(region -> region.getLeagues().stream())
                .filter(League::isTop)
                .sorted(Comparator.comparing(League::getTopOrder))
                .collect(Collectors.toList());
    }

    public List<Event> fetchFilteredEvents(long leagueId) throws Exception {
        EventResponse eventResponse = fetchEventsData(leagueId);
        return eventResponse.getData().stream()
                .filter(event -> !"outright".equalsIgnoreCase(event.getBetline()))
                .collect(Collectors.toList());
    }

    public Market findMarketByName(EventDetailsResponse eventDetailsResponse, String marketName) {
        return eventDetailsResponse.getMarkets().stream()
                .filter(market -> marketName.equalsIgnoreCase(market.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Market not found: " + marketName));
    }
}
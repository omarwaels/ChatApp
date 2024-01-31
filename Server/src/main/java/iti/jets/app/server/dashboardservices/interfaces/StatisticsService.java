package iti.jets.app.server.dashboardservices.interfaces;

import java.util.Map;

public interface StatisticsService {

    Map<String, Integer> getMaleFemaleCount();

    Map<String, Integer> getUsersCountByCountry();

    Map<String, Integer> getUsersByStatus();


}

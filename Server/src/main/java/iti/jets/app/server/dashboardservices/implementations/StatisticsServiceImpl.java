package iti.jets.app.server.dashboardservices.implementations;

import iti.jets.app.server.dashboardservices.interfaces.StatisticsService;
import iti.jets.app.server.db.DashboardDao;

import java.util.Map;

public class StatisticsServiceImpl implements StatisticsService {
    private final DashboardDao dashboardDao;

    private StatisticsServiceImpl() {
        this.dashboardDao = new DashboardDao();
    }
    private static StatisticsService statisticsService;



    public static StatisticsService getStatisticsService() {
        if (statisticsService == null) statisticsService = new StatisticsServiceImpl();
        return statisticsService;
    }

    @Override
    public Map<String, Integer> getMaleFemaleCount() {
        return dashboardDao.getMaleFemaleCount();
    }

    @Override
    public Map<String, Integer> getUsersCountByCountry() {
        return dashboardDao.getUsersCountByCountry();
    }

    @Override
    public Map<String, Integer> getUsersByStatus () {
        return dashboardDao.getUsersCountByStatus();
    }
}

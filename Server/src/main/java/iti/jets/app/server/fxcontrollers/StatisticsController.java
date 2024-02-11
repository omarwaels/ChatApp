package iti.jets.app.server.fxcontrollers;

import iti.jets.app.server.dashboardservices.implementations.StatisticsServiceImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    PieChart pieChart;

    @FXML
    Label status;

    @FXML
    MenuButton chartMenuButton;

    @FXML
    private void updateChartMenuButton(String chartName) {
        chartMenuButton.setText(chartName + " Statistics");
    }

    public void updateStats(ActionEvent event) {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Male", StatisticsServiceImpl.getStatisticsService().getMaleFemaleCount().get("Male")),
                new PieChart.Data("Female", StatisticsServiceImpl.getStatisticsService().getMaleFemaleCount().get("Female"))
        );
        pieChart.setData(list);

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<MouseEvent>) mouseEvent -> status.setText(String.valueOf((int)data.getPieValue()+" Users")));
        }

    }

    @FXML
    private void showGenderChart() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                    new PieChart.Data("Male", StatisticsServiceImpl.getStatisticsService().getMaleFemaleCount().get("Male")),
                    new PieChart.Data("Female", StatisticsServiceImpl.getStatisticsService().getMaleFemaleCount().get("Female"))
            );
            pieChart.setTitle("User Gender Statistics");
            pieChart.setLegendSide(Side.BOTTOM);
            updateChartMenuButton("Gender");
            updateChart(list);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void showUserStatusChart() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            Map<String, Integer> countryCountMap = StatisticsServiceImpl.getStatisticsService().getUsersByStatus();
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
            countryCountMap.forEach((users, count) -> list.add(new PieChart.Data(users, count)));
            pieChart.setTitle("User Status Statistics");
            pieChart.setLegendSide(Side.BOTTOM);
            updateChartMenuButton("Status");
            updateChart(list);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateChart(ObservableList<PieChart.Data> dataList) {
        pieChart.setData(dataList);
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<MouseEvent>) mouseEvent ->
                    status.setText(String.valueOf((int)data.getPieValue()+" Users")));
        }
    }

    @FXML
    private void showCountryChart() {
        Map<String, Integer> countryCountMap = StatisticsServiceImpl.getStatisticsService().getUsersCountByCountry();
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        countryCountMap.forEach((country, count) -> list.add(new PieChart.Data(country, count)));
        pieChart.setTitle("User Country Statistics");
        pieChart.setLegendSide(Side.LEFT);
        updateChartMenuButton("Country");
        updateChart(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> updateStats(null)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Male", StatisticsServiceImpl.getStatisticsService().getMaleFemaleCount().get("Male")),
                new PieChart.Data("Female", StatisticsServiceImpl.getStatisticsService().getMaleFemaleCount().get("Female"))
        );
        pieChart.setData(list);
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<MouseEvent>) mouseEvent -> status.setText(String.valueOf((int)data.getPieValue()+" Users")));
        }
    }
}

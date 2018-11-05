package model;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModelTable {
    TableView<ResultData> table = new TableView<ResultData>();
    // Создание колонок
    TableColumn<ResultData, Double> LCol = new TableColumn<ResultData, Double>("L");
    TableColumn<ResultData, Double> ModulationCol = new TableColumn<ResultData, Double>("Глубина кодирования, бит/символ");
    TableColumn<ResultData, String> ModulationNameCol = new TableColumn<ResultData, String>("Модуляция");
    TableColumn<ResultData, Double> SpeedCol = new TableColumn<ResultData, Double>("Скорость передачи, МБайт/с");
    ModelTable(ObservableList<ResultData> list) {
        // Заполнители
        LCol.setCellValueFactory(new PropertyValueFactory<>("L"));
        ModulationCol.setCellValueFactory(new PropertyValueFactory<>("modulation"));
        ModulationNameCol.setCellValueFactory(new PropertyValueFactory<>("modulationName"));
        SpeedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        table.getColumns().addAll(LCol, ModulationCol, ModulationNameCol, SpeedCol);
        table.setItems(list);
        // Размеры
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(25));
    }

    public void setSizes() {
        LCol.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        ModulationCol.prefWidthProperty().bind(table.widthProperty().multiply(0.35));
        ModulationNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        SpeedCol.prefWidthProperty().bind(table.widthProperty().multiply(0.39));
    }

    public TableView<ResultData> getTable() {
        return table;
    }
}

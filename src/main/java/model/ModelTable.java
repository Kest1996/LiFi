package model;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModelTable {
    TableView<ResultData> table = new TableView<ResultData>();

    // Создание колонок
    TableColumn<ResultData, String> ReceiverCol = new TableColumn<ResultData, String>("Приемник");
    TableColumn<ResultData, Double> PercentCol = new TableColumn<ResultData, Double>("% Достигшей энергии");
    TableColumn<ResultData, Double> ModulationCol = new TableColumn<ResultData, Double>("Глубина кодирования, бит/символ");
    TableColumn<ResultData, String> ModulationNameCol = new TableColumn<ResultData, String>("Модуляция");
    TableColumn<ResultData, Double> SpeedCol = new TableColumn<ResultData, Double>("Максимальная скорость передачи, МБайт/с");

    /**
     * Конструктор
     * @param list
     */

    ModelTable(ObservableList<ResultData> list) {

        // Заполнители
        ReceiverCol.setCellValueFactory(new PropertyValueFactory<>("Receiver"));
        PercentCol.setCellValueFactory(new PropertyValueFactory<>("Percent"));
        ModulationCol.setCellValueFactory(new PropertyValueFactory<>("modulation"));
        ModulationNameCol.setCellValueFactory(new PropertyValueFactory<>("modulationName"));
        SpeedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        table.getColumns().addAll(ReceiverCol, PercentCol, ModulationCol, ModulationNameCol, SpeedCol);
        table.setItems(list);

        // Размеры
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(50));
    }

    /**
     * Запись размеров по умолчанию
     */

    public void setSizes() {
        ReceiverCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        PercentCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        ModulationCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        ModulationNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        SpeedCol.prefWidthProperty().bind(table.widthProperty().multiply(0.39));
    }

    /**
     * Получение таблиц
     * @return
     */

    public TableView<ResultData> getTable() {
        return table;
    }
}

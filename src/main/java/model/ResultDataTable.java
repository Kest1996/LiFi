package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import radiation.Diagram;

import java.util.ArrayList;

public class ResultDataTable {

    ArrayList<ResultData> resultData = new ArrayList<>();
    ObservableList<ResultData> resultDataList;

    /*
    public ResultDataTable(double Ip){
        //double[] Ip = Ip.getYAxis();
        this.resultData.add(new ResultData(Ip));
        resultDataList = FXCollections.observableList(resultData);
    }
    */
    public ResultDataTable() {

    }

    public void add(ResultData newResultData) {
        this.resultData.add(newResultData);
    }
    public ResultData get(int index) {
        return resultData.get(index);
    }
    public void setList() {
        resultDataList = FXCollections.observableList(resultData);
    }

    public ObservableList<ResultData> getList() {
        return resultDataList;
    }
}

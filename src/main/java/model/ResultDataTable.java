package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import radiation.Diagram;

import java.util.ArrayList;

public class ResultDataTable {

    ArrayList<ResultData> resultData = new ArrayList<>();
    ObservableList<ResultData> resultDataList;

    /**
     * Конструктор
     * @param IpL
     */

    public ResultDataTable(Diagram IpL){
        double[] L = IpL.getXAxis();
        double[] Ip = IpL.getYAxis();
        for (int i=0;i<L.length;i++){
            this.resultData.add(new ResultData(L[i],Ip[i]));
        }
        resultDataList = FXCollections.observableList(resultData);
    }

    public ResultData get(int index) {
        return resultData.get(index);
    }

    public ObservableList<ResultData> getList() {
        return resultDataList;
    }
}

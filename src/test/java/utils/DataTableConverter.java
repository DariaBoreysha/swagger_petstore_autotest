package utils;

import io.cucumber.datatable.DataTable;

import java.util.HashMap;

public class DataTableConverter {

    public static HashMap<String, String> toHashMap(DataTable table, String firstColumnName){
        HashMap<String, String> map = new HashMap<>(table.asMap());
        map.remove(firstColumnName);
        return map;
    }
}

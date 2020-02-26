package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseMetadataUtil {
    private Connection connection = null;
    private Connection getCon() {
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName(PropertiesUtil.getProperty("spring.datasource.driver-class-name"));
            connection =  DriverManager.getConnection(
                    PropertiesUtil.getProperty("spring.datasource.url"),
                    PropertiesUtil.getProperty("spring.datasource.username"),
                    PropertiesUtil.getProperty("spring.datasource.password"));
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getTables() {
        List<String> tableNameList = new ArrayList<>();
        String url = PropertiesUtil.getProperty("spring.datasource.url");
        int start = url.lastIndexOf("/") + 1;
        int end = url.lastIndexOf("?") > 0 ? url.lastIndexOf("?") : url.length();
        String catalog = url.substring(start, end);
        try {
            ResultSet tableSet = getCon().getMetaData().getTables(catalog, "%", null, new String[]{"TABLE"});
            while (tableSet.next()) {
                tableNameList.add(tableSet.getString("TABLE_NAME"));
            }
            tableSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNameList;
    }

    public HashMap<String, String> getColumnsAndTypeMap(String tableName) {
        String url = PropertiesUtil.getProperty("spring.datasource.url");
        int start = url.lastIndexOf("/") + 1;
        int end = url.lastIndexOf("?") > 0 ? url.lastIndexOf("?") : url.length();
        String catalog = url.substring(start, end);

        HashMap<String, String> columnAndTypeMap = new HashMap<>();
        try {
            ResultSet colSet = getCon().getMetaData().getColumns(catalog, "%", tableName, "%");
            while (colSet.next()) {
                columnAndTypeMap.put(colSet.getString("COLUMN_NAME"),
                        colSet.getString("TYPE_NAME") + "#" + colSet.getString("REMARKS"));
            }
            colSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return columnAndTypeMap;
    }

    public String getPrimaryKey(String tableName) {
        String url = PropertiesUtil.getProperty("spring.datasource.url");
        int start = url.lastIndexOf("/") + 1;
        int end = url.lastIndexOf("?") > 0 ? url.lastIndexOf("?") : url.length();
        String catalog = url.substring(start, end);

        String primaryColumnName = "";
        try {
            ResultSet resultSet = getCon().getMetaData().getPrimaryKeys(catalog, "%", tableName);
            resultSet.next();
            primaryColumnName = resultSet.getString("COLUMN_NAME");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return primaryColumnName;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

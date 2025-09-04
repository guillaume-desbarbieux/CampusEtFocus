package fr.campusetfocus.db;

class DbUtils {
    static String buildSelect(String table, String columnName, Integer id) {
        return "SELECT " + columnName + " FROM " + table + " WHERE id = " + id;
    }

}

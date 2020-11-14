/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static main.LoginScreenController.userID;

/**
 *
 * @author Ian_R
 */
public class Database {

    public static Connection conn;

    public static void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:Database.db");
                System.out.println("Successfully connected to database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createCategoriesTable() {
        PreparedStatement createCategoriesTable = null;
        PreparedStatement insertDefaultCategories = null;
        ResultSet rs = null;

        openConnection();
        try {
            System.out.println("Checking Categories ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "CATEGORIES", null);
            if (!rs.next()) {
                createCategoriesTable = conn.prepareStatement(
                        "CREATE TABLE CATEGORIES"
                        + "(CATEGORY VARCHAR(100) PRIMARY KEY)"
                );
                createCategoriesTable.execute();
                System.out.println("Categories table created");
                insertDefaultCategories = conn.prepareStatement(
                        "INSERT INTO CATEGORIES(CATEGORY)"
                        + "VALUES ('Work'),"
                        + "('Leisure'),"
                        + "('Exercise'),"
                        + "('Study'), "
                        + "('Travel')"
                );
                insertDefaultCategories.execute();
            } else {
                System.out.println("CATEGORIES table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createUserTable() {
        PreparedStatement createLoginTable = null;
        PreparedStatement insertDemoData = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking USER table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "USER", null);
            if (!rs.next()) {
                createLoginTable = conn.prepareStatement("CREATE TABLE USER "
                        + "(USER VARCHAR(100) PRIMARY KEY, "
                        + "FNAME VARCHAR(100), LNAME VARCHAR(100)"
                        + ", USERNAME VARCHAR(100)"
                        + ", PASSWORD VARCHAR(100))");
                createLoginTable.execute();
                System.out.println("USER table created");
            } else {
                System.out.println("USER table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginWithCredentials(String username, String password) {
        boolean attempt = false;
        try {
            openConnection();
            String myPreparedStatement = "SELECT * FROM USER TABLE WHERE username = ? AND password = ?";
            PreparedStatement st = conn.prepareStatement(myPreparedStatement);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                System.out.println("rs next");
                attempt = true;
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return attempt;
    }

    public static void createEntriesTable() {
        PreparedStatement createEntriesTable = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking ENTRIES table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "ENTRIES", null);

            if (!rs.next()) {
                createEntriesTable = conn.prepareStatement(
                        "CREATE TABLE ENTRIES ("
                        + "ENTRYID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "DESCRIPTION VARCHAR(100), "
                        + ""
                        + "CATEGORY VARCHAR(100), "
                        + "START_TIME TIME, "
                        + "END_TIME TIME, "
                        + "DURATION VARCHAR(100), "
                        + "DURATION_HOURS INTEGER, "
                        + "DURATION_MINUTES INTEGER, "
                        + "DATE_TODAY DATE,"
                        + "USER VARCHAR(100))"
                );
                createEntriesTable.execute();
                System.out.println("ENTRIES table created");
            } else {
                System.out.println("ENTRIES table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTaskTable() {
        PreparedStatement createTaskTable = null;
        ResultSet rsb = null;
        openConnection();
        try {
            System.out.println("Checking Task table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rsb = dbmd.getTables(null, null, "TASK", null);
            if (!rsb.next()) {
                createTaskTable = conn.prepareStatement(
                        "CREATE TABLE TASK ("
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME VARCHAR(100), "
                        + "DESCRIPTION VARCHAR(100), "
                        + "DO_DATE DATE, "
                        + "DUE_DATE DATE, "
                        + "TASK_PRIORITY VARCHAR(100), "
                        + "USER VARCHAR(100) )"
                );
                createTaskTable.execute();
                System.out.println("TASK table created");

            } else {
                System.out.println("Task table exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCompletedTaskTable() {
        PreparedStatement createCompletedTaskTable = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking COMPLETEDTASK table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "COMPLETEDTASK", null);
            if (!rs.next()) {
                createCompletedTaskTable = conn.prepareStatement(
                        "CREATE TABLE COMPLETEDTASK ("
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ", NAME VARCHAR(100)"
                        + ", DESCRIPTION VARCHAR(100)"
                        + ", DO_DATE DATE"
                        + ", DUE_DATE DATE"
                        + ", TASK_PRIORITY VARCHAR(100), USER VARCHAR(100) )"
                );
                createCompletedTaskTable.execute();
                System.out.println("COMPLETEDTASK table created");

            } else {
                System.out.println("COMPLETEDTASK table exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTodayTaskTable() {
        PreparedStatement createTodayTaskTable = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking TODAYTASK table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "TODAYTASK", null);
            if (!rs.next()) {
                createTodayTaskTable = conn.prepareStatement(
                        "CREATE TABLE TODAYTASK ("
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ", NAME VARCHAR(100)"
                        + ", DESCRIPTION VARCHAR(100)"
                        + ", DO_DATE DATE"
                        + ", DUE_DATE DATE"
                        + ", TASK_PRIORITY VARCHAR(100), "
                        + "USER VARCHAR(100) )"
                );
                createTodayTaskTable.execute();
                System.out.println("TODAYTASK table created");

            } else {
                System.out.println("TODAYTASK table exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createWeeklyTaskTable() {
        PreparedStatement createWeeklyTaskTable = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking WEEKLYTASK table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "WEEKLYTASK", null);
            if (!rs.next()) {
                createWeeklyTaskTable = conn.prepareStatement(
                        "CREATE TABLE WEEKLYTASK ("
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ", NAME VARCHAR(100)"
                        + ", DESCRIPTION VARCHAR(100)"
                        + ", DO_DATE DATE"
                        + ", DUE_DATE DATE"
                        + ", TASK_PRIORITY VARCHAR(100), USER VARCHAR(100) )"
                );
                createWeeklyTaskTable.execute();
                System.out.println("WEEKLYTASK table created");

            } else {
                System.out.println("WEEKLYTASK table exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTomorrowTaskTable() {
        PreparedStatement createTomorrowTaskTable = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking TOMORROWTASK table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "TOMORROWTASK", null);
            if (!rs.next()) {
                createTomorrowTaskTable = conn.prepareStatement(
                        "CREATE TABLE TOMORROWTASK ("
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ", NAME VARCHAR(100)"
                        + ", DESCRIPTION VARCHAR(100)"
                        + ", DO_DATE DATE"
                        + ", DUE_DATE DATE"
                        + ", TASK_PRIORITY VARCHAR(100), USER VARCHAR(100) )"
                );
                createTomorrowTaskTable.execute();
                System.out.println("TOMORROWTASK table created");

            } else {
                System.out.println("TOMORROWTASK table exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDeepFocusTaskTable() {
        PreparedStatement createDeepFocusTaskTable = null;
        ResultSet rsb = null;
        openConnection();
        try {
            System.out.println("Checking DEEPFOCUSTASK table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rsb = dbmd.getTables(null, null, "DEEPFOCUSTASK", null);
            if (!rsb.next()) {
                createDeepFocusTaskTable = conn.prepareStatement(
                        "CREATE TABLE DEEPFOCUSTASK "
                        + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME VARCHAR(100), "
                        + "DESCRIPTION VARCHAR(100),  USER VARCHAR(100));");
                createDeepFocusTaskTable.execute();
                System.out.println("DEEPFOCUSTASK table created");

            } else {
                System.out.println("DEEPFOCUSTASK table exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String sqlstatement) throws SQLException {
        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet RS = statement.executeQuery(sqlstatement);
        return RS;
    }

    public static void closeConnection(String tableName) throws SQLException {
        openConnection();
        Statement statement = conn.createStatement();
        String sql = "DROP TABLE " + tableName + ";";
        statement.executeUpdate(sql);
    }
    
    public static void closeConn() throws SQLException {
        conn.close();
    }

    public static void statementInsert(String query) throws SQLException {
        java.sql.Statement statement = null;
        statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    boolean CheckLoginDetails(String username, String password) throws SQLException {
        boolean success = false;

        try {
            openConnection();
            String myPreparedStatement
                    = "SELECT * FROM USER WHERE username = ? AND password = ?";
            PreparedStatement st = conn.prepareStatement(myPreparedStatement);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                System.out.println("Correct input");
                success = true;
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

}

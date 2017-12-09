package uk.ac.bath.se.Database;

import org.junit.Test;

public class MySQLJDBCTest {

    @Test
    public void JDBCDriverTest() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
    }
}
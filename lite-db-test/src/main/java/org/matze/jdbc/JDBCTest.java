package org.matze.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

    public void performJDBC() {

	// Loading the SQLite JDBC driver for the current thread
	try {
	    Class.forName("org.sqlite.JDBC");
	} catch (ClassNotFoundException e1) {
	    e1.printStackTrace();
	}

	try (Connection connection = DriverManager
		.getConnection("jdbc:sqlite:sample.db")) {

	    Statement statement = connection.createStatement();

	    // Creating database table
	    statement.executeUpdate("DROP TABLE IF EXISTS product");
	    statement
		    .executeUpdate("CREATE TABLE product (id INTEGER, name STRING)");

	    // Adding contents into the database
	    statement
		    .executeUpdate("INSERT INTO product VALUES(1, 'Tennis racket')");
	    statement.executeUpdate("INSERT INTO product VALUES(2, 'Guitar')");

	    // Querying the contents of the database using native SQLite query
	    ResultSet results = statement.executeQuery("SELECT * FROM product");

	    System.out.println("List of products\n----------------");

	    while (results.next()) {

		System.out.println(results.getString("name") + " (id="
			+ results.getInt("id") + ")");
	    }
	} catch (SQLException e) {

	    System.err.println(e.getMessage());
	}
    }
}

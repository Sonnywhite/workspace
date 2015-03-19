package org.matze;

import org.matze.jdbc.JDBCTest;
import org.matze.jpa.JPATest;

public class Application {

    public static void main(String[] args) {
	
	JDBCTest test1 = new JDBCTest();
	test1.performJDBC();
	
	JPATest test2 = new JPATest();
	test2.performJPA();
	
    }

}

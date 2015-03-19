package org.matze.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PWChecker {

	public boolean check(String password) {

		if (password == null || password.isEmpty())
			return false;
		
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(password);
		if (!m.find())
			return false;

		return true;
	}

}

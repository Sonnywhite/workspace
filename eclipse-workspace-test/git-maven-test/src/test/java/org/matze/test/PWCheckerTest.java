package org.matze.test;

import org.junit.Assert;
import org.junit.Test;

public class PWCheckerTest {

	@Test
	public void checkEmpty() {
		
		PWChecker pwc = new PWChecker();
		
		Assert.assertFalse(pwc.check(null));
		Assert.assertFalse(pwc.check(""));
		
	}
	
	@Test
	public void hastAtleastOneDigit() {
		
		PWChecker pwc = new PWChecker();
		
		Assert.assertFalse(pwc.check("abcd"));
		Assert.assertTrue(pwc.check("1334"));
		Assert.assertTrue(pwc.check("1df"));
		
		
	}

}

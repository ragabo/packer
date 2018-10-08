package com.mobiquityinc.test;

import org.junit.Assert;
import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;

public class TestPacker {

	@Test
	public void packagingTest(){
		String expected ="4"+System.lineSeparator()
				+"-"+System.lineSeparator()
				+"2,7"+System.lineSeparator()
				+"8,9"+System.lineSeparator();	
		String filePath = "src/test/resources/test.txt";
		String actual="";
		try {
			actual = Packer.pack(filePath);
		} catch (APIException e) {
			System.err.println("APIException is thrown: ");
			e.printStackTrace();
		}
		Assert.assertEquals(expected, actual);
	}
}

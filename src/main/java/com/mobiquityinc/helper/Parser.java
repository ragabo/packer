package com.mobiquityinc.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.dto.Package;
import com.mobiquityinc.exception.APIException;

public class Parser {
	private String line;

	public Parser(String line) {
		// ignore currency symbols
		this.line = line.replaceAll("\\p{Sc}", "");
	}

	/**
	 * Extracts the capacity of pack from string line
	 * 
	 * @return Capacity or Weight limit of the container
	 * @throws APIException 
	 */
	public float getCapacity() throws APIException {
		// return capacity of container ignoring non-numerics
		float capacity=0;
		try {
			String value = line.split(PackerConstant.COLON)[0];
			value = value.replaceAll("[^\\d.]", "");
			capacity = Float.parseFloat(value);
		} catch (Exception e) {
			throw new APIException(PackerConstant.EXC_PARSING, e);
		}
		return capacity;
	}

	/**
	 * Gets bunch of things from the string line
	 * 
	 * @return {@link List} of {@link Package} representing things
	 * @throws APIException
	 */
	public List<Package> getThings() throws APIException {
		List<Package> packages = new ArrayList<>();
		try {
			// extract packs from the line and adding each to Package object
			Matcher m = Pattern.compile(PackerConstant.REGEX).matcher(line);
			Package p;
			while (m.find()) {
				String thing = m.group(1);
				String[] parts = thing.split(PackerConstant.COMMA);
				p = new Package(Integer.parseInt(parts[0]),
						Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
				packages.add(p);
			}
		} catch (Exception e) {
			throw new APIException(PackerConstant.EXC_PARSING, e);
		}
		return packages;
	}

}

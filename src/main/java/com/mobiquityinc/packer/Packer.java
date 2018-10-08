package com.mobiquityinc.packer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mobiquityinc.dto.Package;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.helper.PackerConstant;
import com.mobiquityinc.helper.Parser;

public class Packer {

	/**
	 * Determines which packs to select for each group of things in the sent file.
	 * @param filePath absolute/relative path of a text file
	 * @return line separated result of selected packs for entire file
	 * @throws APIException
	 */
	public static String pack(String filePath) throws APIException {
		Parser parser;
		float capacity = 0;
		List<Package> returned;
		String result = "";
		List<Package> packages = Collections.emptyList();
		List<String> lines = getLines(filePath);
		for (String line : lines) {
			parser = new Parser(line);
			capacity = parser.getCapacity();
			packages = parser.getThings();
			returned = new ArrayList<>();
			solve(packages,	packages.size() - 1, capacity, returned);
			result += print(returned)+System.lineSeparator();
		}
		return result;
	}
	
	/**
	 * Decorates final result with comma
	 * @param results selected packs from the line
	 * @return comma separated indices of select packs
	 */
	private static String print(List<Package> results) {
		String result = "";
		for (int i = 0; i < results.size(); i++) {
			result += results.get(i);
			if (i != results.size() - 1)
				result += ",";
		}
		return result.isEmpty()? PackerConstant.EMPTY_RESULT : result;
	}

	/**
	 * Reads from the file to a list
	 * @param filePath absolute/relative path of a text file
	 * @return {@link List} of String representing lines of the sent file
	 * @throws APIException
	 */
	private static List<String> getLines(String filePath) throws APIException {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(filePath),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new APIException(PackerConstant.EXC_IO, e);
		}
		return lines;
	}

	/**
	 * Determines {@link List} of {@link Package} which should be selected to have maximum cost
	 * within given capacity
	 * @param items {@link List} of all {@link Package} to select from
	 * @param lastIndex index of last element
	 * @param capacity the capacity or weight limit of the container
	 * @param returned A {@link List} of {@link Package} representing the selected Packs
	 * @return
	 */
	private static float solve(List<Package> items, int lastIndex, float capacity,
			List<Package> returned) {
		if (capacity == 0f || lastIndex < 0) {
			return 0f;
		}
		// if item weight is greater than the total capacity, ignore it and continue with the next item
		if (items.get(lastIndex).getWeight() > capacity) {
			List<Package> r = new ArrayList<>();
			float val = solve(items, lastIndex - 1, capacity, r);
			returned.addAll(r);
			return val;
		} else {
			List<Package> rWith = new ArrayList<>();
			List<Package> rWithout = new ArrayList<>();
			float withCurrItem = items.get(lastIndex).getCost()
					+ solve(items, lastIndex - 1,
							capacity - items.get(lastIndex).getWeight(), rWith);
			float withoutCurrItem = solve(items, lastIndex - 1, capacity, rWithout);
			// if costs of both branches are equal, then decide upon weights
			if (withCurrItem == withoutCurrItem) {
				if (calcWeight(rWith) < calcWeight(rWithout)) {
					rWith.add(items.get(lastIndex));
					returned.addAll(rWith);
					return withCurrItem;
				} else {
					returned.addAll(rWithout);
					return withoutCurrItem;
				}
			} else if (withCurrItem > withoutCurrItem) {
				rWith.add(items.get(lastIndex));
				returned.addAll(rWith);
				return withCurrItem;
			} else {
				returned.addAll(rWithout);
				return withoutCurrItem;
			}
		}
	}

	/**
	 * Gets total weight from list of {@link Package}
	 * @param packs packages to get their total weights
	 * @return the total weight of the packages
	 */
	private static float calcWeight(List<Package> packs) {
		float totalWeight = 0f;
		for (Package pack : packs) {
			totalWeight += pack.getWeight();
		}
		return totalWeight;
	}
}

package com.kstarrain.utils;


import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtils {


	public static List<String> intersection(List<String>... datas) {

		return intersection(Arrays.asList(datas));

	}

	public static List<String> intersection(List<List<String>> datas) {
		if(CollectionUtils.isEmpty(datas)){return null;}

		//有效集合
		List<List<String>> efficientList = new ArrayList<>();
		for (List<String> data : datas) {
			if (CollectionUtils.isNotEmpty(data)){
				efficientList.add(data);
			}
		}

		if(CollectionUtils.isEmpty(efficientList)){return null;}

		List<String> intersection = efficientList.get(0);

		if (efficientList.size() == 1){ //如果只有一个非空集合，直接返回第一个

			return intersection;

		} else { // 有多个非空集合，直接挨个交集

			for (List<String> strings : efficientList) {
				intersection.retainAll(strings);
			}
			return intersection;
		}
	}
}

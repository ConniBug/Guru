package com.guru.utils;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Network {

	public static String readURL(String page) {
		try {
			HttpGet request = new HttpGet(page);
			CloseableHttpClient client = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36").setConnectionTimeToLive(5, TimeUnit.SECONDS).build();
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String dat = EntityUtils.toString(entity);
			return dat;
		} catch (Exception e) {}
		
		return "";
	}
	
}

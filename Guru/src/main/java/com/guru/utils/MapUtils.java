package com.guru.utils;

import java.util.HashMap;
import java.util.Map;

/** Utility class to create maps easily
 * @author syntex (saif khan) 
 * @version 1.0
 * @since 1.0
*/
public class MapUtils<K, V> {

	private Map<K, V> map; 
	
	/**
	 * 
	 * @param key the first key value
	 * @param value the first value for the key
	 */
	public MapUtils(K key, V value) {
		 this.map = new HashMap<>();
		 this.map.put(key, value);
	}
	
	/**
	 * 
	 * @param key add a key to the map
	 * @param value add a value for the key provided
	 * @return an instance of this maputils
	 */
	public MapUtils<K, V> put(K key, V value) {
		this.map.put(key, value);
		return this;
	}
	
	/**
	 * retrieve this utils as a map
	 * @return return the map which this instance represents
	 */
	public Map<K, V> get(){
		return this.map;
	}
	
	/**
	 * returns an empty hashmap
	 * @return
	 */
	public static <K, V> Map<K, V> empty(){
		return new HashMap<>();
	}
	
	
	
	
}

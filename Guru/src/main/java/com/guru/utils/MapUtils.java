package com.guru.utils;

import java.util.HashMap;
import java.util.Map;

/** Utility class to create/manga maps more easily
 * @author syntex (saif khan) 
 * @author https://github.com/F12-Syntex?tab=repositories
 * @version 1.0
 * @since 1.0
*/
public class MapUtils<K, V> {

	private Map<K, V> map; //store the current state of the Map
	
	/* Constructor */
	public MapUtils(K key, V value) {
		 this.map = new HashMap<>();
		 this.map.put(key, value);
	}
	
	/* Constructor */
	public MapUtils<K, V> put(K key, V value) {
		this.map.put(key, value);
		return this;
	}
	
	/* @returns the current Map<K, V> */
	public Map<K, V> get(){
		return this.map;
	}
	
	/* @returns an empty HashMap<K, V> */
	public Map<K, V> empty(){
		return new HashMap<>();
	}
	
	
	
	
}

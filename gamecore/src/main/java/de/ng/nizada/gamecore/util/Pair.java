package de.ng.nizada.gamecore.util;

import java.util.Map.Entry;

public class Pair<K, V> implements Entry<K, V> {

	private K key;
	private V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}

	@Override
	public V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}
	
	public K setKey(K key) {
		K oldKey = this.key;
		this.key = key;
		return oldKey;
	}
}
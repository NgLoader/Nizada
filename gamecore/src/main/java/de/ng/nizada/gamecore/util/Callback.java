package de.ng.nizada.gamecore.util;

public interface Callback<V> {
	public void done(V result, Throwable error);
}
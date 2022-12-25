package de.ng.nizada.freebuild.callback;

import de.ng.nizada.gamecore.util.Callback;

public interface TypeCallback<V> extends Callback<V> {
	default void expired() { }

	default boolean isExpired() {
		return false;
	}
}
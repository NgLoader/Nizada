package de.ng.nizada.gamecore.util;

import java.util.Random;

public enum StringGenerator {

	ALPHA("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	NUMERIC("1234567890"),
	SPECIAL(";:#'+*^°´?=)(/&%$§\\\"!\\\\-.,<>|"),

	ALPHANUMERIC(ALPHA.getCharacters() + NUMERIC.getCharacters()),
	ALPHANUMERICSPECIAL(ALPHA.getCharacters() + NUMERIC.getCharacters() + SPECIAL.getCharacters());

	private final String characters;
	private final int charactersLength;

	private StringGenerator(String characters) {
		this.characters = characters;
		this.charactersLength = characters.length();
	}

	public String generateString(int length) {
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();

		for(int i = 0; i < length; i++)
			buffer.append(characters.charAt(random.nextInt(charactersLength)));
		return buffer.toString();
	}

	public String getCharacters() {
		return characters;
	}

	public int getCharactersLength() {
		return charactersLength;
	}
}
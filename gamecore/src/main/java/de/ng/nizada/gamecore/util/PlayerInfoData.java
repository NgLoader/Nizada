package de.ng.nizada.gamecore.util;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import com.google.common.base.MoreObjects;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_12_R1.EnumGamemode;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;

public class PlayerInfoData {

	private final int b;
	private final EnumGamemode c;
	private final GameProfile d;
	private final IChatBaseComponent e;

	public PlayerInfoData(GameProfile gameprofile, int i, EnumGamemode enumgamemode,
			@Nullable IChatBaseComponent ichatbasecomponent) {
		this.d = gameprofile;
		this.b = i;
		this.c = enumgamemode;
		this.e = ichatbasecomponent;
	}

	public GameProfile a() {
		return this.d;
	}

	public int b() {
		return this.b;
	}

	public EnumGamemode c() {
		return this.c;
	}

	@Nullable
	public IChatBaseComponent d() {
		return this.e;
	}

	public String toString() {
		return MoreObjects.toStringHelper(this).add("latency", this.b).add("gameMode", this.c).add("profile", this.d)
				.add("displayName", this.e == null ? null : IChatBaseComponent.ChatSerializer.a(this.e)).toString();
	}
}
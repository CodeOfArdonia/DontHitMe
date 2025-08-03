package com.iafenvoy.dhm;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public final class MixinCache {
    @Nullable
    public static ServerPlayerEntity runningPlayer = null;
    @Nullable
    public static PlayerInteractEntityC2SPacket runningPacket = null;
}

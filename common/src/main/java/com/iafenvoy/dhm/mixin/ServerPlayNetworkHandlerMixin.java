package com.iafenvoy.dhm.mixin;

import com.iafenvoy.dhm.MixinCache;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerInteractEntity", at = @At("HEAD"))
    private void beforeHandleInteract(PlayerInteractEntityC2SPacket packet, CallbackInfo ci) {
        MixinCache.runningPacket = packet;
        MixinCache.runningPlayer = this.player;
    }

    @Inject(method = "onPlayerInteractEntity", at = @At("RETURN"))
    private void afterHandleInteract(PlayerInteractEntityC2SPacket packet, CallbackInfo ci) {
        MixinCache.runningPacket = null;
        MixinCache.runningPlayer = null;
    }
}

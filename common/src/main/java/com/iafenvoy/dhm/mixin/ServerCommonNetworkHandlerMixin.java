package com.iafenvoy.dhm.mixin;

import com.iafenvoy.dhm.MixinCache;
import net.minecraft.entity.Entity;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonNetworkHandler.class)
public class ServerCommonNetworkHandlerMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Inject(method = "disconnect(Lnet/minecraft/network/DisconnectionInfo;)V", at = @At("HEAD"), cancellable = true)
    private void preventKick(DisconnectionInfo disconnectionInfo, CallbackInfo ci) {
        if (MixinCache.runningPacket == null || MixinCache.runningPlayer == null) return;
        Entity entity = MixinCache.runningPacket.getEntity(MixinCache.runningPlayer.getServerWorld());
        LOGGER.warn("[Don't hit me!] Invalid entity was hit! Attacker: {}, Target: {}", MixinCache.runningPlayer.getName().getString(), entity);
        ci.cancel();
    }
}

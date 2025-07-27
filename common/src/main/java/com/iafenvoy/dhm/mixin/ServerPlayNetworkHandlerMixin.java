package com.iafenvoy.dhm.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    @Final
    static Logger LOGGER;
    @Shadow
    public ServerPlayerEntity player;
    @Nullable
    @Unique
    private PlayerInteractEntityC2SPacket dont_hit_me$runningPacket = null;

    @Inject(method = "onPlayerInteractEntity", at = @At("HEAD"))
    private void beforeHandleInteract(PlayerInteractEntityC2SPacket packet, CallbackInfo ci) {
        this.dont_hit_me$runningPacket = packet;
    }

    @Inject(method = "onPlayerInteractEntity", at = @At("RETURN"))
    private void afterHandleInteract(PlayerInteractEntityC2SPacket packet, CallbackInfo ci) {
        this.dont_hit_me$runningPacket = null;
    }

    @Inject(method = "disconnect", at = @At("HEAD"), cancellable = true)
    private void preventTick(Text reason, CallbackInfo ci) {
        if (this.dont_hit_me$runningPacket == null) return;
        Entity entity = this.dont_hit_me$runningPacket.getEntity(this.player.getServerWorld());
        LOGGER.warn("[Don't hit me!] Invalid entity was hit! Attacker: {}, Target: {}", this.player.getName().getString(), entity);
        ci.cancel();
    }
}

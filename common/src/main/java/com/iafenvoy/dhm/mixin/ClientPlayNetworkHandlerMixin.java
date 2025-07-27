package com.iafenvoy.dhm.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

    @Inject(method = "onEntityStatus", at = @At("HEAD"), cancellable = true)
    private void handleSniffer(EntityStatusS2CPacket packet, CallbackInfo ci) {
        Entity entity = packet.getEntity(this.world);
        if (packet.getStatus() == 63 && entity != null && !(entity instanceof SnifferEntity)) {
            entity.handleStatus((byte) 63);
            ci.cancel();
        }
    }
}

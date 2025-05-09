package xyz.apfelmus.cheeto.injection.mixins;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S2APacketParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.apfelmus.cheeto.client.modules.player.AutoFish;

@Mixin({NetHandlerPlayClient.class})
public class MixinNetHandlerPlayClient {
   @Inject(
      method = {"handleParticles"},
      at = {@At("HEAD")}
   )
   private void handleParticles(S2APacketParticles packetIn, CallbackInfo ci) {
      AutoFish.handleParticles(packetIn);
   }
}

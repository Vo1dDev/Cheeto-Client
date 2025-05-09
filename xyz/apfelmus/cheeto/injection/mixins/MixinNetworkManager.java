package xyz.apfelmus.cheeto.injection.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cheeto.client.events.PacketReceivedEvent;

@Mixin({NetworkManager.class})
public class MixinNetworkManager {
   @Inject(
      method = {"channelRead0"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void read(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
      if (Minecraft.func_71410_x().field_71439_g != null) {
         if (CF4M.INSTANCE.moduleManager.isEnabled("AntiKB") && !Minecraft.func_71410_x().field_71439_g.func_180799_ab()) {
            if (packet instanceof S27PacketExplosion) {
               ci.cancel();
            }

            if (packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).func_149412_c() == Minecraft.func_71410_x().field_71439_g.func_145782_y()) {
               ci.cancel();
            }
         }

         (new PacketReceivedEvent(packet)).call();
      }

   }

   @Inject(
      method = {"sendPacket(Lnet/minecraft/network/Packet;)V"},
      at = {@At("HEAD")}
   )
   private void send(Packet<?> packet, CallbackInfo ci) {
   }
}

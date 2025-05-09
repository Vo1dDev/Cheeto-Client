package xyz.apfelmus.cheeto.injection.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.apfelmus.cf4m.CF4M;

@Mixin({EntityPlayerSP.class})
public class MixinEntityPlayerSP {
   @Inject(
      at = {@At("HEAD")},
      method = {"sendChatMessage"},
      cancellable = true
   )
   private void sendChatMessage(String message, CallbackInfo ci) {
      if (CF4M.INSTANCE.commandManager.isCommand(message)) {
         ci.cancel();
      }

   }
}

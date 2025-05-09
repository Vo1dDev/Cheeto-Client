package xyz.apfelmus.cheeto.injection.mixins;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.projectile.EntitySnowball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cheeto.client.modules.combat.GhostArm;

@Mixin({RenderManager.class})
public class MixinRenderManager {
   @Inject(
      method = {"shouldRender"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
      if (CF4M.INSTANCE.moduleManager.isEnabled("GhostArm")) {
         GhostArm ga = (GhostArm)CF4M.INSTANCE.moduleManager.getModule("GhostArm");
         if (ga.HideMobs.isEnabled()) {
            if (ga.Zombies.isEnabled() && entityIn instanceof EntityZombie) {
               cir.setReturnValue(false);
            }

            if (ga.Players.isEnabled() && entityIn instanceof EntityOtherPlayerMP) {
               cir.setReturnValue(false);
            }
         }
      }

      if (CF4M.INSTANCE.moduleManager.isEnabled("SnowballHider") && entityIn instanceof EntitySnowball) {
         cir.setReturnValue(false);
      }

   }
}

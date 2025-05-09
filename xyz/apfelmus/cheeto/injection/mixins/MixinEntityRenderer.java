package xyz.apfelmus.cheeto.injection.mixins;

import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cheeto.client.modules.combat.GhostArm;

@Mixin({EntityRenderer.class})
public class MixinEntityRenderer {
   @Inject(
      method = {"getMouseOver"},
      at = {@At(
   value = "INVOKE",
   target = "Ljava/util/List;size()I",
   ordinal = 0
)},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   private void getMouseOver(float partialTicks, CallbackInfo ci, Entity entity, double d0, double d1, Vec3 vec3, boolean flag, boolean b, Vec3 vec31, Vec3 vec32, Vec3 vec33, float f, List<Entity> list, double d2, int j) {
      this.removeEntities(list);
   }

   @Inject(
      method = {"getMouseOver"},
      at = {@At(
   value = "INVOKE",
   target = "Ljava/util/List;size()I",
   ordinal = 0
)},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   private void getMouseOver(float partialTicks, CallbackInfo ci, Entity entity, double d0, double d1, Vec3 vec3, boolean flag, int i, Vec3 vec31, Vec3 vec32, Vec3 vec33, float f, List<Entity> list, double d2, int j) {
      this.removeEntities(list);
   }

   private void removeEntities(List<Entity> list) {
      if (CF4M.INSTANCE.moduleManager.isEnabled("GhostArm")) {
         if (((GhostArm)CF4M.INSTANCE.moduleManager.getModule("GhostArm")).Zombies.isEnabled()) {
            list.removeIf((listEntity) -> {
               return listEntity instanceof EntityZombie;
            });
         }

         if (((GhostArm)CF4M.INSTANCE.moduleManager.getModule("GhostArm")).Players.isEnabled()) {
            list.removeIf((listEntity) -> {
               return listEntity instanceof EntityOtherPlayerMP;
            });
         }
      }

   }
}

package xyz.apfelmus.cheeto.client.modules.movement;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;
import xyz.apfelmus.cheeto.client.utils.client.KeybindUtils;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.math.VecUtils;
import xyz.apfelmus.cheeto.client.utils.pathfinding.Pathfinder;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;

@Module(
   name = "Pathfinding",
   description = "Do not toggle this module, it won't work",
   category = Category.MOVEMENT
)
public class Pathfinding {
   @Setting(
      name = "UnstuckTime"
   )
   private IntegerSetting unstucktime = new IntegerSetting(3, 1, 10);
   @Setting(
      name = "LookTime"
   )
   private IntegerSetting lookTime = new IntegerSetting(150, 0, 1000);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static int stuckTicks = 0;
   private static BlockPos oldPos;
   private static BlockPos curPos;
   private static TimeHelper unstucker;

   @Enable
   public void onEnable() {
      stuckTicks = 0;
      oldPos = null;
      curPos = null;
      if (!Pathfinder.hasPath()) {
         ChatUtils.send("Pussy bitch, no path found");
         CF4M.INSTANCE.moduleManager.toggle((Object)this);
      } else {
         ChatUtils.send("Navigating to: " + Pathfinder.getGoal());
      }

   }

   @Disable
   public void onDisable() {
      Pathfinder.path = null;
      KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), false);
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (mc.field_71462_r == null || mc.field_71462_r instanceof GuiChat) {
         if (Pathfinder.hasPath()) {
            if (++stuckTicks >= this.unstucktime.getCurrent() * 20) {
               curPos = mc.field_71439_g.func_180425_c();
               if (oldPos != null && Math.sqrt(curPos.func_177951_i(oldPos)) <= 0.1D) {
                  KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), true);
                  KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), true);
                  unstucker = new TimeHelper();
                  unstucker.reset();
                  return;
               }

               oldPos = curPos;
               stuckTicks = 0;
            }

            if (unstucker != null && unstucker.hasReached(2000L)) {
               KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), false);
               KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), false);
               unstucker = null;
            }

            Vec3 first = Pathfinder.getCurrent().func_72441_c(0.5D, 0.0D, 0.5D);
            Rotation needed = RotationUtils.getRotation(first);
            needed.setPitch(mc.field_71439_g.field_70125_A);
            if (VecUtils.getHorizontalDistance(mc.field_71439_g.func_174791_d(), first) > 0.69D) {
               if (RotationUtils.done && needed.getYaw() < 135.0F) {
                  RotationUtils.setup(needed, (long)this.lookTime.getCurrent());
               }

               Vec3 lastTick;
               if (Pathfinder.hasNext()) {
                  lastTick = Pathfinder.getNext().func_72441_c(0.5D, 0.0D, 0.5D);
                  double xDiff = Math.abs(Math.abs(lastTick.field_72450_a) - Math.abs(first.field_72450_a));
                  double zDiff = Math.abs(Math.abs(lastTick.field_72449_c) - Math.abs(first.field_72449_c));
                  mc.field_71439_g.func_70031_b(xDiff == 1.0D && zDiff == 0.0D || xDiff == 0.0D && zDiff == 1.0D);
               }

               lastTick = new Vec3(mc.field_71439_g.field_70142_S, mc.field_71439_g.field_70137_T, mc.field_71439_g.field_70136_U);
               Vec3 diffy = mc.field_71439_g.func_174791_d().func_178788_d(lastTick);
               Vec3 nextTick = mc.field_71439_g.func_174791_d().func_178787_e(diffy);
               KeybindUtils.stopMovement();
               List<KeyBinding> neededPresses = VecUtils.getNeededKeyPresses(mc.field_71439_g.func_174791_d(), first);
               if (!(Math.abs(nextTick.func_72438_d(first) - mc.field_71439_g.func_174791_d().func_72438_d(first)) > 0.05D) || !(nextTick.func_72438_d(first) > mc.field_71439_g.func_174791_d().func_72438_d(first))) {
                  neededPresses.forEach((v) -> {
                     KeyBinding.func_74510_a(v.func_151463_i(), true);
                  });
               }

               if (Math.abs(mc.field_71439_g.field_70163_u - first.field_72448_b) > 0.5D) {
                  KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), mc.field_71439_g.field_70163_u < first.field_72448_b);
               } else {
                  KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), false);
               }
            } else {
               RotationUtils.reset();
               if (!Pathfinder.goNext()) {
                  KeybindUtils.stopMovement();
               }
            }
         } else if (CF4M.INSTANCE.moduleManager.isEnabled((Object)this)) {
            ChatUtils.send("Done navigating");
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
         }

      }
   }

   @Event
   public void onRender(Render3DEvent event) {
      if (Pathfinder.path != null && !Pathfinder.path.isEmpty()) {
         Render3DUtils.drawLines(Pathfinder.path, 2.0F, event.partialTicks);
         Vec3 last = ((Vec3)Pathfinder.path.get(Pathfinder.path.size() - 1)).func_72441_c(0.0D, -1.0D, 0.0D);
         Render3DUtils.renderEspBox(new BlockPos(last), event.partialTicks, ColorUtils.getChroma(3000.0F, (int)(last.field_72450_a + last.field_72448_b + last.field_72449_c)));
      }

      if (mc.field_71462_r == null || mc.field_71462_r instanceof GuiChat) {
         if (!RotationUtils.done) {
            RotationUtils.update();
         }

      }
   }
}

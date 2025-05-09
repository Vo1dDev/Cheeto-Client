package xyz.apfelmus.cheeto.client.modules.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "IceGoblinSlayer",
   category = Category.COMBAT
)
public class IceGoblinSlayer {
   @Setting(
      name = "AimTime"
   )
   private IntegerSetting aimTime = new IntegerSetting(250, 0, 1000);
   @Setting(
      name = "ClickDelay"
   )
   private IntegerSetting clickDelay = new IntegerSetting(200, 0, 1000);
   @Setting(
      name = "AntiAFK"
   )
   private BooleanSetting antiAfk = new BooleanSetting(true);
   @Setting(
      name = "Radius"
   )
   private IntegerSetting radius = new IntegerSetting(30, 0, 30);
   @Setting(
      name = "ItemSlot",
      description = "Juju / Term / Frozen Scythe"
   )
   private IntegerSetting itemSlot = new IntegerSetting(1, 1, 8);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static Entity currentMob = null;
   private static List<Entity> blacklist = new ArrayList();
   private static long curEnd = 0L;
   private static int ticks = 0;
   private static IceGoblinSlayer.KillState killState;
   private static IceGoblinSlayer.AfkState afkState;

   @Enable
   public void onEnable() {
      curEnd = 0L;
      currentMob = null;
      killState = IceGoblinSlayer.KillState.SELECT;
      afkState = IceGoblinSlayer.AfkState.LEFT;
      blacklist.clear();
   }

   @Disable
   public void onDisable() {
      if (this.antiAfk.isEnabled()) {
         KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), false);
         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
      }

   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (this.antiAfk.isEnabled()) {
         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
         KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), false);
      }

      if (++ticks > 40) {
         ticks = 0;
         blacklist.clear();
         if (this.antiAfk.isEnabled()) {
            switch(afkState) {
            case LEFT:
               KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), true);
               afkState = IceGoblinSlayer.AfkState.RIGHT;
               break;
            case RIGHT:
               KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), true);
               afkState = IceGoblinSlayer.AfkState.LEFT;
            }
         }
      }

      switch(killState) {
      case SELECT:
         List<Entity> allPossible = new ArrayList();
         Iterator var3 = mc.field_71441_e.field_72996_f.iterator();

         while(var3.hasNext()) {
            Entity e = (Entity)var3.next();
            if (e instanceof EntityPlayer && !Stream.of("knifethrower", "goblin", "walker").noneMatch((v) -> {
               return e.func_70005_c_().toLowerCase().contains(v);
            }) && mc.field_71439_g.func_70685_l(e) && !e.field_70128_L && !(e.func_70032_d(mc.field_71439_g) >= (float)this.radius.getCurrent()) && !blacklist.contains(e)) {
               allPossible.add(e);
            }
         }

         if (!allPossible.isEmpty()) {
            currentMob = (Entity)Collections.min(allPossible, Comparator.comparing((e2) -> {
               return e2.func_70032_d(mc.field_71439_g);
            }));
            Vec3 vec = currentMob.func_174791_d();
            vec = vec.func_72441_c(0.0D, 1.0D, 0.0D);
            Rotation rot = RotationUtils.getRotation(vec);
            RotationUtils.setup(rot, (long)this.aimTime.getCurrent());
            curEnd = RotationUtils.endTime;
            killState = IceGoblinSlayer.KillState.AIM;
         }
         break;
      case KILL:
         SkyblockUtils.silentUse(this.itemSlot.getCurrent(), this.itemSlot.getCurrent());
         blacklist.add(currentMob);
         currentMob = null;
         killState = IceGoblinSlayer.KillState.SELECT;
      }

   }

   @Event
   public void onRender(Render3DEvent event) {
      if (killState == IceGoblinSlayer.KillState.AIM) {
         if (currentMob.field_70128_L) {
            blacklist.add(currentMob);
            currentMob = null;
            killState = IceGoblinSlayer.KillState.SELECT;
            return;
         }

         if (System.currentTimeMillis() <= curEnd + (long)this.clickDelay.getCurrent()) {
            RotationUtils.update();
         } else {
            killState = IceGoblinSlayer.KillState.KILL;
         }
      }

   }

   static {
      killState = IceGoblinSlayer.KillState.SELECT;
      afkState = IceGoblinSlayer.AfkState.LEFT;
   }

   static enum AfkState {
      LEFT,
      RIGHT;
   }

   static enum KillState {
      SELECT,
      AIM,
      KILL;
   }
}

package xyz.apfelmus.cheeto.client.modules.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.FloatSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;

@Module(
   name = "BloodCamp",
   category = Category.COMBAT
)
public class BloodCamp {
   @Setting(
      name = "AimTime"
   )
   private IntegerSetting aimTime = new IntegerSetting(100, 0, 1000);
   @Setting(
      name = "ClickDelay"
   )
   private IntegerSetting clickDelay = new IntegerSetting(100, 0, 1000);
   @Setting(
      name = "AimLow"
   )
   private FloatSetting aimLow = new FloatSetting(1.0F, 0.0F, 5.0F);
   @Setting(
      name = "GodGamerMode"
   )
   public static BooleanSetting godGamerMode = new BooleanSetting(false);
   private static Minecraft mc = Minecraft.func_71410_x();
   private List<String> names = new ArrayList(Arrays.asList("Revoker", "Psycho", "Reaper", "Parasite", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Flamer", "Tear", "Skull", "Mr. Dead", "Vader", "Frost", "Walker"));
   private List<String> clickedNames = new ArrayList();
   private BloodCamp.KillState ks;
   private long curEnd;

   public BloodCamp() {
      this.ks = BloodCamp.KillState.SELECT;
      this.curEnd = 0L;
   }

   @Enable
   public void onEnable() {
      this.curEnd = 0L;
      this.clickedNames.clear();
      this.ks = BloodCamp.KillState.SELECT;
   }

   @Event
   public void onRender(Render3DEvent event) {
      switch(this.ks) {
      case SELECT:
         Iterator var2 = mc.field_71441_e.field_72996_f.iterator();

         while(var2.hasNext()) {
            Entity e = (Entity)var2.next();
            if (e instanceof EntityOtherPlayerMP && !this.clickedNames.contains(e.func_70005_c_().trim()) && this.names.contains(e.func_70005_c_().trim())) {
               Vec3 vec = e.func_174791_d();
               vec = vec.func_72441_c(0.0D, (double)(-1.0F * this.aimLow.getCurrent()), 0.0D);
               Rotation rot = RotationUtils.getRotation(vec);
               RotationUtils.setup(rot, (long)this.aimTime.getCurrent());
               this.curEnd = RotationUtils.endTime;
               this.clickedNames.add(e.func_70005_c_().trim());
               this.ks = BloodCamp.KillState.AIM;
            }
         }

         return;
      case AIM:
         if (System.currentTimeMillis() <= this.curEnd + (long)this.clickDelay.getCurrent()) {
            RotationUtils.update();
         } else {
            this.ks = BloodCamp.KillState.KILL;
         }
         break;
      case KILL:
         KeyBinding.func_74507_a(mc.field_71474_y.field_74312_F.func_151463_i());
         this.ks = BloodCamp.KillState.SELECT;
      }

   }

   @Event
   public void onWorldLoad(WorldUnloadEvent event) {
      this.clickedNames.clear();
   }

   static enum KillState {
      SELECT,
      AIM,
      KILL;
   }
}

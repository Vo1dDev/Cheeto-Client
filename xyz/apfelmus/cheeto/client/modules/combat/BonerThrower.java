package xyz.apfelmus.cheeto.client.modules.combat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;

@Module(
   name = "BonerThrower",
   category = Category.COMBAT
)
public class BonerThrower {
   @Setting(
      name = "SilentUse",
      description = "Always stay on the configured slot"
   )
   private BooleanSetting silentUse = new BooleanSetting(false);
   @Setting(
      name = "ThrowDelay",
      description = "Throw delay in milliseconds"
   )
   private IntegerSetting throwDelay = new IntegerSetting(100, 0, 1000);
   @Setting(
      name = "MainSlot",
      description = "Slot of the weapon you want held"
   )
   private IntegerSetting mainSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "InvMode",
      description = "A bit bannable"
   )
   private BooleanSetting invMode = new BooleanSetting(false);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static List<Integer> boners = new ArrayList();
   private static TimeHelper throwTimer = new TimeHelper();
   private static int throwSlot = -1;
   private static boolean first = true;

   @Enable
   public void onEnable() {
      boners.clear();
      throwSlot = -1;
      first = true;
      throwTimer.reset();
      if (!this.invMode.isEnabled()) {
         for(int i = 0; i < 8; ++i) {
            ItemStack a = mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (a != null && a.func_82833_r().contains("Bonemerang")) {
               boners.add(i);
            }
         }
      } else {
         throwSlot = InventoryUtils.getAvailableHotbarSlot("Bonemerang");
         boners = InventoryUtils.getAllSlots(throwSlot, "Bonemerang");
         if (throwSlot == -1 || boners.isEmpty()) {
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
         }
      }

   }

   @Event
   public void onRenderTick(Render3DEvent event) {
      if (throwTimer.hasReached((long)this.throwDelay.getCurrent())) {
         int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         if (!this.invMode.isEnabled()) {
            if (!boners.isEmpty()) {
               InventoryUtils.throwSlot((Integer)boners.get(0));
               boners.remove(0);
            }
         } else if (first) {
            InventoryUtils.throwSlot(throwSlot);
            first = false;
         } else {
            if (!boners.isEmpty()) {
               mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, (Integer)boners.get(0), throwSlot, 2, mc.field_71439_g);
               boners.remove(0);
            }

            InventoryUtils.throwSlot(throwSlot);
         }

         if (this.silentUse.isEnabled()) {
            if (this.mainSlot.getCurrent() > 0 && this.mainSlot.getCurrent() <= 8) {
               mc.field_71439_g.field_71071_by.field_70461_c = this.mainSlot.getCurrent() - 1;
            } else {
               mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
            }
         } else if (boners.isEmpty() && this.mainSlot.getCurrent() > 0 && this.mainSlot.getCurrent() <= 8) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.mainSlot.getCurrent() - 1;
         }

         if (boners.isEmpty()) {
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
         }

         throwTimer.reset();
      }

   }
}

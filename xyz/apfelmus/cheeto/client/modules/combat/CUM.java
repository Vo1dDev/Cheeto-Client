package xyz.apfelmus.cheeto.client.modules.combat;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;

@Module(
   name = "CUM",
   category = Category.COMBAT
)
public class CUM {
   private static Minecraft mc = Minecraft.func_71410_x();
   @Setting(
      name = "MainSlot",
      description = "Slot of the weapon you want held"
   )
   private IntegerSetting mainSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "PickupStash"
   )
   private BooleanSetting pickupStash = new BooleanSetting(true);
   @Setting(
      name = "InvMode",
      description = "A bit bannable"
   )
   private BooleanSetting invMode = new BooleanSetting(false);

   @Enable
   public void onEnable() {
      int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
      int snowballSlot;
      if (!this.invMode.isEnabled()) {
         for(snowballSlot = 0; snowballSlot < 8; ++snowballSlot) {
            ItemStack a = mc.field_71439_g.field_71071_by.func_70301_a(snowballSlot);
            if (a != null && a.func_82833_r().contains("Snowball")) {
               InventoryUtils.throwSlot(snowballSlot);
            }
         }
      } else {
         snowballSlot = InventoryUtils.getAvailableHotbarSlot("Snowball");
         if (snowballSlot == -1 || InventoryUtils.getAllSlots(snowballSlot, "Snowball").size() == 0) {
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
            return;
         }

         InventoryUtils.throwSlot(snowballSlot);

         for(Iterator var3 = InventoryUtils.getAllSlots(snowballSlot, "Snowball").iterator(); var3.hasNext(); InventoryUtils.throwSlot(snowballSlot)) {
            int slotNum = (Integer)var3.next();
            ItemStack curInSlot = mc.field_71439_g.field_71071_by.func_70301_a(snowballSlot);
            if (curInSlot == null) {
               mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, slotNum, snowballSlot, 2, mc.field_71439_g);
            }
         }
      }

      if (this.mainSlot.getCurrent() > 0 && this.mainSlot.getCurrent() <= 8) {
         mc.field_71439_g.field_71071_by.field_70461_c = this.mainSlot.getCurrent() - 1;
      } else {
         mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
      }

      if (this.pickupStash.isEnabled()) {
         mc.field_71439_g.func_71165_d("/pickupstash");
      }

      CF4M.INSTANCE.moduleManager.toggle((Object)this);
   }
}

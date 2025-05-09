package xyz.apfelmus.cheeto.client.modules.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.BackgroundDrawnEvent;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;

@Module(
   name = "PetSwap",
   category = Category.MISC
)
public class PetSwap {
   private static Minecraft mc = Minecraft.func_71410_x();
   @Setting(
      name = "PetSlot"
   )
   private IntegerSetting petSlot = new IntegerSetting(11, 11, 44);

   @Enable
   public void onEnable() {
      mc.field_71439_g.func_71165_d("/pets");
   }

   @Event
   public void onTick(BackgroundDrawnEvent event) {
      String invName = InventoryUtils.getInventoryName();
      if (invName != null && invName.endsWith("Pets")) {
         ItemStack is = InventoryUtils.getStackInOpenContainerSlot(this.petSlot.getCurrent() - 1);
         if (is != null) {
            InventoryUtils.clickOpenContainerSlot(this.petSlot.getCurrent() - 1);
            mc.field_71439_g.func_71053_j();
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
         }
      }

   }
}

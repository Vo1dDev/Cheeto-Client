package xyz.apfelmus.cheeto.client.modules.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.GuiOpenEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.FloatSetting;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "F7Aura",
   category = Category.WORLD
)
public class F7Aura {
   @Setting(
      name = "Range"
   )
   FloatSetting range = new FloatSetting(5.0F, 0.0F, 32.0F);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static boolean clicked = false;
   private static List<Entity> terms = new ArrayList();

   @Enable
   public void onEnable() {
      terms.clear();
      clicked = false;
   }

   @Event
   public void onTick(ClientTickEvent event) {
      try {
         if (!SkyblockUtils.isInDungeon()) {
            return;
         }

         BlockPos pp = mc.field_71439_g.func_180425_c();
         int i = pp.func_177956_o();

         while(true) {
            label67: {
               if (i > 0) {
                  IBlockState bs = mc.field_71441_e.func_180495_p(new BlockPos(pp.func_177958_n(), i, pp.func_177952_p()));
                  if (bs == null) {
                     break label67;
                  }

                  Block b = bs.func_177230_c();
                  if (b == Blocks.field_150350_a) {
                     break label67;
                  }

                  if (b == Blocks.field_150353_l || b == Blocks.field_150356_k) {
                     return;
                  }
               }

               Iterator var7 = mc.field_71441_e.field_72996_f.iterator();

               Entity e;
               do {
                  if (!var7.hasNext()) {
                     return;
                  }

                  e = (Entity)var7.next();
               } while(!(e instanceof EntityArmorStand) || !e.func_70005_c_().contains("CLICK HERE") || clicked || mc.field_71439_g.field_71070_bA.field_75152_c != 0 || terms.contains(e) || !(e.func_70032_d(mc.field_71439_g) < this.range.getCurrent()));

               mc.field_71442_b.func_78768_b(mc.field_71439_g, e);
               clicked = true;
               terms.add(e);
               break;
            }

            --i;
         }
      } catch (Exception var6) {
      }

   }

   @Event
   public void onGui(GuiOpenEvent event) {
      clicked = false;
   }

   @Event
   public void onWorldLoad(WorldUnloadEvent event) {
      terms.clear();
      clicked = false;
   }
}

package xyz.apfelmus.cheeto.client.modules.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.PlayerInteractEvent;
import xyz.apfelmus.cheeto.client.utils.client.KeybindUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "SimonSays",
   category = Category.MISC
)
public class SimonSays {
   private static boolean clicking = false;
   private static Minecraft mc = Minecraft.func_71410_x();

   @Event
   public void onInteract(PlayerInteractEvent event) {
      if (!clicking && SkyblockUtils.isInDungeon() && event.action == Action.RIGHT_CLICK_BLOCK) {
         IBlockState bs = mc.field_71441_e.func_180495_p(event.pos);
         if (bs != null && bs.func_177230_c() == Blocks.field_150430_aB) {
            int x = event.pos.func_177958_n();
            int y = event.pos.func_177956_o();
            int z = event.pos.func_177952_p();
            if (x == 309 && y >= 120 && y <= 123 && z >= 291 && z <= 294) {
               clicking = true;

               for(int i = 0; i < 4; ++i) {
                  KeybindUtils.rightClick();
               }

               clicking = false;
            }
         }
      }

   }
}

package xyz.apfelmus.cheeto.client.modules.world;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;

@Module(
   name = "BetterStonk",
   category = Category.WORLD
)
public class BetterStonk {
   @Setting(
      name = "ShiftOnly"
   )
   private BooleanSetting shiftOnly = new BooleanSetting(false);
   private static Minecraft mc = Minecraft.func_71410_x();
   final ArrayList<Block> interactables;

   public BetterStonk() {
      this.interactables = new ArrayList(Arrays.asList(Blocks.field_180410_as, Blocks.field_150467_bQ, Blocks.field_150461_bJ, Blocks.field_150324_C, Blocks.field_180412_aq, Blocks.field_150382_bo, Blocks.field_150483_bI, Blocks.field_150462_ai, Blocks.field_150486_ae, Blocks.field_180409_at, Blocks.field_150453_bW, Blocks.field_180402_cm, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_150381_bn, Blocks.field_150477_bB, Blocks.field_150460_al, Blocks.field_150438_bZ, Blocks.field_180411_ar, Blocks.field_150442_at, Blocks.field_150323_B, Blocks.field_150455_bV, Blocks.field_150441_bU, Blocks.field_150416_aS, Blocks.field_150413_aR, Blocks.field_150472_an, Blocks.field_150444_as, Blocks.field_150415_aT, Blocks.field_150447_bR, Blocks.field_150471_bO, Blocks.field_150430_aB, Blocks.field_180413_ao, Blocks.field_150465_bP));
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (mc.field_71476_x.func_178782_a() != null) {
         if (mc.field_71474_y.field_74312_F.func_151470_d()) {
            if (mc.field_71439_g.field_71071_by.func_70448_g() != null) {
               if (!this.shiftOnly.isEnabled() || mc.field_71439_g.func_70093_af()) {
                  if (mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r().contains("Stonk")) {
                     Block block = Minecraft.func_71410_x().field_71441_e.func_180495_p(mc.field_71476_x.func_178782_a()).func_177230_c();
                     if (!this.interactables.contains(block)) {
                        mc.field_71441_e.func_175698_g(mc.field_71476_x.func_178782_a());
                     }
                  }

               }
            }
         }
      }
   }
}

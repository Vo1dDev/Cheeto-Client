package xyz.apfelmus.cheeto.client.modules.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;

@Module(
   name = "KavenBuster",
   category = Category.PLAYER
)
public class KavenBuster {
   @Setting(
      name = "ShowSpots"
   )
   private BooleanSetting showSpots = new BooleanSetting(false);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static List<Entity> macroers = new ArrayList();
   private static List<BlockPos> kavenSpots = new ArrayList(Arrays.asList(new BlockPos(-101, 215, 125), new BlockPos(-89, 156, 83), new BlockPos(11, 227, 132), new BlockPos(61, 148, 178), new BlockPos(68, 153, 174), new BlockPos(-15, 215, 137), new BlockPos(-33, 243, -35), new BlockPos(48, 218, 122), new BlockPos(104, 164, 49), new BlockPos(-10, 216, 155), new BlockPos(5, 215, 163), new BlockPos(50, 226, -28), new BlockPos(50, 149, 2), new BlockPos(104, 165, 32), new BlockPos(-148, 214, -23), new BlockPos(-2, 135, 178), new BlockPos(-62, 154, 155)));

   @Enable
   public void onEnable() {
      macroers.clear();
   }

   @Event
   public void onTick(ClientTickEvent event) {
      Iterator var2 = mc.field_71441_e.field_72996_f.iterator();

      while(true) {
         Entity e;
         String formatted;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var2.hasNext()) {
                                 return;
                              }

                              e = (Entity)var2.next();
                           } while(!(e instanceof EntityOtherPlayerMP));
                        } while(!kavenSpots.contains(e.func_180425_c()));
                     } while(e.func_70005_c_().equals("Goblin "));
                  } while(e.func_70005_c_().contains("Treasuer Hunter"));
               } while(e.func_70005_c_().contains("Crystal Sentry"));

               formatted = e.func_145748_c_().func_150254_d();
            } while(StringUtils.func_76338_a(formatted).equals(formatted));
         } while(formatted.startsWith("§r") && !formatted.startsWith("§r§"));

         if (!macroers.contains(e)) {
            macroers.add(e);
            BlockPos pos = e.func_180425_c();
            ChatUtils.send("KavenMod Enjoyer at &7" + pos.func_177958_n() + "," + pos.func_177956_o() + "," + pos.func_177952_p() + " &f: " + e.func_70005_c_());
         }
      }
   }

   @Event
   public void onRenderTick(Render3DEvent event) {
      Iterator var2;
      if (this.showSpots.isEnabled()) {
         var2 = kavenSpots.iterator();

         while(var2.hasNext()) {
            BlockPos bp = (BlockPos)var2.next();
            Render3DUtils.renderEspBox(bp, event.partialTicks, -256);
         }
      }

      var2 = (new ArrayList(macroers)).iterator();

      while(var2.hasNext()) {
         Entity macroer = (Entity)var2.next();
         if (mc.field_71441_e.field_72996_f.contains(macroer)) {
            Render3DUtils.renderBoundingBox(macroer, event.partialTicks, -16711936);
         } else {
            ChatUtils.send("KavenMod Enjoyer left: " + macroer.func_70005_c_());
            macroers.remove(macroer);
         }
      }

   }

   @Event
   public void onWorldLoad(WorldUnloadEvent event) {
      macroers.clear();
   }
}

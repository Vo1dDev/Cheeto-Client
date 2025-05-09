package xyz.apfelmus.cheeto.client.modules.misc;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.BackgroundDrawnEvent;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.GuiOpenEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;

@Module(
   name = "ExperimentSolver",
   category = Category.MISC
)
public class ExperimentSolver {
   @Setting(
      name = "Chronomatron"
   )
   BooleanSetting chronomatron = new BooleanSetting(true);
   @Setting(
      name = "Ultrasequencer"
   )
   BooleanSetting ultrasequencer = new BooleanSetting(true);
   private static int until = 0;
   private static int tickAmount = 0;
   private static Minecraft mc = Minecraft.func_71410_x();
   private static Slot[] clickInOrderSlots = new Slot[36];
   private static int lastChronomatronRound = 0;
   private static List<String> chronomatronPattern = new ArrayList();
   private static int chronomatronMouseClicks = 0;
   private static int lastUltraSequencerClicked = 0;
   private static long lastInteractTime = 0L;

   @Event
   public void onTick(BackgroundDrawnEvent event) {
      if (mc.field_71462_r instanceof GuiChest) {
         GuiChest inventory = (GuiChest)event.gui;
         Container containerChest = inventory.field_147002_h;
         if (containerChest instanceof ContainerChest) {
            List<Slot> invSlots = containerChest.field_75151_b;
            String invName = ((ContainerChest)containerChest).func_85151_d().func_145748_c_().func_150260_c().trim();
            EntityPlayerSP player;
            int timerSeconds;
            int i;
            if (this.chronomatron.isEnabled() && invName.startsWith("Chronomatron (")) {
               player = mc.field_71439_g;
               if (player.field_71071_by.func_70445_o() == null && invSlots.size() > 48 && ((Slot)invSlots.get(49)).func_75211_c() != null) {
                  if (((Slot)invSlots.get(49)).func_75211_c().func_82833_r().startsWith("§7Timer: §a") && ((Slot)invSlots.get(4)).func_75211_c() != null) {
                     int round = ((Slot)invSlots.get(4)).func_75211_c().field_77994_a;
                     timerSeconds = Integer.parseInt(StringUtils.func_76338_a(((Slot)invSlots.get(49)).func_75211_c().func_82833_r()).replaceAll("[^\\d]", ""));
                     ItemStack glass;
                     if (round != lastChronomatronRound && timerSeconds == round + 2) {
                        lastChronomatronRound = round;

                        for(i = 10; i <= 43; ++i) {
                           glass = ((Slot)invSlots.get(i)).func_75211_c();
                           if (glass != null && glass.func_77973_b() == Item.func_150898_a(Blocks.field_150406_ce)) {
                              chronomatronPattern.add(glass.func_82833_r());
                              break;
                           }
                        }
                     }

                     if (chronomatronMouseClicks < chronomatronPattern.size() && player.field_71071_by.func_70445_o() == null) {
                        for(i = 10; i <= 43; ++i) {
                           glass = ((Slot)invSlots.get(i)).func_75211_c();
                           if (glass != null && player.field_71071_by.func_70445_o() == null && tickAmount % 5 == 0) {
                              Slot glassSlot = (Slot)invSlots.get(i);
                              if (glass.func_82833_r().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                 mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, glassSlot.field_75222_d, 0, 0, mc.field_71439_g);
                                 lastInteractTime = 0L;
                                 ++chronomatronMouseClicks;
                                 break;
                              }
                           }
                        }
                     }
                  } else if (((Slot)invSlots.get(49)).func_75211_c().func_82833_r().equals("§aRemember the pattern!")) {
                     chronomatronMouseClicks = 0;
                  }
               }
            }

            if (this.ultrasequencer.isEnabled() && invName.startsWith("Ultrasequencer (")) {
               player = mc.field_71439_g;
               if (invSlots.size() > 48 && ((Slot)invSlots.get(49)).func_75211_c() != null && player.field_71071_by.func_70445_o() == null && ((Slot)invSlots.get(49)).func_75211_c().func_82833_r().startsWith("§7Timer: §a")) {
                  lastUltraSequencerClicked = 0;
                  Slot[] var12 = clickInOrderSlots;
                  timerSeconds = var12.length;

                  for(i = 0; i < timerSeconds; ++i) {
                     Slot slot4 = var12[i];
                     if (slot4 != null && slot4.func_75211_c() != null && StringUtils.func_76338_a(slot4.func_75211_c().func_82833_r()).matches("\\d+")) {
                        int number = Integer.parseInt(StringUtils.func_76338_a(slot4.func_75211_c().func_82833_r()));
                        if (number > lastUltraSequencerClicked) {
                           lastUltraSequencerClicked = number;
                        }
                     }
                  }

                  Slot nextSlot;
                  if (clickInOrderSlots[lastUltraSequencerClicked] != null && player.field_71071_by.func_70445_o() == null && tickAmount % 2 == 0 && lastUltraSequencerClicked != 0 && until == lastUltraSequencerClicked) {
                     nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                     mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, nextSlot.field_75222_d, 0, 0, mc.field_71439_g);
                     until = lastUltraSequencerClicked + 1;
                     tickAmount = 0;
                  }

                  if (clickInOrderSlots[lastUltraSequencerClicked] != null && player.field_71071_by.func_70445_o() == null && tickAmount == 18 && lastUltraSequencerClicked < 1) {
                     nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                     mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, nextSlot.field_75222_d, 0, 0, mc.field_71439_g);
                     tickAmount = 0;
                     until = 1;
                  }
               }
            }
         }
      }

   }

   @Event
   public void onGuiOpen(GuiOpenEvent event) {
      clickInOrderSlots = new Slot[36];
      lastChronomatronRound = 0;
      chronomatronPattern.clear();
      chronomatronMouseClicks = 0;
   }

   @Event
   public void onTick(ClientTickEvent event) {
      ++tickAmount;
      if (tickAmount % 20 == 0) {
         tickAmount = 0;
      }

      if (mc.field_71462_r instanceof GuiChest && this.ultrasequencer.isEnabled()) {
         ContainerChest chest = (ContainerChest)mc.field_71439_g.field_71070_bA;
         List<Slot> invSlots = ((GuiChest)mc.field_71462_r).field_147002_h.field_75151_b;
         String chestName = chest.func_85151_d().func_145748_c_().func_150260_c().trim();
         if (chestName.startsWith("Ultrasequencer (") && ((Slot)invSlots.get(49)).func_75211_c() != null && ((Slot)invSlots.get(49)).func_75211_c().func_82833_r().equals("§aRemember the pattern!")) {
            for(int l = 9; l <= 44; ++l) {
               if (invSlots.get(l) != null && ((Slot)invSlots.get(l)).func_75211_c() != null) {
                  String itemName = StringUtils.func_76338_a(((Slot)invSlots.get(l)).func_75211_c().func_82833_r());
                  if (itemName.matches("\\d+")) {
                     int number = Integer.parseInt(itemName);
                     clickInOrderSlots[number - 1] = (Slot)invSlots.get(l);
                  }
               }
            }
         }
      }

   }
}

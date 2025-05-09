package xyz.apfelmus.cheeto.client.modules.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.BackgroundDrawnEvent;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "ApfelTerms",
   category = Category.MISC
)
public class ApfelTerms {
   @Setting(
      name = "Delay"
   )
   private IntegerSetting delay = new IntegerSetting(150, 0, 1000);
   @Setting(
      name = "RandomRange"
   )
   private IntegerSetting randomRange = new IntegerSetting(100, 0, 1000);
   @Setting(
      name = "MaxLag",
      description = "Set to something slightly above your ping"
   )
   private IntegerSetting maxLag = new IntegerSetting(250, 0, 1000);
   @Setting(
      name = "Maze"
   )
   BooleanSetting maze = new BooleanSetting(true);
   @Setting(
      name = "Order"
   )
   BooleanSetting order = new BooleanSetting(true);
   @Setting(
      name = "Panes"
   )
   BooleanSetting panes = new BooleanSetting(true);
   @Setting(
      name = "Name"
   )
   BooleanSetting name = new BooleanSetting(true);
   @Setting(
      name = "Color"
   )
   BooleanSetting color = new BooleanSetting(true);
   @Setting(
      name = "Pingless"
   )
   BooleanSetting pingless = new BooleanSetting(true);
   private static ApfelTerms.Terminal currentTerm;
   private static List<ApfelTerms.TermSlot> clickQueue;
   private static boolean recalculate;
   private static final int[] mazeDirection;
   private static char letterNeeded;
   private static String colorNeeded;
   private static int windowId;
   private static long lastClickTime;
   private static int windowClicks;
   private static int currentSlot;
   private static Minecraft mc;

   @Event
   public void onGuiDraw(BackgroundDrawnEvent event) {
      if (SkyblockUtils.isInDungeon()) {
         if (event.gui instanceof GuiChest) {
            Container container = ((GuiChest)event.gui).field_147002_h;
            if (container instanceof ContainerChest) {
               List<Slot> invSlots = container.field_75151_b;
               if (currentTerm == ApfelTerms.Terminal.NONE) {
                  String chestName = ((ContainerChest)container).func_85151_d().func_145748_c_().func_150260_c();
                  if (chestName.equals("Navigate the maze!")) {
                     currentTerm = ApfelTerms.Terminal.MAZE;
                  } else if (chestName.equals("Click in order!")) {
                     currentTerm = ApfelTerms.Terminal.ORDER;
                  } else if (chestName.equals("Correct all the panes!")) {
                     currentTerm = ApfelTerms.Terminal.PANES;
                  } else if (chestName.startsWith("What starts with:")) {
                     currentTerm = ApfelTerms.Terminal.NAME;
                  } else if (chestName.startsWith("Select all the")) {
                     currentTerm = ApfelTerms.Terminal.COLOR;
                  }
               } else {
                  if (!clickQueue.isEmpty() && !recalculate) {
                     ApfelTerms.TermSlot slot;
                     Slot s;
                     Iterator var7;
                     label90:
                     switch(currentTerm) {
                     case MAZE:
                     case ORDER:
                     case PANES:
                        var7 = clickQueue.iterator();

                        while(true) {
                           if (!var7.hasNext()) {
                              break label90;
                           }

                           slot = (ApfelTerms.TermSlot)var7.next();
                           if (slot.clicked > 0L && System.currentTimeMillis() - slot.clicked >= (long)this.maxLag.getCurrent()) {
                              s = (Slot)invSlots.get(slot.slot.field_75222_d);
                              if (s.func_75216_d() && s.func_75211_c().func_77952_i() != 5) {
                                 currentSlot = clickQueue.indexOf(slot);
                                 windowClicks = currentSlot;
                                 break label90;
                              }
                           }
                        }
                     case NAME:
                     case COLOR:
                        var7 = clickQueue.iterator();

                        while(var7.hasNext()) {
                           slot = (ApfelTerms.TermSlot)var7.next();
                           if (slot.clicked > 0L && System.currentTimeMillis() - slot.clicked >= (long)this.maxLag.getCurrent()) {
                              s = (Slot)invSlots.get(slot.slot.field_75222_d);
                              if (s.func_75216_d() && !s.func_75211_c().func_77948_v()) {
                                 currentSlot = clickQueue.indexOf(slot);
                                 windowClicks = currentSlot;
                                 break;
                              }
                           }
                        }
                     }
                  } else {
                     recalculate = getClicks((ContainerChest)container);
                  }

                  if (!clickQueue.isEmpty() && currentSlot < clickQueue.size() && (double)(System.currentTimeMillis() - lastClickTime) >= (double)this.randomRange.getCurrent() / 2.0D - Math.random() * (double)this.randomRange.getCurrent() + (double)this.delay.getCurrent()) {
                     switch(currentTerm) {
                     case MAZE:
                        if (this.maze.isEnabled()) {
                           this.clickSlot((ApfelTerms.TermSlot)clickQueue.get(currentSlot));
                        }
                        break;
                     case ORDER:
                        if (this.order.isEnabled()) {
                           this.clickSlot((ApfelTerms.TermSlot)clickQueue.get(currentSlot));
                        }
                        break;
                     case PANES:
                        if (this.panes.isEnabled()) {
                           this.clickSlot((ApfelTerms.TermSlot)clickQueue.get(currentSlot));
                        }
                        break;
                     case NAME:
                        if (this.name.isEnabled()) {
                           this.clickSlot((ApfelTerms.TermSlot)clickQueue.get(currentSlot));
                        }
                        break;
                     case COLOR:
                        if (this.color.isEnabled()) {
                           this.clickSlot((ApfelTerms.TermSlot)clickQueue.get(currentSlot));
                        }
                     }
                  }
               }
            }
         }

      }
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (SkyblockUtils.isInDungeon()) {
         if (!(mc.field_71462_r instanceof GuiChest)) {
            currentTerm = ApfelTerms.Terminal.NONE;
            clickQueue.clear();
            letterNeeded = 0;
            colorNeeded = null;
            windowClicks = 0;
            currentSlot = 0;
         }

      }
   }

   private static boolean getClicks(ContainerChest cc) {
      List<Slot> invSlots = cc.field_75151_b;
      String chestName = cc.func_85151_d().func_145748_c_().func_150260_c();
      clickQueue.clear();
      int i;
      int slotNumber;
      Iterator var11;
      Slot slot;
      ItemStack itemStack;
      switch(currentTerm) {
      case MAZE:
         int startSlot = -1;
         int endSlot = -1;
         boolean[] mazeVisited = new boolean[54];
         var11 = invSlots.iterator();

         while(var11.hasNext()) {
            slot = (Slot)var11.next();
            if (startSlot >= 0 && endSlot >= 0) {
               break;
            }

            if (slot.field_75224_c != mc.field_71439_g.field_71071_by) {
               itemStack = slot.func_75211_c();
               if (itemStack != null && itemStack.func_77973_b() == Item.func_150898_a(Blocks.field_150397_co)) {
                  if (itemStack.func_77952_i() == 5) {
                     startSlot = slot.field_75222_d;
                  } else if (itemStack.func_77952_i() == 14) {
                     endSlot = slot.field_75222_d;
                  }
               }
            }
         }

         boolean newSlotChosen;
         do {
            if (startSlot == endSlot) {
               return false;
            }

            newSlotChosen = false;

            for(i = 0; i < 4; ++i) {
               slotNumber = startSlot + mazeDirection[i];
               if (slotNumber == endSlot) {
                  return false;
               }

               if (slotNumber >= 0 && slotNumber <= 53 && (i != 1 || slotNumber % 9 != 8) && (i != 2 || slotNumber % 9 != 0) && !mazeVisited[slotNumber]) {
                  ItemStack stack = ((Slot)invSlots.get(slotNumber)).func_75211_c();
                  if (stack != null && stack.func_77973_b() == Item.func_150898_a(Blocks.field_150397_co) && stack.func_77952_i() == 0) {
                     clickQueue.add(new ApfelTerms.TermSlot((Slot)invSlots.get(slotNumber)));
                     startSlot = slotNumber;
                     mazeVisited[slotNumber] = true;
                     newSlotChosen = true;
                     break;
                  }
               }
            }
         } while(newSlotChosen);

         return true;
      case ORDER:
         while(clickQueue.size() < 14) {
            clickQueue.add((Object)null);
         }

         for(int j = 10; j <= 25; ++j) {
            if (j != 17 && j != 18) {
               ItemStack itemStack3 = ((Slot)invSlots.get(j)).func_75211_c();
               if (itemStack3 != null && itemStack3.func_77973_b() == Item.func_150898_a(Blocks.field_150397_co) && itemStack3.func_77952_i() == 14 && itemStack3.field_77994_a < 15) {
                  clickQueue.set(itemStack3.field_77994_a - 1, new ApfelTerms.TermSlot((Slot)invSlots.get(j)));
               }
            }
         }

         if (clickQueue.removeIf(Objects::isNull)) {
            return true;
         }
         break;
      case PANES:
         var11 = invSlots.iterator();

         while(var11.hasNext()) {
            slot = (Slot)var11.next();
            if (slot.field_75224_c != mc.field_71439_g.field_71071_by && slot.field_75222_d >= 9 && slot.field_75222_d <= 35 && slot.field_75222_d % 9 > 1 && slot.field_75222_d % 9 < 7) {
               itemStack = slot.func_75211_c();
               if (itemStack == null) {
                  return true;
               }

               if (itemStack.func_77973_b() == Item.func_150898_a(Blocks.field_150397_co) && itemStack.func_77952_i() == 14) {
                  clickQueue.add(new ApfelTerms.TermSlot(slot));
               }
            }
         }

         return false;
      case NAME:
         letterNeeded = chestName.charAt(chestName.indexOf("'") + 1);
         if (letterNeeded != 0) {
            var11 = invSlots.iterator();

            while(var11.hasNext()) {
               slot = (Slot)var11.next();
               if (slot.field_75224_c != mc.field_71439_g.field_71071_by && slot.field_75222_d >= 9 && slot.field_75222_d <= 44 && slot.field_75222_d % 9 != 0 && slot.field_75222_d % 9 != 8) {
                  itemStack = slot.func_75211_c();
                  if (itemStack == null) {
                     return true;
                  }

                  if (!itemStack.func_77948_v() && StringUtils.func_76338_a(itemStack.func_82833_r()).charAt(0) == letterNeeded) {
                     clickQueue.add(new ApfelTerms.TermSlot(slot));
                  }
               }
            }
         }
         break;
      case COLOR:
         EnumDyeColor[] var6 = EnumDyeColor.values();
         i = var6.length;

         for(slotNumber = 0; slotNumber < i; ++slotNumber) {
            EnumDyeColor color = var6[slotNumber];
            String colorName = color.func_176610_l().replaceAll("_", " ").toUpperCase();
            if (chestName.contains(colorName)) {
               colorNeeded = color.func_176762_d();
               break;
            }
         }

         if (colorNeeded != null) {
            var11 = invSlots.iterator();

            while(var11.hasNext()) {
               slot = (Slot)var11.next();
               if (slot.field_75224_c != mc.field_71439_g.field_71071_by && slot.field_75222_d >= 9 && slot.field_75222_d <= 44 && slot.field_75222_d % 9 != 0 && slot.field_75222_d % 9 != 8) {
                  itemStack = slot.func_75211_c();
                  if (itemStack == null) {
                     return true;
                  }

                  if (!itemStack.func_77948_v() && itemStack.func_77977_a().contains(colorNeeded)) {
                     clickQueue.add(new ApfelTerms.TermSlot(slot));
                  }
               }
            }
         }
      }

      return false;
   }

   private void clickSlot(ApfelTerms.TermSlot slot) {
      if (windowClicks == 0) {
         windowId = mc.field_71439_g.field_71070_bA.field_75152_c;
      }

      mc.field_71442_b.func_78753_a(windowId + (this.pingless.isEnabled() ? windowClicks : 0), slot.slot.field_75222_d, 2, 0, mc.field_71439_g);
      slot.clicked = System.currentTimeMillis();
      lastClickTime = System.currentTimeMillis();
      ++currentSlot;
      if (this.pingless.isEnabled()) {
         ++windowClicks;
      }

   }

   static {
      currentTerm = ApfelTerms.Terminal.NONE;
      clickQueue = new ArrayList();
      recalculate = false;
      mazeDirection = new int[]{-9, -1, 1, 9};
      letterNeeded = 0;
      colorNeeded = null;
      windowClicks = 0;
      currentSlot = 0;
      mc = Minecraft.func_71410_x();
   }

   private static class TermSlot {
      public Slot slot;
      public long clicked;

      public TermSlot(Slot slot) {
         this.slot = slot;
         this.clicked = -1L;
      }

      public String toString() {
         return this.slot.field_75222_d + " - " + this.clicked;
      }
   }

   private static enum Terminal {
      NONE,
      MAZE,
      ORDER,
      PANES,
      NAME,
      COLOR;
   }
}

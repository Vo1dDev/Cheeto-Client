package xyz.apfelmus.cheeto.client.utils.skyblock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class InventoryUtils {
   private static Minecraft mc = Minecraft.func_71410_x();

   public static String getInventoryName() {
      if (mc.field_71462_r instanceof GuiChest) {
         ContainerChest chest = (ContainerChest)mc.field_71439_g.field_71070_bA;
         IInventory inv = chest.func_85151_d();
         return inv.func_145818_k_() ? inv.func_70005_c_() : null;
      } else {
         return null;
      }
   }

   public static ItemStack getStackInSlot(int slot) {
      return mc.field_71439_g.field_71071_by.func_70301_a(slot);
   }

   public static ItemStack getStackInOpenContainerSlot(int slot) {
      return ((Slot)mc.field_71439_g.field_71070_bA.field_75151_b.get(slot)).func_75216_d() ? ((Slot)mc.field_71439_g.field_71070_bA.field_75151_b.get(slot)).func_75211_c() : null;
   }

   public static int getSlotForItem(String itemName, Item item) {
      Iterator var2 = mc.field_71439_g.field_71070_bA.field_75151_b.iterator();

      while(var2.hasNext()) {
         Slot slot = (Slot)var2.next();
         if (slot.func_75216_d()) {
            ItemStack is = slot.func_75211_c();
            if (is.func_77973_b() == item && is.func_82833_r().contains(itemName)) {
               return slot.field_75222_d;
            }
         }
      }

      return -1;
   }

   public static void clickOpenContainerSlot(int slot, int nextWindow) {
      mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c + nextWindow, slot, 0, 0, mc.field_71439_g);
   }

   public static void clickOpenContainerSlot(int slot) {
      mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, slot, 0, 0, mc.field_71439_g);
   }

   public static int getAvailableHotbarSlot(String name) {
      for(int i = 0; i < 8; ++i) {
         ItemStack is = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (is == null || is.func_82833_r().contains(name)) {
            return i;
         }
      }

      return -1;
   }

   public static List<Integer> getAllSlots(int throwSlot, String name) {
      List<Integer> ret = new ArrayList();

      for(int i = 9; i < 44; ++i) {
         ItemStack is = ((Slot)mc.field_71439_g.field_71069_bz.field_75151_b.get(i)).func_75211_c();
         if (is != null && is.func_82833_r().contains(name) && i - 36 != throwSlot) {
            ret.add(i);
         }
      }

      return ret;
   }

   public static void throwSlot(int slot) {
      ItemStack curInSlot = mc.field_71439_g.field_71071_by.func_70301_a(slot);
      if (curInSlot != null) {
         if (curInSlot.func_82833_r().contains("Snowball")) {
            int ss = curInSlot.field_77994_a;

            for(int i = 0; i < ss; ++i) {
               mc.field_71439_g.field_71071_by.field_70461_c = slot;
               mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70301_a(slot));
            }
         } else {
            mc.field_71439_g.field_71071_by.field_70461_c = slot;
            mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70301_a(slot));
         }
      }

   }

   public static int getAmountInHotbar(String item) {
      for(int i = 0; i < 8; ++i) {
         ItemStack is = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (is != null && StringUtils.func_76338_a(is.func_82833_r()).equals(item)) {
            return is.field_77994_a;
         }
      }

      return 0;
   }

   public static int getItemInHotbar(String itemName) {
      for(int i = 0; i < 8; ++i) {
         ItemStack is = mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (is != null && StringUtils.func_76338_a(is.func_82833_r()).contains(itemName)) {
            return i;
         }
      }

      return -1;
   }

   public static List<ItemStack> getInventoryStacks() {
      List<ItemStack> ret = new ArrayList();

      for(int i = 9; i < 44; ++i) {
         Slot slot = mc.field_71439_g.field_71069_bz.func_75139_a(i);
         if (slot != null) {
            ItemStack stack = slot.func_75211_c();
            if (stack != null) {
               ret.add(stack);
            }
         }
      }

      return ret;
   }
}

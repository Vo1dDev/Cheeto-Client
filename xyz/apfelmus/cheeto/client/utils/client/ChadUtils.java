package xyz.apfelmus.cheeto.client.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MouseHelper;
import org.lwjgl.input.Mouse;

public class ChadUtils {
   private static Minecraft mc = Minecraft.func_71410_x();
   public static boolean isUngrabbed = false;
   private static MouseHelper oldMouseHelper;
   private static boolean doesGameWantUngrab = true;
   private static int oldRenderDist = 0;
   private static int oldFpsCap = 0;
   private static boolean improving = false;

   public static void ungrabMouse() {
      if (mc.field_71415_G && !isUngrabbed) {
         if (oldMouseHelper == null) {
            oldMouseHelper = mc.field_71417_B;
         }

         mc.field_71474_y.field_82881_y = false;
         doesGameWantUngrab = !Mouse.isGrabbed();
         oldMouseHelper.func_74373_b();
         mc.field_71415_G = true;
         mc.field_71417_B = new MouseHelper() {
            public void func_74374_c() {
            }

            public void func_74372_a() {
               ChadUtils.doesGameWantUngrab = false;
            }

            public void func_74373_b() {
               ChadUtils.doesGameWantUngrab = true;
            }
         };
         isUngrabbed = true;
      }
   }

   public static void regrabMouse() {
      if (isUngrabbed) {
         mc.field_71417_B = oldMouseHelper;
         if (!doesGameWantUngrab) {
            mc.field_71417_B.func_74372_a();
         }

         oldMouseHelper = null;
         isUngrabbed = false;
      }
   }

   public static void improveCpuUsage() {
      if (!improving) {
         oldRenderDist = mc.field_71474_y.field_151451_c;
         oldFpsCap = mc.field_71474_y.field_74350_i;
         mc.field_71474_y.field_151451_c = 2;
         mc.field_71474_y.field_74350_i = 30;
         improving = true;
      }

   }

   public static void revertCpuUsage() {
      mc.field_71474_y.field_151451_c = oldRenderDist;
      mc.field_71474_y.field_74350_i = oldFpsCap;
      improving = false;
   }
}

package xyz.apfelmus.cheeto.client.utils.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeybindUtils {
   private static Minecraft mc = Minecraft.func_71410_x();
   private static Method clickMouse;
   private static Method rightClickMouse;

   public static void setup() {
      try {
         clickMouse = Minecraft.class.getDeclaredMethod("clickMouse");
      } catch (NoSuchMethodException var5) {
         try {
            clickMouse = Minecraft.class.getDeclaredMethod("func_147116_af");
         } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
         }
      }

      try {
         rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse");
      } catch (NoSuchMethodException var3) {
         try {
            rightClickMouse = Minecraft.class.getDeclaredMethod("func_147121_ag");
         } catch (NoSuchMethodException var2) {
            var3.printStackTrace();
         }
      }

      if (clickMouse != null) {
         clickMouse.setAccessible(true);
      }

      if (rightClickMouse != null) {
         rightClickMouse.setAccessible(true);
      }

   }

   public static void leftClick() {
      try {
         clickMouse.invoke(Minecraft.func_71410_x());
      } catch (IllegalAccessException | InvocationTargetException var1) {
         var1.printStackTrace();
      }

   }

   public static void rightClick() {
      try {
         rightClickMouse.invoke(Minecraft.func_71410_x());
      } catch (InvocationTargetException | IllegalAccessException var1) {
      }

   }

   public static void stopMovement() {
      KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74368_y.func_151463_i(), false);
   }
}

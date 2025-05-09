package xyz.apfelmus.cheeto.client.utils.client;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FontUtils {
   private static FontRenderer fr;

   public static void drawString(String text, int x, int y, int color) {
      fr.func_175065_a(text, (float)x, (float)y, color, true);
   }

   public static void drawVCenteredString(String text, int x, int y, int color) {
      fr.func_175065_a(text, (float)x, (float)(y - fr.field_78288_b / 2), color, true);
   }

   public static void drawHVCenteredString(String text, int x, int y, int color) {
      fr.func_175065_a(text, (float)(x - fr.func_78256_a(text) / 2), (float)(y - fr.field_78288_b / 2), color, true);
   }

   public static void drawHVCenteredChromaString(String text, int x, int y, int offset) {
      drawChromaString(text, x - fr.func_78256_a(text) / 2, y - fr.field_78288_b / 2, offset);
   }

   public static int getStringWidth(String text) {
      return fr.func_78256_a(text);
   }

   public static int getFontHeight() {
      return fr.field_78288_b;
   }

   public static void drawChromaString(String text, int x, int y, int offset) {
      double tmpX = (double)x;
      char[] var6 = text.toCharArray();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         char tc = var6[var8];
         long t = System.currentTimeMillis() - ((long)((int)tmpX) * 10L - (long)y - (long)offset * 10L);
         int i = Color.HSBtoRGB((float)(t % 2000L) / 2000.0F, 0.88F, 0.88F);
         String tmp = String.valueOf(tc);
         fr.func_175065_a(tmp, (float)((int)tmpX), (float)y, i, true);
         tmpX += (double)fr.func_78263_a(tc);
      }

   }

   static {
      fr = Minecraft.func_71410_x().field_71466_p;
   }
}

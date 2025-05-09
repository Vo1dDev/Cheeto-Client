package xyz.apfelmus.cheeto.client.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Render2DUtils {
   public static void drawRectWH(int x, int y, int width, int height, int color) {
      drawRect(x, y, x + width, y + height, color);
   }

   public static void drawRect(int left, int top, int right, int bottom, int color) {
      int j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(f, f1, f2, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)left, (double)bottom, 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)bottom, 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)top, 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, 0.0D).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawLeftRoundedRect(float x, float y, float width, float height, float radius, int color) {
      width += x;
      x += radius;
      width -= radius;
      if (x < width) {
         width = x;
      }

      height += y;
      float f3;
      if (y < height) {
         f3 = y;
         y = height;
         height = f3;
      }

      f3 = (float)(color >> 24 & 255) / 255.0F;
      float f4 = (float)(color >> 16 & 255) / 255.0F;
      float f5 = (float)(color >> 8 & 255) / 255.0F;
      float f6 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(f4, f5, f6, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)(width - radius), (double)(y - radius), 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)width, (double)(y - radius), 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)width, (double)(height + radius), 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)(width - radius), (double)(height + radius), 0.0D).func_181675_d();
      tessellator.func_78381_a();
      drawArc(width, height + radius, radius, 180);
      drawArc(width, y - radius, radius, 270);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawArc(float x, float y, float radius, int angleStart) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);
      GlStateManager.func_179137_b((double)x, (double)y, 0.0D);
      worldrenderer.func_181662_b(0.0D, 0.0D, 0.0D).func_181675_d();
      int points = 21;

      for(double i = 0.0D; i < (double)points; ++i) {
         double radians = Math.toRadians(i / (double)points * 90.0D + (double)angleStart);
         worldrenderer.func_181662_b((double)radius * Math.sin(radians), (double)radius * Math.cos(radians), 0.0D).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179137_b((double)(-x), (double)(-y), 0.0D);
   }

   public static void drawTexture(ResourceLocation resourceLocation, int x, int y, int width, int height, int textureWidth, int textureHeight, int textureX, int textureY) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.func_146110_a(x, y, (float)textureX, (float)textureY, width, height, (float)textureWidth, (float)textureHeight);
   }
}

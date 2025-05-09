package xyz.apfelmus.cheeto.client.utils.render;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cheeto.client.modules.render.ESP;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;
import xyz.apfelmus.cheeto.injection.mixins.RenderManagerAccessor;

public class Render3DUtils {
   private static Minecraft mc = Minecraft.func_71410_x();

   public static void renderStarredMobBoundingBox(Entity entity, float partialTicks) {
      RenderManagerAccessor rm = (RenderManagerAccessor)mc.func_175598_ae();
      double renderPosX = rm.getRenderPosX();
      double renderPosY = rm.getRenderPosY();
      double renderPosZ = rm.getRenderPosZ();
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks - renderPosX;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks - renderPosY;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks - renderPosZ;
      int color = ColorUtils.getChroma(3000.0F, 0);
      AxisAlignedBB entityBox = entity.func_174813_aQ();
      AxisAlignedBB aabb = AxisAlignedBB.func_178781_a(entityBox.field_72340_a - entity.field_70165_t + x - 0.4D, entityBox.field_72338_b - entity.field_70163_u + y - (entity.func_95999_t().contains("Fels") ? 3.15D : 2.1D), entityBox.field_72339_c - entity.field_70161_v + z - 0.4D, entityBox.field_72336_d - entity.field_70165_t + x + 0.4D, entityBox.field_72337_e - entity.field_70163_u + y, entityBox.field_72334_f - entity.field_70161_v + z + 0.4D);
      drawFilledBoundingBox(aabb, color);
   }

   public static void renderMiniBoundingBox(Entity entity, float partialTicks, int color) {
      RenderManagerAccessor rm = (RenderManagerAccessor)mc.func_175598_ae();
      double renderPosX = rm.getRenderPosX();
      double renderPosY = rm.getRenderPosY();
      double renderPosZ = rm.getRenderPosZ();
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks - renderPosX;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks - renderPosY;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks - renderPosZ;
      AxisAlignedBB entityBox = entity.func_174813_aQ().func_72314_b(0.1D, 0.1D, 0.1D);
      AxisAlignedBB aabb = AxisAlignedBB.func_178781_a(entityBox.field_72340_a - entity.field_70165_t + x, entityBox.field_72338_b - entity.field_70163_u + y, entityBox.field_72339_c - entity.field_70161_v + z, entityBox.field_72336_d - entity.field_70165_t + x, entityBox.field_72337_e - entity.field_70163_u + y, entityBox.field_72334_f - entity.field_70161_v + z);
      drawFilledBoundingBox(aabb, color);
   }

   public static void renderBoundingBox(Entity entity, float partialTicks, int color) {
      RenderManagerAccessor rm = (RenderManagerAccessor)mc.func_175598_ae();
      double renderPosX = rm.getRenderPosX();
      double renderPosY = rm.getRenderPosY();
      double renderPosZ = rm.getRenderPosZ();
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks - renderPosX;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks - renderPosY;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks - renderPosZ;
      AxisAlignedBB bbox = entity.func_174813_aQ();
      AxisAlignedBB aabb = new AxisAlignedBB(bbox.field_72340_a - entity.field_70165_t + x, bbox.field_72338_b - entity.field_70163_u + y, bbox.field_72339_c - entity.field_70161_v + z, bbox.field_72336_d - entity.field_70165_t + x, bbox.field_72337_e - entity.field_70163_u + y, bbox.field_72334_f - entity.field_70161_v + z);
      if (entity instanceof EntityArmorStand) {
         aabb = aabb.func_72314_b(0.3D, 2.0D, 0.3D);
      }

      drawFilledBoundingBox(aabb, color);
   }

   public static void renderSmallBox(Vec3 vec, int color) {
      RenderManagerAccessor rm = (RenderManagerAccessor)mc.func_175598_ae();
      double renderPosX = rm.getRenderPosX();
      double renderPosY = rm.getRenderPosY();
      double renderPosZ = rm.getRenderPosZ();
      double x = vec.field_72450_a - renderPosX;
      double y = vec.field_72448_b - renderPosY;
      double z = vec.field_72449_c - renderPosZ;
      AxisAlignedBB aabb = new AxisAlignedBB(x - 0.05D, y - 0.05D, z - 0.05D, x + 0.05D, y + 0.05D, z + 0.05D);
      drawFilledBoundingBox(aabb, color);
   }

   public static void drawFilledBoundingBox(AxisAlignedBB aabb, int color) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179090_x();
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      Object esp = CF4M.INSTANCE.moduleManager.getModule("ESP");
      ESP es = (ESP)esp;
      float opacity = es.boxOpacity.getCurrent();
      GlStateManager.func_179131_c(r, g, b, a * opacity);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179131_c(r, g, b, a * opacity);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179131_c(r, g, b, a * opacity);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179131_c(r, g, b, a);
      RenderGlobal.func_181561_a(aabb);
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawChestOutline(BlockPos chestPos) {
      RenderManagerAccessor rm = (RenderManagerAccessor)mc.func_175598_ae();
      double renderPosX = rm.getRenderPosX();
      double renderPosY = rm.getRenderPosY();
      double renderPosZ = rm.getRenderPosZ();
      double x = (double)chestPos.func_177958_n() - renderPosX + 0.062D;
      double y = (double)chestPos.func_177956_o() - renderPosY;
      double z = (double)chestPos.func_177952_p() - renderPosZ + 0.062D;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(3.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(0.1764706F, 0.49019608F, 0.98039216F, 1.0F);
      RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 0.876D, y + 0.875D, z + 0.876D));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void renderEspBox(BlockPos blockPos, float partialTicks, int color) {
      if (blockPos != null) {
         IBlockState blockState = mc.field_71441_e.func_180495_p(blockPos);
         if (blockState != null) {
            Block block = blockState.func_177230_c();
            block.func_180654_a(mc.field_71441_e, blockPos);
            double d0 = mc.field_71439_g.field_70142_S + (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70142_S) * (double)partialTicks;
            double d1 = mc.field_71439_g.field_70137_T + (mc.field_71439_g.field_70163_u - mc.field_71439_g.field_70137_T) * (double)partialTicks;
            double d2 = mc.field_71439_g.field_70136_U + (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70136_U) * (double)partialTicks;
            drawFilledBoundingBox(block.func_180646_a(mc.field_71441_e, blockPos).func_72314_b(0.002D, 0.002D, 0.002D).func_72317_d(-d0, -d1, -d2), color);
         }
      }

   }

   public static void drawFairySoulOutline(Entity entity, float partialTicks, int color) {
      RenderManagerAccessor rm = (RenderManagerAccessor)mc.func_175598_ae();
      double renderPosX = rm.getRenderPosX();
      double renderPosY = rm.getRenderPosY();
      double renderPosZ = rm.getRenderPosZ();
      double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks - renderPosX;
      double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks - renderPosY;
      double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks - renderPosZ;
      if (color == -1) {
         color = ColorUtils.getChroma(3000.0F, 0);
      }

      AxisAlignedBB entityBox = entity.func_174813_aQ();
      AxisAlignedBB aabb = AxisAlignedBB.func_178781_a(entityBox.field_72340_a - entity.field_70165_t + x - 0.2D, entityBox.field_72338_b - entity.field_70163_u + y + 1.45D, entityBox.field_72339_c - entity.field_70161_v + z - 0.2D, entityBox.field_72336_d - entity.field_70165_t + x + 0.2D, entityBox.field_72337_e - entity.field_70163_u + y + 0.4D, entityBox.field_72334_f - entity.field_70161_v + z + 0.2D);
      drawFilledBoundingBox(aabb, color);
   }

   public static void draw3DString(Vec3 pos, String text, int color, float partialTicks) {
      EntityPlayer player = mc.field_71439_g;
      double x = pos.field_72450_a - player.field_70142_S + (pos.field_72450_a - player.field_70165_t - (pos.field_72450_a - player.field_70142_S)) * (double)partialTicks;
      double y = pos.field_72448_b - player.field_70137_T + (pos.field_72448_b - player.field_70163_u - (pos.field_72448_b - player.field_70137_T)) * (double)partialTicks;
      double z = pos.field_72449_c - player.field_70136_U + (pos.field_72449_c - player.field_70161_v - (pos.field_72449_c - player.field_70136_U)) * (double)partialTicks;
      RenderManager renderManager = mc.func_175598_ae();
      float f = 1.6F;
      float f1 = 0.016666668F * f;
      int width = mc.field_71466_p.func_78256_a(text) / 2;
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(x, y, z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-f1, -f1, -f1);
      GlStateManager.func_179147_l();
      GlStateManager.func_179140_f();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      mc.field_71466_p.func_78276_b(text, -width, 0, color);
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }

   public static void drawLine(Vec3 start, Vec3 end, float thickness, float partialTicks) {
      Entity render = Minecraft.func_71410_x().func_175606_aa();
      WorldRenderer worldRenderer = Tessellator.func_178181_a().func_178180_c();
      double realX = render.field_70142_S + (render.field_70165_t - render.field_70142_S) * (double)partialTicks;
      double realY = render.field_70137_T + (render.field_70163_u - render.field_70137_T) * (double)partialTicks;
      double realZ = render.field_70136_U + (render.field_70161_v - render.field_70136_U) * (double)partialTicks;
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(-realX, -realY, -realZ);
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glDisable(3553);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GL11.glLineWidth(thickness);
      GlStateManager.func_179097_i();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b(start.field_72450_a, start.field_72448_b, start.field_72449_c).func_181666_a(1.0F, 0.65F, 0.0F, 1.0F).func_181675_d();
      worldRenderer.func_181662_b(end.field_72450_a, end.field_72448_b, end.field_72449_c).func_181666_a(1.0F, 0.65F, 0.0F, 1.0F).func_181675_d();
      Tessellator.func_178181_a().func_78381_a();
      GlStateManager.func_179137_b(realX, realY, realZ);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }

   public static void drawLines(List<Vec3> poses, float thickness, float partialTicks) {
      Entity render = Minecraft.func_71410_x().func_175606_aa();
      WorldRenderer worldRenderer = Tessellator.func_178181_a().func_178180_c();
      double realX = render.field_70142_S + (render.field_70165_t - render.field_70142_S) * (double)partialTicks;
      double realY = render.field_70137_T + (render.field_70163_u - render.field_70137_T) * (double)partialTicks;
      double realZ = render.field_70136_U + (render.field_70161_v - render.field_70136_U) * (double)partialTicks;
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(-realX, -realY, -realZ);
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glDisable(3553);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GL11.glLineWidth(thickness);
      GlStateManager.func_179097_i();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      int num = 0;
      Iterator var12 = poses.iterator();

      while(var12.hasNext()) {
         Vec3 pos = (Vec3)var12.next();
         int i = ColorUtils.getChroma(2500.0F, num++ * 5);
         worldRenderer.func_181662_b(pos.field_72450_a + 0.5D, pos.field_72448_b + 0.5D, pos.field_72449_c + 0.5D).func_181666_a((float)(i >> 16 & 255) / 255.0F, (float)(i >> 8 & 255) / 255.0F, (float)(i & 255) / 255.0F, (float)(i >> 24 & 255) / 255.0F).func_181675_d();
      }

      Tessellator.func_178181_a().func_78381_a();
      GlStateManager.func_179137_b(realX, realY, realZ);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }
}

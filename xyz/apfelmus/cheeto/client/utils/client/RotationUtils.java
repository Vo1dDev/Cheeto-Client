package xyz.apfelmus.cheeto.client.utils.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector3f;
import xyz.apfelmus.cheeto.client.modules.combat.BloodCamp;
import xyz.apfelmus.cheeto.client.utils.math.RandomUtil;

public class RotationUtils {
   private static final Minecraft mc = Minecraft.func_71410_x();
   public static Rotation startRot;
   public static Rotation neededChange;
   public static Rotation endRot;
   public static long startTime;
   public static long endTime;
   public static boolean done = true;
   private static final float[][] BLOCK_SIDES = new float[][]{{0.5F, 0.01F, 0.5F}, {0.5F, 0.99F, 0.5F}, {0.01F, 0.5F, 0.5F}, {0.99F, 0.5F, 0.5F}, {0.5F, 0.5F, 0.01F}, {0.5F, 0.5F, 0.99F}};

   public static Rotation getRotation(Vec3 vec) {
      Vec3 eyes = mc.field_71439_g.func_174824_e(1.0F);
      return getRotation(eyes, vec);
   }

   public static Rotation getRotation(Vec3 from, Vec3 to) {
      double diffX = to.field_72450_a - from.field_72450_a;
      double diffY = to.field_72448_b - from.field_72448_b;
      double diffZ = to.field_72449_c - from.field_72449_c;
      return new Rotation(MathHelper.func_76142_g((float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D)), (float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))));
   }

   public static Rotation getRotation(BlockPos bp) {
      Vec3 vec = new Vec3((double)bp.func_177958_n() + 0.5D, (double)bp.func_177956_o() + 0.5D, (double)bp.func_177952_p() + 0.5D);
      return getRotation(vec);
   }

   public static void setup(Rotation rot, Long aimTime) {
      done = false;
      startRot = new Rotation(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A);
      neededChange = getNeededChange(startRot, rot);
      endRot = new Rotation(startRot.getYaw() + neededChange.getYaw(), startRot.getPitch() + neededChange.getPitch());
      startTime = System.currentTimeMillis();
      endTime = System.currentTimeMillis() + aimTime;
   }

   public static void reset() {
      done = true;
      startRot = null;
      neededChange = null;
      endRot = null;
      startTime = 0L;
      endTime = 0L;
   }

   public static void update() {
      if (System.currentTimeMillis() <= endTime) {
         mc.field_71439_g.field_70177_z = interpolate(startRot.getYaw(), endRot.getYaw());
         mc.field_71439_g.field_70125_A = interpolate(startRot.getPitch(), endRot.getPitch());
      } else if (!done) {
         mc.field_71439_g.field_70177_z = endRot.getYaw();
         mc.field_71439_g.field_70125_A = endRot.getPitch();
         reset();
      }

   }

   public static void snapAngles(Rotation rot) {
      mc.field_71439_g.field_70177_z = rot.getYaw();
      mc.field_71439_g.field_70125_A = rot.getPitch();
   }

   private static float interpolate(float start, float end) {
      float spentMillis = (float)(System.currentTimeMillis() - startTime);
      float relativeProgress = spentMillis / (float)(endTime - startTime);
      return (end - start) * easeOutCubic((double)relativeProgress) + start;
   }

   public static float easeOutCubic(double number) {
      return (float)(1.0D - Math.pow(1.0D - number, 3.0D));
   }

   public static Rotation getNeededChange(Rotation startRot, Rotation endRot) {
      float yawChng = MathHelper.func_76142_g(endRot.getYaw()) - MathHelper.func_76142_g(startRot.getYaw());
      if (yawChng <= -180.0F) {
         yawChng += 360.0F;
      } else if (yawChng > 180.0F) {
         yawChng += -360.0F;
      }

      if (BloodCamp.godGamerMode.isEnabled()) {
         if (yawChng < 0.0F) {
            yawChng += 360.0F;
         } else {
            yawChng -= 360.0F;
         }
      }

      return new Rotation(yawChng, endRot.getPitch() - startRot.getPitch());
   }

   public static double fovFromEntity(Entity en) {
      return ((double)(mc.field_71439_g.field_70177_z - fovToEntity(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
   }

   public static float fovToEntity(Entity ent) {
      double x = ent.field_70165_t - mc.field_71439_g.field_70165_t;
      double z = ent.field_70161_v - mc.field_71439_g.field_70161_v;
      double yaw = Math.atan2(x, z) * 57.2957795D;
      return (float)(yaw * -1.0D);
   }

   public static Rotation getNeededChange(Rotation endRot) {
      Rotation startRot = new Rotation(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A);
      return getNeededChange(startRot, endRot);
   }

   public static List<Vec3> getBlockSides(BlockPos bp) {
      List<Vec3> ret = new ArrayList();
      float[][] var2 = BLOCK_SIDES;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         float[] side = var2[var4];
         ret.add((new Vec3(bp)).func_72441_c((double)side[0], (double)side[1], (double)side[2]));
      }

      return ret;
   }

   public static boolean lookingAt(BlockPos blockPos, float range) {
      float stepSize = 0.15F;
      Vec3 position = new Vec3(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
      Vec3 look = mc.field_71439_g.func_70676_i(0.0F);
      Vector3f step = new Vector3f((float)look.field_72450_a, (float)look.field_72448_b, (float)look.field_72449_c);
      step.scale(stepSize / step.length());

      for(int i = 0; (double)i < Math.floor((double)(range / stepSize)) - 2.0D; ++i) {
         BlockPos blockAtPos = new BlockPos(position.field_72450_a, position.field_72448_b, position.field_72449_c);
         if (blockAtPos.equals(blockPos)) {
            return true;
         }

         position = position.func_178787_e(new Vec3((double)step.x, (double)step.y, (double)step.z));
      }

      return false;
   }

   public static Vec3 getVectorForRotation(float pitch, float yaw) {
      float f2 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
      return new Vec3((double)(MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F) * f2), (double)MathHelper.func_76126_a(-pitch * 0.017453292F), (double)(MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F) * f2));
   }

   public static Vec3 getLook(Vec3 vec) {
      double diffX = vec.field_72450_a - mc.field_71439_g.field_70165_t;
      double diffY = vec.field_72448_b - (mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e());
      double diffZ = vec.field_72449_c - mc.field_71439_g.field_70161_v;
      double dist = (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
      return getVectorForRotation((float)(-(MathHelper.func_181159_b(diffY, dist) * 180.0D / 3.141592653589793D)), (float)(MathHelper.func_181159_b(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D));
   }

   public static EnumFacing calculateEnumfacing(Vec3 pos) {
      int x = MathHelper.func_76128_c(pos.field_72450_a);
      int y = MathHelper.func_76128_c(pos.field_72448_b);
      int z = MathHelper.func_76128_c(pos.field_72449_c);
      MovingObjectPosition position = calculateIntercept(new AxisAlignedBB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)), pos, 50.0F);
      return position != null ? position.field_178784_b : null;
   }

   public static MovingObjectPosition calculateIntercept(AxisAlignedBB aabb, Vec3 block, float range) {
      Vec3 vec3 = mc.field_71439_g.func_174824_e(1.0F);
      Vec3 vec4 = getLook(block);
      return aabb.func_72327_a(vec3, vec3.func_72441_c(vec4.field_72450_a * (double)range, vec4.field_72448_b * (double)range, vec4.field_72449_c * (double)range));
   }

   public static List<Vec3> getPointsOnBlock(BlockPos bp) {
      List<Vec3> ret = new ArrayList();
      float[][] var2 = BLOCK_SIDES;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         float[] side = var2[var4];

         for(int i = 0; i < 20; ++i) {
            float x = side[0];
            float y = side[1];
            float z = side[2];
            if ((double)x == 0.5D) {
               x = RandomUtil.randBetween(0.1F, 0.9F);
            }

            if ((double)y == 0.5D) {
               y = RandomUtil.randBetween(0.1F, 0.9F);
            }

            if ((double)z == 0.5D) {
               z = RandomUtil.randBetween(0.1F, 0.9F);
            }

            ret.add((new Vec3(bp)).func_72441_c((double)x, (double)y, (double)z));
         }
      }

      return ret;
   }
}

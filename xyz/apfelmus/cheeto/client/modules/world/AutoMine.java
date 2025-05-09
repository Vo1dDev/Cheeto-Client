package xyz.apfelmus.cheeto.client.modules.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.math.RandomUtil;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;

@Module(
   name = "AutoMine",
   category = Category.WORLD
)
public class AutoMine implements Runnable {
   @Setting(
      name = "Sneak",
      description = "Makes the player sneak while mining"
   )
   private BooleanSetting sneak = new BooleanSetting(false);
   @Setting(
      name = "CoalOre"
   )
   private BooleanSetting coalOre = new BooleanSetting(true);
   @Setting(
      name = "LapisOre"
   )
   private BooleanSetting lapisOre = new BooleanSetting(true);
   @Setting(
      name = "IronOre"
   )
   private BooleanSetting ironOre = new BooleanSetting(true);
   @Setting(
      name = "GoldOre"
   )
   private BooleanSetting goldOre = new BooleanSetting(true);
   @Setting(
      name = "RedstoneOre"
   )
   private BooleanSetting redstoneOre = new BooleanSetting(true);
   @Setting(
      name = "DiamondOre"
   )
   private BooleanSetting diamondOre = new BooleanSetting(true);
   @Setting(
      name = "EmeraldOre"
   )
   private BooleanSetting emeraldOre = new BooleanSetting(true);
   @Setting(
      name = "GoldBlocks"
   )
   private BooleanSetting goldBlocks = new BooleanSetting(true);
   @Setting(
      name = "LookTime"
   )
   private IntegerSetting lookTime = new IntegerSetting(500, 0, 2500);
   @Setting(
      name = "MaxMineTime",
      description = "Set to slightly more than it takes to mine"
   )
   private IntegerSetting maxMineTime = new IntegerSetting(5000, 0, 10000);
   private static Minecraft mc = Minecraft.func_71410_x();
   private Thread thread;
   private Map<BlockPos, List<Vec3>> blocksNear = new HashMap();
   private List<BlockPos> blacklist = new ArrayList();
   private int delayMs = 500;
   private BlockPos curBlockPos;
   private Block curBlock;
   private TimeHelper mineTimer;
   private Vec3 startRot;
   private Vec3 endRot;
   private AutoMine.MineState mineState;

   public AutoMine() {
      this.mineState = AutoMine.MineState.CHOOSE;
   }

   @Enable
   public void onEnable() {
      this.mineState = AutoMine.MineState.CHOOSE;
      this.blocksNear.clear();
      this.blacklist.clear();
      this.mineTimer = new TimeHelper();
      this.curBlockPos = null;
      this.curBlock = null;
      this.startRot = null;
      this.endRot = null;
      KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), this.sneak.isEnabled());
   }

   @Disable
   public void onDisable() {
      KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
      mc.field_71442_b.func_78767_c();
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (mc.field_71439_g != null && mc.field_71441_e != null) {
         if (this.thread == null || !this.thread.isAlive()) {
            (this.thread = new Thread(this)).setDaemon(false);
            this.thread.setPriority(1);
            this.thread.start();
         }

         Iterator it = this.blocksNear.entrySet().iterator();

         while(true) {
            Entry entry;
            Vec3 randPoint;
            MovingObjectPosition mop;
            label72:
            do {
               while(it.hasNext()) {
                  entry = (Entry)it.next();
                  randPoint = (Vec3)((List)entry.getValue()).get(RandomUtil.randBetween(0, ((List)entry.getValue()).size() - 1));
                  mop = mc.field_71441_e.func_72933_a(mc.field_71439_g.func_174824_e(1.0F), randPoint);
                  if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK) {
                     continue label72;
                  }

                  it.remove();
               }

               if (!this.blocksNear.containsKey(this.curBlockPos)) {
                  this.mineState = AutoMine.MineState.CHOOSE;
               }

               IBlockState ibs;
               switch(this.mineState) {
               case CHOOSE:
                  AutoMithril.BlockPosWithVec closest = this.getClosestBlock((BlockPos)null);
                  if (closest != null) {
                     this.curBlockPos = closest.getBlockPos();
                     ibs = mc.field_71441_e.func_180495_p(this.curBlockPos);
                     if (ibs != null) {
                        this.curBlock = ibs.func_177230_c();
                     }

                     this.startRot = closest.getVec3();
                     this.endRot = null;
                     RotationUtils.setup(RotationUtils.getRotation(closest.getVec3()), (long)this.lookTime.getCurrent());
                     this.mineState = AutoMine.MineState.LOOK;
                  } else {
                     KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
                  }
                  break;
               case MINE:
                  if (this.mineTimer.hasReached((long)this.maxMineTime.getCurrent())) {
                     this.blocksNear.remove(this.curBlockPos);
                     this.mineState = AutoMine.MineState.CHOOSE;
                  } else {
                     ibs = mc.field_71441_e.func_180495_p(this.curBlockPos);
                     if (ibs != null) {
                        if ((this.curBlock == null || ibs.func_177230_c() == this.curBlock) && ibs.func_177230_c() != Blocks.field_150357_h && ibs.func_177230_c() != Blocks.field_150350_a) {
                           if (!mc.field_71474_y.field_74312_F.func_151470_d() && mc.field_71462_r == null) {
                              mc.field_71415_G = true;
                              KeyBinding.func_74507_a(mc.field_71474_y.field_74312_F.func_151463_i());
                              KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), true);
                           }
                        } else {
                           this.blocksNear.remove(this.curBlockPos);
                           this.mineState = AutoMine.MineState.CHOOSE;
                           this.curBlock = null;
                           this.startRot = null;
                           this.endRot = null;
                        }
                     }
                  }
               }

               return;
            } while(mop.func_178782_a().equals(entry.getKey()) && randPoint.func_72438_d(mc.field_71439_g.func_174824_e(1.0F)) < (double)mc.field_71442_b.func_78757_d());

            it.remove();
         }
      }
   }

   @Event
   public void onRender(Render3DEvent event) {
      Iterator var2 = (new ArrayList(((Map)(new HashMap(this.blocksNear)).clone()).keySet())).iterator();

      while(var2.hasNext()) {
         BlockPos bp = (BlockPos)var2.next();
         if (!bp.equals(this.curBlockPos)) {
            Render3DUtils.renderEspBox(bp, event.partialTicks, -16711681);
         }
      }

      if (this.blocksNear.containsKey(this.curBlockPos)) {
         Render3DUtils.renderEspBox(this.curBlockPos, event.partialTicks, -65536);
      }

      if (this.startRot != null && this.endRot != null) {
         Render3DUtils.drawLine(this.startRot, this.endRot, 1.0F, event.partialTicks);
      }

      if (this.startRot != null) {
         Render3DUtils.renderSmallBox(this.startRot, -16711936);
      }

      if (this.endRot != null) {
         Render3DUtils.renderSmallBox(this.endRot, -65536);
      }

      switch(this.mineState) {
      case MINE:
         if (System.currentTimeMillis() <= RotationUtils.endTime) {
            RotationUtils.update();
         } else {
            this.startRot = null;
            this.endRot = null;
            if (!RotationUtils.done) {
               RotationUtils.update();
            }
         }
         break;
      case LOOK:
         if (System.currentTimeMillis() <= RotationUtils.endTime) {
            RotationUtils.update();
         } else {
            if (!RotationUtils.done) {
               RotationUtils.update();
            }

            this.mineTimer.reset();
            AutoMithril.BlockPosWithVec next = this.getClosestBlock(this.curBlockPos);
            if (next != null && this.blocksNear.containsKey(this.curBlockPos)) {
               ((List)this.blocksNear.get(this.curBlockPos)).stream().min(Comparator.comparing((v) -> {
                  return RotationUtils.getNeededChange(RotationUtils.getRotation(next.getVec3()), RotationUtils.getRotation(v)).getValue();
               })).ifPresent((nextPointOnSameBlock) -> {
                  this.curBlock = mc.field_71441_e.func_180495_p(this.curBlockPos).func_177230_c();
                  this.endRot = nextPointOnSameBlock;
                  RotationUtils.setup(RotationUtils.getRotation(nextPointOnSameBlock), (long)this.maxMineTime.getCurrent());
               });
            }

            this.mineState = AutoMine.MineState.MINE;
         }
      }

   }

   private void addBlockIfHittable(BlockPos xyz) {
      List<Vec3> pointsOnBlock = RotationUtils.getPointsOnBlock(xyz);
      Iterator var3 = pointsOnBlock.iterator();

      while(var3.hasNext()) {
         Vec3 point = (Vec3)var3.next();
         MovingObjectPosition mop = mc.field_71441_e.func_72933_a(mc.field_71439_g.func_174824_e(1.0F), point);
         if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK && mop.func_178782_a().equals(xyz) && point.func_72438_d(mc.field_71439_g.func_174824_e(1.0F)) < (double)mc.field_71442_b.func_78757_d()) {
            if (!this.blocksNear.containsKey(xyz)) {
               this.blocksNear.put(xyz, new ArrayList(Collections.singletonList(point)));
            } else {
               ((List)this.blocksNear.get(xyz)).add(point);
            }
         }
      }

   }

   private AutoMithril.BlockPosWithVec getClosestBlock(BlockPos excluding) {
      BlockPos closest = null;
      Rotation closestRot = null;
      Vec3 closestPoint = null;
      List<BlockPos> asd = new ArrayList(this.blocksNear.keySet());
      asd.remove(excluding);
      Iterator var6 = asd.iterator();

      label31:
      while(var6.hasNext()) {
         BlockPos bp = (BlockPos)var6.next();
         Iterator var8 = ((List)this.blocksNear.get(bp)).iterator();

         while(true) {
            Vec3 point;
            Rotation needed;
            do {
               if (!var8.hasNext()) {
                  continue label31;
               }

               point = (Vec3)var8.next();
               Rotation endRot = RotationUtils.getRotation(point);
               needed = RotationUtils.getNeededChange(endRot);
            } while(closestRot != null && needed.getValue() >= closestRot.getValue());

            closest = bp;
            closestRot = needed;
            closestPoint = point;
         }
      }

      if (closest != null) {
         return new AutoMithril.BlockPosWithVec(closest, closestPoint);
      } else {
         return null;
      }
   }

   public void run() {
      label133:
      while(true) {
         if (!this.thread.isInterrupted() && mc.field_71439_g != null && mc.field_71441_e != null) {
            if (!CF4M.INSTANCE.moduleManager.isEnabled((Object)this)) {
               this.thread.interrupt();
               continue;
            }

            int radius = 6;
            int px = MathHelper.func_76128_c(mc.field_71439_g.field_70165_t);
            int py = MathHelper.func_76128_c(mc.field_71439_g.field_70163_u + 1.0D);
            int pz = MathHelper.func_76128_c(mc.field_71439_g.field_70161_v);
            Vec3 eyes = mc.field_71439_g.func_174824_e(1.0F);

            for(int x = px - radius; x < px + radius + 1; ++x) {
               for(int y = py - radius; y < py + radius + 1; ++y) {
                  for(int z = pz - radius; z < pz + radius + 1; ++z) {
                     BlockPos xyz = new BlockPos(x, y, z);
                     IBlockState bs = mc.field_71441_e.func_180495_p(xyz);
                     if (!this.blocksNear.containsKey(xyz) && !this.blacklist.contains(xyz)) {
                        Block block = bs.func_177230_c();
                        if (Math.sqrt(xyz.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) <= 6.0D) {
                           if (block == Blocks.field_150365_q && this.coalOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if (block == Blocks.field_150369_x && this.lapisOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if (block == Blocks.field_150366_p && this.ironOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if (block == Blocks.field_150352_o && this.goldOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if ((block == Blocks.field_150450_ax || block == Blocks.field_150439_ay) && this.redstoneOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if (block == Blocks.field_150482_ag && this.diamondOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if (block == Blocks.field_150412_bA && this.emeraldOre.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           } else if (block == Blocks.field_150340_R && this.goldBlocks.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           }
                        }
                     }
                  }
               }
            }

            Iterator var13 = (new HashMap(this.blocksNear)).keySet().iterator();

            while(true) {
               BlockPos bp;
               Block block;
               do {
                  if (!var13.hasNext()) {
                     try {
                        Thread.sleep((long)this.delayMs);
                     } catch (InterruptedException var12) {
                        var12.printStackTrace();
                     }
                     continue label133;
                  }

                  bp = (BlockPos)var13.next();
                  IBlockState state = mc.field_71441_e.func_180495_p(bp);
                  block = null;
                  if (state != null) {
                     block = state.func_177230_c();
                  }
               } while(!(Math.sqrt(bp.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) > 5.0D) && block != Blocks.field_150357_h && block != Blocks.field_150350_a);

               this.blocksNear.remove(bp);
            }
         }

         this.thread = null;
         return;
      }
   }

   static enum MineState {
      CHOOSE,
      LOOK,
      MINE;
   }
}

package xyz.apfelmus.cheeto.client.utils.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import xyz.apfelmus.cheeto.client.utils.math.VecUtils;

public class AStarCustomPathfinder {
   private Vec3 startVec3;
   private Vec3 endVec3;
   private ArrayList<Vec3> path = new ArrayList();
   private ArrayList<AStarCustomPathfinder.Hub> hubs = new ArrayList();
   private ArrayList<AStarCustomPathfinder.Hub> hubsToWork = new ArrayList();
   private double minDistanceSquared;
   private boolean nearest = true;
   private static Vec3[] flatCardinalDirections = new Vec3[]{new Vec3(1.0D, 0.0D, 0.0D), new Vec3(-1.0D, 0.0D, 0.0D), new Vec3(0.0D, 0.0D, 1.0D), new Vec3(0.0D, 0.0D, -1.0D)};
   private static Minecraft mc = Minecraft.func_71410_x();

   public AStarCustomPathfinder(Vec3 startVec3, Vec3 endVec3, double minDistanceSquared) {
      this.startVec3 = VecUtils.floorVec(startVec3);
      this.endVec3 = VecUtils.floorVec(endVec3);
      this.minDistanceSquared = minDistanceSquared;
   }

   public ArrayList<Vec3> getPath() {
      return this.path;
   }

   public void compute() {
      this.compute(1000, 4);
   }

   public void compute(int loops, int depth) {
      this.path.clear();
      this.hubsToWork.clear();
      ArrayList<Vec3> initPath = new ArrayList();
      initPath.add(this.startVec3);
      this.hubsToWork.add(new AStarCustomPathfinder.Hub(this.startVec3, (AStarCustomPathfinder.Hub)null, initPath, this.startVec3.func_72436_e(this.endVec3), 0.0D, 0.0D));

      label96:
      for(int i = 0; i < loops; ++i) {
         Collections.sort(this.hubsToWork, new AStarCustomPathfinder.CompareHub());
         int j = 0;
         if (this.hubsToWork.size() == 0) {
            break;
         }

         Iterator var6 = (new ArrayList(this.hubsToWork)).iterator();

         while(var6.hasNext()) {
            AStarCustomPathfinder.Hub hub = (AStarCustomPathfinder.Hub)var6.next();
            ++j;
            if (j > depth) {
               break;
            }

            this.hubsToWork.remove(hub);
            this.hubs.add(hub);
            Vec3[] var8 = flatCardinalDirections;
            int var9 = var8.length;

            int var10;
            Vec3 direction;
            Vec3 loc;
            for(var10 = 0; var10 < var9; ++var10) {
               direction = var8[var10];
               loc = VecUtils.ceilVec(hub.getLoc().func_178787_e(direction));
               if (checkPositionValidity(loc, true)) {
                  if (isSlab(loc.func_72441_c(0.0D, -1.0D, 0.0D), EnumBlockHalf.BOTTOM)) {
                     if (this.addHub(hub, loc.func_72441_c(0.0D, -0.5D, 0.0D), 0.0D)) {
                        break label96;
                     }
                  } else if (this.addHub(hub, loc, 0.0D)) {
                     break label96;
                  }
               }
            }

            var8 = flatCardinalDirections;
            var9 = var8.length;

            for(var10 = 0; var10 < var9; ++var10) {
               direction = var8[var10];
               loc = VecUtils.ceilVec(hub.getLoc().func_178787_e(direction).func_72441_c(0.0D, 1.0D, 0.0D));
               if (checkPositionValidity(loc, true) && checkPositionValidity(hub.getLoc().func_72441_c(0.0D, 1.0D, 0.0D), false)) {
                  if (isSlab(loc.func_72441_c(0.0D, -1.0D, 0.0D), EnumBlockHalf.BOTTOM)) {
                     if (this.addHub(hub, loc.func_72441_c(0.0D, -0.5D, 0.0D), 0.0D)) {
                        break label96;
                     }
                  } else if (!isSlab(hub.getLoc(), EnumBlockHalf.BOTTOM) && this.addHub(hub, loc, 0.0D)) {
                     break label96;
                  }
               }
            }

            var8 = flatCardinalDirections;
            var9 = var8.length;

            for(var10 = 0; var10 < var9; ++var10) {
               direction = var8[var10];
               loc = VecUtils.ceilVec(hub.getLoc().func_178787_e(direction).func_72441_c(0.0D, -1.0D, 0.0D));
               if (checkPositionValidity(loc, true) && checkPositionValidity(loc.func_72441_c(0.0D, 1.0D, 0.0D), false)) {
                  if (isSlab(loc, EnumBlockHalf.BOTTOM)) {
                     if (this.addHub(hub, loc.func_72441_c(0.0D, 0.5D, 0.0D), 0.0D)) {
                        break label96;
                     }
                  } else if (this.addHub(hub, loc, 0.0D)) {
                     break label96;
                  }
               }
            }
         }
      }

      if (this.nearest) {
         this.hubs.sort(new AStarCustomPathfinder.CompareHub());
         this.path = ((AStarCustomPathfinder.Hub)this.hubs.get(0)).getPath();
      }

   }

   public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
      return checkPositionValidity((int)loc.field_72450_a, (int)loc.field_72448_b, (int)loc.field_72449_c, checkGround);
   }

   public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
      BlockPos block1 = new BlockPos(x, y, z);
      BlockPos block2 = new BlockPos(x, y + 1, z);
      BlockPos block3 = new BlockPos(x, y - 1, z);
      return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
   }

   public static boolean isSlab(Vec3 loc, EnumBlockHalf slabType) {
      IBlockState bs = mc.field_71441_e.func_180495_p(new BlockPos(loc));
      return bs.func_177230_c() instanceof BlockSlab && bs.func_177229_b(BlockSlab.field_176554_a) == slabType;
   }

   private static boolean isBlockSolid(BlockPos block) {
      IBlockState bs = mc.field_71441_e.func_180495_p(block);
      if (bs == null) {
         return false;
      } else {
         Block b = bs.func_177230_c();
         return mc.field_71441_e.func_175665_u(block) || b instanceof BlockSlab || b instanceof BlockStairs || b instanceof BlockCactus || b instanceof BlockChest || b instanceof BlockEnderChest || b instanceof BlockSkull || b instanceof BlockPane || b instanceof BlockFence || b instanceof BlockWall || b instanceof BlockGlass || b instanceof BlockPistonBase || b instanceof BlockPistonExtension || b instanceof BlockPistonMoving || b instanceof BlockStainedGlass || b instanceof BlockTrapDoor;
      }
   }

   private static boolean isSafeToWalkOn(BlockPos block) {
      IBlockState bs = mc.field_71441_e.func_180495_p(block);
      if (bs == null) {
         return false;
      } else {
         Block b = bs.func_177230_c();
         return !(b instanceof BlockFence) && !(b instanceof BlockWall);
      }
   }

   public AStarCustomPathfinder.Hub isHubExisting(Vec3 loc) {
      Iterator var2 = this.hubs.iterator();

      AStarCustomPathfinder.Hub hub;
      do {
         if (!var2.hasNext()) {
            var2 = this.hubsToWork.iterator();

            do {
               if (!var2.hasNext()) {
                  return null;
               }

               hub = (AStarCustomPathfinder.Hub)var2.next();
            } while(hub.getLoc().field_72450_a != loc.field_72450_a || hub.getLoc().field_72448_b != loc.field_72448_b || hub.getLoc().field_72449_c != loc.field_72449_c);

            return hub;
         }

         hub = (AStarCustomPathfinder.Hub)var2.next();
      } while(hub.getLoc().field_72450_a != loc.field_72450_a || hub.getLoc().field_72448_b != loc.field_72448_b || hub.getLoc().field_72449_c != loc.field_72449_c);

      return hub;
   }

   public boolean addHub(AStarCustomPathfinder.Hub parent, Vec3 loc, double cost) {
      AStarCustomPathfinder.Hub existingHub = this.isHubExisting(loc);
      double totalCost = cost;
      if (parent != null) {
         totalCost = cost + parent.getTotalCost();
      }

      ArrayList path;
      if (existingHub == null) {
         if (loc.field_72450_a == this.endVec3.field_72450_a && loc.field_72448_b == this.endVec3.field_72448_b && loc.field_72449_c == this.endVec3.field_72449_c || this.minDistanceSquared != 0.0D && loc.func_72436_e(this.endVec3) <= this.minDistanceSquared) {
            this.path.clear();
            this.path = parent.getPath();
            this.path.add(loc);
            return true;
         }

         path = new ArrayList(parent.getPath());
         path.add(loc);
         this.hubsToWork.add(new AStarCustomPathfinder.Hub(loc, parent, path, loc.func_72436_e(this.endVec3), cost, totalCost));
      } else if (existingHub.getCost() > cost) {
         path = new ArrayList(parent.getPath());
         path.add(loc);
         existingHub.setLoc(loc);
         existingHub.setParent(parent);
         existingHub.setPath(path);
         existingHub.setSquareDistanceToFromTarget(loc.func_72436_e(this.endVec3));
         existingHub.setCost(cost);
         existingHub.setTotalCost(totalCost);
      }

      return false;
   }

   public class CompareHub implements Comparator<AStarCustomPathfinder.Hub> {
      public int compare(AStarCustomPathfinder.Hub o1, AStarCustomPathfinder.Hub o2) {
         return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
      }
   }

   private class Hub {
      private Vec3 loc = null;
      private AStarCustomPathfinder.Hub parent = null;
      private ArrayList<Vec3> path;
      private double squareDistanceToFromTarget;
      private double cost;
      private double totalCost;

      public Hub(Vec3 loc, AStarCustomPathfinder.Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
         this.loc = loc;
         this.parent = parent;
         this.path = path;
         this.squareDistanceToFromTarget = squareDistanceToFromTarget;
         this.cost = cost;
         this.totalCost = totalCost;
      }

      public Vec3 getLoc() {
         return this.loc;
      }

      public AStarCustomPathfinder.Hub getParent() {
         return this.parent;
      }

      public ArrayList<Vec3> getPath() {
         return this.path;
      }

      public double getSquareDistanceToFromTarget() {
         return this.squareDistanceToFromTarget;
      }

      public double getCost() {
         return this.cost;
      }

      public void setLoc(Vec3 loc) {
         this.loc = loc;
      }

      public void setParent(AStarCustomPathfinder.Hub parent) {
         this.parent = parent;
      }

      public void setPath(ArrayList<Vec3> path) {
         this.path = path;
      }

      public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
         this.squareDistanceToFromTarget = squareDistanceToFromTarget;
      }

      public void setCost(double cost) {
         this.cost = cost;
      }

      public double getTotalCost() {
         return this.totalCost;
      }

      public void setTotalCost(double totalCost) {
         this.totalCost = totalCost;
      }
   }
}

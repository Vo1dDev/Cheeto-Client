package xyz.apfelmus.cheeto.client.modules.misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.EntityInteractEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;
import xyz.apfelmus.cheeto.client.utils.client.KeybindUtils;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "ArrowAlign",
   category = Category.MISC
)
public class ArrowAlign {
   @Setting(
      name = "ShowClicks"
   )
   private static BooleanSetting showClicks = new BooleanSetting(true);
   @Setting(
      name = "ClickNeeded"
   )
   private static BooleanSetting clickNeeded = new BooleanSetting(true);
   private static BlockPos topLeft = (new BlockPos(197, 124, 278)).func_177984_a();
   private static BlockPos botRight = (new BlockPos(197, 120, 274)).func_177984_a();
   private static List<BlockPos> box;
   private static LinkedHashSet<ArrowAlign.MazeSpace> grid;
   private static HashMap<Point, Integer> directionSet;
   private static int ticks;
   private static EnumFacing[] directions;
   private static boolean clicking;
   private static Minecraft mc;

   @Enable
   public void onEnable() {
      grid.clear();
      directionSet.clear();
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (SkyblockUtils.isInDungeon()) {
         if (ticks % 20 == 0) {
            if (mc.field_71439_g.func_174831_c(topLeft) <= 625.0D) {
               List frames;
               if (grid.size() < 25) {
                  frames = mc.field_71441_e.func_175644_a(EntityItemFrame.class, (it) -> {
                     return it != null && box.contains(it.func_180425_c()) && it.func_82335_i() != null && (it.func_82335_i().func_77973_b().equals(Items.field_151032_g) || it.func_82335_i().func_77973_b().equals(Item.func_150898_a(Blocks.field_150325_L)));
                  });
                  if (!frames.isEmpty()) {
                     for(int i = 0; i < box.size(); ++i) {
                        int row = i % 5;
                        int column = (int)Math.floor((double)((float)i / 5.0F));
                        Point coords = new Point(row, column);
                        EntityItemFrame frame = (EntityItemFrame)frames.stream().filter((v) -> {
                           return v.func_180425_c().equals(box.get(i));
                        }).findFirst().orElse((Object)null);
                        if (frame != null) {
                           ItemStack item = frame.func_82335_i();
                           ArrowAlign.SpaceType type = ArrowAlign.SpaceType.EMPTY;
                           if (item.func_77973_b() == Items.field_151032_g) {
                              type = ArrowAlign.SpaceType.PATH;
                           } else if (item.func_77973_b() == Item.func_150898_a(Blocks.field_150325_L)) {
                              if (item.func_77952_i() == 5) {
                                 type = ArrowAlign.SpaceType.START;
                              } else if (item.func_77952_i() == 14) {
                                 type = ArrowAlign.SpaceType.END;
                              } else {
                                 type = ArrowAlign.SpaceType.PATH;
                              }
                           }

                           grid.add(new ArrowAlign.MazeSpace(frame.func_174857_n(), type, coords));
                        } else {
                           grid.add(new ArrowAlign.MazeSpace((BlockPos)null, ArrowAlign.SpaceType.EMPTY, coords));
                        }
                     }
                  }
               } else if (directionSet.isEmpty()) {
                  frames = (List)grid.stream().filter((it) -> {
                     return it.type == ArrowAlign.SpaceType.START;
                  }).collect(Collectors.toList());
                  List<ArrowAlign.MazeSpace> endPositions = (List)grid.stream().filter((it) -> {
                     return it.type == ArrowAlign.SpaceType.END;
                  }).collect(Collectors.toList());
                  int[][] layout = getLayout();
                  Iterator var5 = frames.iterator();

                  label76:
                  while(var5.hasNext()) {
                     ArrowAlign.MazeSpace start = (ArrowAlign.MazeSpace)var5.next();
                     Iterator var7 = endPositions.iterator();

                     while(true) {
                        List pointMap;
                        do {
                           if (!var7.hasNext()) {
                              continue label76;
                           }

                           ArrowAlign.MazeSpace endPosition = (ArrowAlign.MazeSpace)var7.next();
                           pointMap = solve(layout, start.coords, endPosition.coords);
                        } while(pointMap.size() == 0);

                        List<ArrowAlign.GridMove> moveSet = this.convertPointMapToMoves(pointMap);
                        Iterator var11 = moveSet.iterator();

                        while(var11.hasNext()) {
                           ArrowAlign.GridMove move = (ArrowAlign.GridMove)var11.next();
                           directionSet.put(move.point, move.directionNum);
                        }
                     }
                  }
               }
            }

            ticks = 0;
         }

         ++ticks;
      }
   }

   @Event
   public void onRightClick(EntityInteractEvent event) {
      if (!clicking && SkyblockUtils.isInDungeon()) {
         if (event.event.target instanceof EntityItemFrame) {
            EntityItemFrame frame = (EntityItemFrame)event.event.target;
            Iterator var3 = grid.iterator();

            while(var3.hasNext()) {
               ArrowAlign.MazeSpace space = (ArrowAlign.MazeSpace)var3.next();
               if (frame.func_174857_n().equals(space.framePos) && space.type == ArrowAlign.SpaceType.PATH && space.framePos != null) {
                  int neededClicks = (Integer)directionSet.getOrDefault(space.coords, 0) - frame.func_82333_j();
                  if (neededClicks == 0) {
                     event.event.setCanceled(true);
                  }

                  if (neededClicks < 0) {
                     neededClicks += 8;
                  }

                  if (neededClicks > 1 && clickNeeded.isEnabled()) {
                     clicking = true;

                     for(int i = 0; i < neededClicks - 1; ++i) {
                        KeybindUtils.rightClick();
                     }

                     clicking = false;
                  }

                  return;
               }
            }
         }

      }
   }

   @Event
   public void onRenderWorld(Render3DEvent event) {
      if (showClicks.isEnabled()) {
         Iterator var2 = grid.iterator();

         while(var2.hasNext()) {
            ArrowAlign.MazeSpace space = (ArrowAlign.MazeSpace)var2.next();
            if (space.type == ArrowAlign.SpaceType.PATH && space.framePos != null) {
               EntityItemFrame frame = (EntityItemFrame)mc.field_71441_e.field_72996_f.stream().filter((it) -> {
                  return it instanceof EntityItemFrame && ((EntityItemFrame)it).func_174857_n().equals(space.framePos);
               }).findFirst().orElse((Object)null);
               if (frame != null) {
                  int neededClicks = (Integer)directionSet.getOrDefault(space.coords, 0) - frame.func_82333_j();
                  if (neededClicks != 0) {
                     if (neededClicks < 0) {
                        neededClicks += 8;
                     }

                     Render3DUtils.draw3DString(this.getVec3RelativeToGrid(space.coords.x, space.coords.y).func_72441_c(0.1D, 0.6D, 0.5D), "" + neededClicks, ColorUtils.getChroma(3000.0F, 0), event.partialTicks);
                  }
               }
            }
         }

      }
   }

   @Event
   public void onWorldLoad(WorldUnloadEvent event) {
      grid.clear();
      directionSet.clear();
   }

   private Vec3 getVec3RelativeToGrid(int row, int column) {
      return new Vec3(topLeft.func_177977_b().func_177964_d(row).func_177979_c(column));
   }

   private List<ArrowAlign.GridMove> convertPointMapToMoves(List<Point> solution) {
      Collections.reverse(solution);
      List<ArrowAlign.GridMove> moves = new ArrayList();

      for(int i = 0; i < solution.size() - 1; ++i) {
         Point current = (Point)solution.get(i);
         Point next = (Point)solution.get(i + 1);
         int diffX = current.x - next.x;
         int diffY = current.y - next.y;
         EnumFacing[] var8 = EnumFacing.field_176754_o;
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            EnumFacing dir = var8[var10];
            int dirX = dir.func_176730_m().func_177958_n();
            int dirY = dir.func_176730_m().func_177952_p();
            if (dirX == diffX && dirY == diffY) {
               int rotation = 0;
               switch(dir.func_176734_d()) {
               case NORTH:
                  rotation = 7;
                  break;
               case SOUTH:
                  rotation = 3;
                  break;
               case WEST:
                  rotation = 5;
                  break;
               case EAST:
                  rotation = 1;
               }

               moves.add(new ArrowAlign.GridMove(current, rotation));
               break;
            }
         }
      }

      Collections.reverse(solution);
      return moves;
   }

   private static List<Point> solve(int[][] grid, Point start, Point end) {
      LinkedList<Point> queue = new LinkedList();
      Point[][] gridCopy = new Point[grid.length][grid[0].length];
      queue.addLast(start);
      gridCopy[start.y][start.x] = start;

      while(queue.size() != 0) {
         Point currPos = (Point)queue.pollFirst();
         EnumFacing[] var6 = directions;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            EnumFacing dir = var6[var8];
            Point nextPos = move(grid, gridCopy, currPos, dir);
            if (nextPos != null) {
               queue.addLast(nextPos);
               gridCopy[nextPos.y][nextPos.x] = new Point(currPos.x, currPos.y);
               if (end.equals(new Point(nextPos.x, nextPos.y))) {
                  List<Point> steps = new ArrayList();
                  Point tmp = currPos;
                  int count = 0;
                  steps.add(nextPos);
                  steps.add(currPos);

                  while(tmp != start) {
                     ++count;
                     tmp = gridCopy[tmp.y][tmp.x];
                     steps.add(tmp);
                  }

                  return steps;
               }
            }
         }
      }

      return new ArrayList();
   }

   private static Point move(int[][] grid, Point[][] gridCopy, Point currPos, EnumFacing dir) {
      int x = currPos.x;
      int y = currPos.y;
      int diffX = dir.func_176730_m().func_177958_n();
      int diffY = dir.func_176730_m().func_177952_p();
      int i = x + diffX >= 0 && x + diffX < grid[0].length && y + diffY >= 0 && y + diffY < grid.length && grid[y + diffY][x + diffX] != 1 ? 1 : 0;
      return gridCopy[y + i * diffY][x + i * diffX] != null ? null : new Point(x + i * diffX, y + i * diffY);
   }

   private static int[][] getLayout() {
      int[][] ret = new int[5][5];

      for(int row = 0; row < 5; ++row) {
         for(int col = 0; col < 5; ++col) {
            ArrowAlign.MazeSpace space = (ArrowAlign.MazeSpace)grid.stream().filter((it) -> {
               return it.coords.equals(new Point(row, col));
            }).findFirst().orElse((Object)null);
            ret[col][row] = space != null ? (space.framePos != null ? 0 : 1) : 1;
         }
      }

      return ret;
   }

   private static EnumFacing[] reverse(EnumFacing[] array) {
      for(int i = 0; i < array.length / 2; ++i) {
         EnumFacing temp = array[i];
         array[i] = array[array.length - i - 1];
         array[array.length - i - 1] = temp;
      }

      return array;
   }

   static {
      box = (List)StreamSupport.stream(BlockPos.func_177980_a(topLeft, botRight).spliterator(), false).sorted((a, b) -> {
         return a.func_177956_o() == b.func_177956_o() ? b.func_177952_p() - a.func_177952_p() : Integer.compare(b.func_177956_o(), a.func_177956_o());
      }).collect(Collectors.toList());
      grid = new LinkedHashSet();
      directionSet = new HashMap();
      ticks = 0;
      directions = reverse((EnumFacing[])EnumFacing.field_176754_o.clone());
      clicking = false;
      mc = Minecraft.func_71410_x();
   }

   private static class MazeSpace {
      BlockPos framePos;
      ArrowAlign.SpaceType type;
      Point coords;

      public MazeSpace(BlockPos framePos, ArrowAlign.SpaceType type, Point coords) {
         this.framePos = framePos;
         this.type = type;
         this.coords = coords;
      }

      public String toString() {
         return this.framePos + " - " + this.type + " - " + this.coords;
      }
   }

   private static class GridMove {
      Point point;
      int directionNum;

      public GridMove(Point point, int directionNum) {
         this.point = point;
         this.directionNum = directionNum;
      }
   }

   private static enum SpaceType {
      EMPTY,
      PATH,
      START,
      END;
   }
}

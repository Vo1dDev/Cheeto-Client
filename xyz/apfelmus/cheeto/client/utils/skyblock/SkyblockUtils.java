package xyz.apfelmus.cheeto.client.utils.skyblock;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

public class SkyblockUtils {
   private static Minecraft mc = Minecraft.func_71410_x();
   private static final ArrayList<Block> interactables;

   public static void ghostBlock() {
      if (mc.field_71476_x.func_178782_a() != null) {
         Block block = Minecraft.func_71410_x().field_71441_e.func_180495_p(mc.field_71476_x.func_178782_a()).func_177230_c();
         if (!interactables.contains(block)) {
            mc.field_71441_e.func_175698_g(mc.field_71476_x.func_178782_a());
         }

      }
   }

   public static int getMobHp(EntityArmorStand aStand) {
      double mobHp = -1.0D;
      Pattern pattern = Pattern.compile(".+? ([.\\d]+)[Mk]?/[.\\d]+[Mk]?");
      String stripped = stripString(aStand.func_70005_c_());
      Matcher mat = pattern.matcher(stripped);
      if (mat.matches()) {
         try {
            mobHp = Double.parseDouble(mat.group(1));
         } catch (NumberFormatException var7) {
         }
      }

      return (int)Math.ceil(mobHp);
   }

   public static Entity getEntityCuttingOtherEntity(Entity e, Class<?> entityType) {
      List<Entity> possible = mc.field_71441_e.func_175674_a(e, e.func_174813_aQ().func_72314_b(0.3D, 2.0D, 0.3D), (a) -> {
         return !a.field_70128_L && !a.equals(mc.field_71439_g) && !(a instanceof EntityArmorStand) && !(a instanceof EntityFireball) && !(a instanceof EntityFishHook) && (entityType == null || entityType.isInstance(a));
      });
      return !possible.isEmpty() ? (Entity)Collections.min(possible, Comparator.comparing((e2) -> {
         return e2.func_70032_d(e);
      })) : null;
   }

   public static SkyblockUtils.Location getLocation() {
      if (isInIsland()) {
         return SkyblockUtils.Location.ISLAND;
      } else if (isInHub()) {
         return SkyblockUtils.Location.HUB;
      } else if (isAtLift()) {
         return SkyblockUtils.Location.LIFT;
      } else if (isInSkyblock()) {
         return SkyblockUtils.Location.SKYBLOCK;
      } else if (isInLobby()) {
         return SkyblockUtils.Location.LOBBY;
      } else {
         IBlockState ibs = mc.field_71441_e.func_180495_p(mc.field_71439_g.func_180425_c().func_177977_b());
         return ibs != null && ibs.func_177230_c() == Blocks.field_150344_f ? SkyblockUtils.Location.LIMBO : SkyblockUtils.Location.NONE;
      }
   }

   public static boolean isInIsland() {
      return hasLine("Your Island");
   }

   public static boolean isInHub() {
      return hasLine("Village") && !hasLine("Dwarven");
   }

   public static boolean isAtLift() {
      return hasLine("The Lift");
   }

   public static boolean isInDungeon() {
      return hasLine("Dungeon Cleared:") || hasLine("Start");
   }

   public static boolean isInFloor(String floor) {
      return hasLine("The Catacombs (" + floor + ")");
   }

   public static boolean isInSkyblock() {
      return hasLine("SKYBLOCK");
   }

   public static boolean isInLobby() {
      return hasLine("HYPIXEL") || hasLine("PROTOTYPE");
   }

   public static boolean hasLine(String sbString) {
      if (mc != null && mc.field_71439_g != null) {
         ScoreObjective sbo = mc.field_71441_e.func_96441_U().func_96539_a(1);
         if (sbo != null) {
            List<String> scoreboard = getSidebarLines();
            scoreboard.add(StringUtils.func_76338_a(sbo.func_96678_d()));
            Iterator var3 = scoreboard.iterator();

            while(var3.hasNext()) {
               String s = (String)var3.next();
               String validated = stripString(s);
               if (validated.contains(sbString)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static String stripString(String s) {
      char[] nonValidatedString = StringUtils.func_76338_a(s).toCharArray();
      StringBuilder validated = new StringBuilder();
      char[] var3 = nonValidatedString;
      int var4 = nonValidatedString.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char a = var3[var5];
         if (a < 127 && a > 20) {
            validated.append(a);
         }
      }

      return validated.toString();
   }

   private static List<String> getSidebarLines() {
      List<String> lines = new ArrayList();
      Scoreboard scoreboard = Minecraft.func_71410_x().field_71441_e.func_96441_U();
      if (scoreboard == null) {
         return lines;
      } else {
         ScoreObjective objective = scoreboard.func_96539_a(1);
         if (objective == null) {
            return lines;
         } else {
            Collection<Score> scores = scoreboard.func_96534_i(objective);
            List<Score> list = new ArrayList();
            Iterator var5 = scores.iterator();

            Score score;
            while(var5.hasNext()) {
               score = (Score)var5.next();
               if (score != null && score.func_96653_e() != null && !score.func_96653_e().startsWith("#")) {
                  list.add(score);
               }
            }

            ArrayList scores;
            if (list.size() > 15) {
               scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
            } else {
               scores = list;
            }

            var5 = scores.iterator();

            while(var5.hasNext()) {
               score = (Score)var5.next();
               ScorePlayerTeam team = scoreboard.func_96509_i(score.func_96653_e());
               lines.add(ScorePlayerTeam.func_96667_a(team, score.func_96653_e()));
            }

            return lines;
         }
      }
   }

   public static void silentUse(int mainSlot, int useSlot) {
      int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
      if (useSlot > 0 && useSlot <= 9) {
         mc.field_71439_g.field_71071_by.field_70461_c = useSlot - 1;
         mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
      }

      if (mainSlot > 0 && mainSlot <= 9) {
         mc.field_71439_g.field_71071_by.field_70461_c = mainSlot - 1;
      } else if (mainSlot == 0) {
         mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
      }

   }

   static {
      interactables = new ArrayList(Arrays.asList(Blocks.field_180410_as, Blocks.field_150467_bQ, Blocks.field_150461_bJ, Blocks.field_150324_C, Blocks.field_180412_aq, Blocks.field_150382_bo, Blocks.field_150483_bI, Blocks.field_150462_ai, Blocks.field_150486_ae, Blocks.field_180409_at, Blocks.field_150453_bW, Blocks.field_180402_cm, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_150381_bn, Blocks.field_150477_bB, Blocks.field_150460_al, Blocks.field_150438_bZ, Blocks.field_180411_ar, Blocks.field_150442_at, Blocks.field_150323_B, Blocks.field_150455_bV, Blocks.field_150441_bU, Blocks.field_150416_aS, Blocks.field_150413_aR, Blocks.field_150472_an, Blocks.field_150444_as, Blocks.field_150415_aT, Blocks.field_150447_bR, Blocks.field_150471_bO, Blocks.field_150430_aB, Blocks.field_180413_ao, Blocks.field_150465_bP));
   }

   public static enum Location {
      ISLAND,
      HUB,
      LIFT,
      SKYBLOCK,
      LOBBY,
      LIMBO,
      NONE;
   }
}

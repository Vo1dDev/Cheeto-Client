package xyz.apfelmus.cheeto.client.modules.combat;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "SlayerAimbot",
   category = Category.COMBAT
)
public class SlayerAimbot {
   @Setting(
      name = "AimSpeed"
   )
   private IntegerSetting aimSpeed = new IntegerSetting(45, 5, 100);
   @Setting(
      name = "Range"
   )
   private IntegerSetting range = new IntegerSetting(4, 3, 7);
   @Setting(
      name = "Blatant"
   )
   private BooleanSetting blatant = new BooleanSetting(false);
   @Setting(
      name = "Minibosses"
   )
   private BooleanSetting miniBosses = new BooleanSetting(true);
   @Setting(
      name = "DisableForTP"
   )
   private BooleanSetting disable = new BooleanSetting(false);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static EntityArmorStand slayerStand;
   private static Entity slayerMob;
   private static boolean isMini;
   private static final Map<String, Class<?>> slayerMap = new HashMap<String, Class<?>>() {
      {
         this.put("Revenant Horror", EntityZombie.class);
         this.put("Atoned Horror", EntityZombie.class);
         this.put("Tarantula Broodfather", EntitySpider.class);
         this.put("Sven Packmaster", EntityWolf.class);
         this.put("Voidgloom Seraph", EntityEnderman.class);
      }
   };
   private static final Map<String, Class<?>> miniMap = new HashMap<String, Class<?>>() {
      {
         this.put("Revenant Champion", EntityZombie.class);
         this.put("Deformed Revenant", EntityZombie.class);
         this.put("Atoned Champion", EntityZombie.class);
         this.put("Atoned Revenant", EntityZombie.class);
         this.put("Tarantula Beast", EntitySpider.class);
         this.put("Mutant Tarantula", EntitySpider.class);
         this.put("Sven Follower", EntityWolf.class);
         this.put("Sven Alpha", EntityWolf.class);
         this.put("Voidling Devotee", EntityEnderman.class);
         this.put("Voidling Radical", EntityEnderman.class);
         this.put("Voidcrazed Maniac", EntityEnderman.class);
      }
   };

   @Enable
   public void onEnable() {
      slayerMob = null;
      slayerStand = null;
      isMini = false;
   }

   @Event
   public void onTick(ClientTickEvent event) {
      HashMap allPossibleSlayers;
      if (slayerMob == null) {
         allPossibleSlayers = new HashMap();
         Map<Entity, EntityArmorStand> allPossibleMinis = new HashMap();
         int rongo = this.range.getCurrent();
         Iterator var5 = mc.field_71441_e.func_175674_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b((double)rongo, (double)rongo, (double)rongo), (Predicate)null).iterator();

         while(var5.hasNext()) {
            Entity e = (Entity)var5.next();
            if (e instanceof EntityArmorStand) {
               slayerMap.forEach((k, v) -> {
                  if (e.func_95999_t().contains(k) && e.func_70032_d(mc.field_71439_g) <= (float)this.range.getCurrent()) {
                     Entity slay = SkyblockUtils.getEntityCuttingOtherEntity(e, v);
                     if (slay != null) {
                        allPossibleSlayers.put(slay, (EntityArmorStand)e);
                     }
                  }

               });
               if (this.miniBosses.isEnabled()) {
                  miniMap.forEach((k, v) -> {
                     if (e.func_95999_t().contains(k) && e.func_70032_d(mc.field_71439_g) <= (float)this.range.getCurrent()) {
                        Entity mini = SkyblockUtils.getEntityCuttingOtherEntity(e, v);
                        if (mini != null) {
                           allPossibleMinis.put(mini, (EntityArmorStand)e);
                        }
                     }

                  });
               }
            }
         }

         if (!allPossibleSlayers.isEmpty()) {
            slayerMob = (Entity)Collections.min(allPossibleSlayers.keySet(), Comparator.comparing((e2) -> {
               return e2.func_70032_d(mc.field_71439_g);
            }));
            slayerStand = (EntityArmorStand)allPossibleSlayers.get(slayerMob);
         } else if (!allPossibleMinis.isEmpty()) {
            slayerMob = (Entity)Collections.min(allPossibleMinis.keySet(), Comparator.comparing((e2) -> {
               return e2.func_70032_d(mc.field_71439_g);
            }));
            slayerStand = (EntityArmorStand)allPossibleMinis.get(slayerMob);
            isMini = true;
         }
      } else if (this.miniBosses.isEnabled() && isMini) {
         allPossibleSlayers = new HashMap();
         int rongo = this.range.getCurrent();
         Iterator var8 = mc.field_71441_e.func_175674_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b((double)rongo, (double)rongo, (double)rongo), (Predicate)null).iterator();

         while(var8.hasNext()) {
            Entity e = (Entity)var8.next();
            if (e instanceof EntityArmorStand) {
               slayerMap.forEach((k, v) -> {
                  if (e.func_95999_t().contains(k) && e.func_70032_d(mc.field_71439_g) <= (float)this.range.getCurrent()) {
                     Entity slay = SkyblockUtils.getEntityCuttingOtherEntity(e, v);
                     if (slay != null) {
                        allPossibleSlayers.put(slay, (EntityArmorStand)e);
                     }
                  }

               });
            }
         }

         if (!allPossibleSlayers.isEmpty()) {
            slayerMob = (Entity)Collections.min(allPossibleSlayers.keySet(), Comparator.comparing((e2) -> {
               return e2.func_70032_d(mc.field_71439_g);
            }));
            slayerStand = (EntityArmorStand)allPossibleSlayers.get(slayerMob);
            isMini = false;
         }
      }

   }

   @Event
   public void onRender(Render3DEvent event) {
      if (this.disable.isEnabled()) {
         ItemStack currentHeld = mc.field_71439_g.func_70694_bm();
         if (currentHeld == null || currentHeld.func_82833_r().contains("Aspect of the ")) {
            return;
         }
      }

      if (slayerMob != null) {
         int mobHp = this.getSlayerHp();
         if (slayerMob.func_70032_d(mc.field_71439_g) > 5.0F || slayerMob.field_70128_L || mobHp == 0) {
            slayerMob = null;
            slayerStand = null;
            return;
         }

         EntityPlayerSP var10000;
         if (!this.blatant.isEnabled() && (mc.field_71476_x == null || mc.field_71476_x.field_72313_a != MovingObjectType.ENTITY || !mc.field_71476_x.field_72308_g.equals(slayerMob))) {
            double n = RotationUtils.fovFromEntity(slayerMob);
            double complimentSpeed = n * ThreadLocalRandom.current().nextDouble(-1.47328D, 2.48293D) / 100.0D;
            float val = (float)(-(complimentSpeed + n / (101.0D - (double)((float)ThreadLocalRandom.current().nextDouble((double)this.aimSpeed.getCurrent() - 4.723847D, (double)this.aimSpeed.getCurrent())))));
            var10000 = mc.field_71439_g;
            var10000.field_70177_z += val;
         } else if (this.blatant.isEnabled()) {
            Rotation needed = RotationUtils.getNeededChange(RotationUtils.getRotation(slayerMob.func_174791_d()));
            var10000 = mc.field_71439_g;
            var10000.field_70177_z += needed.getYaw();
         }
      }

   }

   @Event
   public void onWorldUnload(WorldUnloadEvent event) {
      slayerMob = null;
   }

   private int getSlayerHp() {
      int mobHp = -1;
      Pattern pattern = Pattern.compile(".+? (\\d+)[Mk]?");
      String stripped = SkyblockUtils.stripString(slayerStand.func_70005_c_());
      Matcher mat = pattern.matcher(stripped);
      if (mat.matches()) {
         try {
            mobHp = Integer.parseInt(mat.group(1));
         } catch (NumberFormatException var6) {
         }
      }

      return mobHp;
   }
}

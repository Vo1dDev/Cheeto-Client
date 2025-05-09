package xyz.apfelmus.cheeto.client.modules.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.settings.ModeSetting;
import xyz.apfelmus.cheeto.client.utils.client.ChadUtils;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;
import xyz.apfelmus.cheeto.client.utils.client.JsonUtils;
import xyz.apfelmus.cheeto.client.utils.client.KeybindUtils;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.fishing.FishingJson;
import xyz.apfelmus.cheeto.client.utils.fishing.Location;
import xyz.apfelmus.cheeto.client.utils.fishing.PathPoint;
import xyz.apfelmus.cheeto.client.utils.math.RandomUtil;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.math.VecUtils;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "AutoFish",
   category = Category.PLAYER
)
public class AutoFish {
   private static FishingJson fishingJson = JsonUtils.getFishingJson();
   @Setting(
      name = "FishingSpot",
      description = "Spot to fish at, requires Etherwarp"
   )
   private ModeSetting fishingSpot = new ModeSetting("None", this.getFishingSpotNames());
   @Setting(
      name = "RecastDelay"
   )
   private IntegerSetting recastDelay = new IntegerSetting(250, 0, 2000);
   @Setting(
      name = "AntiAfk"
   )
   private BooleanSetting antiAfk = new BooleanSetting(true);
   @Setting(
      name = "RodSlot"
   )
   private IntegerSetting rodSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "WhipSlot",
      description = "Configure for Automatic SC Killing"
   )
   private IntegerSetting whipSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "KillMode",
      description = "Left or Right Click"
   )
   private ModeSetting killMode = new ModeSetting("Right", Arrays.asList("Left", "Right"));
   @Setting(
      name = "KillPrio",
      description = "Kill SC before Re-casting (Sword)"
   )
   private BooleanSetting killPrio = new BooleanSetting(false);
   @Setting(
      name = "SCRange",
      description = "Range for Sea Creature Killing"
   )
   private IntegerSetting scRange = new IntegerSetting(10, 0, 20);
   @Setting(
      name = "PetSwap",
      description = "Activates PetSwap on bobber in water"
   )
   private BooleanSetting petSwap = new BooleanSetting(false);
   @Setting(
      name = "AotvSlot"
   )
   private IntegerSetting aotvSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "WarpLookTime",
      description = "Set higher if low mana or bad ping"
   )
   private IntegerSetting warpLookTime = new IntegerSetting(500, 0, 2500);
   @Setting(
      name = "WarpTime",
      description = "Set higher if low mana or bad ping"
   )
   private IntegerSetting warpTime = new IntegerSetting(250, 0, 1000);
   @Setting(
      name = "MaxPlayerRange",
      description = "Range the bot will warp out at"
   )
   private IntegerSetting maxPlayerRange = new IntegerSetting(5, 0, 10);
   @Setting(
      name = "Sneak",
      description = "Makes the player sneak while fishing"
   )
   private BooleanSetting sneak = new BooleanSetting(false);
   @Setting(
      name = "Ungrab",
      description = "Automatically tabs out"
   )
   private BooleanSetting ungrab = new BooleanSetting(true);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static List<String> fishingMobs = JsonUtils.getListFromUrl("https://gist.githubusercontent.com/Apfelmus1337/da641d3805bddf800eef170cbb0068ec/raw", "mobs");
   private static TimeHelper warpTimer = new TimeHelper();
   private static TimeHelper throwTimer = new TimeHelper();
   private static TimeHelper inWaterTimer = new TimeHelper();
   private static TimeHelper killTimer = new TimeHelper();
   private static TimeHelper recoverTimer = new TimeHelper();
   private static EntityArmorStand curScStand = null;
   private static Entity curSc = null;
   private static boolean killing = false;
   private static Location currentLocation = null;
   private static List<PathPoint> path = null;
   private static BlockPos oldPos = null;
   private static double oldBobberPosY = 0.0D;
   private static boolean oldBobberInWater = false;
   private static int ticks = 0;
   private static Vec3 startPos = null;
   private static Rotation startRot = null;
   private static List<AutoFish.ParticleEntry> particleList = new ArrayList();
   private AutoFish.AutoFishState afs;
   private AutoFish.WarpState warpState;
   private AutoFish.AAState aaState;

   public AutoFish() {
      this.afs = AutoFish.AutoFishState.THROWING;
      this.warpState = AutoFish.WarpState.SETUP;
      this.aaState = AutoFish.AAState.AWAY;
   }

   @Enable
   public void onEnable() {
      this.afs = AutoFish.AutoFishState.THROWING;
      this.aaState = AutoFish.AAState.AWAY;
      throwTimer.reset();
      inWaterTimer.reset();
      warpTimer.reset();
      ticks = 0;
      oldBobberPosY = 0.0D;
      oldBobberInWater = false;
      curScStand = null;
      curSc = null;
      killing = true;
      particleList.clear();
      RotationUtils.reset();
      if (this.rodSlot.getCurrent() == 0) {
         ChatUtils.send("Configure your RodSlot pls ty");
         CF4M.INSTANCE.moduleManager.toggle((Object)this);
      } else if (this.whipSlot.getCurrent() != 0 && fishingMobs.isEmpty()) {
         ChatUtils.send("An error occured while getting Fishing Mobs, reloading...");
         fishingMobs = JsonUtils.getListFromUrl("https://gist.githubusercontent.com/Apfelmus1337/da641d3805bddf800eef170cbb0068ec/raw", "mobs");
         CF4M.INSTANCE.moduleManager.toggle((Object)this);
      } else {
         if (!this.fishingSpot.getCurrent().equals("None")) {
            if (fishingJson == null) {
               ChatUtils.send("An error occured while getting Fishing Locations, reloading...");
               fishingJson = JsonUtils.getFishingJson();
               CF4M.INSTANCE.moduleManager.toggle((Object)this);
               return;
            }

            if (this.aotvSlot.getCurrent() == 0) {
               ChatUtils.send("Configure your AotvSlot pls ty");
               CF4M.INSTANCE.moduleManager.toggle((Object)this);
               return;
            }

            this.afs = AutoFish.AutoFishState.WARP_ISLAND;
            this.warpState = AutoFish.WarpState.SETUP;
         }

         startPos = mc.field_71439_g.func_174791_d();
         startRot = new Rotation(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A);
         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), this.sneak.isEnabled());
         if (this.ungrab.isEnabled()) {
            ChadUtils.ungrabMouse();
         }

      }
   }

   @Disable
   public void onDisable() {
      if (this.sneak.isEnabled()) {
         KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
      }

      KeybindUtils.stopMovement();
      RotationUtils.reset();
      if (this.ungrab.isEnabled()) {
         ChadUtils.regrabMouse();
      }

   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (mc.field_71462_r == null || mc.field_71462_r instanceof GuiChat) {
         if (this.antiAfk.isEnabled() && this.afs != AutoFish.AutoFishState.WARP_ISLAND && this.afs != AutoFish.AutoFishState.WARP_SPOT && this.afs != AutoFish.AutoFishState.NAVIGATING) {
            KeybindUtils.stopMovement();
            if (++ticks > 40) {
               ticks = 0;
               List<KeyBinding> neededPresses = VecUtils.getNeededKeyPresses(mc.field_71439_g.func_174791_d(), startPos);
               neededPresses.forEach((v) -> {
                  KeyBinding.func_74510_a(v.func_151463_i(), true);
               });
               if (RotationUtils.done) {
                  switch(this.aaState) {
                  case AWAY:
                     Rotation afk = new Rotation(startRot.getYaw(), startRot.getPitch());
                     afk.addYaw((float)(Math.random() * 4.0D - 2.0D));
                     afk.addPitch((float)(Math.random() * 4.0D - 2.0D));
                     RotationUtils.setup(afk, (long)RandomUtil.randBetween(400, 600));
                     this.aaState = AutoFish.AAState.BACK;
                     break;
                  case BACK:
                     RotationUtils.setup(startRot, (long)RandomUtil.randBetween(400, 600));
                     this.aaState = AutoFish.AAState.AWAY;
                  }
               }
            }
         }

         particleList.removeIf((v) -> {
            return System.currentTimeMillis() - v.timeAdded > 1000L;
         });
         if (this.whipSlot.getCurrent() != 0) {
            if (curScStand != null && SkyblockUtils.getMobHp(curScStand) <= 0) {
               if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
               }

               curScStand = null;
               curSc = null;
            }

            if (curSc == null && killing) {
               RotationUtils.setup(startRot, (long)RandomUtil.randBetween(400, 600));
               killing = false;
            }
         }

         SkyblockUtils.Location sbLoc = SkyblockUtils.getLocation();
         String warpLoc;
         if (recoverTimer.hasReached(5000L)) {
            warpLoc = "";
            switch(sbLoc) {
            case LOBBY:
               ChatUtils.send("Detected player in Lobby, re-warping");
               warpLoc = "/play skyblock";
               break;
            case LIMBO:
               ChatUtils.send("Detected player in Limbo, re-warping");
               warpLoc = "/l";
            }

            if (!warpLoc.equals("")) {
               this.afs = AutoFish.AutoFishState.THROWING;
               this.aaState = AutoFish.AAState.AWAY;
               throwTimer.reset();
               inWaterTimer.reset();
               warpTimer.reset();
               recoverTimer.reset();
               ticks = 0;
               oldBobberPosY = 0.0D;
               oldBobberInWater = false;
               curScStand = null;
               curSc = null;
               killing = true;
               particleList.clear();
               RotationUtils.reset();
               mc.field_71439_g.func_71165_d(warpLoc);
               this.afs = AutoFish.AutoFishState.WARP_ISLAND;
               this.warpState = AutoFish.WarpState.SETUP;
            }

            recoverTimer.reset();
         }

         if (this.afs == AutoFish.AutoFishState.THROWING || this.afs == AutoFish.AutoFishState.IN_WATER || this.afs == AutoFish.AutoFishState.FISH_BITE) {
            int rongo = this.maxPlayerRange.getCurrent();
            if (rongo != 0) {
               String warpName = "";
               boolean warpOut = false;
               Iterator var6 = mc.field_71441_e.func_175674_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b((double)rongo, (double)(rongo >> 1), (double)rongo), (a) -> {
                  return a instanceof EntityOtherPlayerMP || a instanceof EntityArmorStand;
               }).iterator();

               while(var6.hasNext()) {
                  Entity e = (Entity)var6.next();
                  if (e instanceof EntityArmorStand) {
                     ItemStack bushSlot = ((EntityArmorStand)e).func_71124_b(4);
                     if (bushSlot != null && Item.func_150898_a(Blocks.field_150330_I) == bushSlot.func_77973_b()) {
                        warpOut = true;
                        warpName = "Dead Bush";
                        break;
                     }
                  } else if (e instanceof EntityOtherPlayerMP) {
                     String formatted = e.func_145748_c_().func_150254_d();
                     if (!StringUtils.func_76338_a(formatted).equals(formatted) && (!formatted.startsWith("§r") || formatted.startsWith("§r§"))) {
                        warpOut = true;
                        warpName = e.func_70005_c_();
                     }
                  }
               }

               if (warpOut) {
                  if (!this.fishingSpot.getCurrent().equals("None")) {
                     ChatUtils.send("Switching lobbies cause a nice person is near you: " + warpName);
                     this.afs = AutoFish.AutoFishState.THROWING;
                     this.aaState = AutoFish.AAState.AWAY;
                     throwTimer.reset();
                     inWaterTimer.reset();
                     warpTimer.reset();
                     recoverTimer.reset();
                     ticks = 0;
                     oldBobberPosY = 0.0D;
                     oldBobberInWater = false;
                     curScStand = null;
                     curSc = null;
                     killing = true;
                     particleList.clear();
                     RotationUtils.reset();
                     this.afs = AutoFish.AutoFishState.WARP_ISLAND;
                     this.warpState = AutoFish.WarpState.SETUP;
                  } else {
                     mc.field_71439_g.func_71165_d("/warp home");
                     ChatUtils.send("A nice person was near you (" + warpName + "), but you didn't configure a fishing spot, so I evacuated you");
                  }
               }
            }

            if (this.sneak.isEnabled()) {
               KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
            }

            if (this.killPrio.isEnabled()) {
               this.findAndSetCurrentSeaCreature();
               if (curSc != null) {
                  throwTimer.reset();
               }
            } else if (curScStand != null && SkyblockUtils.getMobHp(curScStand) <= 0) {
               curScStand = null;
               curSc = null;
               if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
               }
            }
         }

         switch(this.afs) {
         case WARP_ISLAND:
            if (warpTimer.hasReached(5000L)) {
               Optional<Location> loc = fishingJson.locations.stream().filter((v) -> {
                  return v.name.equals(this.fishingSpot.getCurrent());
               }).findFirst();
               loc.ifPresent((location) -> {
                  currentLocation = location;
               });
               if (currentLocation != null) {
                  ChatUtils.send("Navigating to: " + currentLocation.name.split(" - ")[1]);
                  mc.field_71439_g.func_71165_d("/warp home");
                  warpTimer.reset();
                  this.afs = AutoFish.AutoFishState.WARP_SPOT;
               } else {
                  ChatUtils.send("Couldn't determine location, very weird");
                  CF4M.INSTANCE.moduleManager.toggle((Object)this);
               }
            }
            break;
         case WARP_SPOT:
            if (warpTimer.hasReached(5000L)) {
               warpLoc = currentLocation.name.split(" - ")[0];
               mc.field_71439_g.func_71165_d(warpLoc);
               warpTimer.reset();
               path = null;
               this.afs = AutoFish.AutoFishState.NAVIGATING;
            }
            break;
         case NAVIGATING:
            if (warpTimer.hasReached(5000L) && path == null) {
               path = new ArrayList(currentLocation.path);
               oldPos = null;
               KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
               warpTimer.reset();
               this.warpState = AutoFish.WarpState.SETUP;
            }
            break;
         case THROWING:
            if (mc.field_71439_g.field_71104_cf == null && throwTimer.hasReached((long)this.recastDelay.getCurrent())) {
               if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
               }

               mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
               throwTimer.reset();
               inWaterTimer.reset();
               this.afs = AutoFish.AutoFishState.IN_WATER;
            } else if (throwTimer.hasReached(2500L) && mc.field_71439_g.field_71104_cf != null) {
               this.afs = AutoFish.AutoFishState.FISH_BITE;
            }
            break;
         case IN_WATER:
            ItemStack heldItem = mc.field_71439_g.func_70694_bm();
            if (heldItem != null && heldItem.func_77973_b() == Items.field_151112_aM) {
               if (throwTimer.hasReached(500L) && mc.field_71439_g.field_71104_cf != null) {
                  if (!mc.field_71439_g.field_71104_cf.func_70090_H() && !mc.field_71439_g.field_71104_cf.func_180799_ab()) {
                     if (inWaterTimer.hasReached(2500L)) {
                        this.afs = AutoFish.AutoFishState.FISH_BITE;
                     }
                  } else {
                     if (!this.killPrio.isEnabled()) {
                        this.findAndSetCurrentSeaCreature();
                     }

                     if (!oldBobberInWater) {
                        if (this.petSwap.isEnabled()) {
                           KeybindUtils.stopMovement();
                           CF4M.INSTANCE.moduleManager.toggle("PetSwap");
                        }

                        oldBobberInWater = true;
                        inWaterTimer.reset();
                     }

                     EntityFishHook bobber = mc.field_71439_g.field_71104_cf;
                     if (inWaterTimer.hasReached(2500L) && Math.abs(bobber.field_70159_w) < 0.01D && Math.abs(bobber.field_70179_y) < 0.01D) {
                        double movement = bobber.field_70163_u - oldBobberPosY;
                        oldBobberPosY = bobber.field_70163_u;
                        if (movement < -0.04D && this.bobberIsNearParticles(bobber) || bobber.field_146043_c != null) {
                           this.afs = AutoFish.AutoFishState.FISH_BITE;
                        }
                     }
                  }
               } else if (throwTimer.hasReached(1000L) && mc.field_71439_g.field_71104_cf == null) {
                  throwTimer.reset();
                  this.afs = AutoFish.AutoFishState.THROWING;
               }
            } else if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
               mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
            }
            break;
         case FISH_BITE:
            if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
               mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
            }

            mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
            RotationUtils.setup(startRot, (long)this.recastDelay.getCurrent());
            oldBobberInWater = false;
            throwTimer.reset();
            this.afs = AutoFish.AutoFishState.THROWING;
         }

      }
   }

   private void findAndSetCurrentSeaCreature() {
      if (this.whipSlot.getCurrent() != 0) {
         int ranga = this.scRange.getCurrent();
         List<Entity> mobs = mc.field_71441_e.func_175674_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b((double)ranga, (double)(ranga >> 1), (double)ranga), (e) -> {
            return e instanceof EntityArmorStand;
         });
         Optional<Entity> filtered = mobs.stream().filter((v) -> {
            return v.func_70032_d(mc.field_71439_g) < (float)ranga && !v.func_70005_c_().contains(mc.field_71439_g.func_70005_c_()) && fishingMobs.stream().anyMatch((a) -> {
               return v.func_95999_t().contains(a);
            });
         }).min(Comparator.comparing((v) -> {
            return v.func_70032_d(mc.field_71439_g);
         }));
         if (filtered.isPresent()) {
            curScStand = (EntityArmorStand)filtered.get();
            curSc = SkyblockUtils.getEntityCuttingOtherEntity(curScStand, (Class)null);
            if (curSc != null && SkyblockUtils.getMobHp(curScStand) > 0) {
               killing = true;
               if (this.killMode.getCurrent().equals("Right")) {
                  RotationUtils.setup(RotationUtils.getRotation(curSc.func_174791_d()), 250L);
               } else {
                  RotationUtils.setup(RotationUtils.getRotation(curSc.func_174791_d().func_72441_c(0.0D, (double)curSc.func_70047_e(), 0.0D)), 250L);
               }
            } else if (SkyblockUtils.getMobHp(curScStand) <= 0) {
               curScStand = null;
               curSc = null;
               if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
               }
            }
         } else if (RotationUtils.done) {
            curScStand = null;
            curSc = null;
            if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
               mc.field_71439_g.field_71071_by.field_70461_c = this.rodSlot.getCurrent() - 1;
            }
         }
      }

   }

   @Event
   public void onRenderTick(Render3DEvent event) {
      if (curSc != null) {
         Render3DUtils.renderBoundingBox(curSc, event.partialTicks, -16776961);
      }

      if (mc.field_71462_r == null || mc.field_71462_r instanceof GuiChat) {
         if (!RotationUtils.done) {
            RotationUtils.update();
         }

         if (this.afs == AutoFish.AutoFishState.NAVIGATING && path != null) {
            switch(this.warpState) {
            case SETUP:
               if (path.size() > 0) {
                  if (warpTimer.hasReached((long)this.warpTime.getCurrent()) && !mc.field_71439_g.func_180425_c().equals(oldPos)) {
                     PathPoint a = (PathPoint)path.get(0);
                     path.remove(0);
                     RotationUtils.setup(RotationUtils.getRotation(new Vec3(a.x, a.y, a.z)), (long)this.warpLookTime.getCurrent());
                     oldPos = mc.field_71439_g.func_180425_c();
                     this.warpState = AutoFish.WarpState.LOOK;
                  } else if (warpTimer.hasReached(2500L)) {
                     ChatUtils.send("Got stuck while tp'ing, re-navigating");
                     mc.field_71439_g.func_71165_d("/l");
                     recoverTimer.reset();
                     warpTimer.reset();
                  }
               } else if (warpTimer.hasReached(1000L)) {
                  if (!this.sneak.isEnabled()) {
                     KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
                  }

                  startRot = currentLocation.rotation;
                  startPos = mc.field_71439_g.func_174791_d();
                  RotationUtils.setup(startRot, (long)this.recastDelay.getCurrent());
                  throwTimer.reset();
                  this.afs = AutoFish.AutoFishState.THROWING;
               }
               break;
            case LOOK:
               if (System.currentTimeMillis() <= RotationUtils.endTime) {
                  RotationUtils.update();
               } else {
                  RotationUtils.update();
                  warpTimer.reset();
                  this.warpState = AutoFish.WarpState.WARP;
               }
               break;
            case WARP:
               if (warpTimer.hasReached((long)this.warpTime.getCurrent())) {
                  SkyblockUtils.silentUse(0, this.aotvSlot.getCurrent());
                  warpTimer.reset();
                  this.warpState = AutoFish.WarpState.SETUP;
               }
            }

         } else {
            if (curSc != null && killTimer.hasReached(125L)) {
               if (this.whipSlot.getCurrent() > 0 && this.whipSlot.getCurrent() <= 8) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.whipSlot.getCurrent() - 1;
               }

               String var2 = this.killMode.getCurrent();
               byte var3 = -1;
               switch(var2.hashCode()) {
               case 2364455:
                  if (var2.equals("Left")) {
                     var3 = 0;
                  }
                  break;
               case 78959100:
                  if (var2.equals("Right")) {
                     var3 = 1;
                  }
               }

               switch(var3) {
               case 0:
                  KeybindUtils.leftClick();
                  break;
               case 1:
                  KeybindUtils.rightClick();
               }

               killTimer.reset();
            }

         }
      }
   }

   @Event
   public void onWorldUnload(WorldUnloadEvent event) {
      if (CF4M.INSTANCE.moduleManager.isEnabled((Object)this) && this.fishingSpot.getCurrent().equals("None")) {
         ChatUtils.send("Your server closed and you didn't have a Spot configured, stopping AutoFish");
         CF4M.INSTANCE.moduleManager.toggle((Object)this);
      } else {
         if (!this.fishingSpot.getCurrent().equals("None") && this.afs != AutoFish.AutoFishState.WARP_ISLAND && this.afs != AutoFish.AutoFishState.WARP_SPOT && this.afs != AutoFish.AutoFishState.NAVIGATING) {
            this.afs = AutoFish.AutoFishState.THROWING;
            this.aaState = AutoFish.AAState.AWAY;
            throwTimer.reset();
            inWaterTimer.reset();
            warpTimer.reset();
            recoverTimer.reset();
            ticks = 0;
            oldBobberPosY = 0.0D;
            oldBobberInWater = false;
            curScStand = null;
            curSc = null;
            killing = true;
            particleList.clear();
            RotationUtils.reset();
            this.afs = AutoFish.AutoFishState.WARP_ISLAND;
            this.warpState = AutoFish.WarpState.SETUP;
         }

      }
   }

   public static void handleParticles(S2APacketParticles packet) {
      if (packet.func_179749_a() == EnumParticleTypes.WATER_WAKE || packet.func_179749_a() == EnumParticleTypes.SMOKE_NORMAL) {
         particleList.add(new AutoFish.ParticleEntry(new Vec3(packet.func_149220_d(), packet.func_149226_e(), packet.func_149225_f()), System.currentTimeMillis()));
      }

   }

   private boolean bobberIsNearParticles(EntityFishHook bobber) {
      return particleList.stream().anyMatch((v) -> {
         return VecUtils.getHorizontalDistance(bobber.func_174791_d(), v.position) < 0.2D;
      });
   }

   private List<String> getFishingSpotNames() {
      List<String> ret = new ArrayList();
      ret.add("None");
      ret.addAll((Collection)fishingJson.locations.stream().map((v) -> {
         return v.name;
      }).collect(Collectors.toList()));
      return ret;
   }

   private static class ParticleEntry {
      public Vec3 position;
      public long timeAdded;

      public ParticleEntry(Vec3 position, long timeAdded) {
         this.position = position;
         this.timeAdded = timeAdded;
      }
   }

   private static enum AAState {
      AWAY,
      BACK;
   }

   private static enum WarpState {
      SETUP,
      LOOK,
      WARP;
   }

   private static enum AutoFishState {
      WARP_ISLAND,
      WARP_SPOT,
      NAVIGATING,
      THROWING,
      IN_WATER,
      FISH_BITE;
   }
}

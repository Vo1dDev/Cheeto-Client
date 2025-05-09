package xyz.apfelmus.cheeto.client.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientChatReceivedEvent;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.modules.player.CommMacro;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.settings.ModeSetting;
import xyz.apfelmus.cheeto.client.utils.client.ChadUtils;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.math.RandomUtil;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.mining.Location;
import xyz.apfelmus.cheeto.client.utils.mining.PathPoint;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "AutoMithril",
   category = Category.WORLD
)
public class AutoMithril implements Runnable {
   @Setting(
      name = "MiningSpot",
      description = "Spot to mine at, requires Etherwarp"
   )
   private ModeSetting miningSpot = new ModeSetting("None", this.getMiningSpotNames());
   @Setting(
      name = "PickSlot"
   )
   private IntegerSetting pickSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "Sneak",
      description = "Makes the player sneak while mining"
   )
   private BooleanSetting sneak = new BooleanSetting(false);
   @Setting(
      name = "BlueWool"
   )
   private BooleanSetting blueWool = new BooleanSetting(true);
   @Setting(
      name = "Prismarine"
   )
   private BooleanSetting prismarine = new BooleanSetting(true);
   @Setting(
      name = "Titanium"
   )
   private BooleanSetting titanium = new BooleanSetting(true);
   @Setting(
      name = "GrayShit"
   )
   private BooleanSetting grayShit = new BooleanSetting(true);
   @Setting(
      name = "LookTime"
   )
   private IntegerSetting lookTime = new IntegerSetting(500, 0, 2500);
   @Setting(
      name = "MaxMineTime",
      description = "Set to slightly more than it takes to mine"
   )
   private IntegerSetting maxMineTime = new IntegerSetting(5000, 0, 10000);
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
      name = "Ungrab",
      description = "Automatically tabs out"
   )
   private BooleanSetting ungrab = new BooleanSetting(true);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static Location currentLocation = null;
   private static List<PathPoint> path = null;
   private Thread thread;
   private List<AutoMithril.BetterBlockPos> blocksNear = new ArrayList();
   private List<BlockPos> blacklist = new ArrayList();
   private int delayMs = 500;
   private BlockPos curBlockPos;
   private Block curBlock;
   private TimeHelper mineTimer;
   private Vec3 startRot;
   private Vec3 endRot;
   private static TimeHelper warpTimer = new TimeHelper();
   private static TimeHelper recoverTimer = new TimeHelper();
   private static TimeHelper boostTimer = new TimeHelper();
   private static BlockPos oldPos = null;
   private static int oldDrillSlot = -1;
   private static String fsMsg = "";
   private AutoMithril.MineState mineState;
   private AutoMithril.WarpState warpState;
   private AutoMithril.RefuelState refuelState;

   public AutoMithril() {
      this.mineState = AutoMithril.MineState.CHOOSE;
      this.warpState = AutoMithril.WarpState.SETUP;
      this.refuelState = AutoMithril.RefuelState.CLICK_MERCHANT;
   }

   @Enable
   public void onEnable() {
      if (!this.miningSpot.getCurrent().equals("None") && !CF4M.INSTANCE.moduleManager.isEnabled("CommMacro")) {
         if (this.aotvSlot.getCurrent() > 0 && this.pickSlot.getCurrent() > 0) {
            this.mineState = AutoMithril.MineState.WARP_FORGE;
            this.refuelState = AutoMithril.RefuelState.CLICK_MERCHANT;
            currentLocation = null;
            warpTimer.reset();
            recoverTimer.reset();
            boostTimer.reset();
         } else {
            ChatUtils.send("Configure your fucking Slots if you want FailSafes retard");
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
         }
      } else {
         this.mineState = AutoMithril.MineState.CHOOSE;
      }

      this.warpState = AutoMithril.WarpState.SETUP;
      this.blocksNear.clear();
      this.blacklist.clear();
      this.mineTimer = new TimeHelper();
      this.curBlockPos = null;
      this.curBlock = null;
      this.startRot = null;
      this.endRot = null;
      KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), this.sneak.isEnabled());
      if (this.ungrab.isEnabled()) {
         ChadUtils.ungrabMouse();
      }

   }

   @Disable
   public void onDisable() {
      KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
      mc.field_71442_b.func_78767_c();
      if (this.ungrab.isEnabled()) {
         ChadUtils.regrabMouse();
      }

   }

   @Event
   public void onTick(ClientTickEvent event) {
      int rongo;
      if (this.mineState == AutoMithril.MineState.REFUEL && mc.field_71462_r instanceof GuiChest) {
         if (currentLocation != null && currentLocation.name.equals("REFUEL") && "Drill Anvil".equals(InventoryUtils.getInventoryName())) {
            switch(this.refuelState) {
            case CLICK_DRILL_IN:
               oldDrillSlot = InventoryUtils.getSlotForItem("Drill", Items.field_179562_cC);
               mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, oldDrillSlot, 0, 1, mc.field_71439_g);
               this.refuelState = AutoMithril.RefuelState.CLICK_FUEL_IN;
               break;
            case CLICK_FUEL_IN:
               ItemStack aboveDrill = InventoryUtils.getStackInOpenContainerSlot(20);
               if (aboveDrill != null && aboveDrill.func_77952_i() == 5) {
                  rongo = InventoryUtils.getSlotForItem("Volta", Items.field_151144_bL);
                  if (rongo == -1) {
                     rongo = InventoryUtils.getSlotForItem("Oil Barrel", Items.field_151144_bL);
                  }

                  if (rongo == -1) {
                     ChatUtils.send("Bozo you don't have any fuel");
                     CF4M.INSTANCE.moduleManager.toggle((Object)this);
                     mc.field_71439_g.func_71053_j();
                  } else {
                     mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, rongo, 0, 1, mc.field_71439_g);
                     this.refuelState = AutoMithril.RefuelState.REFUEL_DRILL;
                  }
               }
               break;
            case REFUEL_DRILL:
               ItemStack hopper = InventoryUtils.getStackInOpenContainerSlot(22);
               if (hopper != null && hopper.func_77948_v()) {
                  mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, mc.field_71439_g);
                  this.refuelState = AutoMithril.RefuelState.CLICK_DRILL_OUT;
               }
               break;
            case CLICK_DRILL_OUT:
               ItemStack oldDrill = InventoryUtils.getStackInOpenContainerSlot(29);
               if (oldDrill == null) {
                  mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, 13, 0, 1, mc.field_71439_g);
                  this.refuelState = AutoMithril.RefuelState.DONE_REFUELING;
               }
               break;
            case DONE_REFUELING:
               mc.field_71439_g.func_71053_j();
               this.mineState = AutoMithril.MineState.WARP_FORGE;
               this.refuelState = AutoMithril.RefuelState.CLICK_MERCHANT;
               recoverTimer.reset();
            }
         }

      } else {
         if (this.thread == null || !this.thread.isAlive()) {
            (this.thread = new Thread(this)).setDaemon(false);
            this.thread.setPriority(1);
            this.thread.start();
         }

         if (this.mineState == AutoMithril.MineState.CHOOSE || this.mineState == AutoMithril.MineState.LOOK || this.mineState == AutoMithril.MineState.MINE) {
            if (!CF4M.INSTANCE.moduleManager.isEnabled("CommMacro")) {
               boolean warpOut = false;
               rongo = this.maxPlayerRange.getCurrent();
               if (currentLocation != null && !currentLocation.name.equals("REFUEL")) {
                  if (boostTimer.hasReached(125000L)) {
                     mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
                     boostTimer.reset();
                  }

                  if (rongo != 0) {
                     String warpName = "";
                     Iterator var5 = mc.field_71441_e.func_175674_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b((double)rongo, (double)(rongo >> 1), (double)rongo), (a) -> {
                        return a instanceof EntityOtherPlayerMP || a instanceof EntityArmorStand;
                     }).iterator();

                     while(var5.hasNext()) {
                        Entity e = (Entity)var5.next();
                        if (e instanceof EntityArmorStand) {
                           ItemStack bushSlot = ((EntityArmorStand)e).func_71124_b(4);
                           if (bushSlot != null && Item.func_150898_a(Blocks.field_150330_I) == bushSlot.func_77973_b()) {
                              warpOut = true;
                              warpName = "Dead Bush";
                              break;
                           }
                        } else if (e instanceof EntityOtherPlayerMP && !e.func_70005_c_().equals("Goblin ") && !e.func_70005_c_().contains("Treasuer Hunter") && !e.func_70005_c_().contains("Crystal Sentry")) {
                           String formatted = e.func_145748_c_().func_150254_d();
                           if (!StringUtils.func_76338_a(formatted).equals(formatted) && (!formatted.startsWith("§r") || formatted.startsWith("§r§"))) {
                              warpOut = true;
                              warpName = e.func_70005_c_();
                           }
                        }
                     }

                     if (warpOut) {
                        if (!this.miningSpot.getCurrent().equals("None")) {
                           ChatUtils.send("Switching lobbies cause a nice person is near you: " + warpName);
                           mc.field_71439_g.func_71165_d("/warp home");
                           this.mineState = AutoMithril.MineState.WARP_FORGE;
                           KeyBinding.func_74506_a();
                           recoverTimer.reset();
                        } else {
                           ChatUtils.send("A person was near you, but you didn't configure a Mining Spot, sending to your island: " + warpName);
                           mc.field_71439_g.func_71165_d("/warp home");
                           KeyBinding.func_74506_a();
                           if (CF4M.INSTANCE.moduleManager.isEnabled((Object)this)) {
                              CF4M.INSTANCE.moduleManager.toggle((Object)this);
                           }
                        }
                     }
                  }
               }
            }

            this.blocksNear.removeIf((v) -> {
               Vec3 randPoint = (Vec3)v.points.get(RandomUtil.randBetween(0, v.points.size() - 1));
               MovingObjectPosition mop = mc.field_71441_e.func_72933_a(mc.field_71439_g.func_174824_e(1.0F), randPoint);
               if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK) {
                  return !mop.func_178782_a().equals(v.blockPos) || !(randPoint.func_72438_d(mc.field_71439_g.func_174824_e(1.0F)) < (double)mc.field_71442_b.func_78757_d());
               } else {
                  return true;
               }
            });
            if (this.blocksNear.stream().noneMatch((v) -> {
               return v.blockPos.equals(this.curBlockPos);
            })) {
               this.mineState = AutoMithril.MineState.CHOOSE;
            }
         }

         if (recoverTimer.hasReached(5000L)) {
            SkyblockUtils.Location curLoc;
            curLoc = SkyblockUtils.getLocation();
            label154:
            switch(curLoc) {
            case SKYBLOCK:
               IBlockState ibs;
               switch(this.mineState) {
               case WARP_FORGE:
                  Optional<Location> loc = CommMacro.miningJson.locations.stream().filter((v) -> {
                     return v.name.equals(this.miningSpot.getCurrent());
                  }).findFirst();
                  loc.ifPresent((location) -> {
                     currentLocation = location;
                  });
                  if (currentLocation != null) {
                     ChatUtils.send("Navigating to: " + currentLocation.name);
                     mc.field_71439_g.func_71165_d("/warp forge");
                     path = null;
                     this.mineState = AutoMithril.MineState.NAVIGATING;
                  } else {
                     ChatUtils.send("Couldn't determine location, very weird");
                     CF4M.INSTANCE.moduleManager.toggle((Object)this);
                  }
                  break label154;
               case NAVIGATING:
                  if (mc.field_71439_g.func_180425_c().equals(new BlockPos(1, 149, -68)) && path == null) {
                     path = new ArrayList(currentLocation.path);
                     oldPos = null;
                     KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
                     warpTimer.reset();
                     this.warpState = AutoMithril.WarpState.SETUP;
                  }
                  break label154;
               case CHOOSE:
                  if (boostTimer.hasReached(125000L)) {
                     mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
                     boostTimer.reset();
                  }

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
                     this.mineState = AutoMithril.MineState.LOOK;
                  } else {
                     KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
                  }
                  break label154;
               case MINE:
                  if (this.mineTimer.hasReached((long)this.maxMineTime.getCurrent())) {
                     this.blocksNear.removeIf((v) -> {
                        return v.blockPos.equals(this.curBlockPos);
                     });
                     this.mineState = AutoMithril.MineState.CHOOSE;
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
                           this.blocksNear.removeIf((v) -> {
                              return v.blockPos.equals(this.curBlockPos);
                           });
                           this.mineState = AutoMithril.MineState.CHOOSE;
                           this.curBlockPos = null;
                           this.curBlock = null;
                           this.startRot = null;
                           this.endRot = null;
                        }
                     }
                  }
               default:
                  break label154;
               }
            case ISLAND:
               ChatUtils.send("Detected player in Island, re-warping");
               fsMsg = "/warp forge";
               break;
            case HUB:
               ChatUtils.send("Detected player in Hub, re-warping");
               fsMsg = "/warp forge";
               break;
            case LIFT:
               ChatUtils.send("Detected player at Lift, re-warping");
               fsMsg = "/warp forge";
               break;
            case LOBBY:
               ChatUtils.send("Detected player in Lobby, re-warping");
               fsMsg = "/play skyblock";
               break;
            case LIMBO:
               ChatUtils.send("Detected player in Limbo, re-warping");
               fsMsg = "/l";
            }

            if (curLoc != SkyblockUtils.Location.SKYBLOCK && !this.miningSpot.getCurrent().equals("None") && !CF4M.INSTANCE.moduleManager.isEnabled("CommMacro")) {
               this.mineState = AutoMithril.MineState.WARP_FORGE;
               mc.field_71439_g.func_71165_d(fsMsg);
               KeyBinding.func_74506_a();
               recoverTimer.reset();
            }
         }

      }
   }

   @Event
   public void onRender(Render3DEvent event) {
      Iterator var2 = (new ArrayList(this.blocksNear)).iterator();

      while(var2.hasNext()) {
         AutoMithril.BetterBlockPos bbp = (AutoMithril.BetterBlockPos)var2.next();
         if (!bbp.blockPos.equals(this.curBlockPos)) {
            Render3DUtils.renderEspBox(bbp.blockPos, event.partialTicks, -16711681);
         }
      }

      if (this.blocksNear.stream().anyMatch((v) -> {
         return v.blockPos.equals(this.curBlockPos);
      })) {
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

      if (mc.field_71462_r == null) {
         if (this.mineState == AutoMithril.MineState.NAVIGATING && path != null) {
            switch(this.warpState) {
            case SETUP:
               if (path.size() > 0) {
                  if (warpTimer.hasReached((long)this.warpTime.getCurrent()) && !mc.field_71439_g.func_180425_c().equals(oldPos)) {
                     PathPoint a = (PathPoint)path.get(0);
                     path.remove(0);
                     RotationUtils.setup(RotationUtils.getRotation(new Vec3(a.x, a.y, a.z)), (long)this.warpLookTime.getCurrent());
                     oldPos = mc.field_71439_g.func_180425_c();
                     this.warpState = AutoMithril.WarpState.LOOK;
                  } else if (warpTimer.hasReached(2500L)) {
                     ChatUtils.send("Got stuck while tp'ing, re-navigating");
                     mc.field_71439_g.func_71165_d("/l");
                     recoverTimer.reset();
                     warpTimer.reset();
                  }
               } else {
                  if (!this.sneak.isEnabled()) {
                     KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
                  }

                  if (this.pickSlot.getCurrent() > 0 && this.pickSlot.getCurrent() <= 8) {
                     mc.field_71439_g.field_71071_by.field_70461_c = this.pickSlot.getCurrent() - 1;
                  }

                  if (currentLocation.name.equals("REFUEL")) {
                     this.mineState = AutoMithril.MineState.REFUEL;
                  } else {
                     this.mineState = AutoMithril.MineState.CHOOSE;
                  }
               }
               break;
            case LOOK:
               if (System.currentTimeMillis() <= RotationUtils.endTime) {
                  RotationUtils.update();
               } else {
                  RotationUtils.update();
                  warpTimer.reset();
                  this.warpState = AutoMithril.WarpState.WARP;
               }
               break;
            case WARP:
               if (warpTimer.hasReached((long)this.warpTime.getCurrent())) {
                  SkyblockUtils.silentUse(0, this.aotvSlot.getCurrent());
                  warpTimer.reset();
                  this.warpState = AutoMithril.WarpState.SETUP;
               }
            }
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
               if (next != null) {
                  Optional<AutoMithril.BetterBlockPos> bbp = this.blocksNear.stream().filter((v) -> {
                     return v.blockPos.equals(this.curBlockPos);
                  }).findFirst();
                  if (bbp.isPresent()) {
                     List<Vec3> points = ((AutoMithril.BetterBlockPos)bbp.get()).points;
                     points.stream().min(Comparator.comparing((v) -> {
                        return RotationUtils.getNeededChange(RotationUtils.getRotation(next.getVec3()), RotationUtils.getRotation(v)).getValue();
                     })).ifPresent((nextPointOnSameBlock) -> {
                        this.curBlock = mc.field_71441_e.func_180495_p(this.curBlockPos).func_177230_c();
                        this.endRot = nextPointOnSameBlock;
                        RotationUtils.setup(RotationUtils.getRotation(nextPointOnSameBlock), (long)this.maxMineTime.getCurrent());
                     });
                  }
               }

               this.mineState = AutoMithril.MineState.MINE;
            }
         }

      }
   }

   @Event
   public void onChatReceived(ClientChatReceivedEvent event) {
      if (!CF4M.INSTANCE.moduleManager.isEnabled("CommMacro")) {
         String msg = event.message.func_150260_c();
         if (this.mineState == AutoMithril.MineState.CHOOSE || this.mineState == AutoMithril.MineState.LOOK || this.mineState == AutoMithril.MineState.MINE) {
            if (msg.startsWith("Mining Speed Boost is now available!")) {
               mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
               boostTimer.reset();
            } else if (msg.startsWith("Your") && msg.contains("is empty! Refuel it by talking to a Drill Mechanic!")) {
               if (!this.miningSpot.getCurrent().equals("None")) {
                  currentLocation = (Location)CommMacro.miningJson.locations.get(CommMacro.miningJson.locations.size() - 1);
                  this.mineState = AutoMithril.MineState.WARP_FORGE;
               } else {
                  ChatUtils.send("Can't use Drill Refuel without a Mining Spot");
                  CF4M.INSTANCE.moduleManager.toggle((Object)this);
               }
            }
         }

         if (msg.contains("You can't fast travel while in combat!")) {
            mc.field_71439_g.func_71165_d("/l");
            recoverTimer.reset();
         }
      }

   }

   private List<String> getMiningSpotNames() {
      List<String> ret = new ArrayList();
      ret.add("None");
      ret.addAll((Collection)CommMacro.miningJson.locations.subList(0, CommMacro.miningJson.locations.size() - 5).stream().map((v) -> {
         return v.name;
      }).collect(Collectors.toList()));
      return ret;
   }

   private void addBlockIfHittable(BlockPos xyz) {
      List<Vec3> pointsOnBlock = RotationUtils.getPointsOnBlock(xyz);
      Iterator var3 = pointsOnBlock.iterator();

      while(var3.hasNext()) {
         Vec3 point = (Vec3)var3.next();
         MovingObjectPosition mop = mc.field_71441_e.func_72933_a(mc.field_71439_g.func_174824_e(1.0F), point);
         if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK && mop.func_178782_a().equals(xyz) && point.func_72438_d(mc.field_71439_g.func_174824_e(1.0F)) < (double)mc.field_71442_b.func_78757_d()) {
            if (this.blocksNear.stream().noneMatch((v) -> {
               return v.blockPos.equals(xyz);
            })) {
               this.blocksNear.add(new AutoMithril.BetterBlockPos(xyz, new ArrayList(Collections.singletonList(point))));
            } else {
               this.blocksNear.stream().filter((v) -> {
                  return v.blockPos.equals(xyz);
               }).findFirst().ifPresent((v) -> {
                  v.points.add(point);
               });
            }
         }
      }

   }

   private AutoMithril.BlockPosWithVec getClosestBlock(BlockPos excluding) {
      BlockPos closest = null;
      Rotation closestRot = null;
      Vec3 closestPoint = null;
      List<AutoMithril.BetterBlockPos> asd = new ArrayList(this.blocksNear);
      asd.removeIf((v) -> {
         return v.blockPos.equals(excluding);
      });
      List<AutoMithril.BetterBlockPos> tits = new ArrayList();
      if (CommMacro.hugeTits) {
         asd.forEach((bbpx) -> {
            IBlockState bs = mc.field_71441_e.func_180495_p(bbpx.blockPos);
            Block b = bs.func_177230_c();
            if (b == Blocks.field_150348_b && bs.func_177229_b(BlockStone.field_176247_a) == EnumType.DIORITE_SMOOTH) {
               tits.add(bbpx);
            }

         });
         if (!tits.isEmpty()) {
            asd = tits;
         }
      }

      List<AutoMithril.BetterBlockPos> miths = new ArrayList();
      if (CommMacro.mithril) {
         asd.forEach((bbpx) -> {
            IBlockState bs = mc.field_71441_e.func_180495_p(bbpx.blockPos);
            Block b = bs.func_177230_c();
            if (b == Blocks.field_150325_L && bs.func_177229_b(BlockColored.field_176581_a) == EnumDyeColor.GRAY) {
               miths.add(bbpx);
            } else if (b == Blocks.field_150406_ce && bs.func_177229_b(BlockColored.field_176581_a) == EnumDyeColor.CYAN) {
               miths.add(bbpx);
            }

         });
         if (!miths.isEmpty()) {
            asd = miths;
         }
      }

      Iterator var8 = asd.iterator();

      label43:
      while(var8.hasNext()) {
         AutoMithril.BetterBlockPos bbp = (AutoMithril.BetterBlockPos)var8.next();
         Iterator var10 = bbp.points.iterator();

         while(true) {
            Vec3 point;
            Rotation needed;
            do {
               if (!var10.hasNext()) {
                  continue label43;
               }

               point = (Vec3)var10.next();
               Rotation endRot = RotationUtils.getRotation(point);
               needed = RotationUtils.getNeededChange(endRot);
            } while(closestRot != null && needed.getValue() >= closestRot.getValue());

            closest = bbp.blockPos;
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
      label109:
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
                     if (this.blocksNear.stream().noneMatch((v) -> {
                        return v.blockPos.equals(xyz);
                     }) && !this.blacklist.contains(xyz)) {
                        Block block = bs.func_177230_c();
                        if (Math.sqrt(xyz.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) <= 6.0D) {
                           if (block == Blocks.field_150325_L) {
                              if (bs.func_177229_b(BlockColored.field_176581_a) == EnumDyeColor.LIGHT_BLUE) {
                                 if (this.blueWool.isEnabled()) {
                                    this.addBlockIfHittable(xyz);
                                 }
                              } else if (bs.func_177229_b(BlockColored.field_176581_a) == EnumDyeColor.GRAY && this.grayShit.isEnabled()) {
                                 this.addBlockIfHittable(xyz);
                              }
                           } else if (block == Blocks.field_180397_cI) {
                              if (this.prismarine.isEnabled()) {
                                 this.addBlockIfHittable(xyz);
                              }
                           } else if (block == Blocks.field_150348_b) {
                              if (bs.func_177229_b(BlockStone.field_176247_a) == EnumType.DIORITE_SMOOTH && this.titanium.isEnabled()) {
                                 this.addBlockIfHittable(xyz);
                              }
                           } else if (block == Blocks.field_150406_ce && bs.func_177229_b(BlockColored.field_176581_a) == EnumDyeColor.CYAN && this.grayShit.isEnabled()) {
                              this.addBlockIfHittable(xyz);
                           }
                        }
                     }
                  }
               }
            }

            Iterator var13 = (new ArrayList(this.blocksNear)).iterator();

            while(true) {
               AutoMithril.BetterBlockPos bbp;
               Block block;
               do {
                  if (!var13.hasNext()) {
                     try {
                        Thread.sleep((long)this.delayMs);
                     } catch (InterruptedException var12) {
                        var12.printStackTrace();
                     }
                     continue label109;
                  }

                  bbp = (AutoMithril.BetterBlockPos)var13.next();
                  IBlockState state = mc.field_71441_e.func_180495_p(bbp.blockPos);
                  block = null;
                  if (state != null) {
                     block = state.func_177230_c();
                  }
               } while(!(Math.sqrt(bbp.blockPos.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) > 5.0D) && block != Blocks.field_150357_h && block != Blocks.field_150350_a);

               this.blocksNear.remove(bbp);
            }
         }

         this.thread = null;
         return;
      }
   }

   public static class BlockPosWithVec {
      private BlockPos blockPos;
      private Vec3 vec3;

      public BlockPosWithVec(BlockPos blockPos, Vec3 vec3) {
         this.blockPos = blockPos;
         this.vec3 = vec3;
      }

      public BlockPos getBlockPos() {
         return this.blockPos;
      }

      public Vec3 getVec3() {
         return this.vec3;
      }
   }

   private static class BetterBlockPos {
      BlockPos blockPos;
      List<Vec3> points;

      public BetterBlockPos(BlockPos blockPos, List<Vec3> points) {
         this.blockPos = blockPos;
         this.points = points;
      }
   }

   private static enum RefuelState {
      CLICK_MERCHANT,
      CLICK_DRILL_IN,
      CLICK_FUEL_IN,
      REFUEL_DRILL,
      CLICK_DRILL_OUT,
      DONE_REFUELING;
   }

   private static enum WarpState {
      SETUP,
      LOOK,
      WARP;
   }

   static enum MineState {
      WARP_FORGE,
      NAVIGATING,
      REFUEL,
      CHOOSE,
      LOOK,
      MINE;
   }
}

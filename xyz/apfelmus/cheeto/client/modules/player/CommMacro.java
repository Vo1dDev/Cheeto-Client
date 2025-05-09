package xyz.apfelmus.cheeto.client.modules.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
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
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.ChadUtils;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;
import xyz.apfelmus.cheeto.client.utils.client.JsonUtils;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.math.RandomUtil;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.mining.Location;
import xyz.apfelmus.cheeto.client.utils.mining.MiningJson;
import xyz.apfelmus.cheeto.client.utils.mining.PathPoint;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "CommMacro",
   category = Category.PLAYER
)
public class CommMacro {
   @Setting(
      name = "LookTime",
      description = "Set higher if low mana or bad ping"
   )
   private IntegerSetting lookTime = new IntegerSetting(500, 0, 2500);
   @Setting(
      name = "WarpTime",
      description = "Set higher if low mana or bad ping"
   )
   private IntegerSetting warpTime = new IntegerSetting(250, 0, 1000);
   @Setting(
      name = "MaxPlayerRange"
   )
   private IntegerSetting maxPlayerRange = new IntegerSetting(5, 0, 10);
   @Setting(
      name = "PickSlot"
   )
   private IntegerSetting pickSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "AotvSlot"
   )
   private IntegerSetting aotvSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "PigeonSlot"
   )
   private IntegerSetting pigeonSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "Ungrab",
      description = "Automatically tabs out"
   )
   private BooleanSetting ungrab = new BooleanSetting(true);
   private static Minecraft mc = Minecraft.func_71410_x();
   public static MiningJson miningJson = JsonUtils.getMiningJson();
   private static CommMacro.Quest currentQuest = null;
   private static Location currentLocation = null;
   private static List<PathPoint> path = null;
   private static TimeHelper pigeonTimer = new TimeHelper();
   private static TimeHelper warpTimer = new TimeHelper();
   private static TimeHelper recoverTimer = new TimeHelper();
   private static TimeHelper boostTimer = new TimeHelper();
   private static BlockPos oldPos = null;
   public static boolean hugeTits = false;
   public static boolean mithril = false;
   private static int oldDrillSlot = -1;
   private static CommMacro.CommState commState;
   private static CommMacro.WarpState warpState;
   private static CommMacro.RefuelState refuelState;

   @Enable
   public void onEnable() {
      pigeonTimer.reset();
      warpTimer.reset();
      recoverTimer.reset();
      boostTimer.reset();
      commState = CommMacro.CommState.CLICK_PIGEON;
      warpState = CommMacro.WarpState.SETUP;
      refuelState = CommMacro.RefuelState.CLICK_MERCHANT;
      if (miningJson == null) {
         ChatUtils.send("An error occured while getting Mining Locations, reloading...");
         miningJson = JsonUtils.getMiningJson();
         CF4M.INSTANCE.moduleManager.toggle((Object)this);
      } else {
         if (this.pickSlot.getCurrent() == 0 || this.aotvSlot.getCurrent() == 0 || this.pigeonSlot.getCurrent() == 0) {
            ChatUtils.send("Configure your fucking Item Slots retard");
            CF4M.INSTANCE.moduleManager.toggle((Object)this);
         }

         if (this.ungrab.isEnabled()) {
            ChadUtils.ungrabMouse();
         }

      }
   }

   @Disable
   public void onDisable() {
      hugeTits = false;
      mithril = false;
      KeyBinding.func_74506_a();
      if (CF4M.INSTANCE.moduleManager.isEnabled("AutoMithril")) {
         CF4M.INSTANCE.moduleManager.toggle("AutoMithril");
      }

      if (CF4M.INSTANCE.moduleManager.isEnabled("IceGoblinSlayer")) {
         CF4M.INSTANCE.moduleManager.toggle("IceGoblinSlayer");
      }

      if (this.ungrab.isEnabled()) {
         ChadUtils.regrabMouse();
      }

   }

   @Event
   public void onTick(ClientTickEvent event) {
      int complSlot;
      if (mc.field_71462_r instanceof GuiChest && currentLocation != null && currentLocation.name.equals("REFUEL") && "Drill Anvil".equals(InventoryUtils.getInventoryName())) {
         switch(refuelState) {
         case CLICK_DRILL_IN:
            oldDrillSlot = InventoryUtils.getSlotForItem("Drill", Items.field_179562_cC);
            mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, oldDrillSlot, 0, 1, mc.field_71439_g);
            refuelState = CommMacro.RefuelState.CLICK_FUEL_IN;
            break;
         case CLICK_FUEL_IN:
            ItemStack aboveDrill = InventoryUtils.getStackInOpenContainerSlot(20);
            if (aboveDrill != null && aboveDrill.func_77952_i() == 5) {
               complSlot = InventoryUtils.getSlotForItem("Volta", Items.field_151144_bL);
               if (complSlot == -1) {
                  complSlot = InventoryUtils.getSlotForItem("Oil Barrel", Items.field_151144_bL);
               }

               if (complSlot == -1) {
                  ChatUtils.send("Bozo you don't have any fuel");
                  CF4M.INSTANCE.moduleManager.toggle((Object)this);
                  mc.field_71439_g.func_71053_j();
               } else {
                  mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, complSlot, 0, 1, mc.field_71439_g);
                  refuelState = CommMacro.RefuelState.REFUEL_DRILL;
               }
            }
            break;
         case REFUEL_DRILL:
            ItemStack hopper = InventoryUtils.getStackInOpenContainerSlot(22);
            if (hopper != null && hopper.func_77948_v()) {
               mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, mc.field_71439_g);
               refuelState = CommMacro.RefuelState.CLICK_DRILL_OUT;
            }
            break;
         case CLICK_DRILL_OUT:
            ItemStack oldDrill = InventoryUtils.getStackInOpenContainerSlot(29);
            if (oldDrill == null) {
               mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, 13, 0, 1, mc.field_71439_g);
               refuelState = CommMacro.RefuelState.DONE_REFUELING;
            }
            break;
         case DONE_REFUELING:
            mc.field_71439_g.func_71053_j();
            commState = CommMacro.CommState.CLICK_PIGEON;
            refuelState = CommMacro.RefuelState.CLICK_MERCHANT;
            recoverTimer.reset();
         }
      }

      if (recoverTimer.hasReached(5000L)) {
         SkyblockUtils.Location curLoc;
         curLoc = SkyblockUtils.getLocation();
         label162:
         switch(curLoc) {
         case SKYBLOCK:
            switch(commState) {
            case CLICK_PIGEON:
               if (pigeonTimer.hasReached(5000L)) {
                  SkyblockUtils.silentUse(0, this.pigeonSlot.getCurrent());
                  commState = CommMacro.CommState.IN_PIGEON;
                  pigeonTimer.reset();
               }
               break label162;
            case IN_PIGEON:
               if (pigeonTimer.hasReached(1000L) && mc.field_71462_r instanceof GuiChest) {
                  complSlot = this.getCompletedSlot();
                  if (complSlot != -1) {
                     InventoryUtils.clickOpenContainerSlot(complSlot);
                     pigeonTimer.reset();
                     return;
                  }

                  List<Integer> questSlots = this.getQuestSlots();
                  if (questSlots.isEmpty()) {
                     return;
                  }

                  Iterator var16 = questSlots.iterator();

                  while(var16.hasNext()) {
                     int i = (Integer)var16.next();
                     ItemStack is = InventoryUtils.getStackInOpenContainerSlot(i);
                     List<String> itemLore = this.getLore(is);
                     if (itemLore.size() > 4) {
                        String lore = (String)itemLore.get(4);
                        currentQuest = getQuest(lore);
                        if (currentQuest != null) {
                           currentLocation = getLocation(currentQuest);
                           if (currentLocation == null) {
                              continue;
                           }
                           break;
                        }
                     }
                  }

                  mc.field_71439_g.func_71053_j();
                  commState = CommMacro.CommState.WARP_FORGE;
               } else if (pigeonTimer.hasReached(1000L)) {
                  commState = CommMacro.CommState.CLICK_PIGEON;
               }
               break label162;
            case WARP_FORGE:
               if (currentLocation == null) {
                  ChatUtils.send("Couldn't determine Commission");
                  pigeonTimer.reset();
                  commState = CommMacro.CommState.CLICK_PIGEON;
               } else {
                  ChatUtils.send("Navigating to: " + currentLocation.name);
                  mc.field_71439_g.func_71165_d("/warp forge");
                  path = null;
                  commState = CommMacro.CommState.NAVIGATE;
               }
               break label162;
            case NAVIGATE:
               if (mc.field_71439_g.func_180425_c().equals(new BlockPos(1, 149, -68)) && path == null) {
                  path = new ArrayList(currentLocation.path);
                  warpTimer.reset();
                  oldPos = null;
                  KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
                  warpState = CommMacro.WarpState.SETUP;
               }
               break label162;
            case COMMIT:
               if (currentQuest != CommMacro.Quest.GOBLIN_SLAYER && currentQuest != CommMacro.Quest.ICE_WALKER_SLAYER) {
                  if (currentLocation != null && currentLocation.name.equals("REFUEL") && refuelState == CommMacro.RefuelState.CLICK_MERCHANT) {
                     List<Entity> possible = mc.field_71441_e.func_175674_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b(5.0D, 3.0D, 5.0D), (a) -> {
                        return a.func_70005_c_().contains("Jotraeline Greatforge");
                     });
                     if (!possible.isEmpty()) {
                        mc.field_71442_b.func_78768_b(mc.field_71439_g, (Entity)possible.get(0));
                        refuelState = CommMacro.RefuelState.CLICK_DRILL_IN;
                     }
                     break label162;
                  }

                  if (this.pickSlot.getCurrent() > 0 && this.pickSlot.getCurrent() <= 8) {
                     mc.field_71439_g.field_71071_by.field_70461_c = this.pickSlot.getCurrent() - 1;
                  }

                  hugeTits = currentQuest.questName.contains("Titanium");
                  mithril = currentQuest.questName.contains("Mithril");
                  CF4M.INSTANCE.moduleManager.toggle("AutoMithril");
                  commState = CommMacro.CommState.COMMITTING;
                  break label162;
               }

               CF4M.INSTANCE.moduleManager.toggle("IceGoblinSlayer");
               commState = CommMacro.CommState.COMMITTING;
               break label162;
            case COMMITTING:
               boolean warpOut = false;
               int rongo = this.maxPlayerRange.getCurrent();
               if (currentQuest != CommMacro.Quest.GOBLIN_SLAYER && currentQuest != CommMacro.Quest.ICE_WALKER_SLAYER) {
                  if (boostTimer.hasReached(125000L)) {
                     mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
                     boostTimer.reset();
                  }

                  if (rongo != 0) {
                     String warpName = "";
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
                        } else if (e instanceof EntityOtherPlayerMP && !e.func_70005_c_().equals("Goblin ") && !e.func_70005_c_().contains("Treasuer Hunter") && !e.func_70005_c_().contains("Crystal Sentry")) {
                           String formatted = e.func_145748_c_().func_150254_d();
                           if (!StringUtils.func_76338_a(formatted).equals(formatted) && (!formatted.startsWith("§r") || formatted.startsWith("§r§"))) {
                              warpOut = true;
                              warpName = e.func_70005_c_();
                           }
                        }
                     }

                     if (warpOut) {
                        ChatUtils.send("Switching lobbies cause a nice person is near you: " + warpName);
                        mc.field_71439_g.func_71165_d("/warp home");
                        if (CF4M.INSTANCE.moduleManager.isEnabled("AutoMithril")) {
                           CF4M.INSTANCE.moduleManager.toggle("AutoMithril");
                        }

                        if (CF4M.INSTANCE.moduleManager.isEnabled("IceGoblinSlayer")) {
                           CF4M.INSTANCE.moduleManager.toggle("IceGoblinSlayer");
                        }

                        KeyBinding.func_74506_a();
                        recoverTimer.reset();
                        commState = CommMacro.CommState.CLICK_PIGEON;
                     }
                  }
               }
            default:
               break label162;
            }
         case ISLAND:
            ChatUtils.send("Detected player in Island, re-warping");
            mc.field_71439_g.func_71165_d("/warp forge");
            break;
         case HUB:
            ChatUtils.send("Detected player in Hub, re-warping");
            mc.field_71439_g.func_71165_d("/warp forge");
            break;
         case LIFT:
            ChatUtils.send("Detected player at Lift, re-warping");
            mc.field_71439_g.func_71165_d("/warp forge");
            break;
         case LOBBY:
            ChatUtils.send("Detected player in Lobby, re-warping");
            mc.field_71439_g.func_71165_d("/play skyblock");
            break;
         case LIMBO:
            ChatUtils.send("Detected player in Limbo, re-warping");
            mc.field_71439_g.func_71165_d("/l");
         }

         if (curLoc != SkyblockUtils.Location.SKYBLOCK) {
            if (CF4M.INSTANCE.moduleManager.isEnabled("AutoMithril")) {
               CF4M.INSTANCE.moduleManager.toggle("AutoMithril");
            }

            if (CF4M.INSTANCE.moduleManager.isEnabled("IceGoblinSlayer")) {
               CF4M.INSTANCE.moduleManager.toggle("IceGoblinSlayer");
            }

            commState = CommMacro.CommState.CLICK_PIGEON;
            KeyBinding.func_74506_a();
            recoverTimer.reset();
         }
      }

   }

   private int getCompletedSlot() {
      for(int i = 9; i < 18; ++i) {
         ItemStack is = InventoryUtils.getStackInOpenContainerSlot(i);
         if (is != null && SkyblockUtils.stripString(is.func_82833_r()).startsWith("Commission #")) {
            List<String> itemLore = this.getLore(is);
            if (itemLore.stream().anyMatch((v) -> {
               return v.toLowerCase().contains("completed");
            })) {
               return i;
            }
         }
      }

      return -1;
   }

   private List<Integer> getQuestSlots() {
      List<Integer> ret = new ArrayList();

      for(int i = 9; i < 18; ++i) {
         ItemStack is = InventoryUtils.getStackInOpenContainerSlot(i);
         if (is != null && SkyblockUtils.stripString(is.func_82833_r()).startsWith("Commission #")) {
            List<String> itemLore = this.getLore(is);
            if (itemLore.stream().noneMatch((v) -> {
               return v.toLowerCase().contains("completed");
            })) {
               ret.add(i);
            }
         }
      }

      return ret;
   }

   @Event
   public void onChatReceived(ClientChatReceivedEvent event) {
      String msg = event.message.func_150260_c();
      if (commState == CommMacro.CommState.COMMITTING) {
         if (msg.startsWith("Mining Speed Boost is now available!")) {
            mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm());
            boostTimer.reset();
         } else if (msg.contains("Commission Complete! Visit the King to claim your rewards!") && !msg.contains(":")) {
            if (CF4M.INSTANCE.moduleManager.isEnabled("AutoMithril")) {
               CF4M.INSTANCE.moduleManager.toggle("AutoMithril");
            }

            if (CF4M.INSTANCE.moduleManager.isEnabled("IceGoblinSlayer")) {
               CF4M.INSTANCE.moduleManager.toggle("IceGoblinSlayer");
            }

            commState = CommMacro.CommState.CLICK_PIGEON;
         } else if (msg.startsWith("Your") && msg.contains("is empty! Refuel it by talking to a Drill Mechanic!")) {
            if (CF4M.INSTANCE.moduleManager.isEnabled("AutoMithril")) {
               CF4M.INSTANCE.moduleManager.toggle("AutoMithril");
            }

            if (CF4M.INSTANCE.moduleManager.isEnabled("IceGoblinSlayer")) {
               CF4M.INSTANCE.moduleManager.toggle("IceGoblinSlayer");
            }

            currentLocation = (Location)miningJson.locations.get(miningJson.locations.size() - 1);
            commState = CommMacro.CommState.WARP_FORGE;
         }
      }

      if (msg.contains("You can't fast travel while in combat!")) {
         ChatUtils.send("Detected travel in combat, evacuating");
         mc.field_71439_g.func_71165_d("/l");
         recoverTimer.reset();
         commState = CommMacro.CommState.CLICK_PIGEON;
      }

   }

   @Event
   public void onRenderTick(Render3DEvent event) {
      if (commState == CommMacro.CommState.NAVIGATE && path != null) {
         switch(warpState) {
         case SETUP:
            if (path.size() > 0) {
               if (warpTimer.hasReached((long)this.warpTime.getCurrent()) && !mc.field_71439_g.func_180425_c().equals(oldPos)) {
                  PathPoint a = (PathPoint)path.get(0);
                  path.remove(0);
                  RotationUtils.setup(RotationUtils.getRotation(new Vec3(a.x, a.y, a.z)), (long)this.lookTime.getCurrent());
                  oldPos = mc.field_71439_g.func_180425_c();
                  warpState = CommMacro.WarpState.LOOK;
               } else if (warpTimer.hasReached(2500L)) {
                  ChatUtils.send("Got stuck while tp'ing, re-navigating");
                  mc.field_71439_g.func_71165_d("/l");
                  recoverTimer.reset();
                  warpTimer.reset();
                  commState = CommMacro.CommState.CLICK_PIGEON;
               }
            } else {
               KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
               commState = CommMacro.CommState.COMMIT;
            }
            break;
         case LOOK:
            if (System.currentTimeMillis() <= RotationUtils.endTime) {
               RotationUtils.update();
            } else {
               RotationUtils.update();
               warpTimer.reset();
               warpState = CommMacro.WarpState.WARP;
            }
            break;
         case WARP:
            if (warpTimer.hasReached((long)this.warpTime.getCurrent())) {
               SkyblockUtils.silentUse(0, this.aotvSlot.getCurrent());
               warpTimer.reset();
               warpState = CommMacro.WarpState.SETUP;
            }
         }
      }

   }

   @Event
   public void onWorldUnload(WorldUnloadEvent event) {
   }

   public List<String> getLore(ItemStack is) {
      List<String> lore = new ArrayList();
      if (is != null && is.func_77942_o()) {
         NBTTagCompound nbt = is.func_77978_p();
         if (nbt.func_74775_l("display") != null) {
            NBTTagCompound display = nbt.func_74775_l("display");
            if (display.func_150295_c("Lore", 8) != null) {
               NBTTagList nbtLore = display.func_150295_c("Lore", 8);

               for(int i = 0; i < nbtLore.func_74745_c(); ++i) {
                  lore.add(SkyblockUtils.stripString(nbtLore.func_179238_g(i).toString()).replaceAll("\"", ""));
               }
            }
         }

         return lore;
      } else {
         return lore;
      }
   }

   public static CommMacro.Quest getQuest(String lore) {
      CommMacro.Quest[] var1 = CommMacro.Quest.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         CommMacro.Quest q = var1[var3];
         if (q.questName.equalsIgnoreCase(lore)) {
            return q;
         }
      }

      return null;
   }

   public static Location getLocation(CommMacro.Quest quest) {
      switch(quest) {
      case MITHRIL_MINER:
      case TITANIUM_MINER:
         List<Location> sub = miningJson.locations.subList(0, miningJson.locations.size() - 5);
         if (sub.size() > 0) {
            return (Location)sub.get(RandomUtil.randBetween(0, sub.size() - 1));
         }
         break;
      default:
         List<Location> possible = new ArrayList();
         Iterator var3 = miningJson.locations.iterator();

         while(var3.hasNext()) {
            Location loc = (Location)var3.next();
            if (quest.questName.toLowerCase().contains(loc.name.toLowerCase())) {
               possible.add(loc);
            } else {
               String name = null;
               if (loc.name.contains("COMMISSION")) {
                  name = org.apache.commons.lang3.StringUtils.substringBefore(loc.name, " COMMISSION");
               } else if (loc.name.contains(" (")) {
                  name = org.apache.commons.lang3.StringUtils.substringBefore(loc.name, " (");
               }

               if (name != null && quest.questName.toLowerCase().contains(name.toLowerCase())) {
                  possible.add(loc);
               }
            }
         }

         if (possible.size() > 0) {
            return (Location)possible.get(RandomUtil.randBetween(0, possible.size() - 1));
         }
      }

      return null;
   }

   static {
      commState = CommMacro.CommState.CLICK_PIGEON;
      warpState = CommMacro.WarpState.SETUP;
      refuelState = CommMacro.RefuelState.CLICK_MERCHANT;
   }

   private static enum Quest {
      MITHRIL_MINER("Mithril Miner"),
      TITANIUM_MINER("Titanium Miner"),
      UPPER_MINES_MITHRIL("Upper Mines Mithril"),
      ROYAL_MINES_MITHRIL("Royal Mines Mithril"),
      LAVA_SPRINGS_MITHRIL("Lava Springs Mithril"),
      CLIFFSIDE_VEINS_MITHRIL("Cliffside Veins Mithril"),
      RAMPARTS_QUARRY_MITHRIL("Rampart's Quarry Mithril"),
      UPPER_MINES_TITANIUM("Upper Mines Titanium"),
      ROYAL_MINES_TITANIUM("Royal Mines Titanium"),
      LAVA_SPRINGS_TITANIUM("Lava Springs Titanium"),
      CLIFFSIDE_VEINS_TITANIUM("Cliffside Veins Titanium"),
      RAMPARTS_QUARRY_TITANIUM("Rampart's Quarry Titanium"),
      GOBLIN_SLAYER("Goblin Slayer"),
      ICE_WALKER_SLAYER("Ice Walker Slayer");

      String questName;

      private Quest(String questName) {
         this.questName = questName;
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

   private static enum CommState {
      CLICK_PIGEON,
      IN_PIGEON,
      WARP_FORGE,
      NAVIGATE,
      COMMIT,
      COMMITTING;
   }
}

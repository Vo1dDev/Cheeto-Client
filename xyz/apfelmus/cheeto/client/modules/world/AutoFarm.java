package xyz.apfelmus.cheeto.client.modules.world;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientChatReceivedEvent;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.settings.ModeSetting;
import xyz.apfelmus.cheeto.client.settings.StringSetting;
import xyz.apfelmus.cheeto.client.utils.client.ChadUtils;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.math.RandomUtil;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "AutoFarm",
   category = Category.WORLD
)
public class AutoFarm {
   @Setting(
      name = "HoeSlot"
   )
   private IntegerSetting hoeSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "FailSafeDelay"
   )
   private IntegerSetting failSafeDelay = new IntegerSetting(5000, 0, 10000);
   @Setting(
      name = "CPUSaver"
   )
   private BooleanSetting cpuSaver = new BooleanSetting(true);
   @Setting(
      name = "AutoTab"
   )
   private BooleanSetting autoTab = new BooleanSetting(true);
   @Setting(
      name = "SoundAlerts"
   )
   private BooleanSetting soundAlerts = new BooleanSetting(true);
   @Setting(
      name = "WebhookUpdates"
   )
   private BooleanSetting webhookUpdates = new BooleanSetting(false);
   @Setting(
      name = "WebhookURL"
   )
   private StringSetting webhookUrl = new StringSetting("");
   @Setting(
      name = "Direction"
   )
   private ModeSetting direction = new ModeSetting("NORTH", new ArrayList(Arrays.asList("NORTH", "EAST", "SOUTH", "WEST")));
   private Minecraft mc = Minecraft.func_71410_x();
   private int stuckTicks = 0;
   private BlockPos oldPos;
   private BlockPos curPos;
   private boolean invFull = false;
   private List<ItemStack> oldInv;
   private int oldInvCount = 0;
   private final int GREEN = 3066993;
   private final int ORANGE = 15439360;
   private final int RED = 15158332;
   private final int BLUE = 1689596;
   private boolean banned;
   private AutoFarm.FarmingState farmingState;
   private AutoFarm.FarmingDirection farmingDirection;
   private AutoFarm.AlertState alertState;
   private AutoFarm.SameInvState sameInvState;
   private AutoFarm.IsRebootState isRebootState;
   private TimeHelper alertTimer = new TimeHelper();
   private TimeHelper sameInvTimer = new TimeHelper();
   private TimeHelper recoverTimer = new TimeHelper();
   private Map<SoundCategory, Float> oldSounds = new HashMap();
   private String recoverStr = " ";
   private boolean recoverBool = false;
   private boolean islandReboot;
   private List<String> msgs = new ArrayList(Arrays.asList("hey?", "wtf?? why am i here", "What is this place!", "Hello?", "helpp where am i?"));

   @Enable
   public void onEnable() {
      this.farmingState = AutoFarm.FarmingState.START_FARMING;
      this.farmingDirection = AutoFarm.FarmingDirection.LEFT;
      this.alertState = AutoFarm.AlertState.CHILLING;
      this.sameInvState = AutoFarm.SameInvState.CHILLING;
      this.isRebootState = AutoFarm.IsRebootState.ISLAND;
      this.islandReboot = false;
      this.banned = false;
      if (this.autoTab.isEnabled()) {
         ChadUtils.ungrabMouse();
      }

      if (this.cpuSaver.isEnabled()) {
         ChadUtils.improveCpuUsage();
      }

   }

   @Disable
   public void onDisable() {
      KeyBinding.func_74506_a();
      if (this.autoTab.isEnabled()) {
         ChadUtils.regrabMouse();
      }

      if (this.cpuSaver.isEnabled()) {
         ChadUtils.revertCpuUsage();
      }

      if (this.soundAlerts.isEnabled() && this.alertState != AutoFarm.AlertState.CHILLING) {
         SoundCategory[] var1 = SoundCategory.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            SoundCategory category = var1[var3];
            this.mc.field_71474_y.func_151439_a(category, (Float)this.oldSounds.get(category));
         }

         this.alertState = AutoFarm.AlertState.CHILLING;
      }

   }

   @Event
   public void onTick(ClientTickEvent event) {
      label224:
      switch(this.farmingState) {
      case START_FARMING:
         this.oldInv = null;
         Rotation rot = new Rotation(0.0F, 3.0F);
         String var12 = this.direction.getCurrent();
         byte var15 = -1;
         switch(var12.hashCode()) {
         case 2120701:
            if (var12.equals("EAST")) {
               var15 = 2;
            }
            break;
         case 2660783:
            if (var12.equals("WEST")) {
               var15 = 0;
            }
            break;
         case 74469605:
            if (var12.equals("NORTH")) {
               var15 = 1;
            }
         }

         switch(var15) {
         case 0:
            rot.setYaw(90.0F);
            break;
         case 1:
            rot.setYaw(180.0F);
            break;
         case 2:
            rot.setYaw(-90.0F);
         }

         RotationUtils.setup(rot, 1000L);
         this.farmingState = AutoFarm.FarmingState.SET_ANGLES;
         break;
      case PRESS_KEYS:
         if (this.hoeSlot.getCurrent() > 0 && this.hoeSlot.getCurrent() <= 8) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = this.hoeSlot.getCurrent() - 1;
         }

         this.pressKeys();
         this.farmingState = AutoFarm.FarmingState.FARMING;
         break;
      case FARMING:
         if (!this.banned && this.mc.field_71462_r instanceof GuiDisconnected) {
            GuiDisconnected gd = (GuiDisconnected)this.mc.field_71462_r;
            IChatComponent message = (IChatComponent)ObfuscationReflectionHelper.getPrivateValue(GuiDisconnected.class, gd, new String[]{"message", "field_146304_f"});
            StringBuilder reason = new StringBuilder();
            Iterator var6 = message.func_150253_a().iterator();

            while(var6.hasNext()) {
               IChatComponent cc = (IChatComponent)var6.next();
               reason.append(cc.func_150260_c());
            }

            String re = reason.toString();
            re = re.replace("\r", "\\r").replace("\n", "\\n");
            if (re.contains("banned")) {
               this.banned = true;
               this.sendWebhook("BEAMED", "You got beamed shitter!\\r\\n" + re, 15158332, true);
            }
         }

         if (this.recoverTimer.hasReached(2000L)) {
            switch(SkyblockUtils.getLocation()) {
            case NONE:
               KeyBinding.func_74506_a();
               break label224;
            case ISLAND:
               if (this.recoverBool) {
                  ChatUtils.send("Recovered! Starting to farm again!");
                  this.sendWebhook("RECOVERED", "Recovered!\\r\\nStarting to farm again!", 3066993, false);
                  this.recoverBool = false;
               }

               BlockPos standing = new BlockPos(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u - 1.0D, this.mc.field_71439_g.field_70161_v);
               IBlockState standBs = this.mc.field_71441_e.func_180495_p(standing);
               int[] mods = this.getModifiers(this.direction.getCurrent());
               BlockPos sideBlock;
               if (this.farmingDirection == AutoFarm.FarmingDirection.LEFT) {
                  sideBlock = new BlockPos(this.mc.field_71439_g.field_70165_t + (double)mods[0], this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + (double)mods[1]);
               } else {
                  sideBlock = new BlockPos(this.mc.field_71439_g.field_70165_t + (double)(mods[0] * -1), this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + (double)(mods[1] * -1));
               }

               IBlockState sideBs = this.mc.field_71441_e.func_180495_p(sideBlock);
               if (sideBs != null && sideBs.func_177230_c() != Blocks.field_150350_a && standBs != null && standBs.func_177230_c() != Blocks.field_150350_a) {
                  this.farmingDirection = this.farmingDirection == AutoFarm.FarmingDirection.LEFT ? AutoFarm.FarmingDirection.RIGHT : AutoFarm.FarmingDirection.LEFT;
                  this.pressKeys();
               }

               if (standBs != null && standBs.func_177230_c() == Blocks.field_150357_h) {
                  this.sendWebhook("ADMIN CHECK", "Stopped Farming:\\r\\nBitch you are getting Admin checked - do something!", 15158332, true);
                  if (this.alertState == AutoFarm.AlertState.CHILLING) {
                     this.alertState = AutoFarm.AlertState.TURNUP;
                  }

                  (new Thread(() -> {
                     try {
                        Thread.sleep(1500L);
                        this.farmingState = AutoFarm.FarmingState.STOP_FARMING;
                        Thread.sleep(2500L);
                        this.mc.field_71439_g.func_71165_d((String)this.msgs.get(RandomUtil.randBetween(0, this.msgs.size() - 1)));
                     } catch (InterruptedException var2) {
                        var2.printStackTrace();
                     }

                  })).start();
               }

               ItemStack heldItem = this.mc.field_71439_g.func_70694_bm();
               if (heldItem != null && (heldItem.func_77973_b() == Items.field_151098_aY || heldItem.func_77973_b() == Items.field_151148_bJ)) {
                  this.sendWebhook("MAPTCHA", "Stopped Farming:\\r\\nShitter you got a Maptcha, you should probably solve it!", 15158332, true);
                  if (this.alertState == AutoFarm.AlertState.CHILLING) {
                     this.alertState = AutoFarm.AlertState.TURNUP;
                  }

                  this.farmingState = AutoFarm.FarmingState.STOP_FARMING;
               }

               if (++this.stuckTicks >= 100) {
                  this.curPos = this.mc.field_71439_g.func_180425_c();
                  if (this.oldPos != null && Math.sqrt(this.curPos.func_177951_i(this.oldPos)) <= 2.0D && !this.invFull) {
                     this.sendWebhook("I AM STUCK", "Oh no - I'm stuck, Step Bro come help me! >_<", 15158332, true);
                     if (this.alertState == AutoFarm.AlertState.CHILLING) {
                        this.alertState = AutoFarm.AlertState.TURNUP;
                     }
                  }

                  this.oldPos = this.curPos;
                  this.stuckTicks = 0;
               }

               switch(this.sameInvState) {
               case CHILLING:
                  List<ItemStack> inv = InventoryUtils.getInventoryStacks();
                  if (inv.equals(this.oldInv)) {
                     if (++this.oldInvCount >= 240) {
                        if (this.alertState == AutoFarm.AlertState.CHILLING) {
                           this.alertState = AutoFarm.AlertState.TURNUP;
                        }

                        this.sameInvState = AutoFarm.SameInvState.UNPRESS;
                        this.sameInvTimer.reset();
                        this.oldInvCount = 0;
                     }
                  } else {
                     this.oldInv = InventoryUtils.getInventoryStacks();
                     this.oldInvCount = 0;
                  }
                  break;
               case UNPRESS:
                  KeyBinding.func_74506_a();
                  this.sameInvState = AutoFarm.SameInvState.PRESS;
                  this.sameInvTimer.reset();
                  break;
               case PRESS:
                  if (this.sameInvTimer.hasReached(30000L)) {
                     this.pressKeys();
                     this.sameInvState = AutoFarm.SameInvState.CHILLING;
                  }
               }

               if (this.mc.field_71439_g.field_71071_by.func_70447_i() == -1 && !this.invFull) {
                  this.sendWebhook("FULL INVENTORY", "Stopped Farming:\\r\\nInventory Full!", 15439360, false);
                  if (this.alertState == AutoFarm.AlertState.CHILLING) {
                     this.alertState = AutoFarm.AlertState.TURNUP;
                  }

                  this.invFull = true;
                  KeyBinding.func_74506_a();
               } else if (this.mc.field_71439_g.field_71071_by.func_70447_i() != -1 && this.invFull) {
                  this.sendWebhook("INVENTORY NOT FULL", "Continued Farming:\\r\\nInventory not full anymore!", 3066993, false);
                  this.invFull = false;
                  this.pressKeys();
               }
               break label224;
            case SKYBLOCK:
               ChatUtils.send("Player isn't in Island!");
               ChatUtils.send("Re-warping in " + this.failSafeDelay.getCurrent() + "ms");
               this.sendWebhook("NOT IN ISLAND", "Player isn't in Island!\\r\\nRecovering...", 15439360, false);
               this.recoverStr = "/warp home";
               this.recoverBool = true;
               this.farmingState = AutoFarm.FarmingState.RECOVER;
               this.recoverTimer.reset();
               KeyBinding.func_74506_a();
               break label224;
            case LOBBY:
               ChatUtils.send("Player isn't in Skyblock!");
               ChatUtils.send("Joining Skyblock in " + this.failSafeDelay.getCurrent() + "ms");
               this.sendWebhook("NOT IN SKYBLOCK", "Player isn't in Island!\\r\\nRecovering...", 15439360, false);
               this.recoverStr = "/play skyblock";
               this.recoverBool = true;
               this.farmingState = AutoFarm.FarmingState.RECOVER;
               this.recoverTimer.reset();
               KeyBinding.func_74506_a();
               break label224;
            case LIMBO:
               ChatUtils.send("Player is in Limbo!");
               ChatUtils.send("Escaping in " + this.failSafeDelay.getCurrent() + "ms");
               this.sendWebhook("PLAYER IN LIMBO", "Player isn't in Island!\\r\\nRecovering...", 15439360, false);
               this.recoverStr = "/l";
               this.recoverBool = true;
               this.farmingState = AutoFarm.FarmingState.RECOVER;
               this.recoverTimer.reset();
               KeyBinding.func_74506_a();
            }
         }
         break;
      case RECOVER:
         if (this.islandReboot) {
            switch(this.isRebootState) {
            case ISLAND:
               if (this.recoverTimer.hasReached((long)this.failSafeDelay.getCurrent())) {
                  this.mc.field_71439_g.func_71165_d(this.recoverStr);
                  this.recoverStr = "/warp home";
                  this.isRebootState = AutoFarm.IsRebootState.HUB;
                  this.recoverTimer.reset();
               }
               break label224;
            case HUB:
               if (this.recoverTimer.hasReached(15000L)) {
                  this.mc.field_71439_g.func_71165_d(this.recoverStr);
                  this.farmingState = AutoFarm.FarmingState.FARMING;
                  this.recoverTimer.reset();
                  this.islandReboot = false;
               }
            }
         } else if (this.recoverTimer.hasReached((long)this.failSafeDelay.getCurrent()) && SkyblockUtils.getLocation() != SkyblockUtils.Location.NONE) {
            this.mc.field_71439_g.func_71165_d(this.recoverStr);
            this.farmingState = AutoFarm.FarmingState.FARMING;
            this.recoverTimer.reset();
         }
         break;
      case STOP_FARMING:
         if (this.autoTab.isEnabled()) {
            ChadUtils.regrabMouse();
         }

         if (this.cpuSaver.isEnabled()) {
            ChadUtils.revertCpuUsage();
         }

         KeyBinding.func_74506_a();
      }

      if (this.soundAlerts.isEnabled()) {
         SoundCategory[] var10;
         int var13;
         SoundCategory category;
         int var19;
         switch(this.alertState) {
         case TURNUP:
            var10 = SoundCategory.values();
            var13 = var10.length;

            for(var19 = 0; var19 < var13; ++var19) {
               category = var10[var19];
               this.oldSounds.put(category, this.mc.field_71474_y.func_151438_a(category));
               this.mc.field_71474_y.func_151439_a(category, 0.5F);
            }

            this.alertState = AutoFarm.AlertState.PLAY;
            this.alertTimer.reset();
            break;
         case PLAY:
            if (this.alertTimer.hasReached(100L)) {
               this.mc.field_71439_g.func_85030_a("mob.enderdragon.growl", 1.0F, 1.0F);
               this.alertState = AutoFarm.AlertState.TURNDOWN;
               this.alertTimer.reset();
            }
            break;
         case TURNDOWN:
            if (this.alertTimer.hasReached(2500L)) {
               var10 = SoundCategory.values();
               var13 = var10.length;

               for(var19 = 0; var19 < var13; ++var19) {
                  category = var10[var19];
                  this.mc.field_71474_y.func_151439_a(category, (Float)this.oldSounds.get(category));
               }

               this.alertState = AutoFarm.AlertState.CHILLING;
            }
         }
      }

   }

   @Event
   public void onRenderWorld(Render3DEvent event) {
      if (this.farmingState == AutoFarm.FarmingState.SET_ANGLES) {
         if (System.currentTimeMillis() <= RotationUtils.endTime) {
            RotationUtils.update();
         } else {
            RotationUtils.update();
            this.farmingDirection = this.determineDirection();
            this.farmingState = AutoFarm.FarmingState.PRESS_KEYS;
         }
      }

   }

   @Event
   public void onChat(ClientChatReceivedEvent event) {
      if (event.type != 2) {
         if (this.farmingState == AutoFarm.FarmingState.FARMING) {
            String msg = StringUtils.func_76338_a(event.message.func_150260_c());
            if (msg.startsWith("From") || msg.matches("\\[SkyBlock] .*? is visiting Your Island!.*") || msg.contains("has invited you to join their party!")) {
               this.alertState = AutoFarm.AlertState.TURNUP;
               if (msg.startsWith("From")) {
                  this.sendWebhook("MESSAGE", "Received Message:\\r\\n" + msg, 1689596, true);
               } else if (msg.matches("\\[SkyBlock] .*? is visiting Your Island!.*")) {
                  Pattern pat = Pattern.compile("\\[SkyBlock] (.*?) is visiting Your Island!.*");
                  Matcher mat = pat.matcher(msg);
                  if (mat.matches()) {
                     this.sendWebhook("GETTING VISITED", "Player is visiting you:\\r\\n" + mat.group(1), 1689596, true);
                  }
               } else if (msg.contains("has invited you to join their party!")) {
                  String[] split = msg.split("\n");
                  if (split.length == 4) {
                     String mm = split[1];
                     Pattern pat = Pattern.compile("(.*?) has invited you to join their party!.*");
                     Matcher mat = pat.matcher(mm);
                     if (mat.matches()) {
                        this.sendWebhook("PARTY REQUEST", "Player partied you:\\r\\n" + mat.group(1), 1689596, true);
                     }
                  }
               }
            }

            if (msg.startsWith("[Important] This server will restart soon:")) {
               this.alertState = AutoFarm.AlertState.TURNUP;
               this.mc.field_71439_g.func_71165_d("/setspawn");
               this.islandReboot = true;
               this.isRebootState = AutoFarm.IsRebootState.ISLAND;
               ChatUtils.send("Server is rebooting!");
               ChatUtils.send("Escaping in " + this.failSafeDelay.getCurrent() + "ms");
               this.recoverStr = "/warp hub";
               this.farmingState = AutoFarm.FarmingState.RECOVER;
               this.recoverTimer.reset();
               KeyBinding.func_74506_a();
            }
         }

      }
   }

   private AutoFarm.FarmingDirection determineDirection() {
      int[] mod = this.getModifiers(this.direction.getCurrent());

      int i;
      BlockPos further;
      BlockPos down;
      IBlockState upBs;
      IBlockState downBs;
      for(i = 0; i < 160; ++i) {
         further = new BlockPos(this.mc.field_71439_g.field_70165_t + (double)(mod[0] * (i + 1)), this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + (double)(mod[1] * (i + 1)));
         down = new BlockPos(this.mc.field_71439_g.field_70165_t + (double)(mod[0] * i), this.mc.field_71439_g.field_70163_u - 1.0D, this.mc.field_71439_g.field_70161_v + (double)(mod[1] * i));
         upBs = this.mc.field_71441_e.func_180495_p(further);
         downBs = this.mc.field_71441_e.func_180495_p(down);
         if (downBs != null && downBs.func_177230_c() == Blocks.field_150350_a && upBs != null && upBs.func_177230_c() != Blocks.field_150350_a) {
            return AutoFarm.FarmingDirection.LEFT;
         }
      }

      for(i = 0; i < 160; ++i) {
         further = new BlockPos(this.mc.field_71439_g.field_70165_t - (double)(mod[0] * (i + 1)), this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v - (double)(mod[1] * (i + 1)));
         down = new BlockPos(this.mc.field_71439_g.field_70165_t - (double)(mod[0] * i), this.mc.field_71439_g.field_70163_u - 1.0D, this.mc.field_71439_g.field_70161_v - (double)(mod[1] * i));
         upBs = this.mc.field_71441_e.func_180495_p(further);
         downBs = this.mc.field_71441_e.func_180495_p(down);
         if (downBs != null && downBs.func_177230_c() == Blocks.field_150350_a && upBs != null && upBs.func_177230_c() != Blocks.field_150350_a) {
            return AutoFarm.FarmingDirection.RIGHT;
         }
      }

      return AutoFarm.FarmingDirection.LEFT;
   }

   private int[] getModifiers(String direction) {
      int[] ret = new int[2];
      byte var4 = -1;
      switch(direction.hashCode()) {
      case 2120701:
         if (direction.equals("EAST")) {
            var4 = 3;
         }
         break;
      case 2660783:
         if (direction.equals("WEST")) {
            var4 = 1;
         }
         break;
      case 74469605:
         if (direction.equals("NORTH")) {
            var4 = 2;
         }
         break;
      case 79090093:
         if (direction.equals("SOUTH")) {
            var4 = 0;
         }
      }

      switch(var4) {
      case 0:
         ret[0] = 1;
         break;
      case 1:
         ret[1] = 1;
         break;
      case 2:
         ret[0] = -1;
         break;
      case 3:
         ret[1] = -1;
      }

      return ret;
   }

   private void pressKeys() {
      this.mc.field_71462_r = null;
      this.mc.field_71415_G = true;
      KeyBinding.func_74510_a(this.mc.field_71474_y.field_74351_w.func_151463_i(), true);
      KeyBinding.func_74510_a(this.mc.field_71474_y.field_74312_F.func_151463_i(), true);
      switch(this.farmingDirection) {
      case LEFT:
         KeyBinding.func_74510_a(this.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         KeyBinding.func_74510_a(this.mc.field_71474_y.field_74370_x.func_151463_i(), true);
         break;
      case RIGHT:
         KeyBinding.func_74510_a(this.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(this.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      }

   }

   private void sendWebhook(String title, String updateReason, int color, boolean ping) {
      if (this.webhookUpdates.isEnabled()) {
         try {
            HttpURLConnection con = (HttpURLConnection)(new URL(this.webhookUrl.getCurrent())).openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            Session session = this.mc.func_110432_I();
            String json = "{ \"content\": " + (ping ? "\"@everyone\"" : "null") + ", \"embeds\": [ { \"title\": \"" + title + "\", \"description\": \"**Account Info**\\nIGN: " + session.func_111285_a() + "\", \"color\": " + color + ", \"fields\": [ { \"name\": \"Update Reason:\", \"value\": \"" + updateReason + "\" } ], \"footer\": { \"text\": \"Made by Apfelsaft#0002\", \"icon_url\": \"https://visage.surgeplay.com/face/128/7c224caeaea249a49783053b9bcf4ed1\" } } ], \"username\": \"" + session.func_111285_a() + "\", \"avatar_url\": \"https://visage.surgeplay.com/face/128/" + session.func_148255_b() + "\" }";
            OutputStream output = con.getOutputStream();
            Throwable var9 = null;

            try {
               output.write(json.getBytes(StandardCharsets.UTF_8));
            } catch (Throwable var19) {
               var9 = var19;
               throw var19;
            } finally {
               if (output != null) {
                  if (var9 != null) {
                     try {
                        output.close();
                     } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                     }
                  } else {
                     output.close();
                  }
               }

            }

            int resp = con.getResponseCode();
            if (resp != 200 && resp != 204) {
               ChatUtils.send("Error while sending Webhook");
            } else {
               ChatUtils.send("Webhook sent successfully");
            }
         } catch (IOException var21) {
            var21.printStackTrace();
         }
      }

   }

   static enum IsRebootState {
      ISLAND,
      HUB;
   }

   static enum SameInvState {
      CHILLING,
      UNPRESS,
      PRESS;
   }

   static enum AlertState {
      CHILLING,
      TURNUP,
      PLAY,
      TURNDOWN;
   }

   static enum FarmingDirection {
      LEFT,
      RIGHT;
   }

   static enum FarmingState {
      START_FARMING,
      SET_ANGLES,
      PRESS_KEYS,
      FARMING,
      STOP_FARMING,
      RECOVER;
   }
}

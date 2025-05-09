package xyz.apfelmus.cheeto.client.modules.world;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
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
import xyz.apfelmus.cheeto.client.utils.client.ChadUtils;
import xyz.apfelmus.cheeto.client.utils.client.KeybindUtils;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.math.TimeHelper;
import xyz.apfelmus.cheeto.client.utils.skyblock.InventoryUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "AutoForaging",
   category = Category.WORLD
)
public class AutoForaging {
   @Setting(
      name = "SaplingSlot",
      description = "Slot for Jungle Saplings"
   )
   private IntegerSetting saplingSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "BoneMealSlot",
      description = "Slot for Ench Bonemeal"
   )
   private IntegerSetting boneMealSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "AxeSlot",
      description = "Slot for your Tree Cap"
   )
   private IntegerSetting axeSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "RodSlot",
      description = "Slot for Rod for Pet Swapping"
   )
   private IntegerSetting rodSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "Delay"
   )
   private IntegerSetting delay = new IntegerSetting(200, 0, 1000);
   @Setting(
      name = "Direction"
   )
   private ModeSetting direction = new ModeSetting("NORTH", new ArrayList(Arrays.asList("NORTH", "EAST", "SOUTH", "WEST")));
   @Setting(
      name = "Ungrab",
      description = "Automatically tabs out"
   )
   private BooleanSetting ungrab = new BooleanSetting(true);
   private Minecraft mc = Minecraft.func_71410_x();
   private TimeHelper yepTimer;
   private TimeHelper failSafeTimer;
   private AutoForaging.ForagingState foragingState;
   private int currentTree;
   private int treeWait;

   @Enable
   public void onEnable() {
      this.foragingState = AutoForaging.ForagingState.TREE;
      this.currentTree = 1;
      this.yepTimer = new TimeHelper();
      this.failSafeTimer = new TimeHelper();
      this.treeWait = this.delay.getCurrent();
      if (this.ungrab.isEnabled()) {
         ChadUtils.ungrabMouse();
      }

   }

   @Disable
   public void onDisable() {
      if (this.ungrab.isEnabled()) {
         ChadUtils.regrabMouse();
      }

   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (!SkyblockUtils.isInIsland()) {
         this.mc.field_71439_g.func_85030_a("random.orb", 1.0F, 1.0F);
      } else {
         int saplingCount = InventoryUtils.getAmountInHotbar("Jungle Sapling");
         int boneMealCount = InventoryUtils.getAmountInHotbar("Enchanted Bone Meal");
         if (saplingCount >= 5 && boneMealCount >= 2) {
            ItemStack heldItem = this.mc.field_71439_g.func_70694_bm();
            if (heldItem != null && (heldItem.func_77973_b() == Items.field_151098_aY || heldItem.func_77973_b() == Items.field_151148_bJ)) {
               this.mc.field_71439_g.func_85030_a("random.orb", 1.0F, 1.0F);
            } else {
               int xMod = 0;
               int zMod = 0;
               String var7 = this.direction.getCurrent();
               byte var8 = -1;
               switch(var7.hashCode()) {
               case 2120701:
                  if (var7.equals("EAST")) {
                     var8 = 1;
                  }
                  break;
               case 2660783:
                  if (var7.equals("WEST")) {
                     var8 = 3;
                  }
                  break;
               case 74469605:
                  if (var7.equals("NORTH")) {
                     var8 = 0;
                  }
                  break;
               case 79090093:
                  if (var7.equals("SOUTH")) {
                     var8 = 2;
                  }
               }

               switch(var8) {
               case 0:
                  zMod = -2;
                  break;
               case 1:
                  xMod = 3;
                  break;
               case 2:
                  xMod = 1;
                  zMod = 3;
                  break;
               case 3:
                  xMod = -2;
                  zMod = 1;
               }

               Vec3 posVec = this.mc.field_71439_g.func_174791_d();
               Vec3 middle = (new Vec3((double)MathHelper.func_76128_c(posVec.field_72450_a), (double)MathHelper.func_76128_c(posVec.field_72448_b), (double)MathHelper.func_76128_c(posVec.field_72449_c))).func_178787_e(new Vec3((double)xMod, 1.0D, (double)zMod));
               Rotation rot = RotationUtils.getRotation(middle);
               switch(this.foragingState) {
               case TREE:
                  if (this.yepTimer.hasReached((long)this.treeWait)) {
                     this.swapSlot(this.saplingSlot.getCurrent());
                     if (this.currentTree == 1) {
                        rot.addYaw(1.5F);
                        rot.addPitch(-0.5F);
                     } else if (this.currentTree == 2) {
                        rot.addYaw(-1.0F);
                        rot.addPitch(-1.0F);
                     } else if (this.currentTree == 3) {
                        rot.addYaw(-1.5F);
                        rot.addPitch(0.5F);
                     } else if (this.currentTree == 4) {
                        rot.addYaw(0.5F);
                        rot.addPitch(0.5F);
                     }

                     RotationUtils.setup(rot, (long)this.delay.getCurrent() / 2L);
                     this.foragingState = AutoForaging.ForagingState.LOOKING;
                  }
                  break;
               case BONEMEAL:
                  if (this.yepTimer.hasReached((long)this.delay.getCurrent())) {
                     this.swapSlot(this.boneMealSlot.getCurrent());
                     KeybindUtils.rightClick();
                     this.yepTimer.reset();
                     this.foragingState = AutoForaging.ForagingState.RODSWAP;
                  }
                  break;
               case RODSWAP:
                  if (this.yepTimer.hasReached((long)this.delay.getCurrent())) {
                     SkyblockUtils.silentUse(this.axeSlot.getCurrent(), this.rodSlot.getCurrent());
                     KeybindUtils.rightClick();
                     this.yepTimer.reset();
                     this.failSafeTimer.reset();
                     this.foragingState = AutoForaging.ForagingState.HARVEST;
                  }
                  break;
               case HARVEST:
                  if (this.failSafeTimer.hasReached(2000L)) {
                     this.foragingState = AutoForaging.ForagingState.TREE;
                     this.currentTree = 1;
                  }

                  if (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72313_a == MovingObjectType.BLOCK) {
                     BlockPos bp = this.mc.field_71476_x.func_178782_a();
                     IBlockState ibs = this.mc.field_71441_e.func_180495_p(bp);
                     if (ibs != null && ibs.func_177230_c() == Blocks.field_150364_r && this.yepTimer.hasReached((long)this.delay.getCurrent())) {
                        KeybindUtils.leftClick();
                        this.yepTimer.reset();
                        this.foragingState = AutoForaging.ForagingState.TREE;
                        this.currentTree = 1;
                        this.treeWait = 500;
                     }
                  }
               }

            }
         }
      }
   }

   @Event
   public void onRenderWorld(Render3DEvent event) {
      if (this.foragingState == AutoForaging.ForagingState.LOOKING) {
         if (System.currentTimeMillis() <= RotationUtils.endTime) {
            RotationUtils.update();
            this.yepTimer.reset();
         } else if (!this.yepTimer.hasReached((long)(this.delay.getCurrent() / 2))) {
            RotationUtils.update();
         } else {
            KeybindUtils.rightClick();
            this.yepTimer.reset();
            if (this.currentTree++ < 4) {
               this.foragingState = AutoForaging.ForagingState.TREE;
               this.treeWait = this.delay.getCurrent();
            } else {
               this.foragingState = AutoForaging.ForagingState.BONEMEAL;
            }
         }
      }

   }

   @Event
   public void onChat(ClientChatReceivedEvent event) {
      if (event.type != 2) {
         String msg = StringUtils.func_76338_a(event.message.func_150260_c());
         if (msg.startsWith("From") || msg.matches("\\[SkyBlock] .*? is visiting Your Island!.*") || msg.contains("has invited you to join their party!")) {
            this.mc.field_71439_g.func_85030_a("mob.enderdragon.growl", 1.0F, 1.0F);
         }

      }
   }

   private void swapSlot(int slot) {
      if (slot > 0 && slot <= 8) {
         this.mc.field_71439_g.field_71071_by.field_70461_c = slot - 1;
      }

   }

   static enum ForagingState {
      TREE,
      BONEMEAL,
      RODSWAP,
      HARVEST,
      LOOKING;
   }
}

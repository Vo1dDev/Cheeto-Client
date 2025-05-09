package xyz.apfelmus.cheeto.client.modules.world;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.Setting;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.events.ClientChatReceivedEvent;
import xyz.apfelmus.cheeto.client.events.ClientTickEvent;
import xyz.apfelmus.cheeto.client.events.PlayerInteractEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.FloatSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.utils.client.RotationUtils;
import xyz.apfelmus.cheeto.client.utils.render.Render3DUtils;
import xyz.apfelmus.cheeto.client.utils.skyblock.SkyblockUtils;

@Module(
   name = "SecretAura",
   category = Category.WORLD
)
public class SecretAura implements Runnable {
   @Setting(
      name = "ScanRange"
   )
   private IntegerSetting scanRange = new IntegerSetting(7, 0, 8);
   @Setting(
      name = "ClickRange"
   )
   private FloatSetting clickRange = new FloatSetting(5.0F, 0.0F, 8.0F);
   @Setting(
      name = "ClickSlot"
   )
   private IntegerSetting clickSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "Chests"
   )
   private BooleanSetting chests = new BooleanSetting(true);
   @Setting(
      name = "ChestClose"
   )
   private BooleanSetting chestClose = new BooleanSetting(true);
   @Setting(
      name = "Levers"
   )
   private BooleanSetting levers = new BooleanSetting(true);
   @Setting(
      name = "Essences"
   )
   private BooleanSetting essences = new BooleanSetting(true);
   @Setting(
      name = "StonklessStonk"
   )
   private BooleanSetting stonklessStonk = new BooleanSetting(true);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static List<BlockPos> clicked = new ArrayList();
   private int delayMs = 500;
   private Thread thread;
   private List<BlockPos> blocksNear = new ArrayList();
   private boolean inChest = false;
   private BlockPos selected;
   private static BlockPos lastPos;

   @Enable
   public void onEnable() {
      this.blocksNear.clear();
      clicked.clear();
      this.inChest = false;
      this.selected = null;
      lastPos = null;
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if ((this.thread == null || !this.thread.isAlive()) && mc.field_71441_e != null && mc.field_71439_g != null && SkyblockUtils.isInDungeon()) {
         (this.thread = new Thread(this)).setDaemon(false);
         this.thread.setPriority(1);
         this.thread.start();
      }

      if (mc.field_71441_e != null && mc.field_71439_g != null && this.thread != null) {
         if (!this.stonklessStonk.isEnabled()) {
            if (!this.inChest) {
               Vec3 eyes = mc.field_71439_g.func_174824_e(1.0F);
               Iterator var3 = (new ArrayList(this.blocksNear)).iterator();

               while(var3.hasNext()) {
                  BlockPos bp = (BlockPos)var3.next();
                  if (Math.sqrt(bp.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) < (double)this.clickRange.getCurrent()) {
                     IBlockState bs = mc.field_71441_e.func_180495_p(bp);
                     if (bs != null) {
                        if (bs.func_177230_c() == Blocks.field_150486_ae) {
                           this.inChest = true;
                        }

                        this.handleClick(bp);
                     } else {
                        this.blocksNear.remove(bp);
                        clicked.add(bp);
                     }
                     break;
                  }
               }
            }
         } else {
            clicked.clear();
         }

         if (SkyblockUtils.isInDungeon() && this.inChest && this.chestClose.isEnabled()) {
            if (mc.field_71462_r instanceof GuiChest) {
               mc.field_71439_g.func_71053_j();
               this.inChest = false;
            }
         } else {
            this.inChest = false;
         }
      }

   }

   @Event
   public void onRender(Render3DEvent event) {
      this.selected = null;
      Iterator var2 = (new ArrayList(this.blocksNear)).iterator();

      while(true) {
         while(var2.hasNext()) {
            BlockPos bp = (BlockPos)var2.next();
            if (this.selected == null && RotationUtils.lookingAt(bp, this.clickRange.getCurrent())) {
               this.selected = bp;
            } else {
               Render3DUtils.renderEspBox(bp, event.partialTicks, -1754827);
            }
         }

         if (this.selected != null) {
            Render3DUtils.renderEspBox(this.selected, event.partialTicks, -19712);
         }

         return;
      }
   }

   @Event
   public void onInteract(PlayerInteractEvent event) {
      if (this.stonklessStonk.isEnabled() && this.selected != null && !clicked.contains(this.selected)) {
         MovingObjectPosition omo = mc.field_71476_x;
         if (omo != null && omo.field_72313_a == MovingObjectType.BLOCK && omo.func_178782_a().equals(this.selected)) {
            return;
         }

         if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            clicked.add(this.selected);
            this.handleClick(this.selected);
            mc.field_71439_g.func_71038_i();
         }
      }

   }

   @Event
   public void onWorldLoad(WorldUnloadEvent event) {
      clicked.clear();
      this.inChest = false;
   }

   @Event
   public void onChat(ClientChatReceivedEvent event) {
      if (event.message.func_150260_c().contains("locked")) {
         clicked.clear();
         this.inChest = false;
      }

   }

   public void run() {
      while(!this.thread.isInterrupted()) {
         BlockPos curPos = mc.field_71439_g.func_180425_c();
         if (!CF4M.INSTANCE.moduleManager.isEnabled((Object)this)) {
            this.thread.interrupt();
         } else {
            if (SkyblockUtils.isInDungeon() && !curPos.equals(lastPos)) {
               lastPos = curPos;
               int radius = this.scanRange.getCurrent();
               int px = MathHelper.func_76128_c(mc.field_71439_g.field_70165_t);
               int py = MathHelper.func_76128_c(mc.field_71439_g.field_70163_u + 1.0D);
               int pz = MathHelper.func_76128_c(mc.field_71439_g.field_70161_v);
               Vec3 eyes = mc.field_71439_g.func_174824_e(1.0F);

               for(int x = px - radius; x < px + radius + 1; ++x) {
                  for(int y = py - radius; y < py + radius + 1; ++y) {
                     for(int z = pz - radius; z < pz + radius + 1; ++z) {
                        BlockPos xyz = new BlockPos(x, y, z);
                        IBlockState bs = mc.field_71441_e.func_180495_p(xyz);
                        Block block = bs.func_177230_c();
                        if (!clicked.contains(xyz) && !this.blocksNear.contains(xyz) && Math.sqrt(xyz.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) <= (double)this.scanRange.getCurrent()) {
                           if (this.chests.isEnabled() && block instanceof BlockChest) {
                              TileEntityChest te = (TileEntityChest)mc.field_71441_e.func_175625_s(xyz);
                              if (te.field_145989_m == 0.0F) {
                                 this.blocksNear.add(xyz);
                              }
                           }

                           if (this.levers.isEnabled() && block instanceof BlockLever) {
                              this.blocksNear.add(xyz);
                           }

                           if (this.essences.isEnabled() && block instanceof BlockSkull) {
                              TileEntitySkull te = (TileEntitySkull)mc.field_71441_e.func_175625_s(xyz);
                              GameProfile gp = te.func_152108_a();
                              if (gp != null) {
                                 BlockPos bp = te.func_174877_v();
                                 if (bp != null && gp.getId().toString().equals("26bb1a8d-7c66-31c6-82d5-a9c04c94fb02")) {
                                    this.blocksNear.add(xyz);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               List<BlockPos> blocksToRemove = new ArrayList();
               Iterator var18 = this.blocksNear.iterator();

               label79:
               while(true) {
                  BlockPos bp;
                  Block block;
                  do {
                     if (!var18.hasNext()) {
                        this.blocksNear.removeAll(blocksToRemove);
                        break label79;
                     }

                     bp = (BlockPos)var18.next();
                     IBlockState state = mc.field_71441_e.func_180495_p(bp);
                     block = null;
                     if (state != null) {
                        block = state.func_177230_c();
                     }
                  } while(!(Math.sqrt(bp.func_177957_d(eyes.field_72450_a, eyes.field_72448_b, eyes.field_72449_c)) > (double)this.scanRange.getCurrent()) && block != Blocks.field_150350_a);

                  blocksToRemove.add(bp);
               }
            }

            try {
               Thread.sleep((long)this.delayMs);
            } catch (InterruptedException var16) {
               var16.printStackTrace();
            }
         }
      }

      this.thread = null;
   }

   private void handleClick(BlockPos xyz) {
      if (this.clickSlot.getCurrent() != 0) {
         int holding = mc.field_71439_g.field_71071_by.field_70461_c;
         mc.field_71439_g.field_71071_by.field_70461_c = this.clickSlot.getCurrent();
         mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70448_g(), xyz, EnumFacing.func_176733_a((double)RotationUtils.getRotation(xyz).getYaw()), mc.field_71476_x.field_72307_f);
         mc.field_71439_g.field_71071_by.field_70461_c = holding;
      } else {
         mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70448_g(), xyz, EnumFacing.func_176733_a((double)RotationUtils.getRotation(xyz).getYaw()), mc.field_71476_x.field_72307_f);
      }

      if (!this.stonklessStonk.isEnabled()) {
         this.blocksNear.remove(xyz);
         clicked.add(xyz);
      }

   }
}

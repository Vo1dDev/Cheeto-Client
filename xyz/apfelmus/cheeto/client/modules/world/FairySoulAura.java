package xyz.apfelmus.cheeto.client.modules.world;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
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

@Module(
   name = "FairySoulAura",
   category = Category.WORLD
)
public class FairySoulAura {
   @Setting(
      name = "ClickRange"
   )
   private FloatSetting clickRange = new FloatSetting(2.75F, 0.0F, 5.0F);
   @Setting(
      name = "ClickSlot"
   )
   private IntegerSetting clickSlot = new IntegerSetting(0, 0, 8);
   @Setting(
      name = "ClickDelay"
   )
   private FloatSetting clickDelay = new FloatSetting(500.0F, 0.0F, 2500.0F);
   @Setting(
      name = "Stonkless"
   )
   private BooleanSetting stonkless = new BooleanSetting(true);
   private static Minecraft mc = Minecraft.func_71410_x();
   private static List<Entity> clicked = new ArrayList();
   private List<Entity> soulsNear = new ArrayList();
   private List<Entity> foundSouls = new ArrayList();
   private Entity selected;
   private Method syncCurrentPlayItem = null;
   private long lastClickTime;

   @Enable
   public void onEnable() {
      if (this.syncCurrentPlayItem == null) {
         try {
            this.syncCurrentPlayItem = PlayerControllerMP.class.getDeclaredMethod("syncCurrentPlayItem");
         } catch (NoSuchMethodException var4) {
            try {
               this.syncCurrentPlayItem = PlayerControllerMP.class.getDeclaredMethod("func_78750_j");
            } catch (NoSuchMethodException var3) {
            }
         }
      }

      if (this.syncCurrentPlayItem != null) {
         this.syncCurrentPlayItem.setAccessible(true);
      }

      this.soulsNear.clear();
      this.foundSouls.clear();
      clicked.clear();
      this.selected = null;
   }

   @Event
   public void onTick(ClientTickEvent event) {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         Iterator var2 = mc.field_71441_e.field_72996_f.iterator();

         Entity e;
         while(var2.hasNext()) {
            e = (Entity)var2.next();
            if (!this.soulsNear.contains(e) && e instanceof EntityArmorStand) {
               ItemStack stack = ((EntityArmorStand)e).func_82169_q(3);
               if (stack != null && stack.func_82833_r().equals("Head")) {
                  NBTTagCompound skullOwner = stack.func_179543_a("SkullOwner", false);
                  if (skullOwner != null && skullOwner.func_74779_i("Id").equals("57a4c8dc-9b8e-3d41-80da-a608901a6147") && !this.foundSouls.contains(e)) {
                     this.soulsNear.add(e);
                  }
               }
            }
         }

         if (!this.stonkless.isEnabled()) {
            var2 = (new ArrayList(this.soulsNear)).iterator();

            while(var2.hasNext()) {
               e = (Entity)var2.next();
               Vec3 eyes = mc.field_71439_g.func_174824_e(1.0F);
               if (!clicked.contains(e) && e.func_70011_f(eyes.field_72450_a, eyes.field_72448_b - 2.0D, eyes.field_72449_c) < (double)this.clickRange.getCurrent()) {
                  if ((float)(System.currentTimeMillis() - this.lastClickTime) >= this.clickDelay.getCurrent()) {
                     this.handleClick(e);
                     clicked.add(e);
                  }
                  break;
               }
            }
         } else {
            clicked.clear();
         }
      }

   }

   @Event
   public void onRender(Render3DEvent event) {
      this.selected = null;
      Iterator var2 = (new ArrayList(this.soulsNear)).iterator();

      while(true) {
         while(var2.hasNext()) {
            Entity e = (Entity)var2.next();
            if (this.selected == null && RotationUtils.lookingAt(e.func_180425_c().func_177982_a(0, 1, 0), this.clickRange.getCurrent())) {
               this.selected = e;
            } else if (!clicked.contains(e)) {
               Render3DUtils.drawFairySoulOutline(e, event.partialTicks, -1);
            } else {
               Render3DUtils.drawFairySoulOutline(e, event.partialTicks, -12345273);
            }
         }

         if (this.selected != null) {
            Render3DUtils.drawFairySoulOutline(this.selected, event.partialTicks, -19712);
         }

         return;
      }
   }

   @Event
   public void onInteract(PlayerInteractEvent event) {
      if (this.stonkless.isEnabled() && this.selected != null && !clicked.contains(this.selected)) {
         MovingObjectPosition omo = mc.field_71476_x;
         if (omo != null && omo.field_72313_a == MovingObjectType.ENTITY && omo.field_72308_g.equals(this.selected)) {
            return;
         }

         if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            clicked.add(this.selected);
            this.handleClick(this.selected);
         }
      }

   }

   @Event
   public void onWorldLoad(WorldUnloadEvent event) {
      clicked.clear();
   }

   @Event
   public void onChat(ClientChatReceivedEvent event) {
      String msg = StringUtils.func_76338_a(event.message.func_150260_c());
      if (msg.equals("SOUL! You found a Fairy Soul!") || msg.equals("You have already found that Fairy Soul!")) {
         Optional<Entity> nearest = this.soulsNear.stream().min(Comparator.comparing((a) -> {
            return a.func_70032_d(mc.field_71439_g);
         }));
         nearest.ifPresent((e) -> {
            this.foundSouls.add(e);
            this.soulsNear.remove(e);
         });
      }

   }

   private void handleClick(Entity e) {
      this.lastClickTime = System.currentTimeMillis();
      if (mc.field_71476_x != null) {
         MovingObjectPosition movingObject = mc.field_71476_x;
         if (this.clickSlot.getCurrent() != 0) {
            int holding = mc.field_71439_g.field_71071_by.field_70461_c;
            mc.field_71439_g.field_71071_by.field_70461_c = this.clickSlot.getCurrent();
            this.syncItem();
            Vec3 vec3 = new Vec3(movingObject.field_72307_f.field_72450_a - e.field_70165_t, movingObject.field_72307_f.field_72448_b - e.field_70163_u, movingObject.field_72307_f.field_72449_c - e.field_70161_v);
            mc.func_147114_u().func_147297_a(new C02PacketUseEntity(e, vec3));
            e.func_174825_a(mc.field_71439_g, vec3);
            mc.field_71439_g.field_71071_by.field_70461_c = holding;
         } else {
            this.syncItem();
            Vec3 vec3 = new Vec3(movingObject.field_72307_f.field_72450_a - e.field_70165_t, movingObject.field_72307_f.field_72448_b - e.field_70163_u, movingObject.field_72307_f.field_72449_c - e.field_70161_v);
            mc.func_147114_u().func_147297_a(new C02PacketUseEntity(e, vec3));
         }

         if (!this.stonkless.isEnabled()) {
            this.soulsNear.remove(e);
            clicked.add(e);
         }

      }
   }

   private void syncItem() {
      if (this.syncCurrentPlayItem != null) {
         try {
            this.syncCurrentPlayItem.invoke(mc.field_71442_b);
         } catch (InvocationTargetException | IllegalAccessException var2) {
            var2.printStackTrace();
         }
      }

   }
}

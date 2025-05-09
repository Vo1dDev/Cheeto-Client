package xyz.apfelmus.cheeto.client.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import xyz.apfelmus.cheeto.client.clickgui.ConfigGUI;
import xyz.apfelmus.cheeto.client.events.Render2DEvent;
import xyz.apfelmus.cheeto.client.events.Render3DEvent;
import xyz.apfelmus.cheeto.client.events.RenderLivingEventPre;
import xyz.apfelmus.cheeto.client.events.WorldUnloadEvent;

public class EventListenerRegister {
   private static Minecraft mc = Minecraft.func_71410_x();

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (event.phase != Phase.END && mc.field_71439_g != null && mc.field_71441_e != null) {
         (new xyz.apfelmus.cheeto.client.events.ClientTickEvent()).call();
      }
   }

   @SubscribeEvent
   public void onRenderGui(RenderTickEvent event) {
      if (mc.field_71439_g != null && mc.field_71441_e != null && !mc.field_71474_y.field_74330_P && (mc.field_71462_r == null || mc.field_71462_r instanceof GuiChat || mc.field_71462_r instanceof ConfigGUI)) {
         (new Render2DEvent()).call();
      }
   }

   @SubscribeEvent
   public void onRenderWorld(RenderWorldLastEvent event) {
      (new Render3DEvent(event.partialTicks)).call();
   }

   @SubscribeEvent
   public void onChatReceived(ClientChatReceivedEvent event) {
      (new xyz.apfelmus.cheeto.client.events.ClientChatReceivedEvent(event.type, event.message)).call();
   }

   @SubscribeEvent
   public void onWorldUnload(Unload event) {
      (new WorldUnloadEvent()).call();
   }

   @SubscribeEvent
   public void onGuiOpen(GuiOpenEvent event) {
      (new xyz.apfelmus.cheeto.client.events.GuiOpenEvent(event.gui)).call();
   }

   @SubscribeEvent
   public void onBackgroundDrawn(BackgroundDrawnEvent event) {
      (new xyz.apfelmus.cheeto.client.events.BackgroundDrawnEvent(event.gui)).call();
   }

   @SubscribeEvent
   public void onBeforeRenderEntity(Pre<EntityLivingBase> event) {
      (new RenderLivingEventPre(event.entity)).call();
   }

   @SubscribeEvent
   public void onInteract(PlayerInteractEvent event) {
      (new xyz.apfelmus.cheeto.client.events.PlayerInteractEvent(event.action, event.pos)).call();
   }

   @SubscribeEvent
   public void onEntityInteract(EntityInteractEvent event) {
      (new xyz.apfelmus.cheeto.client.events.EntityInteractEvent(event)).call();
   }

   @SubscribeEvent
   public void onMouseInput(MouseInputEvent event) {
      (new xyz.apfelmus.cheeto.client.events.MouseInputEvent(event)).call();
   }
}

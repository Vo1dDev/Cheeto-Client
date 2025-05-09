package xyz.apfelmus.cheeto.client.events;

import xyz.apfelmus.cf4m.event.Listener;

public class EntityInteractEvent extends Listener {
   public net.minecraftforge.event.entity.player.EntityInteractEvent event;

   public EntityInteractEvent(net.minecraftforge.event.entity.player.EntityInteractEvent event) {
      super(Listener.At.HEAD);
      this.event = event;
   }
}

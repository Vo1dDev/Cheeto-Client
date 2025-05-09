package xyz.apfelmus.cheeto.client.events;

import xyz.apfelmus.cf4m.event.Listener;

public class MouseInputEvent extends Listener {
   public net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent event;

   public MouseInputEvent(net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent event) {
      super(Listener.At.HEAD);
      this.event = event;
   }
}

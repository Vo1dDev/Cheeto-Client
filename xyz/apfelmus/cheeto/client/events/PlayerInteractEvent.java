package xyz.apfelmus.cheeto.client.events;

import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import xyz.apfelmus.cf4m.event.Listener;

public class PlayerInteractEvent extends Listener {
   public Action action;
   public BlockPos pos;

   public PlayerInteractEvent(Action action, BlockPos pos) {
      super(Listener.At.HEAD);
      this.action = action;
      this.pos = pos;
   }
}

package xyz.apfelmus.cheeto.injection.mixins;

import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({C13PacketPlayerAbilities.class})
public interface C13PacketPlayerAbilitiesAccessor {
   @Accessor
   float getFlySpeed();
}

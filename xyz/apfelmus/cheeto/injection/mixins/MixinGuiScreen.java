package xyz.apfelmus.cheeto.injection.mixins;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({GuiScreen.class})
public class MixinGuiScreen {
   @Shadow
   public Minecraft field_146297_k;
   @Shadow
   protected List<GuiButton> field_146292_n;
   @Shadow
   public int field_146294_l;
   @Shadow
   public int field_146295_m;
}

package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.events.UIClickEvent;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.ExtensionsKt;
import gg.essential.elementa.state.State;
import gg.essential.universal.USound;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 3,
   xi = 48,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¨\u0006\u0005"},
   d2 = {"<anonymous>", "", "Lgg/essential/elementa/UIComponent;", "it", "Lgg/essential/elementa/events/UIClickEvent;", "gg/essential/vigilance/utils/ExtensionsKt$onLeftClick$1"}
)
public final class SwitchComponent$special$$inlined$onLeftClick$1 extends Lambda implements Function2<UIComponent, UIClickEvent, Unit> {
   // $FF: synthetic field
   final SwitchComponent this$0;

   public SwitchComponent$special$$inlined$onLeftClick$1(SwitchComponent var1) {
      super(2);
      this.this$0 = var1;
   }

   public final void invoke(@NotNull UIComponent $this$onMouseClick, @NotNull UIClickEvent it) {
      Intrinsics.checkNotNullParameter($this$onMouseClick, "$this$onMouseClick");
      Intrinsics.checkNotNullParameter(it, "it");
      if (it.getMouseButton() == 0) {
         int var5 = false;
         USound.playButtonPress$default(USound.INSTANCE, 0.0F, 1, (Object)null);
         this.this$0.setEnabled$Cheeto(!this.this$0.getEnabled$Cheeto());
         SettingComponent.changeValue$default((SettingComponent)this.this$0, this.this$0.getEnabled$Cheeto(), false, 2, (Object)null);
         int $i$f$animate = false;
         $this$onMouseClick.getEffects().removeIf((Predicate)(new SwitchComponent$_init_$lambda-3$$inlined$removeEffect$1()));
         $this$onMouseClick.enableEffect((Effect)SwitchComponent.access$getOutlineEffect(this.this$0));
         SwitchComponent.access$getSwitchBox$p(this.this$0).setColor((ColorConstraint)ExtensionsKt.toConstraint((State)($this$onMouseClick.isHovered() ? (BasicState)SwitchComponent.access$getSwitchColor(this.this$0).map((Function1)null.INSTANCE) : SwitchComponent.access$getSwitchColor(this.this$0))));
         UIComponent $this$animate$iv = (UIComponent)SwitchComponent.access$getSwitchBox$p(this.this$0);
         $i$f$animate = false;
         int var10 = false;
         AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
         int var13 = false;
         AnimatingConstraints.setXAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.5F, (XConstraint)SwitchComponent.access$getSwitchPosition(this.this$0), 0.0F, 8, (Object)null);
         $this$animate$iv.animateTo(anim$iv);
      }

   }
}

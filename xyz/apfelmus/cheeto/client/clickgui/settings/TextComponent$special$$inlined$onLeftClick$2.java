package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.input.UIPasswordInput;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.events.UIClickEvent;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.ExtensionsKt;
import gg.essential.elementa.state.State;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.BooleanRef;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 3,
   xi = 48,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¨\u0006\u0005"},
   d2 = {"<anonymous>", "", "Lgg/essential/elementa/UIComponent;", "it", "Lgg/essential/elementa/events/UIClickEvent;", "gg/essential/vigilance/utils/ExtensionsKt$onLeftClick$1"}
)
public final class TextComponent$special$$inlined$onLeftClick$2 extends Lambda implements Function2<UIComponent, UIClickEvent, Unit> {
   // $FF: synthetic field
   final BooleanRef $toggle$inlined;
   // $FF: synthetic field
   final TextComponent this$0;

   public TextComponent$special$$inlined$onLeftClick$2(BooleanRef var1, TextComponent var2) {
      super(2);
      this.$toggle$inlined = var1;
      this.this$0 = var2;
   }

   public final void invoke(@NotNull UIComponent $this$onMouseClick, @NotNull UIClickEvent it) {
      Intrinsics.checkNotNullParameter($this$onMouseClick, "$this$onMouseClick");
      Intrinsics.checkNotNullParameter(it, "it");
      if (it.getMouseButton() == 0) {
         int var5 = false;
         this.$toggle$inlined.element = !this.$toggle$inlined.element;
         ((UIPasswordInput)TextComponent.access$getTextInput$p(this.this$0)).setProtection(!this.$toggle$inlined.element);
         int $i$f$animate = false;
         int var10 = false;
         AnimatingConstraints anim$iv = $this$onMouseClick.makeAnimation();
         int var13 = false;
         AnimatingConstraints.setColorAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.2F, (ColorConstraint)ExtensionsKt.toConstraint((State)(this.$toggle$inlined.element ? new BasicState(Color.RED) : new BasicState(Color.GRAY))), 0.0F, 8, (Object)null);
         $this$onMouseClick.animateTo(anim$iv);
      }

   }
}

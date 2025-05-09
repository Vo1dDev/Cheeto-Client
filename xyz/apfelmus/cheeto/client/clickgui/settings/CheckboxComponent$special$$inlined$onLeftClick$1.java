package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.events.UIClickEvent;
import gg.essential.universal.USound;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Unit;
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
public final class CheckboxComponent$special$$inlined$onLeftClick$1 extends Lambda implements Function2<UIComponent, UIClickEvent, Unit> {
   // $FF: synthetic field
   final CheckboxComponent this$0;

   public CheckboxComponent$special$$inlined$onLeftClick$1(CheckboxComponent var1) {
      super(2);
      this.this$0 = var1;
   }

   public final void invoke(@NotNull UIComponent $this$onMouseClick, @NotNull UIClickEvent it) {
      Intrinsics.checkNotNullParameter($this$onMouseClick, "$this$onMouseClick");
      Intrinsics.checkNotNullParameter(it, "it");
      if (it.getMouseButton() == 0) {
         int var5 = false;
         USound.playButtonPress$default(USound.INSTANCE, 0.0F, 1, (Object)null);
         this.this$0.setChecked(!this.this$0.getChecked());
         int $i$f$removeEffect = false;
         $this$onMouseClick.getEffects().removeIf((Predicate)(new CheckboxComponent$_init_$lambda-2$$inlined$removeEffect$1()));
         ComponentsKt.effect($this$onMouseClick, (Effect)CheckboxComponent.access$getOutlineEffect(this.this$0));
         if (this.this$0.getChecked()) {
            UIComponent.unhide$default((UIComponent)CheckboxComponent.access$getCheckmark$p(this.this$0), false, 1, (Object)null);
         } else {
            UIComponent.hide$default((UIComponent)CheckboxComponent.access$getCheckmark$p(this.this$0), false, 1, (Object)null);
         }
      }

   }
}

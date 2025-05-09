package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.events.UIClickEvent;
import gg.essential.universal.USound;
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
public final class DropDown$special$$inlined$onLeftClick$1 extends Lambda implements Function2<UIComponent, UIClickEvent, Unit> {
   // $FF: synthetic field
   final DropDown this$0;

   public DropDown$special$$inlined$onLeftClick$1(DropDown var1) {
      super(2);
      this.this$0 = var1;
   }

   public final void invoke(@NotNull UIComponent $this$onMouseClick, @NotNull UIClickEvent it) {
      Intrinsics.checkNotNullParameter($this$onMouseClick, "$this$onMouseClick");
      Intrinsics.checkNotNullParameter(it, "it");
      if (it.getMouseButton() == 0) {
         int var5 = false;
         USound.playButtonPress$default(USound.INSTANCE, 0.0F, 1, (Object)null);
         it.stopPropagation();
         if (DropDown.access$getActive$p(this.this$0)) {
            DropDown.collapse$default(this.this$0, false, false, 3, (Object)null);
         } else {
            DropDown.access$expand(this.this$0);
         }
      }

   }
}

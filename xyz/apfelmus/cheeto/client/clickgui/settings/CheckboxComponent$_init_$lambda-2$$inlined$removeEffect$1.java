package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.effects.OutlineEffect;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 3,
   xi = 48,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¨\u0006\u0005"},
   d2 = {"<anonymous>", "", "T", "it", "Lgg/essential/elementa/effects/Effect;", "gg/essential/elementa/UIComponent$removeEffect$1"}
)
public final class CheckboxComponent$_init_$lambda-2$$inlined$removeEffect$1<T> implements Predicate {
   public final boolean test(@NotNull Effect it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it instanceof OutlineEffect;
   }
}

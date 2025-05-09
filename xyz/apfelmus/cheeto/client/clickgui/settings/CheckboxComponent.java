package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIImage;
import gg.essential.elementa.constraints.AspectConstraint;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.effects.OutlineEffect;
import java.awt.Color;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fH\u0002R$\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0004R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/CheckboxComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/SettingComponent;", "initialValue", "", "(Z)V", "value", "checked", "getChecked", "()Z", "setChecked", "checkmark", "Lgg/essential/elementa/components/UIImage;", "getOutlineEffect", "Lgg/essential/elementa/effects/OutlineEffect;", "getSettingColor", "Ljava/awt/Color;", "kotlin.jvm.PlatformType", "Cheeto"}
)
public final class CheckboxComponent extends SettingComponent {
   private boolean checked;
   @NotNull
   private final UIImage checkmark;

   public CheckboxComponent(boolean initialValue) {
      this.checked = initialValue;
      UIComponent $this$onLeftClick$iv = (UIComponent)UIImage.Companion.ofResourceCached("/vigilance/check.png");
      int $i$f$onLeftClick = false;
      int var6 = false;
      UIConstraints $this$_init__u24lambda_u2d1 = $this$onLeftClick$iv.getConstraints();
      int var8 = false;
      $this$_init__u24lambda_u2d1.setX((XConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d1.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d1.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)16, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d1.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)12, false, false, 3, (Object)null));
      Color var9 = ColorUtils.SELECTED;
      Intrinsics.checkNotNullExpressionValue(var9, "SELECTED");
      $this$_init__u24lambda_u2d1.setColor((ColorConstraint)UtilitiesKt.toConstraint(var9));
      this.checkmark = (UIImage)ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      var6 = false;
      $this$_init__u24lambda_u2d1 = $this$onLeftClick$iv.getConstraints();
      var8 = false;
      $this$_init__u24lambda_u2d1.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d1.setHeight((HeightConstraint)(new AspectConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      ComponentsKt.effect((UIComponent)this, (Effect)this.getOutlineEffect());
      if (!this.checked) {
         this.checkmark.hide(true);
      }

      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new CheckboxComponent$special$$inlined$onLeftClick$1(this)));
   }

   public final boolean getChecked() {
      return this.checked;
   }

   public final void setChecked(boolean value) {
      SettingComponent.changeValue$default((SettingComponent)this, value, false, 2, (Object)null);
      this.checked = value;
   }

   private final OutlineEffect getOutlineEffect() {
      Color var1 = this.getSettingColor();
      Intrinsics.checkNotNullExpressionValue(var1, "getSettingColor()");
      return new OutlineEffect(var1, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null);
   }

   private final Color getSettingColor() {
      return this.checked ? ColorUtils.SELECTED : ColorUtils.SELECT;
   }

   // $FF: synthetic method
   public static final OutlineEffect access$getOutlineEffect(CheckboxComponent $this) {
      return $this.getOutlineEffect();
   }

   // $FF: synthetic method
   public static final UIImage access$getCheckmark$p(CheckboxComponent $this) {
      return $this.checkmark;
   }
}

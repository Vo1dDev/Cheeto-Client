package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.effects.OutlineEffect;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.ExtensionsKt;
import gg.essential.elementa.state.State;
import java.awt.Color;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002R\u001a\u0010\u0005\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0004R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/SwitchComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/SettingComponent;", "initialState", "", "(Z)V", "enabled", "getEnabled$Cheeto", "()Z", "setEnabled$Cheeto", "switchBox", "Lgg/essential/elementa/components/UIBlock;", "getOutlineEffect", "Lgg/essential/elementa/effects/OutlineEffect;", "getSwitchColor", "Lgg/essential/elementa/state/BasicState;", "Ljava/awt/Color;", "getSwitchPosition", "Lgg/essential/elementa/constraints/PixelConstraint;", "Cheeto"}
)
public final class SwitchComponent extends SettingComponent {
   private boolean enabled;
   @NotNull
   private final UIBlock switchBox;

   public SwitchComponent(boolean initialState) {
      this.enabled = initialState;
      UIComponent $this$onLeftClick$iv = (UIComponent)(new UIBlock((State)this.getSwitchColor()));
      int $i$f$onLeftClick = false;
      int var6 = false;
      UIConstraints $this$_init__u24lambda_u2d1 = $this$onLeftClick$iv.getConstraints();
      int var8 = false;
      $this$_init__u24lambda_u2d1.setX((XConstraint)this.getSwitchPosition());
      $this$_init__u24lambda_u2d1.setWidth((WidthConstraint)(new RelativeConstraint(0.5F)));
      $this$_init__u24lambda_u2d1.setHeight((HeightConstraint)(new RelativeConstraint(1.0F)));
      this.switchBox = (UIBlock)ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      var6 = false;
      $this$_init__u24lambda_u2d1 = $this$onLeftClick$iv.getConstraints();
      var8 = false;
      $this$_init__u24lambda_u2d1.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d1.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      ComponentsKt.effect((UIComponent)this, (Effect)this.getOutlineEffect());
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new SwitchComponent$special$$inlined$onLeftClick$1(this)));
      this.onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
            Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
            UIComponent $this$animate$iv = (UIComponent)SwitchComponent.this.switchBox;
            SwitchComponent var3 = SwitchComponent.this;
            int $i$f$animate = false;
            int var7 = false;
            AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
            int var10 = false;
            AnimatingConstraints.setColorAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.25F, (ColorConstraint)ExtensionsKt.toConstraint((State)var3.getSwitchColor().map((Function1)null.INSTANCE)), 0.0F, 8, (Object)null);
            $this$animate$iv.animateTo(anim$iv);
         }
      }));
      this.onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
            Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
            UIComponent $this$animate$iv = (UIComponent)SwitchComponent.this.switchBox;
            SwitchComponent var3 = SwitchComponent.this;
            int $i$f$animate = false;
            int var7 = false;
            AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
            int var10 = false;
            AnimatingConstraints.setColorAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.25F, (ColorConstraint)ExtensionsKt.toConstraint((State)var3.getSwitchColor()), 0.0F, 8, (Object)null);
            $this$animate$iv.animateTo(anim$iv);
         }
      }));
   }

   public final boolean getEnabled$Cheeto() {
      return this.enabled;
   }

   public final void setEnabled$Cheeto(boolean var1) {
      this.enabled = var1;
   }

   private final OutlineEffect getOutlineEffect() {
      return (new OutlineEffect((Color)this.getSwitchColor().get(), 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null)).bindColor((State)this.getSwitchColor());
   }

   private final BasicState<Color> getSwitchColor() {
      BasicState var10000;
      Color var1;
      if (this.enabled) {
         var1 = ColorUtils.SELECTED;
         Intrinsics.checkNotNullExpressionValue(var1, "SELECTED");
         var10000 = new BasicState(var1);
      } else {
         var1 = ColorUtils.SELECT;
         Intrinsics.checkNotNullExpressionValue(var1, "SELECT");
         var10000 = new BasicState(var1);
      }

      return var10000;
   }

   private final PixelConstraint getSwitchPosition() {
      return this.enabled ? UtilitiesKt.pixels$default((Number)0, true, false, 2, (Object)null) : UtilitiesKt.pixels$default((Number)0, false, false, 3, (Object)null);
   }

   // $FF: synthetic method
   public static final PixelConstraint access$getSwitchPosition(SwitchComponent $this) {
      return $this.getSwitchPosition();
   }

   // $FF: synthetic method
   public static final OutlineEffect access$getOutlineEffect(SwitchComponent $this) {
      return $this.getOutlineEffect();
   }
}

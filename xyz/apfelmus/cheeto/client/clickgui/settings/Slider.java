package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.constraints.AspectConstraint;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.dsl.BasicConstraintsKt;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.effects.OutlineEffect;
import java.awt.Color;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0012\u001a\u00020\u0003J\u001a\u0010\r\u001a\u00020\u000f2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u000eJ\u0018\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/Slider;", "Lgg/essential/elementa/components/UIContainer;", "initialValue", "", "(F)V", "completionBox", "Lgg/essential/elementa/components/UIBlock;", "dragging", "", "grabBox", "getGrabBox", "()Lgg/essential/elementa/components/UIBlock;", "grabOffset", "onValueChange", "Lkotlin/Function1;", "", "outerBox", "percentage", "getCurrentPercentage", "listener", "setCurrentPercentage", "newPercentage", "callListener", "Cheeto"}
)
public final class Slider extends UIContainer {
   private float percentage;
   @NotNull
   private Function1<? super Float, Unit> onValueChange;
   private boolean dragging;
   private float grabOffset;
   @NotNull
   private final UIContainer outerBox;
   @NotNull
   private final UIBlock completionBox;
   @NotNull
   private final UIBlock grabBox;

   public Slider(float initialValue) {
      this.percentage = initialValue;
      this.onValueChange = (Function1)null.INSTANCE;
      UIComponent $this$onLeftClick$iv = (UIComponent)(new UIContainer());
      int $i$f$onLeftClick = false;
      int var6 = false;
      UIConstraints $this$grabBox_u24lambda_u2d2 = $this$onLeftClick$iv.getConstraints();
      int var8 = false;
      $this$grabBox_u24lambda_u2d2.setX(BasicConstraintsKt.basicXConstraint((Function1)(new Function1<UIComponent, Float>() {
         @NotNull
         public final Float invoke(@NotNull UIComponent it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return Slider.this.getLeft() + 1.0F + Slider.this.getHeight() * 0.75F;
         }
      })));
      $this$grabBox_u24lambda_u2d2.setY((YConstraint)(new CenterConstraint()));
      $this$grabBox_u24lambda_u2d2.setWidth(BasicConstraintsKt.basicWidthConstraint((Function1)(new Function1<UIComponent, Float>() {
         @NotNull
         public final Float invoke(@NotNull UIComponent it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return Slider.this.getWidth() - 2.0F - Slider.this.getHeight() * 1.5F;
         }
      })));
      $this$grabBox_u24lambda_u2d2.setHeight((HeightConstraint)(new RelativeConstraint(0.5F)));
      UIComponent var10001 = ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      Color var11 = ColorUtils.C_BORDER;
      Intrinsics.checkNotNullExpressionValue(var11, "C_BORDER");
      this.outerBox = (UIContainer)ComponentsKt.effect(var10001, (Effect)(new OutlineEffect(var11, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null)));
      $this$onLeftClick$iv = (UIComponent)(new UIBlock((ColorConstraint)null, 1, (DefaultConstructorMarker)null));
      $i$f$onLeftClick = false;
      var6 = false;
      $this$grabBox_u24lambda_u2d2 = $this$onLeftClick$iv.getConstraints();
      var8 = false;
      $this$grabBox_u24lambda_u2d2.setWidth((WidthConstraint)(new RelativeConstraint(this.percentage)));
      $this$grabBox_u24lambda_u2d2.setHeight((HeightConstraint)(new RelativeConstraint(1.0F)));
      Color var9 = ColorUtils.SELECTED;
      Intrinsics.checkNotNullExpressionValue(var9, "SELECTED");
      $this$grabBox_u24lambda_u2d2.setColor((ColorConstraint)UtilitiesKt.toConstraint(var9));
      this.completionBox = (UIBlock)ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this.outerBox);
      $this$onLeftClick$iv = (UIComponent)(new UIBlock((ColorConstraint)null, 1, (DefaultConstructorMarker)null));
      $i$f$onLeftClick = false;
      var6 = false;
      $this$grabBox_u24lambda_u2d2 = $this$onLeftClick$iv.getConstraints();
      var8 = false;
      $this$grabBox_u24lambda_u2d2.setX(BasicConstraintsKt.basicXConstraint((Function1)(new Function1<UIComponent, Float>() {
         @NotNull
         public final Float invoke(@NotNull UIComponent it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return Slider.this.completionBox.getRight() - it.getWidth() / 2.0F;
         }
      })));
      $this$grabBox_u24lambda_u2d2.setY((YConstraint)ConstraintsKt.boundTo((SuperConstraint)(new CenterConstraint()), (UIComponent)this.outerBox));
      $this$grabBox_u24lambda_u2d2.setWidth((WidthConstraint)(new AspectConstraint(1.0F)));
      $this$grabBox_u24lambda_u2d2.setHeight((HeightConstraint)UtilitiesKt.percent((Number)100));
      var9 = ColorUtils.SELECTED;
      Intrinsics.checkNotNullExpressionValue(var9, "SELECTED");
      $this$grabBox_u24lambda_u2d2.setColor((ColorConstraint)UtilitiesKt.toConstraint(var9));
      var10001 = ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      var11 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var11, "M_BORDER");
      this.grabBox = (UIBlock)ComponentsKt.effect(var10001, (Effect)(new OutlineEffect(var11, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null)));
      $this$onLeftClick$iv = (UIComponent)this.grabBox;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new Slider$special$$inlined$onLeftClick$1(this))).onMouseRelease((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseRelease) {
            Intrinsics.checkNotNullParameter($this$onMouseRelease, "$this$onMouseRelease");
            Slider.this.dragging = false;
            Slider.this.grabOffset = 0.0F;
         }
      })).onMouseDrag((Function4)(new Function4<UIComponent, Float, Float, Integer, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseDrag, float mouseX, float $noName_1, int $noName_2) {
            Intrinsics.checkNotNullParameter($this$onMouseDrag, "$this$onMouseDrag");
            if (Slider.this.dragging) {
               float clamped = ((Number)RangesKt.coerceIn((Comparable)mouseX + Slider.this.getGrabBox().getLeft() - Slider.this.grabOffset, RangesKt.rangeTo(Slider.this.outerBox.getLeft(), Slider.this.outerBox.getRight()))).floatValue();
               float percentage = (clamped - Slider.this.outerBox.getLeft()) / Slider.this.outerBox.getWidth();
               Slider.setCurrentPercentage$default(Slider.this, percentage, false, 2, (Object)null);
            }
         }
      }));
      $this$onLeftClick$iv = (UIComponent)this.outerBox;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new Slider$special$$inlined$onLeftClick$2(this)));
   }

   @NotNull
   public final UIBlock getGrabBox() {
      return this.grabBox;
   }

   public final float getCurrentPercentage() {
      return this.percentage;
   }

   public final void setCurrentPercentage(float newPercentage, boolean callListener) {
      this.percentage = ((Number)RangesKt.coerceIn((Comparable)newPercentage, RangesKt.rangeTo(0.0F, 1.0F))).floatValue();
      this.completionBox.setWidth((WidthConstraint)(new RelativeConstraint(this.percentage)));
      if (callListener) {
         this.onValueChange.invoke(this.percentage);
      }

   }

   // $FF: synthetic method
   public static void setCurrentPercentage$default(Slider var0, float var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = true;
      }

      var0.setCurrentPercentage(var1, var2);
   }

   public final void onValueChange(@NotNull Function1<? super Float, Unit> listener) {
      Intrinsics.checkNotNullParameter(listener, "listener");
      this.onValueChange = listener;
   }
}

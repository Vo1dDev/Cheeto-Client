package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.constraints.SiblingConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.font.DefaultFonts;
import java.awt.Color;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/DecimalSliderComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/AbstractSliderComponent;", "initialValue", "", "min", "max", "decimalPlaces", "", "(FFFI)V", "currentValueText", "Lgg/essential/elementa/components/UIText;", "getCurrentValueText", "()Lgg/essential/elementa/components/UIText;", "currentValueText$delegate", "Lkotlin/properties/ReadWriteProperty;", "slider", "Lxyz/apfelmus/cheeto/client/clickgui/settings/Slider;", "getSlider", "()Lxyz/apfelmus/cheeto/client/clickgui/settings/Slider;", "slider$delegate", "Cheeto"}
)
public final class DecimalSliderComponent extends AbstractSliderComponent {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final ReadWriteProperty slider$delegate;
   @NotNull
   private final ReadWriteProperty currentValueText$delegate;

   public DecimalSliderComponent(float initialValue, final float min, final float max, final int decimalPlaces) {
      UIComponent $this$constrain$iv = (UIComponent)(new UIText(String.valueOf(min), false, (Color)null, 6, (DefaultConstructorMarker)null));
      int $i$f$constrain = false;
      int var9 = false;
      UIConstraints $this$currentValueText_delegate_u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      int var11 = false;
      $this$currentValueText_delegate_u24lambda_u2d3.setY((YConstraint)(new CenterConstraint()));
      Color var12 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var12, "LABEL");
      $this$currentValueText_delegate_u24lambda_u2d3.setColor((ColorConstraint)UtilitiesKt.toConstraint(var12));
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this);
      $this$constrain$iv = (UIComponent)(new Slider((initialValue - min) / (max - min)));
      $i$f$constrain = false;
      var9 = false;
      $this$currentValueText_delegate_u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      var11 = false;
      $this$currentValueText_delegate_u24lambda_u2d3.setX((XConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$currentValueText_delegate_u24lambda_u2d3.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)60, false, false, 3, (Object)null));
      $this$currentValueText_delegate_u24lambda_u2d3.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)12, false, false, 3, (Object)null));
      this.slider$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      $this$constrain$iv = (UIComponent)(new UIText(String.valueOf(max), false, (Color)null, 6, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var9 = false;
      $this$currentValueText_delegate_u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      var11 = false;
      $this$currentValueText_delegate_u24lambda_u2d3.setX((XConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$currentValueText_delegate_u24lambda_u2d3.setY((YConstraint)(new CenterConstraint()));
      var12 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var12, "LABEL");
      $this$currentValueText_delegate_u24lambda_u2d3.setColor((ColorConstraint)UtilitiesKt.toConstraint(var12));
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this);
      $this$constrain$iv = (UIComponent)(new UIText(String.valueOf(initialValue), false, (Color)null, 6, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var9 = false;
      $this$currentValueText_delegate_u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      var11 = false;
      $this$currentValueText_delegate_u24lambda_u2d3.setX((XConstraint)ConstraintsKt.boundTo((SuperConstraint)(new CenterConstraint()), (UIComponent)this.getSlider().getGrabBox()));
      $this$currentValueText_delegate_u24lambda_u2d3.setY((YConstraint)(new RelativeConstraint(1.5F)));
      var12 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var12, "LABEL");
      $this$currentValueText_delegate_u24lambda_u2d3.setColor((ColorConstraint)UtilitiesKt.toConstraint(var12));
      $this$currentValueText_delegate_u24lambda_u2d3.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      this.currentValueText$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getSlider()), this, $$delegatedProperties[1]);
      this.getSlider().onValueChange((Function1)(new Function1<Float, Unit>() {
         public final void invoke(float newPercentage) {
            String var3 = "%." + decimalPlaces + 'f';
            Locale var4 = Locale.US;
            Object[] var5 = new Object[]{min + newPercentage * (max - min)};
            String var6 = String.format(var4, var3, Arrays.copyOf(var5, var5.length));
            Intrinsics.checkNotNullExpressionValue(var6, "format(locale, this, *args)");
            SettingComponent.changeValue$default((SettingComponent)DecimalSliderComponent.this, Float.parseFloat(var6), false, 2, (Object)null);
            DecimalSliderComponent.this.getCurrentValueText().setText(var6);
         }
      }));
      this.sliderInit();
   }

   // $FF: synthetic method
   public DecimalSliderComponent(float var1, float var2, float var3, int var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 8) != 0) {
         var4 = 1;
      }

      this(var1, var2, var3, var4);
   }

   @NotNull
   protected Slider getSlider() {
      return (Slider)this.slider$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final UIText getCurrentValueText() {
      return (UIText)this.currentValueText$delegate.getValue(this, $$delegatedProperties[1]);
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DecimalSliderComponent.class, "slider", "getSlider()Lxyz/apfelmus/cheeto/client/clickgui/settings/Slider;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DecimalSliderComponent.class, "currentValueText", "getCurrentValueText()Lgg/essential/elementa/components/UIText;", 0)))};
      $$delegatedProperties = var0;
   }
}

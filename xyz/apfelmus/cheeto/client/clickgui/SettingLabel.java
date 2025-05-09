package xyz.apfelmus.cheeto.client.clickgui;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.UIWrappedText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ChildBasedSizeConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.FillConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.SiblingConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.effects.OutlineEffect;
import gg.essential.elementa.font.DefaultFonts;
import java.awt.Color;
import java.util.List;
import java.util.Set;
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
import org.jetbrains.annotations.Nullable;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cheeto.client.clickgui.settings.DecimalSliderComponent;
import xyz.apfelmus.cheeto.client.clickgui.settings.SelectorComponent;
import xyz.apfelmus.cheeto.client.clickgui.settings.SettingComponent;
import xyz.apfelmus.cheeto.client.clickgui.settings.SliderComponent;
import xyz.apfelmus.cheeto.client.clickgui.settings.SwitchComponent;
import xyz.apfelmus.cheeto.client.clickgui.settings.TextComponent;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.FloatSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.settings.ModeSetting;
import xyz.apfelmus.cheeto.client.settings.StringSetting;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/SettingLabel;", "Lgg/essential/elementa/components/UIContainer;", "module", "", "setting", "(Ljava/lang/Object;Ljava/lang/Object;)V", "textContainer", "getTextContainer", "()Lgg/essential/elementa/components/UIContainer;", "textContainer$delegate", "Lkotlin/properties/ReadWriteProperty;", "Cheeto"}
)
public final class SettingLabel extends UIContainer {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final Object module;
   @NotNull
   private final Object setting;
   @NotNull
   private final ReadWriteProperty textContainer$delegate;

   public SettingLabel(@NotNull Object module, @NotNull Object setting) {
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super();
      this.module = module;
      this.setting = setting;
      UIComponent $this$constrain$iv = (UIComponent)(new UIContainer());
      int $i$f$constrain = false;
      int var8 = false;
      UIConstraints $this$_init__u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      int var10 = false;
      $this$_init__u24lambda_u2d3.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d3.setWidth((WidthConstraint)UtilitiesKt.percent((Number)75));
      $this$_init__u24lambda_u2d3.setHeight((HeightConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      this.textContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      String var13 = CF4M.INSTANCE.settingManager.getName(this.module, this.setting);
      Intrinsics.checkNotNullExpressionValue(var13, "INSTANCE.settingManager.getName(module, setting)");
      $this$constrain$iv = (UIComponent)(new UIText(var13, false, (Color)null, 6, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var8 = false;
      $this$_init__u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      var10 = false;
      $this$_init__u24lambda_u2d3.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      String var11 = CF4M.INSTANCE.settingManager.getDescription(this.module, this.setting);
      Intrinsics.checkNotNullExpressionValue(var11, "INSTANCE.settingManager.…cription(module, setting)");
      $this$_init__u24lambda_u2d3.setY(((CharSequence)var11).length() == 0 ? (YConstraint)(new CenterConstraint()) : (YConstraint)UtilitiesKt.pixels$default((Number)0, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d3.setTextScale((HeightConstraint)UtilitiesKt.pixels$default((Number)1.5F, false, false, 3, (Object)null));
      Color var23 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var23, "LABEL");
      $this$_init__u24lambda_u2d3.setColor((ColorConstraint)UtilitiesKt.toConstraint(var23));
      $this$_init__u24lambda_u2d3.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getTextContainer());
      var13 = CF4M.INSTANCE.settingManager.getDescription(this.module, this.setting);
      Intrinsics.checkNotNullExpressionValue(var13, "INSTANCE.settingManager.…cription(module, setting)");
      $this$constrain$iv = (UIComponent)(new UIWrappedText(var13, false, (Color)null, false, false, 0.0F, (String)null, 126, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var8 = false;
      $this$_init__u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      var10 = false;
      $this$_init__u24lambda_u2d3.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d3.setY((YConstraint)ConstraintsKt.plus((SuperConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)2, false, false, 3, (Object)null)));
      var23 = ColorUtils.SUB_LABEL;
      Intrinsics.checkNotNullExpressionValue(var23, "SUB_LABEL");
      $this$_init__u24lambda_u2d3.setColor((ColorConstraint)UtilitiesKt.toConstraint(var23));
      $this$_init__u24lambda_u2d3.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getTextContainer());
      $this$constrain$iv = (UIComponent)this;
      $i$f$constrain = false;
      var8 = false;
      $this$_init__u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      var10 = false;
      $this$_init__u24lambda_u2d3.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d3.setY((YConstraint)ConstraintsKt.plus((SuperConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d3.setWidth((WidthConstraint)ConstraintsKt.minus((SuperConstraint)(new FillConstraint(false)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d3.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)45, false, false, 3, (Object)null));
      UIComponent var10000 = $this$constrain$iv;
      Color var14 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var14, "M_BORDER");
      ComponentsKt.effect(var10000, (Effect)(new OutlineEffect(var14, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null)));
      Object var15 = this.setting;
      SettingComponent var26;
      if (var15 instanceof BooleanSetting) {
         var26 = (SettingComponent)(new SwitchComponent(((BooleanSetting)this.setting).isEnabled()));
      } else {
         int var10002;
         if (var15 instanceof IntegerSetting) {
            Integer var5 = ((IntegerSetting)this.setting).getCurrent();
            Intrinsics.checkNotNullExpressionValue(var5, "setting.current");
            var10002 = ((Number)var5).intValue();
            var5 = ((IntegerSetting)this.setting).getMin();
            Intrinsics.checkNotNullExpressionValue(var5, "setting.min");
            int var10003 = ((Number)var5).intValue();
            var5 = ((IntegerSetting)this.setting).getMax();
            Intrinsics.checkNotNullExpressionValue(var5, "setting.max");
            var26 = (SettingComponent)(new SliderComponent(var10002, var10003, ((Number)var5).intValue()));
         } else if (var15 instanceof FloatSetting) {
            Float var18 = ((FloatSetting)this.setting).getCurrent();
            Intrinsics.checkNotNullExpressionValue(var18, "setting.current");
            float var27 = ((Number)var18).floatValue();
            var18 = ((FloatSetting)this.setting).getMin();
            Intrinsics.checkNotNullExpressionValue(var18, "setting.min");
            float var28 = ((Number)var18).floatValue();
            var18 = ((FloatSetting)this.setting).getMax();
            Intrinsics.checkNotNullExpressionValue(var18, "setting.max");
            var26 = (SettingComponent)(new DecimalSliderComponent(var27, var28, ((Number)var18).floatValue(), 1));
         } else if (var15 instanceof ModeSetting) {
            var10002 = ((ModeSetting)this.setting).getModes().indexOf(((ModeSetting)this.setting).getCurrent());
            List var19 = ((ModeSetting)this.setting).getModes();
            Intrinsics.checkNotNullExpressionValue(var19, "setting.modes");
            var26 = (SettingComponent)(new SelectorComponent(var10002, var19));
         } else if (var15 instanceof StringSetting) {
            String var20 = ((StringSetting)this.setting).getCurrent();
            Intrinsics.checkNotNullExpressionValue(var20, "setting.current");
            var26 = (SettingComponent)(new TextComponent(var20, "<empty>", false, false));
         } else {
            var26 = (SettingComponent)(new SwitchComponent(true));
         }
      }

      SettingComponent comp = var26;
      UIComponent $this$constrain$iv = (UIComponent)comp;
      int $i$f$constrain = false;
      int var22 = false;
      UIConstraints $this$_init__u24lambda_u2d4 = $this$constrain$iv.getConstraints();
      int var25 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)UtilitiesKt.pixels$default((Number)12, true, false, 2, (Object)null));
      $this$_init__u24lambda_u2d4.setY(comp instanceof SwitchComponent ? (YConstraint)UtilitiesKt.pixels$default((Number)18, false, false, 3, (Object)null) : (YConstraint)UtilitiesKt.pixels$default((Number)12, false, false, 3, (Object)null));
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this);
      comp.onValueChange((Function1)(new Function1<Object, Unit>() {
         public final void invoke(@Nullable Object it) {
            Object var2 = SettingLabel.this.setting;
            if (var2 instanceof BooleanSetting) {
               BooleanSetting var10000 = (BooleanSetting)SettingLabel.this.setting;
               if (it == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
               }

               var10000.setState((Boolean)it);
            } else if (var2 instanceof IntegerSetting) {
               IntegerSetting var3 = (IntegerSetting)SettingLabel.this.setting;
               if (it == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
               }

               var3.setCurrent((Integer)it);
            } else if (var2 instanceof FloatSetting) {
               FloatSetting var4 = (FloatSetting)SettingLabel.this.setting;
               if (it == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Float");
               }

               var4.setCurrent((Float)it);
            } else if (var2 instanceof ModeSetting) {
               ModeSetting var5 = (ModeSetting)SettingLabel.this.setting;
               List var10001 = ((ModeSetting)SettingLabel.this.setting).getModes();
               if (it == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
               }

               var5.setCurrent((String)var10001.get((Integer)it));
            } else if (var2 instanceof StringSetting) {
               StringSetting var6 = (StringSetting)SettingLabel.this.setting;
               if (it == null) {
                  throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
               }

               var6.setCurrent((String)it);
            }

         }
      }));
   }

   private final UIContainer getTextContainer() {
      return (UIContainer)this.textContainer$delegate.getValue(this, $$delegatedProperties[0]);
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(SettingLabel.class, "textContainer", "getTextContainer()Lgg/essential/elementa/components/UIContainer;", 0)))};
      $$delegatedProperties = var0;
   }
}

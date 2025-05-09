package xyz.apfelmus.cheeto.client.clickgui;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.SiblingConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.font.DefaultFonts;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
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
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/ConfigLabel;", "Lgg/essential/elementa/components/UIContainer;", "gui", "Lxyz/apfelmus/cheeto/client/clickgui/ConfigGUI;", "config", "", "(Lxyz/apfelmus/cheeto/client/clickgui/ConfigGUI;Ljava/lang/String;)V", "isSelected", "", "()Z", "setSelected", "(Z)V", "text", "Lgg/essential/elementa/components/UIText;", "getText", "()Lgg/essential/elementa/components/UIText;", "text$delegate", "Lkotlin/properties/ReadWriteProperty;", "deselect", "", "select", "Cheeto"}
)
public final class ConfigLabel extends UIContainer {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final ConfigGUI gui;
   @NotNull
   private final String config;
   @NotNull
   private final ReadWriteProperty text$delegate;
   private boolean isSelected;

   public ConfigLabel(@NotNull ConfigGUI gui, @NotNull String config) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(config, "config");
      super();
      this.gui = gui;
      this.config = config;
      UIComponent $this$onLeftClick$iv = (UIComponent)(new UIText(this.config, false, (Color)null, 6, (DefaultConstructorMarker)null));
      int $i$f$onLeftClick = false;
      int var7 = false;
      UIConstraints $this$_init__u24lambda_u2d1 = $this$onLeftClick$iv.getConstraints();
      int var9 = false;
      $this$_init__u24lambda_u2d1.setX((XConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d1.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d1.setTextScale((HeightConstraint)UtilitiesKt.pixels$default((Number)1, false, false, 3, (Object)null));
      Color var10 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var10, "LABEL");
      $this$_init__u24lambda_u2d1.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      $this$_init__u24lambda_u2d1.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      this.text$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      var7 = false;
      $this$_init__u24lambda_u2d1 = $this$onLeftClick$iv.getConstraints();
      var9 = false;
      $this$_init__u24lambda_u2d1.setX((XConstraint)UtilitiesKt.pixels$default((Number)0, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d1.setY((YConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d1.setWidth((WidthConstraint)(new ChildBasedMaxSizeConstraint()));
      $this$_init__u24lambda_u2d1.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null));
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new ConfigLabel$special$$inlined$onLeftClick$1(this)));
      this.onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
            Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
            if (!ConfigLabel.this.isSelected()) {
               UIComponent $this$animate$iv = (UIComponent)ConfigLabel.this.getText();
               int $i$f$animate = false;
               int var6 = false;
               AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
               int var9 = false;
               AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
               Color var10 = ColorUtils.TEXT_HOVERED;
               Intrinsics.checkNotNullExpressionValue(var10, "TEXT_HOVERED");
               AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.5F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
               $this$animate$iv.animateTo(anim$iv);
            }

         }
      }));
      this.onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
            Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
            if (!ConfigLabel.this.isSelected()) {
               UIComponent $this$animate$iv = (UIComponent)ConfigLabel.this.getText();
               int $i$f$animate = false;
               int var6 = false;
               AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
               int var9 = false;
               AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
               Color var10 = ColorUtils.LABEL;
               Intrinsics.checkNotNullExpressionValue(var10, "LABEL");
               AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.5F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
               $this$animate$iv.animateTo(anim$iv);
            }

         }
      }));
   }

   private final UIText getText() {
      return (UIText)this.text$delegate.getValue(this, $$delegatedProperties[0]);
   }

   public final boolean isSelected() {
      return this.isSelected;
   }

   public final void setSelected(boolean var1) {
      this.isSelected = var1;
   }

   public final void select() {
      this.gui.selectConfig(this.config);
      this.isSelected = true;
      UIComponent $this$animate$iv = (UIComponent)this.getText();
      int $i$f$animate = false;
      int var5 = false;
      AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
      int var8 = false;
      AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
      Color var9 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(var9, "WHITE");
      AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.5F, (ColorConstraint)UtilitiesKt.toConstraint(var9), 0.0F, 8, (Object)null);
      $this$animate$iv.animateTo(anim$iv);
   }

   public final void deselect() {
      this.isSelected = false;
      UIComponent $this$animate$iv = (UIComponent)this.getText();
      int $i$f$animate = false;
      int var5 = false;
      AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
      int var8 = false;
      AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
      Color var9 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var9, "LABEL");
      AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.5F, (ColorConstraint)UtilitiesKt.toConstraint(var9), 0.0F, 8, (Object)null);
      $this$animate$iv.animateTo(anim$iv);
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigLabel.class, "text", "getText()Lgg/essential/elementa/components/UIText;", 0)))};
      $$delegatedProperties = var0;
   }
}

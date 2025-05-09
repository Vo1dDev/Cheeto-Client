package xyz.apfelmus.cheeto.client.clickgui;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.SiblingConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.font.DefaultFonts;
import java.awt.Color;
import java.util.Locale;
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
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0017\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0016\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001e"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/CategoryLabel;", "Lgg/essential/elementa/components/UIContainer;", "gui", "Lxyz/apfelmus/cheeto/client/clickgui/ConfigGUI;", "category", "Lxyz/apfelmus/cf4m/module/Category;", "(Lxyz/apfelmus/cheeto/client/clickgui/ConfigGUI;Lxyz/apfelmus/cf4m/module/Category;)V", "categoryName", "getCategoryName", "()Lxyz/apfelmus/cf4m/module/Category;", "setCategoryName", "(Lxyz/apfelmus/cf4m/module/Category;)V", "isSelected", "", "()Z", "setSelected", "(Z)V", "text", "Lgg/essential/elementa/components/UIText;", "getText", "()Lgg/essential/elementa/components/UIText;", "text$delegate", "Lkotlin/properties/ReadWriteProperty;", "textContainer", "getTextContainer", "()Lgg/essential/elementa/components/UIContainer;", "textContainer$delegate", "deselect", "", "select", "Cheeto"}
)
public final class CategoryLabel extends UIContainer {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final ConfigGUI gui;
   @NotNull
   private final Category category;
   @NotNull
   private Category categoryName;
   @NotNull
   private final ReadWriteProperty textContainer$delegate;
   @NotNull
   private final ReadWriteProperty text$delegate;
   private boolean isSelected;

   public CategoryLabel(@NotNull ConfigGUI gui, @NotNull Category category) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(category, "category");
      super();
      this.gui = gui;
      this.category = category;
      this.categoryName = this.category;
      Color var3 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var3, "M_BORDER");
      UIComponent $this$onLeftClick$iv = (UIComponent)(new UIBlock(var3));
      int $i$f$onLeftClick = false;
      int var8 = false;
      UIConstraints $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      int var10 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)UtilitiesKt.percent((Number)100));
      ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      $this$onLeftClick$iv = (UIComponent)(new UIContainer());
      $i$f$onLeftClick = false;
      var8 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      var10 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d4.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedMaxSizeConstraint()), (SuperConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null)));
      this.textContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      CategoryLabel var10000 = this;
      String var22 = this.category.name().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var22, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      String var21 = var22;
      String var25;
      if (((CharSequence)var22).length() > 0) {
         StringBuilder var10001 = new StringBuilder();
         char p0 = var22.charAt(0);
         StringBuilder var13 = var10001;
         int var5 = false;
         String var14 = CharsKt.titlecase(p0);
         var10000 = this;
         var10001 = var13.append(var14);
         byte var24 = 1;
         String var6 = var21.substring(var24);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).substring(startIndex)");
         var25 = var10001.append(var6).toString();
      } else {
         var25 = var22;
      }

      Object var15 = null;
      byte var16 = 6;
      Object var17 = null;
      boolean var18 = false;
      String var19 = var25;
      $this$onLeftClick$iv = (UIComponent)(new UIText(var19, var18, (Color)var17, var16, (DefaultConstructorMarker)var15));
      $i$f$onLeftClick = false;
      var8 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      CategoryLabel var12 = var10000;
      var10 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d4.setY((YConstraint)(new CenterConstraint()));
      Color var11 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var11, "LABEL");
      $this$_init__u24lambda_u2d4.setColor((ColorConstraint)UtilitiesKt.toConstraint(var11));
      $this$_init__u24lambda_u2d4.setTextScale((HeightConstraint)UtilitiesKt.pixels$default((Number)1.5F, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d4.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      var12.text$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this.getTextContainer()), this, $$delegatedProperties[1]);
      var3 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var3, "M_BORDER");
      $this$onLeftClick$iv = (UIComponent)(new UIBlock(var3));
      $i$f$onLeftClick = false;
      var8 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      var10 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)ConstraintsKt.minus((SuperConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)UtilitiesKt.percent((Number)100));
      ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      var8 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      var10 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d4.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)(new ChildBasedMaxSizeConstraint()));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)14, false, false, 3, (Object)null));
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new CategoryLabel$special$$inlined$onLeftClick$1(this)));
      this.onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
            Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
            if (!CategoryLabel.this.isSelected()) {
               UIComponent $this$animate$iv = (UIComponent)CategoryLabel.this.getText();
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
            if (!CategoryLabel.this.isSelected()) {
               UIComponent $this$animate$iv = (UIComponent)CategoryLabel.this.getText();
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

   @NotNull
   public final Category getCategoryName() {
      return this.categoryName;
   }

   public final void setCategoryName(@NotNull Category var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.categoryName = var1;
   }

   private final UIContainer getTextContainer() {
      return (UIContainer)this.textContainer$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final UIText getText() {
      return (UIText)this.text$delegate.getValue(this, $$delegatedProperties[1]);
   }

   public final boolean isSelected() {
      return this.isSelected;
   }

   public final void setSelected(boolean var1) {
      this.isSelected = var1;
   }

   public final void select() {
      this.gui.selectCategory(this.category);
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
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(CategoryLabel.class, "textContainer", "getTextContainer()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(CategoryLabel.class, "text", "getText()Lgg/essential/elementa/components/UIText;", 0)))};
      $$delegatedProperties = var0;
   }
}

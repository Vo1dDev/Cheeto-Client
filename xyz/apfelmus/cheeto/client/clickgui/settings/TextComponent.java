package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIImage;
import gg.essential.elementa.components.input.AbstractTextInput;
import gg.essential.elementa.components.input.UIMultilineTextInput;
import gg.essential.elementa.components.input.UIPasswordInput;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.ChildBasedSizeConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import org.jetbrains.annotations.NotNull;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/TextComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/SettingComponent;", "initial", "", "placeholder", "wrap", "", "protected", "(Ljava/lang/String;Ljava/lang/String;ZZ)V", "hasSetInitialText", "textHolder", "Lgg/essential/elementa/components/UIBlock;", "textInput", "Lgg/essential/elementa/components/input/AbstractTextInput;", "animationFrame", "", "closePopups", "instantly", "Cheeto"}
)
public final class TextComponent extends SettingComponent {
   @NotNull
   private final String initial;
   @NotNull
   private final UIBlock textHolder;
   @NotNull
   private final AbstractTextInput textInput;
   private boolean hasSetInitialText;

   public TextComponent(@NotNull String initial, @NotNull String placeholder, boolean wrap, boolean var4) {
      Intrinsics.checkNotNullParameter(initial, "initial");
      Intrinsics.checkNotNullParameter(placeholder, "placeholder");
      super();
      this.initial = initial;
      UIComponent $this$constrain$iv = (UIComponent)(new UIBlock((ColorConstraint)null, 1, (DefaultConstructorMarker)null));
      int $i$f$constrain = false;
      int var10 = false;
      UIConstraints $this$_init__u24lambda_u2d8 = $this$constrain$iv.getConstraints();
      int var12 = false;
      $this$_init__u24lambda_u2d8.setWidth((WidthConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)var4 ? 14 : 6, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d8.setHeight((HeightConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)6, false, false, 3, (Object)null)));
      Color var13 = ColorUtils.MENU_BG;
      Intrinsics.checkNotNullExpressionValue(var13, "MENU_BG");
      $this$_init__u24lambda_u2d8.setColor((ColorConstraint)UtilitiesKt.toConstraint(var13));
      UIComponent var10001 = ComponentsKt.childOf($this$constrain$iv, (UIComponent)this);
      Color var16 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var16, "M_BORDER");
      this.textHolder = (UIBlock)ComponentsKt.effect(var10001, (Effect)(new OutlineEffect(var16, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null)));
      TextComponent var10000;
      AbstractTextInput var22;
      if (wrap) {
         $this$constrain$iv = (UIComponent)(new UIMultilineTextInput(placeholder, false, (Color)null, (Color)null, false, (Color)null, (Color)null, (Color)null, 254, (DefaultConstructorMarker)null));
         $i$f$constrain = false;
         var10 = false;
         $this$_init__u24lambda_u2d8 = $this$constrain$iv.getConstraints();
         var12 = false;
         $this$_init__u24lambda_u2d8.setX((XConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         $this$_init__u24lambda_u2d8.setY((YConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         $this$_init__u24lambda_u2d8.setWidth(BasicConstraintsKt.basicWidthConstraint((Function1)(new Function1<UIComponent, Float>() {
            @NotNull
            public final Float invoke(@NotNull UIComponent it) {
               Intrinsics.checkNotNullParameter(it, "it");
               return TextComponent.this.getParent().getWidth() * 0.4F;
            }
         })));
         var10000 = this;
         var22 = (AbstractTextInput)((UIMultilineTextInput)$this$constrain$iv).setMaxLines(10);
      } else if (var4) {
         $this$constrain$iv = (UIComponent)(new UIPasswordInput('\u0000', placeholder, false, (Color)null, (Color)null, false, (Color)null, (Color)null, (Color)null, 509, (DefaultConstructorMarker)null));
         $i$f$constrain = false;
         var10 = false;
         $this$_init__u24lambda_u2d8 = $this$constrain$iv.getConstraints();
         var12 = false;
         $this$_init__u24lambda_u2d8.setX((XConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         $this$_init__u24lambda_u2d8.setY((YConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         var10000 = this;
         var22 = (AbstractTextInput)((UIPasswordInput)$this$constrain$iv).setMinWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)50, false, false, 3, (Object)null)).setMaxWidth(BasicConstraintsKt.basicWidthConstraint((Function1)(new Function1<UIComponent, Float>() {
            @NotNull
            public final Float invoke(@NotNull UIComponent it) {
               Intrinsics.checkNotNullParameter(it, "it");
               return TextComponent.this.getParent().getWidth() * 0.5F;
            }
         })));
      } else {
         $this$constrain$iv = (UIComponent)(new UITextInput(placeholder, false, (Color)null, (Color)null, false, (Color)null, (Color)null, (Color)null, 254, (DefaultConstructorMarker)null));
         $i$f$constrain = false;
         var10 = false;
         $this$_init__u24lambda_u2d8 = $this$constrain$iv.getConstraints();
         var12 = false;
         $this$_init__u24lambda_u2d8.setX((XConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         $this$_init__u24lambda_u2d8.setY((YConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         var10000 = this;
         var22 = (AbstractTextInput)((UITextInput)$this$constrain$iv).setMinWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)50, false, false, 3, (Object)null)).setMaxWidth(BasicConstraintsKt.basicWidthConstraint((Function1)(new Function1<UIComponent, Float>() {
            @NotNull
            public final Float invoke(@NotNull UIComponent it) {
               Intrinsics.checkNotNullParameter(it, "it");
               return TextComponent.this.getParent().getWidth() * 0.5F;
            }
         })));
      }

      var10000.textInput = var22;
      ComponentsKt.childOf((UIComponent)this.textInput, (UIComponent)this.textHolder);
      $this$constrain$iv = (UIComponent)this.textInput.onUpdate((Function1)(new Function1<String, Unit>() {
         public final void invoke(@NotNull String newText) {
            Intrinsics.checkNotNullParameter(newText, "newText");
            SettingComponent.changeValue$default((SettingComponent)TextComponent.this, newText, false, 2, (Object)null);
         }
      }));
      $i$f$constrain = false;
      $this$constrain$iv.onMouseClick((Function2)(new TextComponent$special$$inlined$onLeftClick$1(this))).onFocus((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onFocus) {
            Intrinsics.checkNotNullParameter($this$onFocus, "$this$onFocus");
            TextComponent.this.textInput.setActive(true);
         }
      })).onFocusLost((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onFocusLost) {
            Intrinsics.checkNotNullParameter($this$onFocusLost, "$this$onFocusLost");
            TextComponent.this.textInput.setActive(false);
         }
      }));
      AbstractTextInput var23 = this.textInput;
      var16 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var16, "LABEL");
      var23.setColor(var16);
      if (var4) {
         final BooleanRef toggle = new BooleanRef();
         UIComponent $this$onLeftClick$iv = (UIComponent)UIImage.Companion.ofResourceCached("/vigilance/eye.png");
         int $i$f$onLeftClick = false;
         int var19 = false;
         UIConstraints $this$_init__u24lambda_u2d5 = $this$onLeftClick$iv.getConstraints();
         int var21 = false;
         $this$_init__u24lambda_u2d5.setY((YConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
         $this$_init__u24lambda_u2d5.setX((XConstraint)UtilitiesKt.pixels$default((Number)3, true, false, 2, (Object)null));
         $this$_init__u24lambda_u2d5.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)12, false, false, 3, (Object)null));
         $this$_init__u24lambda_u2d5.setHeight(BasicConstraintsKt.basicHeightConstraint((Function1)(new Function1<UIComponent, Float>() {
            @NotNull
            public final Float invoke(@NotNull UIComponent it) {
               Intrinsics.checkNotNullParameter(it, "it");
               return TextComponent.this.textInput.getHeight();
            }
         })));
         Color var14 = ColorUtils.SUB_LABEL;
         Intrinsics.checkNotNullExpressionValue(var14, "SUB_LABEL");
         $this$_init__u24lambda_u2d5.setColor((ColorConstraint)UtilitiesKt.toConstraint(var14));
         $this$onLeftClick$iv = ((UIImage)$this$onLeftClick$iv).onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
            public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
               Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
               if (!toggle.element) {
                  int $i$f$animate = false;
                  int var6 = false;
                  AnimatingConstraints anim$iv = $this$onMouseEnter.makeAnimation();
                  int var9 = false;
                  AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
                  Color var10 = ColorUtils.LABEL;
                  Intrinsics.checkNotNullExpressionValue(var10, "LABEL");
                  AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.2F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
                  $this$onMouseEnter.animateTo(anim$iv);
               }

            }
         })).onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
            public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
               Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
               if (!toggle.element) {
                  int $i$f$animate = false;
                  int var6 = false;
                  AnimatingConstraints anim$iv = $this$onMouseLeave.makeAnimation();
                  int var9 = false;
                  AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
                  Color var10 = ColorUtils.SUB_LABEL;
                  Intrinsics.checkNotNullExpressionValue(var10, "SUB_LABEL");
                  AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.2F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
                  $this$onMouseLeave.animateTo(anim$iv);
               }

            }
         }));
         $i$f$onLeftClick = false;
         ComponentsKt.childOf($this$onLeftClick$iv.onMouseClick((Function2)(new TextComponent$special$$inlined$onLeftClick$2(toggle, this))), (UIComponent)this.textHolder);
         this.textHolder.setHeight(BasicConstraintsKt.basicHeightConstraint((Function1)(new Function1<UIComponent, Float>() {
            @NotNull
            public final Float invoke(@NotNull UIComponent it) {
               Intrinsics.checkNotNullParameter(it, "it");
               return TextComponent.this.textInput.getHeight() + 6.0F;
            }
         })));
      }

      $this$constrain$iv = (UIComponent)this;
      $i$f$constrain = false;
      var10 = false;
      $this$_init__u24lambda_u2d8 = $this$constrain$iv.getConstraints();
      var12 = false;
      $this$_init__u24lambda_u2d8.setWidth((WidthConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d8.setHeight((HeightConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
   }

   public void animationFrame() {
      super.animationFrame();
      if (!this.hasSetInitialText) {
         this.textInput.setText(this.initial);
         this.hasSetInitialText = true;
      }

   }

   public void closePopups(boolean instantly) {
      this.textInput.setActive(false);
   }
}

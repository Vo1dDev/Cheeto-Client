package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIRoundedRectangle;
import gg.essential.elementa.components.UIWrappedText;
import gg.essential.elementa.constraints.CenterConstraint;
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
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.font.DefaultFonts;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.State;
import gg.essential.elementa.utils.ExtensionsKt;
import gg.essential.vigilance.data.CallablePropertyValue;
import gg.essential.vigilance.data.PropertyData;
import gg.essential.vigilance.data.PropertyValue;
import gg.essential.vigilance.gui.ExpandingClickEffect;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
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
import org.jetbrains.annotations.Nullable;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u001b\b\u0016\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u0014\u0010\u001c\u001a\u00020\u00002\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001bJ\u0006\u0010\u0017\u001a\u00020\u0003J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0003R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0011\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0012\u0010\u000eR\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0010\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u001bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/ButtonComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/SettingComponent;", "placeholder", "", "data", "Lgg/essential/vigilance/data/PropertyData;", "(Ljava/lang/String;Lgg/essential/vigilance/data/PropertyData;)V", "callback", "Lkotlin/Function0;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "container", "Lgg/essential/elementa/components/UIRoundedRectangle;", "getContainer", "()Lgg/essential/elementa/components/UIRoundedRectangle;", "container$delegate", "Lkotlin/properties/ReadWriteProperty;", "contentContainer", "getContentContainer", "contentContainer$delegate", "listener", "text", "Lgg/essential/elementa/components/UIWrappedText;", "getText", "()Lgg/essential/elementa/components/UIWrappedText;", "text$delegate", "textState", "Lgg/essential/elementa/state/State;", "bindText", "newTextState", "setText", "Companion", "Cheeto"}
)
public final class ButtonComponent extends SettingComponent {
   @NotNull
   public static final ButtonComponent.Companion Companion;
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final Function0<Unit> callback;
   @NotNull
   private State<String> textState;
   @NotNull
   private Function0<Unit> listener;
   @NotNull
   private final ReadWriteProperty container$delegate;
   @NotNull
   private final ReadWriteProperty contentContainer$delegate;
   @NotNull
   private final ReadWriteProperty text$delegate;

   public ButtonComponent(@Nullable String placeholder, @NotNull Function0<Unit> callback) {
      Intrinsics.checkNotNullParameter(callback, "callback");
      super();
      this.callback = callback;
      ButtonComponent var10000 = this;
      String var10001 = placeholder;
      if (placeholder == null) {
         var10001 = "";
      }

      CharSequence var3 = (CharSequence)var10001;
      boolean $i$f$onLeftClick;
      Object var15;
      if (var3.length() == 0) {
         $i$f$onLeftClick = false;
         var15 = "Activate";
         var10000 = this;
      } else {
         var15 = var3;
      }

      Object var12 = var15;
      var10000.textState = (State)(new BasicState(var12)).map((Function1)null.INSTANCE);
      this.listener = this.textState.onSetValue((Function1)(new Function1<String, Unit>() {
         public final void invoke(@NotNull String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            ButtonComponent.this.getText().setText((String)ButtonComponent.this.textState.get());
         }
      }));
      UIComponent $this$onLeftClick$iv = (UIComponent)(new UIRoundedRectangle(2.5F));
      $i$f$onLeftClick = false;
      int var7 = false;
      UIConstraints $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      int var9 = false;
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)2, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)2, false, false, 3, (Object)null)));
      Color var10 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var10, "M_BORDER");
      $this$_init__u24lambda_u2d4.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      this.container$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      $this$onLeftClick$iv = (UIComponent)(new UIRoundedRectangle(2.5F));
      $i$f$onLeftClick = false;
      var7 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      var9 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d4.setY((YConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      var10 = ColorUtils.MENU_BG;
      Intrinsics.checkNotNullExpressionValue(var10, "MENU_BG");
      $this$_init__u24lambda_u2d4.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      this.contentContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this.getContainer()), this, $$delegatedProperties[1]);
      $this$onLeftClick$iv = (UIComponent)(new UIWrappedText((String)this.textState.get(), false, (Color)null, false, true, 0.0F, (String)null, 110, (DefaultConstructorMarker)null));
      $i$f$onLeftClick = false;
      var7 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      var9 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d4.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)ConstraintsKt.coerceAtMost((SuperConstraint)$this$_init__u24lambda_u2d4.getWidth(), (SuperConstraint)UtilitiesKt.pixels$default((Number)300, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      var10 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var10, "LABEL");
      $this$_init__u24lambda_u2d4.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      $this$_init__u24lambda_u2d4.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      this.text$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this.getContentContainer()), this, $$delegatedProperties[2]);
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      var7 = false;
      $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      var9 = false;
      $this$_init__u24lambda_u2d4.setWidth((WidthConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d4.setHeight((HeightConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      Color var14 = ColorUtils.SELECTED;
      Intrinsics.checkNotNullExpressionValue(var14, "SELECTED");
      this.enableEffect((Effect)(new ExpandingClickEffect(ExtensionsKt.withAlpha(var14, 0.5F), 0.0F, (UIComponent)this.getContentContainer(), 2, (DefaultConstructorMarker)null)));
      $this$onLeftClick$iv = this.getContainer().onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
            Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
            UIComponent $this$animate$iv = (UIComponent)ButtonComponent.this.getContainer();
            int $i$f$animate = false;
            int var6 = false;
            AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
            int var9 = false;
            AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
            Color var10 = ColorUtils.SELECTED;
            Intrinsics.checkNotNullExpressionValue(var10, "SELECTED");
            AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.5F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
            $this$animate$iv.animateTo(anim$iv);
         }
      })).onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
            Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
            UIComponent $this$animate$iv = (UIComponent)ButtonComponent.this.getContainer();
            int $i$f$animate = false;
            int var6 = false;
            AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
            int var9 = false;
            AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
            Color var10 = ColorUtils.M_BORDER;
            Intrinsics.checkNotNullExpressionValue(var10, "M_BORDER");
            AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.5F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
            $this$animate$iv.animateTo(anim$iv);
         }
      }));
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new ButtonComponent$special$$inlined$onLeftClick$1(this)));
   }

   // $FF: synthetic method
   public ButtonComponent(String var1, Function0 var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 1) != 0) {
         var1 = null;
      }

      this(var1, var2);
   }

   private final UIRoundedRectangle getContainer() {
      return (UIRoundedRectangle)this.container$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final UIRoundedRectangle getContentContainer() {
      return (UIRoundedRectangle)this.contentContainer$delegate.getValue(this, $$delegatedProperties[1]);
   }

   private final UIWrappedText getText() {
      return (UIWrappedText)this.text$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ButtonComponent bindText(@NotNull State<String> newTextState) {
      Intrinsics.checkNotNullParameter(newTextState, "newTextState");
      final ButtonComponent $this$bindText_u24lambda_u2d6 = (ButtonComponent)this;
      int var4 = false;
      $this$bindText_u24lambda_u2d6.listener.invoke();
      $this$bindText_u24lambda_u2d6.textState = newTextState;
      $this$bindText_u24lambda_u2d6.getText().bindText((State)$this$bindText_u24lambda_u2d6.textState.map((Function1)null.INSTANCE));
      $this$bindText_u24lambda_u2d6.listener = $this$bindText_u24lambda_u2d6.textState.onSetValue((Function1)(new Function1<String, Unit>() {
         public final void invoke(@NotNull String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            $this$bindText_u24lambda_u2d6.getText().setText((String)$this$bindText_u24lambda_u2d6.textState.get());
         }
      }));
      return (ButtonComponent)this;
   }

   @NotNull
   public final String getText() {
      return (String)this.textState.get();
   }

   @NotNull
   public final ButtonComponent setText(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      ButtonComponent $this$setText_u24lambda_u2d7 = (ButtonComponent)this;
      int var4 = false;
      $this$setText_u24lambda_u2d7.textState.set(text);
      return (ButtonComponent)this;
   }

   public ButtonComponent(@Nullable String placeholder, @NotNull PropertyData data) {
      Intrinsics.checkNotNullParameter(data, "data");
      this(placeholder, Companion.callbackFromPropertyData(data));
   }

   // $FF: synthetic method
   public ButtonComponent(String var1, PropertyData var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 1) != 0) {
         var1 = null;
      }

      this(var1, var2);
   }

   // $FF: synthetic method
   public static final Function0 access$getCallback$p(ButtonComponent $this) {
      return $this.callback;
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ButtonComponent.class, "container", "getContainer()Lgg/essential/elementa/components/UIRoundedRectangle;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ButtonComponent.class, "contentContainer", "getContentContainer()Lgg/essential/elementa/components/UIRoundedRectangle;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ButtonComponent.class, "text", "getText()Lgg/essential/elementa/components/UIWrappedText;", 0)))};
      $$delegatedProperties = var0;
      Companion = new ButtonComponent.Companion((DefaultConstructorMarker)null);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\b"},
      d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/ButtonComponent$Companion;", "", "()V", "callbackFromPropertyData", "Lkotlin/Function0;", "", "data", "Lgg/essential/vigilance/data/PropertyData;", "Cheeto"}
   )
   public static final class Companion {
      private Companion() {
      }

      private final Function0<Unit> callbackFromPropertyData(final PropertyData data) {
         final PropertyValue value = data.getValue();
         if (!(value instanceof CallablePropertyValue)) {
            throw new IllegalStateException();
         } else {
            return (Function0)(new Function0<Unit>() {
               public final void invoke() {
                  ((CallablePropertyValue)value).invoke(data.getInstance());
               }
            });
         }
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}

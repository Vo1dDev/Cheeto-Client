package xyz.apfelmus.cheeto.client.clickgui;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.Window;
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
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.manager.ModuleManager;
import xyz.apfelmus.cheeto.client.clickgui.settings.ButtonComponent;
import xyz.apfelmus.cheeto.client.clickgui.settings.CheckboxComponent;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\f"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/KeybindLabel;", "Lgg/essential/elementa/components/UIContainer;", "module", "", "window", "Lgg/essential/elementa/components/Window;", "(Ljava/lang/Object;Lgg/essential/elementa/components/Window;)V", "textContainer", "getTextContainer", "()Lgg/essential/elementa/components/UIContainer;", "textContainer$delegate", "Lkotlin/properties/ReadWriteProperty;", "Cheeto"}
)
public final class KeybindLabel extends UIContainer {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final Object module;
   @NotNull
   private final ReadWriteProperty textContainer$delegate;

   public KeybindLabel(@NotNull Object module, @NotNull Window window) {
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(window, "window");
      super();
      this.module = module;
      UIComponent $this$constrain$iv = (UIComponent)(new UIContainer());
      int $i$f$constrain = false;
      int var9 = false;
      UIConstraints $this$_init__u24lambda_u2d2 = $this$constrain$iv.getConstraints();
      int var11 = false;
      $this$_init__u24lambda_u2d2.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d2.setWidth((WidthConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d2.setHeight((HeightConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      this.textContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      $this$constrain$iv = (UIComponent)(new UIText("Enable", false, (Color)null, 6, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var9 = false;
      $this$_init__u24lambda_u2d2 = $this$constrain$iv.getConstraints();
      var11 = false;
      $this$_init__u24lambda_u2d2.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d2.setY((YConstraint)(new CenterConstraint()));
      $this$_init__u24lambda_u2d2.setTextScale((HeightConstraint)UtilitiesKt.pixels$default((Number)1.5F, false, false, 3, (Object)null));
      Color var12 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var12, "LABEL");
      $this$_init__u24lambda_u2d2.setColor((ColorConstraint)UtilitiesKt.toConstraint(var12));
      $this$_init__u24lambda_u2d2.setFontProvider(DefaultFonts.getVANILLA_FONT_RENDERER());
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getTextContainer());
      $this$constrain$iv = (UIComponent)this;
      $i$f$constrain = false;
      var9 = false;
      $this$_init__u24lambda_u2d2 = $this$constrain$iv.getConstraints();
      var11 = false;
      $this$_init__u24lambda_u2d2.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d2.setY((YConstraint)ConstraintsKt.plus((SuperConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d2.setWidth((WidthConstraint)ConstraintsKt.minus((SuperConstraint)(new FillConstraint(false)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d2.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)45, false, false, 3, (Object)null));
      UIComponent var10000 = $this$constrain$iv;
      Color var17 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var17, "M_BORDER");
      ComponentsKt.effect(var10000, (Effect)(new OutlineEffect(var17, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null)));
      CheckboxComponent enable = new CheckboxComponent(CF4M.INSTANCE.moduleManager.isEnabled(this.module));
      UIComponent $this$constrain$iv = (UIComponent)enable;
      int $i$f$constrain = false;
      int var22 = false;
      UIConstraints $this$_init__u24lambda_u2d3 = $this$constrain$iv.getConstraints();
      int var24 = false;
      $this$_init__u24lambda_u2d3.setX((XConstraint)(new SiblingConstraint(20.0F, false, 2, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d3.setY((YConstraint)UtilitiesKt.pixels$default((Number)12, false, false, 3, (Object)null));
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this);
      enable.onValueChange((Function1)(new Function1<Object, Unit>() {
         public final void invoke(@Nullable Object it) {
            ModuleManager var10000 = CF4M.INSTANCE.moduleManager;
            Object var10001 = KeybindLabel.this.module;
            if (it == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
            } else {
               var10000.setEnabled(var10001, (Boolean)it);
            }
         }
      }));
      String key = Keyboard.getKeyName(CF4M.INSTANCE.moduleManager.getKey(this.module));
      final BooleanRef changing = new BooleanRef();
      UIComponent $this$onLeftClick$iv = (UIComponent)(new ButtonComponent(Intrinsics.stringPlus("Keybind: ", key), (Function0)(new Function0<Unit>() {
         public final void invoke() {
            changing.element = !changing.element;
         }
      })));
      int $i$f$onLeftClick = false;
      int var13 = false;
      UIConstraints $this$_init__u24lambda_u2d4 = $this$onLeftClick$iv.getConstraints();
      int var15 = false;
      $this$_init__u24lambda_u2d4.setX((XConstraint)UtilitiesKt.pixels$default((Number)12, true, false, 2, (Object)null));
      $this$_init__u24lambda_u2d4.setY((YConstraint)UtilitiesKt.pixels$default((Number)12, false, false, 3, (Object)null));
      final ButtonComponent keybind = (ButtonComponent)ComponentsKt.childOf($this$onLeftClick$iv, (UIComponent)this);
      $this$onLeftClick$iv = (UIComponent)keybind;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new KeybindLabel$special$$inlined$onLeftClick$1(changing, keybind)));
      window.onKeyType((Function3)(new Function3<UIComponent, Character, Integer, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onKeyType, char typedChar, int keyCode) {
            Intrinsics.checkNotNullParameter($this$onKeyType, "$this$onKeyType");
            if (changing.element) {
               if (keyCode == 14) {
                  keybind.setText("Keybind: NONE");
                  CF4M.INSTANCE.moduleManager.setKey(KeybindLabel.this.module, 0);
               } else if (keyCode != 1) {
                  keybind.setText(Intrinsics.stringPlus("Keybind: ", Keyboard.getKeyName(keyCode)));
                  CF4M.INSTANCE.moduleManager.setKey(KeybindLabel.this.module, keyCode);
               }

               changing.element = false;
            }

         }
      }));
   }

   private final UIContainer getTextContainer() {
      return (UIContainer)this.textContainer$delegate.getValue(this, $$delegatedProperties[0]);
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(KeybindLabel.class, "textContainer", "getTextContainer()Lgg/essential/elementa/components/UIContainer;", 0)))};
      $$delegatedProperties = var0;
   }
}

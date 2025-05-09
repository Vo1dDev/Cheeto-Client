package xyz.apfelmus.cheeto.client.clickgui;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.ScrollComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
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
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.State;
import gg.essential.universal.GuiScale;
import gg.essential.universal.GuiScale.Companion;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cheeto.client.clickgui.settings.TextComponent;
import xyz.apfelmus.cheeto.client.configs.ClientConfig;
import xyz.apfelmus.cheeto.client.utils.client.ColorUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010>\u001a\u00020?H\u0016J\b\u0010@\u001a\u00020AH\u0016J\u000e\u0010B\u001a\u00020A2\u0006\u0010C\u001a\u00020DJ\u000e\u0010E\u001a\u00020A2\u0006\u0010\f\u001a\u00020FJ\u000e\u0010G\u001a\u00020A2\u0006\u0010H\u001a\u00020IJ\b\u0010J\u001a\u00020AH\u0016R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\b\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0011\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\b\u001a\u0004\b\u0012\u0010\u0006R\u001b\u0010\u0014\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0015\u0010\u000fR\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\b\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001c\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\b\u001a\u0004\b\u001d\u0010\u000fR\u001b\u0010\u001f\u001a\u00020 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\b\u001a\u0004\b!\u0010\"R\u001b\u0010$\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\b\u001a\u0004\b%\u0010\u000fR\u001b\u0010'\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\b\u001a\u0004\b(\u0010\u000fR\u001b\u0010*\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\b\u001a\u0004\b+\u0010\u001aR\u001b\u0010-\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b/\u0010\b\u001a\u0004\b.\u0010\u0006R\u001b\u00100\u001a\u0002018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\b\u001a\u0004\b2\u00103R\u001b\u00105\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\b\u001a\u0004\b6\u0010\u0006R\u001b\u00108\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b:\u0010\b\u001a\u0004\b9\u0010\u000fR\u001b\u0010;\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b=\u0010\b\u001a\u0004\b<\u0010\u001a¨\u0006K"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/ConfigGUI;", "Lgg/essential/elementa/WindowScreen;", "()V", "bottomContent", "Lgg/essential/elementa/components/UIContainer;", "getBottomContent", "()Lgg/essential/elementa/components/UIContainer;", "bottomContent$delegate", "Lkotlin/properties/ReadWriteProperty;", "categoryContainer", "getCategoryContainer", "categoryContainer$delegate", "config", "Lgg/essential/elementa/components/UIBlock;", "getConfig", "()Lgg/essential/elementa/components/UIBlock;", "config$delegate", "configContainer", "getConfigContainer", "configContainer$delegate", "configScrollBar", "getConfigScrollBar", "configScrollBar$delegate", "configScroller", "Lgg/essential/elementa/components/ScrollComponent;", "getConfigScroller", "()Lgg/essential/elementa/components/ScrollComponent;", "configScroller$delegate", "configSpacer", "getConfigSpacer", "configSpacer$delegate", "configText", "Lgg/essential/elementa/components/UIText;", "getConfigText", "()Lgg/essential/elementa/components/UIText;", "configText$delegate", "content", "getContent", "content$delegate", "moduleScrollBar", "getModuleScrollBar", "moduleScrollBar$delegate", "moduleScroller", "getModuleScroller", "moduleScroller$delegate", "scrollContainer", "getScrollContainer", "scrollContainer$delegate", "searchBar", "Lxyz/apfelmus/cheeto/client/clickgui/settings/TextComponent;", "getSearchBar", "()Lxyz/apfelmus/cheeto/client/clickgui/settings/TextComponent;", "searchBar$delegate", "settingContainer", "getSettingContainer", "settingContainer$delegate", "settingScrollBar", "getSettingScrollBar", "settingScrollBar$delegate", "settingScroller", "getSettingScroller", "settingScroller$delegate", "doesGuiPauseGame", "", "onScreenClose", "", "selectCategory", "category", "Lxyz/apfelmus/cf4m/module/Category;", "selectConfig", "", "selectModule", "module", "", "updateGuiScale", "Cheeto"}
)
public final class ConfigGUI extends WindowScreen {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final ReadWriteProperty content$delegate;
   @NotNull
   private final ReadWriteProperty config$delegate;
   @NotNull
   private final ReadWriteProperty configText$delegate;
   @NotNull
   private final ReadWriteProperty configSpacer$delegate;
   @NotNull
   private final ReadWriteProperty configContainer$delegate;
   @NotNull
   private final ReadWriteProperty configScroller$delegate;
   @NotNull
   private final ReadWriteProperty configScrollBar$delegate;
   @NotNull
   private final ReadWriteProperty categoryContainer$delegate;
   @NotNull
   private final ReadWriteProperty bottomContent$delegate;
   @NotNull
   private final ReadWriteProperty scrollContainer$delegate;
   @NotNull
   private final ReadWriteProperty moduleScroller$delegate;
   @NotNull
   private final ReadWriteProperty moduleScrollBar$delegate;
   @NotNull
   private final ReadWriteProperty settingContainer$delegate;
   @NotNull
   private final ReadWriteProperty settingScroller$delegate;
   @NotNull
   private final ReadWriteProperty settingScrollBar$delegate;
   @NotNull
   private final ReadWriteProperty searchBar$delegate;

   public ConfigGUI() {
      ElementaVersion var1 = ElementaVersion.V1;
      int var2 = Companion.scaleForScreenSize$default(GuiScale.Companion, 0, 1, (Object)null).ordinal();
      super(var1, false, false, true, var2, 6, (DefaultConstructorMarker)null);
      Color var12 = ColorUtils.MENU_BG;
      Intrinsics.checkNotNullExpressionValue(var12, "MENU_BG");
      UIComponent $this$constrain$iv = (UIComponent)(new UIBlock(var12));
      int $i$f$constrain = false;
      int var7 = false;
      UIConstraints $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      int var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)60));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.percent((Number)75));
      UIComponent var10001 = ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getWindow());
      var12 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var12, "M_BORDER");
      OutlineEffect var10002 = new OutlineEffect(var12, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null);
      var12 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var12, "M_BORDER");
      this.content$delegate = ComponentsKt.provideDelegate(ComponentsKt.effect(var10001, (Effect)var10002.bindColor((State)(new BasicState(var12)))), this, $$delegatedProperties[0]);
      var12 = ColorUtils.MENU_BG;
      Intrinsics.checkNotNullExpressionValue(var12, "MENU_BG");
      $this$constrain$iv = (UIComponent)(new UIBlock(var12));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)ConstraintsKt.plus((SuperConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)5, false, false, 3, (Object)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)13));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.percent((Number)40));
      var10001 = ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getWindow());
      var12 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var12, "M_BORDER");
      this.config$delegate = ComponentsKt.provideDelegate(ComponentsKt.effect(var10001, (Effect)(new OutlineEffect(var12, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null))), this, $$delegatedProperties[1]);
      $this$constrain$iv = (UIComponent)(new UIText("Configs", false, (Color)null, 6, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setTextScale((HeightConstraint)UtilitiesKt.pixels$default((Number)1.5F, false, false, 3, (Object)null));
      this.configText$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getConfig()), this, $$delegatedProperties[2]);
      var12 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var12, "M_BORDER");
      $this$constrain$iv = (UIComponent)(new UIBlock(var12));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new SiblingConstraint(5.0F, false, 2, (DefaultConstructorMarker)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)ConstraintsKt.minus((SuperConstraint)UtilitiesKt.percent((Number)100), (SuperConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null));
      this.configSpacer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getConfig()), this, $$delegatedProperties[3]);
      $this$constrain$iv = (UIComponent)(new UIContainer());
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new SiblingConstraint(5.0F, false, 2, (DefaultConstructorMarker)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)ConstraintsKt.minus((SuperConstraint)(new FillConstraint(false)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)(new FillConstraint(false)));
      this.configContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getConfig()), this, $$delegatedProperties[4]);
      $this$constrain$iv = (UIComponent)(new ScrollComponent((String)null, 0.0F, (Color)null, false, false, false, false, 25.0F, 0.0F, (UIComponent)null, 895, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)100));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.percent((Number)100));
      this.configScroller$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getConfigContainer()), this, $$delegatedProperties[5]);
      $this$constrain$iv = (UIComponent)(new UIBlock((ColorConstraint)null, 1, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.percent((Number)106));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
      Color var10 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var10, "M_BORDER");
      $this$searchBar_delegate_u24lambda_u2d18.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      this.configScrollBar$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getConfigContainer()), this, $$delegatedProperties[6]);
      this.getConfigScroller().setVerticalScrollBarComponent((UIComponent)this.getConfigScrollBar(), true);
      String active = ClientConfig.getActiveConfig();
      List var17 = ClientConfig.getConfigs();
      Intrinsics.checkNotNullExpressionValue(var17, "getConfigs()");
      Iterable $this$filterIsInstance$iv = (Iterable)var17;
      int $i$f$filterIsInstance = false;
      Iterator var4 = $this$filterIsInstance$iv.iterator();

      while(var4.hasNext()) {
         Object element$iv = var4.next();
         String cfg = (String)element$iv;
         var7 = false;
         Intrinsics.checkNotNullExpressionValue(cfg, "cfg");
         ConfigLabel label = (ConfigLabel)ComponentsKt.childOf((UIComponent)(new ConfigLabel(this, cfg)), (UIComponent)this.getConfigScroller());
         if (Intrinsics.areEqual(active, cfg)) {
            label.select();
         }
      }

      $this$constrain$iv = (UIComponent)(new UIContainer());
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)UtilitiesKt.pixels$default((Number)5, false, false, 3, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)95));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.percent((Number)12));
      this.categoryContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getContent()), this, $$delegatedProperties[7]);
      Object[] $this$forEach$iv = Category.values();
      $i$f$constrain = false;
      Category[] var15 = $this$forEach$iv;
      int var16 = 0;
      int var19 = $this$forEach$iv.length;

      while(var16 < var19) {
         Object element$iv = var15[var16];
         ++var16;
         int var26 = false;
         ComponentsKt.childOf((UIComponent)(new CategoryLabel(this, element$iv)), (UIComponent)this.getCategoryContainer());
      }

      var12 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var12, "M_BORDER");
      $this$constrain$iv = (UIComponent)(new UIBlock(var12));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.pixels$default((Number)0, false, false, 3, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)(new FillConstraint(false)));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.pixel$default((Number)1, false, false, 3, (Object)null));
      ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getContent());
      $this$constrain$iv = (UIComponent)(new UIContainer());
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new SiblingConstraint(10.0F, false, 2, (DefaultConstructorMarker)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)ConstraintsKt.minus((SuperConstraint)(new FillConstraint(false)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)ConstraintsKt.minus((SuperConstraint)(new FillConstraint(false)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      this.bottomContent$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getContent()), this, $$delegatedProperties[8]);
      $this$constrain$iv = (UIComponent)(new UIContainer());
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)(new SiblingConstraint(10.0F, false, 2, (DefaultConstructorMarker)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)27.5D));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)ConstraintsKt.minus((SuperConstraint)(new FillConstraint(false)), (SuperConstraint)UtilitiesKt.pixels$default((Number)10, false, false, 3, (Object)null)));
      this.scrollContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getBottomContent()), this, $$delegatedProperties[9]);
      $this$constrain$iv = (UIComponent)(new ScrollComponent((String)null, 0.0F, (Color)null, false, false, false, false, 25.0F, 0.0F, (UIComponent)null, 895, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)100));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.percent((Number)100));
      this.moduleScroller$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getScrollContainer()), this, $$delegatedProperties[10]);
      $this$constrain$iv = (UIComponent)(new UIBlock((ColorConstraint)null, 1, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.pixels$default((Number)7.5D, true, false, 2, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
      var10 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var10, "M_BORDER");
      $this$searchBar_delegate_u24lambda_u2d18.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      this.moduleScrollBar$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getScrollContainer()), this, $$delegatedProperties[11]);
      $this$constrain$iv = (UIComponent)(new UIContainer());
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null)));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)(new FillConstraint(false)));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)(new FillConstraint(false)));
      var10001 = ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getBottomContent());
      var12 = ColorUtils.C_BORDER;
      Intrinsics.checkNotNullExpressionValue(var12, "C_BORDER");
      this.settingContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.effect(var10001, (Effect)(new OutlineEffect(var12, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null))), this, $$delegatedProperties[12]);
      $this$constrain$iv = (UIComponent)(new ScrollComponent((String)null, 0.0F, (Color)null, false, false, false, false, 25.0F, 0.0F, (UIComponent)null, 895, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.percent((Number)100));
      $this$searchBar_delegate_u24lambda_u2d18.setHeight((HeightConstraint)UtilitiesKt.percent((Number)100));
      this.settingScroller$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getSettingContainer()), this, $$delegatedProperties[13]);
      $this$constrain$iv = (UIComponent)(new UIBlock((ColorConstraint)null, 1, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)UtilitiesKt.pixels$default((Number)2.5D, true, false, 2, (Object)null));
      $this$searchBar_delegate_u24lambda_u2d18.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)3, false, false, 3, (Object)null));
      var10 = ColorUtils.M_BORDER;
      Intrinsics.checkNotNullExpressionValue(var10, "M_BORDER");
      $this$searchBar_delegate_u24lambda_u2d18.setColor((ColorConstraint)UtilitiesKt.toConstraint(var10));
      this.settingScrollBar$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getSettingContainer()), this, $$delegatedProperties[14]);
      this.getModuleScroller().setVerticalScrollBarComponent((UIComponent)this.getModuleScrollBar(), true);
      $this$filterIsInstance$iv = (Iterable)this.getCategoryContainer().getChildren();
      $i$f$filterIsInstance = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterIsInstanceTo = false;
      Iterator var27 = $this$filterIsInstance$iv.iterator();

      Object element$iv$iv;
      while(var27.hasNext()) {
         element$iv$iv = var27.next();
         if (element$iv$iv instanceof CategoryLabel) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      CategoryLabel var10000 = (CategoryLabel)CollectionsKt.firstOrNull((List)destination$iv$iv);
      Unit var29;
      if (var10000 == null) {
         var10000 = null;
      } else {
         var10000.select();
         var29 = Unit.INSTANCE;
      }

      this.getSettingScroller().setVerticalScrollBarComponent((UIComponent)this.getSettingScrollBar(), true);
      $this$filterIsInstance$iv = (Iterable)this.getModuleScroller().getAllChildren();
      $i$f$filterIsInstance = false;
      destination$iv$iv = (Collection)(new ArrayList());
      $i$f$filterIsInstanceTo = false;
      var27 = $this$filterIsInstance$iv.iterator();

      while(var27.hasNext()) {
         element$iv$iv = var27.next();
         if (element$iv$iv instanceof ModuleLabel) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      ModuleLabel var30 = (ModuleLabel)CollectionsKt.firstOrNull((List)destination$iv$iv);
      if (var30 == null) {
         var10000 = null;
      } else {
         var30.select();
         var29 = Unit.INSTANCE;
      }

      $this$constrain$iv = (UIComponent)(new TextComponent("", "Search...", false, false));
      $i$f$constrain = false;
      var7 = false;
      $this$searchBar_delegate_u24lambda_u2d18 = $this$constrain$iv.getConstraints();
      var9 = false;
      $this$searchBar_delegate_u24lambda_u2d18.setX((XConstraint)(new CenterConstraint()));
      $this$searchBar_delegate_u24lambda_u2d18.setY((YConstraint)UtilitiesKt.percent((Number)7.5D));
      this.searchBar$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getWindow()), this, $$delegatedProperties[15]);
      this.getSearchBar().onValueChange((Function1)(new Function1<Object, Unit>() {
         public final void invoke(@Nullable Object it) {
            if (it == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            } else {
               Iterable $this$filterIsInstance$iv;
               Object var10000;
               boolean $i$f$filterIsInstanceTo;
               if (((String)it).equals("")) {
                  ConfigGUI.this.getCategoryContainer().clearChildren();
                  Category[] $this$forEach$iv = Category.values();
                  ConfigGUI var3 = ConfigGUI.this;
                  int $i$f$filterIsInstance = false;
                  Category[] var5 = $this$forEach$iv;
                  int var6 = 0;
                  int var7 = $this$forEach$iv.length;

                  while(var6 < var7) {
                     Object element$ivx = var5[var6];
                     ++var6;
                     int var10 = false;
                     ComponentsKt.childOf((UIComponent)(new CategoryLabel(var3, element$ivx)), (UIComponent)var3.getCategoryContainer());
                  }

                  $this$filterIsInstance$iv = (Iterable)ConfigGUI.this.getCategoryContainer().getChildren();
                  $i$f$filterIsInstance = false;
                  Collection destination$iv$ivx = (Collection)(new ArrayList());
                  int $i$f$filterIsInstanceTox = false;
                  Iterator var25 = $this$filterIsInstance$iv.iterator();

                  Object element$iv$iv;
                  while(var25.hasNext()) {
                     element$iv$iv = var25.next();
                     if (element$iv$iv instanceof CategoryLabel) {
                        destination$iv$ivx.add(element$iv$iv);
                     }
                  }

                  $this$filterIsInstance$iv = (Iterable)((List)destination$iv$ivx);
                  $i$f$filterIsInstance = false;
                  Iterator var16 = $this$filterIsInstance$iv.iterator();

                  while(true) {
                     if (!var16.hasNext()) {
                        var10000 = null;
                        break;
                     }

                     Object element$ivxx = var16.next();
                     CategoryLabel itx = (CategoryLabel)element$ivxx;
                     $i$f$filterIsInstanceTo = false;
                     if (itx.isSelected()) {
                        var10000 = element$ivxx;
                        break;
                     }
                  }

                  CategoryLabel var33 = (CategoryLabel)var10000;
                  if ((CategoryLabel)var10000 != null) {
                     var33.deselect();
                  }

                  $this$filterIsInstance$iv = (Iterable)ConfigGUI.this.getCategoryContainer().getChildren();
                  $i$f$filterIsInstance = false;
                  destination$iv$ivx = (Collection)(new ArrayList());
                  $i$f$filterIsInstanceTox = false;
                  var25 = $this$filterIsInstance$iv.iterator();

                  while(var25.hasNext()) {
                     element$iv$iv = var25.next();
                     if (element$iv$iv instanceof CategoryLabel) {
                        destination$iv$ivx.add(element$iv$iv);
                     }
                  }

                  var33 = (CategoryLabel)CollectionsKt.firstOrNull((List)destination$iv$ivx);
                  if (var33 != null) {
                     var33.select();
                  }
               } else {
                  ConfigGUI.this.getCategoryContainer().clearChildren();
                  CategoryLabel cat = (CategoryLabel)ComponentsKt.childOf((UIComponent)(new CategoryLabel(ConfigGUI.this, Category.NONE)), (UIComponent)ConfigGUI.this.getCategoryContainer());
                  cat.select();
                  ConfigGUI.this.getModuleScroller().clearChildren();
                  ArrayList var14 = CF4M.INSTANCE.moduleManager.getModules();
                  Intrinsics.checkNotNullExpressionValue(var14, "INSTANCE.moduleManager.modules");
                  $this$filterIsInstance$iv = (Iterable)var14;
                  ConfigGUI var15 = ConfigGUI.this;
                  int $i$f$filterIsInstancex = false;
                  Iterator var22 = $this$filterIsInstance$iv.iterator();

                  Object element$iv;
                  boolean var28;
                  while(var22.hasNext()) {
                     element$iv = var22.next();
                     var28 = false;
                     String var31 = CF4M.INSTANCE.moduleManager.getName(element$iv);
                     Intrinsics.checkNotNullExpressionValue(var31, "INSTANCE.moduleManager.getName(mod)");
                     String var11 = var31.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     CharSequence var34 = (CharSequence)var11;
                     var11 = ((String)it).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     if (StringsKt.contains$default(var34, (CharSequence)var11, false, 2, (Object)null)) {
                        Intrinsics.checkNotNullExpressionValue(element$iv, "mod");
                        ComponentsKt.childOf((UIComponent)(new ModuleLabel(var15, element$iv)), (UIComponent)var15.getModuleScroller());
                     }
                  }

                  ConfigGUI.this.getSettingScroller().clearChildren();
                  Iterable $this$filterIsInstance$ivx = (Iterable)ConfigGUI.this.getModuleScroller().getAllChildren();
                  $i$f$filterIsInstancex = false;
                  Collection destination$iv$iv = (Collection)(new ArrayList());
                  $i$f$filterIsInstanceTo = false;
                  Iterator var30 = $this$filterIsInstance$ivx.iterator();

                  Object element$iv$ivx;
                  while(var30.hasNext()) {
                     element$iv$ivx = var30.next();
                     if (element$iv$ivx instanceof ModuleLabel) {
                        destination$iv$iv.add(element$iv$ivx);
                     }
                  }

                  $this$filterIsInstance$ivx = (Iterable)((List)destination$iv$iv);
                  $i$f$filterIsInstancex = false;
                  var22 = $this$filterIsInstance$ivx.iterator();

                  while(true) {
                     if (!var22.hasNext()) {
                        var10000 = null;
                        break;
                     }

                     element$iv = var22.next();
                     ModuleLabel itxx = (ModuleLabel)element$iv;
                     var28 = false;
                     if (itxx.isSelected()) {
                        var10000 = element$iv;
                        break;
                     }
                  }

                  ModuleLabel var35 = (ModuleLabel)var10000;
                  if ((ModuleLabel)var10000 != null) {
                     var35.deselect();
                  }

                  $this$filterIsInstance$ivx = (Iterable)ConfigGUI.this.getModuleScroller().getAllChildren();
                  $i$f$filterIsInstancex = false;
                  destination$iv$iv = (Collection)(new ArrayList());
                  $i$f$filterIsInstanceTo = false;
                  var30 = $this$filterIsInstance$ivx.iterator();

                  while(var30.hasNext()) {
                     element$iv$ivx = var30.next();
                     if (element$iv$ivx instanceof ModuleLabel) {
                        destination$iv$iv.add(element$iv$ivx);
                     }
                  }

                  var35 = (ModuleLabel)CollectionsKt.firstOrNull((List)destination$iv$iv);
                  if (var35 != null) {
                     var35.select();
                  }
               }

            }
         }
      }));
   }

   private final UIBlock getContent() {
      return (UIBlock)this.content$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final UIBlock getConfig() {
      return (UIBlock)this.config$delegate.getValue(this, $$delegatedProperties[1]);
   }

   private final UIText getConfigText() {
      return (UIText)this.configText$delegate.getValue(this, $$delegatedProperties[2]);
   }

   private final UIBlock getConfigSpacer() {
      return (UIBlock)this.configSpacer$delegate.getValue(this, $$delegatedProperties[3]);
   }

   private final UIContainer getConfigContainer() {
      return (UIContainer)this.configContainer$delegate.getValue(this, $$delegatedProperties[4]);
   }

   private final ScrollComponent getConfigScroller() {
      return (ScrollComponent)this.configScroller$delegate.getValue(this, $$delegatedProperties[5]);
   }

   private final UIBlock getConfigScrollBar() {
      return (UIBlock)this.configScrollBar$delegate.getValue(this, $$delegatedProperties[6]);
   }

   private final UIContainer getCategoryContainer() {
      return (UIContainer)this.categoryContainer$delegate.getValue(this, $$delegatedProperties[7]);
   }

   private final UIContainer getBottomContent() {
      return (UIContainer)this.bottomContent$delegate.getValue(this, $$delegatedProperties[8]);
   }

   private final UIContainer getScrollContainer() {
      return (UIContainer)this.scrollContainer$delegate.getValue(this, $$delegatedProperties[9]);
   }

   private final ScrollComponent getModuleScroller() {
      return (ScrollComponent)this.moduleScroller$delegate.getValue(this, $$delegatedProperties[10]);
   }

   private final UIBlock getModuleScrollBar() {
      return (UIBlock)this.moduleScrollBar$delegate.getValue(this, $$delegatedProperties[11]);
   }

   private final UIContainer getSettingContainer() {
      return (UIContainer)this.settingContainer$delegate.getValue(this, $$delegatedProperties[12]);
   }

   private final ScrollComponent getSettingScroller() {
      return (ScrollComponent)this.settingScroller$delegate.getValue(this, $$delegatedProperties[13]);
   }

   private final UIBlock getSettingScrollBar() {
      return (UIBlock)this.settingScrollBar$delegate.getValue(this, $$delegatedProperties[14]);
   }

   private final TextComponent getSearchBar() {
      return (TextComponent)this.searchBar$delegate.getValue(this, $$delegatedProperties[15]);
   }

   public final void selectCategory(@NotNull Category category) {
      Intrinsics.checkNotNullParameter(category, "category");
      this.getModuleScroller().clearChildren();
      ArrayList var2 = CF4M.INSTANCE.moduleManager.getModules(category);
      Intrinsics.checkNotNullExpressionValue(var2, "INSTANCE.moduleManager.getModules(category)");
      Iterable $this$filterIsInstance$iv = (Iterable)var2;
      int $i$f$filterIsInstance = false;
      Iterator var4 = $this$filterIsInstance$iv.iterator();

      boolean $i$f$filterIsInstanceTo;
      while(var4.hasNext()) {
         Object element$iv = var4.next();
         $i$f$filterIsInstanceTo = false;
         Intrinsics.checkNotNullExpressionValue(element$iv, "module");
         ComponentsKt.childOf((UIComponent)(new ModuleLabel(this, element$iv)), (UIComponent)this.getModuleScroller());
      }

      $this$filterIsInstance$iv = (Iterable)this.getModuleScroller().getAllChildren();
      $i$f$filterIsInstance = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterIsInstanceTo = false;
      Iterator var17 = $this$filterIsInstance$iv.iterator();

      Object element$iv$iv;
      while(var17.hasNext()) {
         element$iv$iv = var17.next();
         if (element$iv$iv instanceof ModuleLabel) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      if (!((Collection)((List)destination$iv$iv)).isEmpty()) {
         $this$filterIsInstance$iv = (Iterable)this.getModuleScroller().getAllChildren();
         $i$f$filterIsInstance = false;
         destination$iv$iv = (Collection)(new ArrayList());
         $i$f$filterIsInstanceTo = false;
         var17 = $this$filterIsInstance$iv.iterator();

         while(var17.hasNext()) {
            element$iv$iv = var17.next();
            if (element$iv$iv instanceof ModuleLabel) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         ((ModuleLabel)CollectionsKt.first((List)destination$iv$iv)).select();
      } else {
         this.getSettingScroller().clearChildren();
      }

      Iterable $this$firstOrNull$iv = (Iterable)this.getCategoryContainer().getChildren();
      int $i$f$firstOrNull = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      $i$f$filterIsInstanceTo = false;
      Iterator var19 = $this$firstOrNull$iv.iterator();

      while(var19.hasNext()) {
         Object element$iv$iv = var19.next();
         if (element$iv$iv instanceof CategoryLabel) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$firstOrNull$iv = (Iterable)((List)destination$iv$iv);
      $i$f$firstOrNull = false;
      Iterator var14 = $this$firstOrNull$iv.iterator();

      Object var10000;
      while(true) {
         if (var14.hasNext()) {
            Object element$iv = var14.next();
            CategoryLabel it = (CategoryLabel)element$iv;
            int var20 = false;
            if (!it.isSelected()) {
               continue;
            }

            var10000 = element$iv;
            break;
         }

         var10000 = null;
         break;
      }

      CategoryLabel var21 = (CategoryLabel)var10000;
      if (var21 != null) {
         var21.deselect();
      }

   }

   public final void selectModule(@NotNull Object module) {
      Intrinsics.checkNotNullParameter(module, "module");
      this.getSettingScroller().clearChildren();
      ComponentsKt.childOf((UIComponent)(new KeybindLabel(module, this.getWindow())), (UIComponent)this.getSettingScroller());
      ArrayList settings = CF4M.INSTANCE.settingManager.getSettings(module);
      boolean $i$f$filterIsInstanceTo;
      if (settings != null) {
         ArrayList var3 = CF4M.INSTANCE.settingManager.getSettings(module);
         Intrinsics.checkNotNullExpressionValue(var3, "INSTANCE.settingManager.getSettings(module)");
         Iterable $this$forEach$iv = (Iterable)var3;
         int $i$f$forEach = false;
         Iterator var5 = $this$forEach$iv.iterator();

         while(var5.hasNext()) {
            Object element$iv = var5.next();
            $i$f$filterIsInstanceTo = false;
            Intrinsics.checkNotNullExpressionValue(element$iv, "setting");
            ComponentsKt.childOf((UIComponent)(new SettingLabel(module, element$iv)), (UIComponent)this.getSettingScroller());
         }

         ComponentsKt.childOf((new UIContainer()).setY((YConstraint)(new SiblingConstraint(0.0F, false, 3, (DefaultConstructorMarker)null))).setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null)), (UIComponent)this.getSettingScroller());
      }

      Iterable $this$firstOrNull$iv = (Iterable)this.getModuleScroller().getAllChildren();
      int $i$f$firstOrNull = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      $i$f$filterIsInstanceTo = false;
      Iterator var9 = $this$firstOrNull$iv.iterator();

      while(var9.hasNext()) {
         Object element$iv$iv = var9.next();
         if (element$iv$iv instanceof ModuleLabel) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$firstOrNull$iv = (Iterable)((List)destination$iv$iv);
      $i$f$firstOrNull = false;
      Iterator var14 = $this$firstOrNull$iv.iterator();

      Object var10000;
      while(true) {
         if (var14.hasNext()) {
            Object element$iv = var14.next();
            ModuleLabel it = (ModuleLabel)element$iv;
            int var17 = false;
            if (!it.isSelected()) {
               continue;
            }

            var10000 = element$iv;
            break;
         }

         var10000 = null;
         break;
      }

      ModuleLabel var18 = (ModuleLabel)var10000;
      if (var18 != null) {
         var18.deselect();
      }

   }

   public final void selectConfig(@NotNull String config) {
      Intrinsics.checkNotNullParameter(config, "config");
      ClientConfig.setActiveConfig(config);
      Iterable $this$firstOrNull$iv = (Iterable)this.getConfigScroller().getAllChildren();
      int $i$f$firstOrNull = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterIsInstanceTo = false;
      Iterator var7 = $this$firstOrNull$iv.iterator();

      while(var7.hasNext()) {
         Object element$iv$iv = var7.next();
         if (element$iv$iv instanceof ConfigLabel) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$firstOrNull$iv = (Iterable)((List)destination$iv$iv);
      $i$f$firstOrNull = false;
      Iterator var4 = $this$firstOrNull$iv.iterator();

      Object var10000;
      while(true) {
         if (var4.hasNext()) {
            Object element$iv = var4.next();
            ConfigLabel it = (ConfigLabel)element$iv;
            int var12 = false;
            if (!it.isSelected()) {
               continue;
            }

            var10000 = element$iv;
            break;
         }

         var10000 = null;
         break;
      }

      ConfigLabel var11 = (ConfigLabel)var10000;
      if (var11 != null) {
         var11.deselect();
      }

   }

   public void updateGuiScale() {
      this.setNewGuiScale(Companion.scaleForScreenSize$default(GuiScale.Companion, 0, 1, (Object)null).ordinal());
      super.updateGuiScale();
   }

   public boolean func_73868_f() {
      return false;
   }

   public void onScreenClose() {
      super.onScreenClose();
      if (CF4M.INSTANCE.moduleManager.isEnabled("ClickGUI")) {
         CF4M.INSTANCE.moduleManager.toggle("ClickGUI");
      }

      CF4M.INSTANCE.configManager.save();
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "content", "getContent()Lgg/essential/elementa/components/UIBlock;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "config", "getConfig()Lgg/essential/elementa/components/UIBlock;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "configText", "getConfigText()Lgg/essential/elementa/components/UIText;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "configSpacer", "getConfigSpacer()Lgg/essential/elementa/components/UIBlock;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "configContainer", "getConfigContainer()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "configScroller", "getConfigScroller()Lgg/essential/elementa/components/ScrollComponent;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "configScrollBar", "getConfigScrollBar()Lgg/essential/elementa/components/UIBlock;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "categoryContainer", "getCategoryContainer()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "bottomContent", "getBottomContent()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "scrollContainer", "getScrollContainer()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "moduleScroller", "getModuleScroller()Lgg/essential/elementa/components/ScrollComponent;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "moduleScrollBar", "getModuleScrollBar()Lgg/essential/elementa/components/UIBlock;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "settingContainer", "getSettingContainer()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "settingScroller", "getSettingScroller()Lgg/essential/elementa/components/ScrollComponent;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "settingScrollBar", "getSettingScrollBar()Lgg/essential/elementa/components/UIBlock;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(ConfigGUI.class, "searchBar", "getSearchBar()Lxyz/apfelmus/cheeto/client/clickgui/settings/TextComponent;", 0)))};
      $$delegatedProperties = var0;
   }
}

package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.ScrollComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIImage;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.AdditiveConstraint;
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint;
import gg.essential.elementa.constraints.ChildBasedSizeConstraint;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.CopyConstraintFloat;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
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
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.effects.OutlineEffect;
import gg.essential.elementa.effects.ScissorEffect;
import gg.essential.elementa.events.UIClickEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
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
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001a\u0010/\u001a\u00020 2\b\b\u0002\u00100\u001a\u00020\r2\b\b\u0002\u00101\u001a\u00020\rJ\b\u00102\u001a\u00020 H\u0002J\u0006\u00103\u001a\u00020\u0003J\u0010\u00104\u001a\u00020 2\u0006\u00105\u001a\u00020\u001dH\u0002J\u001a\u0010\u001e\u001a\u00020 2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020 0\u001fJ\b\u00107\u001a\u00020 H\u0002J\u000e\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020\u0003J\u0010\u0010:\u001a\u00020 2\u0006\u00105\u001a\u00020\u001dH\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0015\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001b\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020 0\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010!\u001a\u00020\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u0015\u001a\u0004\b#\u0010$R\u001b\u0010&\u001a\u00020'8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0015\u001a\u0004\b(\u0010)R\u000e\u0010+\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010,\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u0015\u001a\u0004\b-\u0010\u0019¨\u0006;"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/DropDown;", "Lgg/essential/elementa/components/UIBlock;", "initialSelection", "", "options", "", "", "outlineEffect", "Lgg/essential/elementa/effects/OutlineEffect;", "optionPadding", "", "(ILjava/util/List;Lgg/essential/elementa/effects/OutlineEffect;F)V", "active", "", "collapsedWidth", "Lgg/essential/elementa/constraints/AdditiveConstraint;", "currentSelectionText", "Lgg/essential/elementa/components/UIText;", "getCurrentSelectionText", "()Lgg/essential/elementa/components/UIText;", "currentSelectionText$delegate", "Lkotlin/properties/ReadWriteProperty;", "downArrow", "Lgg/essential/elementa/components/UIImage;", "getDownArrow", "()Lgg/essential/elementa/components/UIImage;", "downArrow$delegate", "expandedWidth", "mappedOptions", "Lgg/essential/elementa/UIComponent;", "onValueChange", "Lkotlin/Function1;", "", "optionsHolder", "Lgg/essential/elementa/components/ScrollComponent;", "getOptionsHolder", "()Lgg/essential/elementa/components/ScrollComponent;", "optionsHolder$delegate", "scrollContainer", "Lgg/essential/elementa/components/UIContainer;", "getScrollContainer", "()Lgg/essential/elementa/components/UIContainer;", "scrollContainer$delegate", "selected", "upArrow", "getUpArrow", "upArrow$delegate", "collapse", "unHover", "instantly", "expand", "getValue", "hoverText", "text", "listener", "readOptionComponents", "select", "index", "unHoverText", "Cheeto"}
)
public final class DropDown extends UIBlock {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final List<String> options;
   private int selected;
   @NotNull
   private Function1<? super Integer, Unit> onValueChange;
   private boolean active;
   @NotNull
   private final ReadWriteProperty currentSelectionText$delegate;
   @NotNull
   private final ReadWriteProperty downArrow$delegate;
   @NotNull
   private final ReadWriteProperty upArrow$delegate;
   @NotNull
   private final ReadWriteProperty scrollContainer$delegate;
   @NotNull
   private final ReadWriteProperty optionsHolder$delegate;
   @NotNull
   private final List<UIComponent> mappedOptions;
   @NotNull
   private final AdditiveConstraint collapsedWidth;
   @NotNull
   private final AdditiveConstraint expandedWidth;

   public DropDown(int initialSelection, @NotNull List<String> options, @Nullable OutlineEffect outlineEffect, float optionPadding) {
      Intrinsics.checkNotNullParameter(options, "options");
      super((ColorConstraint)null, 1, (DefaultConstructorMarker)null);
      this.options = options;
      this.selected = initialSelection;
      this.onValueChange = (Function1)null.INSTANCE;
      UIComponent $this$constrain$iv = (UIComponent)(new UIText((String)this.options.get(this.selected), false, (Color)null, 6, (DefaultConstructorMarker)null));
      int $i$f$constrain = false;
      int var11 = false;
      UIConstraints $this$_init__u24lambda_u2d7 = $this$constrain$iv.getConstraints();
      int var13 = false;
      $this$_init__u24lambda_u2d7.setX((XConstraint)UtilitiesKt.pixels$default((Number)5, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setY((YConstraint)UtilitiesKt.pixels$default((Number)6, false, false, 3, (Object)null));
      Color var14 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var14, "LABEL");
      $this$_init__u24lambda_u2d7.setColor((ColorConstraint)UtilitiesKt.toConstraint(var14));
      $this$_init__u24lambda_u2d7.setFontProvider(this.getFontProvider());
      this.currentSelectionText$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this), this, $$delegatedProperties[0]);
      $this$constrain$iv = (UIComponent)UIImage.Companion.ofResourceCached("/vigilance/arrow-down.png");
      $i$f$constrain = false;
      var11 = false;
      $this$_init__u24lambda_u2d7 = $this$constrain$iv.getConstraints();
      var13 = false;
      $this$_init__u24lambda_u2d7.setX((XConstraint)UtilitiesKt.pixels$default((Number)5, true, false, 2, (Object)null));
      $this$_init__u24lambda_u2d7.setY((YConstraint)UtilitiesKt.pixels$default((Number)7.5D, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)9, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)5, false, false, 3, (Object)null));
      this.downArrow$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this), this, $$delegatedProperties[1]);
      $this$constrain$iv = (UIComponent)UIImage.Companion.ofResourceCached("/vigilance/arrow-up.png");
      $i$f$constrain = false;
      var11 = false;
      $this$_init__u24lambda_u2d7 = $this$constrain$iv.getConstraints();
      var13 = false;
      $this$_init__u24lambda_u2d7.setX((XConstraint)UtilitiesKt.pixels$default((Number)5, true, false, 2, (Object)null));
      $this$_init__u24lambda_u2d7.setY((YConstraint)UtilitiesKt.pixels$default((Number)7.5D, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setWidth((WidthConstraint)UtilitiesKt.pixels$default((Number)9, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)5, false, false, 3, (Object)null));
      this.upArrow$delegate = ComponentsKt.provideDelegate($this$constrain$iv, this, $$delegatedProperties[2]);
      $this$constrain$iv = (UIComponent)(new UIContainer());
      $i$f$constrain = false;
      var11 = false;
      $this$_init__u24lambda_u2d7 = $this$constrain$iv.getConstraints();
      var13 = false;
      $this$_init__u24lambda_u2d7.setX((XConstraint)UtilitiesKt.pixels$default((Number)5, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setY((YConstraint)ConstraintsKt.boundTo((SuperConstraint)(new SiblingConstraint(optionPadding, false, 2, (DefaultConstructorMarker)null)), (UIComponent)this.getCurrentSelectionText()));
      $this$_init__u24lambda_u2d7.setWidth((WidthConstraint)(new ChildBasedMaxSizeConstraint()));
      $this$_init__u24lambda_u2d7.setHeight((HeightConstraint)ConstraintsKt.plus((SuperConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)), (SuperConstraint)UtilitiesKt.pixels$default((Number)optionPadding, false, false, 3, (Object)null)));
      this.scrollContainer$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this), this, $$delegatedProperties[3]);
      $this$constrain$iv = (UIComponent)(new ScrollComponent((String)null, 0.0F, (Color)null, false, false, false, false, 0.0F, 0.0F, (UIComponent)this.getScrollContainer(), 511, (DefaultConstructorMarker)null));
      $i$f$constrain = false;
      var11 = false;
      $this$_init__u24lambda_u2d7 = $this$constrain$iv.getConstraints();
      var13 = false;
      $this$_init__u24lambda_u2d7.setX((XConstraint)UtilitiesKt.pixels$default((Number)0, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setY((YConstraint)UtilitiesKt.pixels$default((Number)0, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d7.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)(float)(this.options.size() - 1) * (this.getFontProvider().getStringHeight("Text", $this$_init__u24lambda_u2d7.getTextScale()) + optionPadding) - optionPadding, false, false, 3, (Object)null));
      this.optionsHolder$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf($this$constrain$iv, (UIComponent)this.getScrollContainer()), this, $$delegatedProperties[4]);
      Iterable $this$mapIndexed$iv = (Iterable)this.options;
      $i$f$constrain = false;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10)));
      int $i$f$mapIndexedTo = false;
      int index$iv$iv = 0;
      Iterator var31 = $this$mapIndexed$iv.iterator();

      while(var31.hasNext()) {
         Object item$iv$iv = var31.next();
         final int var35 = index$iv$iv++;
         if (var35 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         String option = (String)item$iv$iv;
         int var16 = false;
         UIComponent $this$constrain$iv = (UIComponent)(new UIText(option, false, (Color)null, 6, (DefaultConstructorMarker)null));
         int $i$f$constrain = false;
         int var21 = false;
         UIConstraints $this$mappedOptions_u24lambda_u2d6_u24lambda_u2d5 = $this$constrain$iv.getConstraints();
         int var23 = false;
         $this$mappedOptions_u24lambda_u2d6_u24lambda_u2d5.setY((YConstraint)(new SiblingConstraint(optionPadding, false, 2, (DefaultConstructorMarker)null)));
         $this$mappedOptions_u24lambda_u2d6_u24lambda_u2d5.setColor((ColorConstraint)UtilitiesKt.toConstraint(new Color(0, 0, 0, 0)));
         $this$mappedOptions_u24lambda_u2d6_u24lambda_u2d5.setFontProvider(this.getFontProvider());
         destination$iv$iv.add(((UIText)$this$constrain$iv).onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
            public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
               Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
               DropDown.this.hoverText($this$onMouseEnter);
            }
         })).onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
            public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
               Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
               DropDown.this.unHoverText($this$onMouseLeave);
            }
         })).onMouseClick((Function2)(new Function2<UIComponent, UIClickEvent, Unit>() {
            public final void invoke(@NotNull UIComponent $this$onMouseClick, @NotNull UIClickEvent event) {
               Intrinsics.checkNotNullParameter($this$onMouseClick, "$this$onMouseClick");
               Intrinsics.checkNotNullParameter(event, "event");
               event.stopPropagation();
               DropDown.this.select(var35);
            }
         })));
      }

      this.mappedOptions = (List)destination$iv$iv;
      this.collapsedWidth = ConstraintsKt.plus((SuperConstraint)UtilitiesKt.pixels$default((Number)22, false, false, 3, (Object)null), (new CopyConstraintFloat(false, 1, (DefaultConstructorMarker)null)).to((UIComponent)this.getCurrentSelectionText()));
      this.expandedWidth = ConstraintsKt.plus((SuperConstraint)UtilitiesKt.pixels$default((Number)22, false, false, 3, (Object)null), (SuperConstraint)ConstraintsKt.coerceAtLeast((new ChildBasedMaxSizeConstraint()).to((UIComponent)this.getOptionsHolder()), (new CopyConstraintFloat(false, 1, (DefaultConstructorMarker)null)).to((UIComponent)this.getCurrentSelectionText())));
      $this$constrain$iv = (UIComponent)this;
      $i$f$constrain = false;
      var11 = false;
      $this$_init__u24lambda_u2d7 = $this$constrain$iv.getConstraints();
      var13 = false;
      $this$_init__u24lambda_u2d7.setWidth((WidthConstraint)this.collapsedWidth);
      $this$_init__u24lambda_u2d7.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null));
      var14 = ColorUtils.MENU_BG;
      Intrinsics.checkNotNullExpressionValue(var14, "MENU_BG");
      $this$_init__u24lambda_u2d7.setColor((ColorConstraint)UtilitiesKt.toConstraint(var14));
      this.readOptionComponents();
      this.getOptionsHolder().hide(true);
      if (outlineEffect == null) {
         Object var10000 = null;
      } else {
         Effect p0 = (Effect)outlineEffect;
         int var29 = false;
         this.enableEffect(p0);
      }

      UIComponent $this$onLeftClick$iv = (UIComponent)(new UIContainer());
      int $i$f$onLeftClick = false;
      int var34 = false;
      UIConstraints $this$_init__u24lambda_u2d8 = $this$onLeftClick$iv.getConstraints();
      int var36 = false;
      $this$_init__u24lambda_u2d8.setX((XConstraint)UtilitiesKt.pixels$default((Number)-1, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d8.setY((YConstraint)UtilitiesKt.pixels$default((Number)-1, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d8.setWidth((WidthConstraint)ConstraintsKt.plus((SuperConstraint)(new RelativeConstraint(1.0F)), (SuperConstraint)UtilitiesKt.pixels$default((Number)2, false, false, 3, (Object)null)));
      $this$_init__u24lambda_u2d8.setHeight((HeightConstraint)ConstraintsKt.plus((SuperConstraint)(new RelativeConstraint(1.0F)), (SuperConstraint)UtilitiesKt.pixels$default((Number)3.0F, false, false, 3, (Object)null)));
      UIContainer outlineContainer = (UIContainer)$this$onLeftClick$iv;
      outlineContainer.setParent((UIComponent)this);
      this.getChildren().add(0, outlineContainer);
      this.enableEffect((Effect)(new ScissorEffect((UIComponent)outlineContainer, false, 2, (DefaultConstructorMarker)null)));
      this.onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
            Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
            DropDown.this.hoverText((UIComponent)DropDown.this.getCurrentSelectionText());
         }
      }));
      this.onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
            Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
            if (!DropDown.this.active) {
               DropDown.this.unHoverText((UIComponent)DropDown.this.getCurrentSelectionText());
            }
         }
      }));
      $this$onLeftClick$iv = (UIComponent)this;
      $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new DropDown$special$$inlined$onLeftClick$1(this)));
   }

   // $FF: synthetic method
   public DropDown(int var1, List var2, OutlineEffect var3, float var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 4) != 0) {
         Color var7 = ColorUtils.M_BORDER;
         Intrinsics.checkNotNullExpressionValue(var7, "M_BORDER");
         var3 = new OutlineEffect(var7, 1.0F, false, false, (Set)null, 28, (DefaultConstructorMarker)null);
      }

      if ((var5 & 8) != 0) {
         var4 = 6.0F;
      }

      this(var1, var2, var3, var4);
   }

   private final UIText getCurrentSelectionText() {
      return (UIText)this.currentSelectionText$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final UIImage getDownArrow() {
      return (UIImage)this.downArrow$delegate.getValue(this, $$delegatedProperties[1]);
   }

   private final UIImage getUpArrow() {
      return (UIImage)this.upArrow$delegate.getValue(this, $$delegatedProperties[2]);
   }

   private final UIContainer getScrollContainer() {
      return (UIContainer)this.scrollContainer$delegate.getValue(this, $$delegatedProperties[3]);
   }

   private final ScrollComponent getOptionsHolder() {
      return (ScrollComponent)this.optionsHolder$delegate.getValue(this, $$delegatedProperties[4]);
   }

   public final void select(int index) {
      if (0 <= index ? index < this.options.size() : false) {
         this.selected = index;
         this.onValueChange.invoke(index);
         this.getCurrentSelectionText().setText((String)this.options.get(index));
         collapse$default(this, false, false, 3, (Object)null);
         this.readOptionComponents();
      }

   }

   public final void onValueChange(@NotNull Function1<? super Integer, Unit> listener) {
      Intrinsics.checkNotNullParameter(listener, "listener");
      this.onValueChange = listener;
   }

   public final int getValue() {
      return this.selected;
   }

   private final void expand() {
      this.active = true;
      Iterable $this$forEach$iv = (Iterable)this.mappedOptions;
      int $i$f$animate = false;
      Iterator var3 = $this$forEach$iv.iterator();

      while(var3.hasNext()) {
         Object element$iv = var3.next();
         UIComponent it = (UIComponent)element$iv;
         int var6 = false;
         Color var7 = ColorUtils.LABEL;
         Intrinsics.checkNotNullExpressionValue(var7, "LABEL");
         it.setColor((ColorConstraint)UtilitiesKt.toConstraint(var7));
      }

      UIComponent $this$animate$iv = (UIComponent)this;
      $i$f$animate = false;
      int var12 = false;
      AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
      int var10 = false;
      AnimatingConstraints.setHeightAnimation$default(anim$iv, (AnimationStrategy)Animations.IN_SIN, 0.25F, (HeightConstraint)ConstraintsKt.plus((SuperConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null), ConstraintsKt.boundTo((SuperConstraint)(new RelativeConstraint(1.0F)), (UIComponent)this.getScrollContainer())), 0.0F, 8, (Object)null);
      $this$animate$iv.animateTo(anim$iv);
      this.getOptionsHolder().scrollToTop(false);
      this.replaceChild((UIComponent)this.getUpArrow(), (UIComponent)this.getDownArrow());
      this.setFloating(true);
      this.getOptionsHolder().unhide(true);
      this.setWidth((WidthConstraint)this.expandedWidth);
   }

   public final void collapse(boolean unHover, boolean instantly) {
      if (this.active) {
         this.replaceChild((UIComponent)this.getDownArrow(), (UIComponent)this.getUpArrow());
      }

      this.active = false;
      if (instantly) {
         this.setHeight((HeightConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null));
         collapse$animationComplete(this);
      } else {
         UIComponent $this$animate$iv = (UIComponent)this;
         int $i$f$animate = false;
         int var7 = false;
         AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
         int var10 = false;
         AnimatingConstraints.setHeightAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_SIN, 0.35F, (HeightConstraint)UtilitiesKt.pixels$default((Number)20, false, false, 3, (Object)null), 0.0F, 8, (Object)null);
         anim$iv.onComplete((Function0)(new Function0<Unit>(this) {
            // $FF: synthetic field
            final DropDown this$0;

            {
               this.this$0 = $receiver;
            }

            public final void invoke() {
               DropDown.collapse$animationComplete(this.this$0);
            }
         }));
         $this$animate$iv.animateTo(anim$iv);
      }

      if (unHover) {
         this.unHoverText((UIComponent)this.getCurrentSelectionText());
      }

      this.setWidth((WidthConstraint)this.collapsedWidth);
   }

   // $FF: synthetic method
   public static void collapse$default(DropDown var0, boolean var1, boolean var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = false;
      }

      if ((var3 & 2) != 0) {
         var2 = false;
      }

      var0.collapse(var1, var2);
   }

   private final void hoverText(UIComponent text) {
      int $i$f$animate = false;
      int var6 = false;
      AnimatingConstraints anim$iv = text.makeAnimation();
      int var9 = false;
      AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
      Color var10 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(var10, "WHITE");
      AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.25F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
      text.animateTo(anim$iv);
   }

   private final void unHoverText(UIComponent text) {
      int $i$f$animate = false;
      int var6 = false;
      AnimatingConstraints anim$iv = text.makeAnimation();
      int var9 = false;
      AnimationStrategy var10001 = (AnimationStrategy)Animations.OUT_EXP;
      Color var10 = ColorUtils.LABEL;
      Intrinsics.checkNotNullExpressionValue(var10, "LABEL");
      AnimatingConstraints.setColorAnimation$default(anim$iv, var10001, 0.25F, (ColorConstraint)UtilitiesKt.toConstraint(var10), 0.0F, 8, (Object)null);
      text.animateTo(anim$iv);
   }

   private final void readOptionComponents() {
      this.getOptionsHolder().clearChildren();
      Iterable $this$forEachIndexed$iv = (Iterable)this.mappedOptions;
      int $i$f$forEachIndexed = false;
      int index$iv = 0;
      Iterator var4 = $this$forEachIndexed$iv.iterator();

      while(var4.hasNext()) {
         Object item$iv = var4.next();
         int var6 = index$iv++;
         if (var6 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         UIComponent component = (UIComponent)item$iv;
         int var9 = false;
         if (var6 != this.selected) {
            ComponentsKt.childOf(component, (UIComponent)this.getOptionsHolder());
         }
      }

   }

   private static final void collapse$animationComplete(DropDown this$0) {
      Iterable $this$forEach$iv = (Iterable)this$0.mappedOptions;
      int $i$f$forEach = false;
      Iterator var3 = $this$forEach$iv.iterator();

      while(var3.hasNext()) {
         Object element$iv = var3.next();
         UIComponent it = (UIComponent)element$iv;
         int var6 = false;
         it.setColor((ColorConstraint)UtilitiesKt.toConstraint(new Color(0, 0, 0, 0)));
      }

      this$0.setFloating(false);
      this$0.getOptionsHolder().hide(true);
   }

   // $FF: synthetic method
   public static final void access$expand(DropDown $this) {
      $this.expand();
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DropDown.class, "currentSelectionText", "getCurrentSelectionText()Lgg/essential/elementa/components/UIText;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DropDown.class, "downArrow", "getDownArrow()Lgg/essential/elementa/components/UIImage;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DropDown.class, "upArrow", "getUpArrow()Lgg/essential/elementa/components/UIImage;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DropDown.class, "scrollContainer", "getScrollContainer()Lgg/essential/elementa/components/UIContainer;", 0))), (KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(DropDown.class, "optionsHolder", "getOptionsHolder()Lgg/essential/elementa/components/ScrollComponent;", 0)))};
      $$delegatedProperties = var0;
   }
}

package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.constraints.ChildBasedMaxSizeConstraint;
import gg.essential.elementa.constraints.ChildBasedSizeConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationStrategy;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.dsl.UtilitiesKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u0007X¤\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0012"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/AbstractSliderComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/SettingComponent;", "()V", "expanded", "", "mouseHeld", "slider", "Lxyz/apfelmus/cheeto/client/clickgui/settings/Slider;", "getSlider", "()Lxyz/apfelmus/cheeto/client/clickgui/settings/Slider;", "incrementBy", "", "inc", "", "setupParentListeners", "parent", "Lgg/essential/elementa/UIComponent;", "sliderInit", "Cheeto"}
)
public abstract class AbstractSliderComponent extends SettingComponent {
   private boolean expanded;
   private boolean mouseHeld;

   public AbstractSliderComponent() {
      UIComponent $this$constrain$iv = (UIComponent)this;
      int $i$f$constrain = false;
      int var5 = false;
      UIConstraints $this$_init__u24lambda_u2d0 = $this$constrain$iv.getConstraints();
      int var7 = false;
      $this$_init__u24lambda_u2d0.setWidth((WidthConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d0.setHeight((HeightConstraint)(new ChildBasedMaxSizeConstraint()));
   }

   @NotNull
   protected abstract Slider getSlider();

   public void setupParentListeners(@NotNull UIComponent parent) {
      Intrinsics.checkNotNullParameter(parent, "parent");
      parent.onMouseEnter((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseEnter) {
            Intrinsics.checkNotNullParameter($this$onMouseEnter, "$this$onMouseEnter");
            UIComponent $this$animate$iv = (UIComponent)AbstractSliderComponent.this.getSlider();
            int $i$f$animate = false;
            int var6 = false;
            AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
            int var9 = false;
            AnimatingConstraints.setWidthAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.25F, (WidthConstraint)UtilitiesKt.pixels$default((Number)100, false, false, 3, (Object)null), 0.0F, 8, (Object)null);
            $this$animate$iv.animateTo(anim$iv);
            AbstractSliderComponent.this.expanded = true;
         }
      })).onMouseLeave((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseLeave) {
            Intrinsics.checkNotNullParameter($this$onMouseLeave, "$this$onMouseLeave");
            if (!AbstractSliderComponent.this.mouseHeld) {
               UIComponent $this$animate$iv = (UIComponent)AbstractSliderComponent.this.getSlider();
               int $i$f$animate = false;
               int var6 = false;
               AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
               int var9 = false;
               AnimatingConstraints.setWidthAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.25F, (WidthConstraint)UtilitiesKt.pixels$default((Number)60, false, false, 3, (Object)null), 0.0F, 8, (Object)null);
               $this$animate$iv.animateTo(anim$iv);
               AbstractSliderComponent.this.expanded = false;
            }

         }
      }));
   }

   protected final void sliderInit() {
      UIComponent $this$onLeftClick$iv = (UIComponent)this;
      int $i$f$onLeftClick = false;
      $this$onLeftClick$iv.onMouseClick((Function2)(new AbstractSliderComponent$sliderInit$$inlined$onLeftClick$1(this)));
      this.onMouseRelease((Function1)(new Function1<UIComponent, Unit>() {
         public final void invoke(@NotNull UIComponent $this$onMouseRelease) {
            Intrinsics.checkNotNullParameter($this$onMouseRelease, "$this$onMouseRelease");
            AbstractSliderComponent.this.mouseHeld = false;
            if (AbstractSliderComponent.this.expanded && !AbstractSliderComponent.this.getSlider().isHovered()) {
               UIComponent $this$animate$iv = (UIComponent)AbstractSliderComponent.this.getSlider();
               int $i$f$animate = false;
               int var6 = false;
               AnimatingConstraints anim$iv = $this$animate$iv.makeAnimation();
               int var9 = false;
               AnimatingConstraints.setWidthAnimation$default(anim$iv, (AnimationStrategy)Animations.OUT_EXP, 0.25F, (WidthConstraint)UtilitiesKt.pixels$default((Number)60, false, false, 3, (Object)null), 0.0F, 8, (Object)null);
               $this$animate$iv.animateTo(anim$iv);
               AbstractSliderComponent.this.expanded = false;
            }

         }
      }));
   }

   public final void incrementBy(float inc) {
      Slider.setCurrentPercentage$default(this.getSlider(), this.getSlider().getCurrentPercentage() + inc, false, 2, (Object)null);
   }
}

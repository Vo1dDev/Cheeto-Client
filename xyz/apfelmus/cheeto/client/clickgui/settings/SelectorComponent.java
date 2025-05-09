package xyz.apfelmus.cheeto.client.clickgui.settings;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.constraints.ChildBasedSizeConstraint;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.WidthConstraint;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.dsl.ComponentsKt;
import gg.essential.elementa.dsl.UtilitiesKt;
import gg.essential.elementa.effects.OutlineEffect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001b\u0010\b\u001a\u00020\t8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"},
   d2 = {"Lxyz/apfelmus/cheeto/client/clickgui/settings/SelectorComponent;", "Lxyz/apfelmus/cheeto/client/clickgui/settings/SettingComponent;", "initialSelection", "", "options", "", "", "(ILjava/util/List;)V", "dropDown", "Lxyz/apfelmus/cheeto/client/clickgui/settings/DropDown;", "getDropDown$Cheeto", "()Lxyz/apfelmus/cheeto/client/clickgui/settings/DropDown;", "dropDown$delegate", "Lkotlin/properties/ReadWriteProperty;", "closePopups", "", "instantly", "", "Cheeto"}
)
public final class SelectorComponent extends SettingComponent {
   // $FF: synthetic field
   static final KProperty<Object>[] $$delegatedProperties;
   @NotNull
   private final ReadWriteProperty dropDown$delegate;

   public SelectorComponent(int initialSelection, @NotNull List<String> options) {
      Intrinsics.checkNotNullParameter(options, "options");
      super();
      Iterable $this$map$iv = (Iterable)options;
      int $i$f$constrain = false;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = false;
      Iterator var8 = $this$map$iv.iterator();

      boolean var11;
      while(var8.hasNext()) {
         Object item$iv$iv = var8.next();
         String it = (String)item$iv$iv;
         var11 = false;
         destination$iv$iv.add(I18n.func_135052_a(it, new Object[0]));
      }

      List var14 = (List)destination$iv$iv;
      Object var15 = null;
      byte var16 = 12;
      float var17 = 0.0F;
      Object var18 = null;
      this.dropDown$delegate = ComponentsKt.provideDelegate(ComponentsKt.childOf((UIComponent)(new DropDown(initialSelection, var14, (OutlineEffect)var18, var17, var16, (DefaultConstructorMarker)var15)), (UIComponent)this), this, $$delegatedProperties[0]);
      UIComponent $this$constrain$iv = (UIComponent)this;
      $i$f$constrain = false;
      int var22 = false;
      UIConstraints $this$_init__u24lambda_u2d1 = $this$constrain$iv.getConstraints();
      var11 = false;
      $this$_init__u24lambda_u2d1.setY((YConstraint)UtilitiesKt.pixels$default((Number)18, false, false, 3, (Object)null));
      $this$_init__u24lambda_u2d1.setWidth((WidthConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      $this$_init__u24lambda_u2d1.setHeight((HeightConstraint)(new ChildBasedSizeConstraint(0.0F, 1, (DefaultConstructorMarker)null)));
      this.getDropDown$Cheeto().onValueChange((Function1)(new Function1<Integer, Unit>() {
         public final void invoke(int newValue) {
            SettingComponent.changeValue$default((SettingComponent)SelectorComponent.this, newValue, false, 2, (Object)null);
         }
      }));
   }

   @NotNull
   public final DropDown getDropDown$Cheeto() {
      return (DropDown)this.dropDown$delegate.getValue(this, $$delegatedProperties[0]);
   }

   public void closePopups(boolean instantly) {
      this.getDropDown$Cheeto().collapse(true, instantly);
   }

   static {
      KProperty[] var0 = new KProperty[]{(KProperty)Reflection.property1((PropertyReference1)(new PropertyReference1Impl(SelectorComponent.class, "dropDown", "getDropDown$Cheeto()Lxyz/apfelmus/cheeto/client/clickgui/settings/DropDown;", 0)))};
      $$delegatedProperties = var0;
   }
}

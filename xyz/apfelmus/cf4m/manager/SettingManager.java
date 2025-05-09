package xyz.apfelmus.cf4m.manager;

import com.google.common.collect.LinkedHashMultimap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Setting;

public class SettingManager {
   private final LinkedHashMultimap<Object, Field> settings = LinkedHashMultimap.create();

   public SettingManager() {
      Iterator var1 = CF4M.INSTANCE.moduleManager.getModules().iterator();

      while(var1.hasNext()) {
         Object module = var1.next();
         Field[] var3 = module.getClass().getDeclaredFields();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            field.setAccessible(true);
            if (field.isAnnotationPresent(Setting.class)) {
               this.settings.put(module, field);
            }
         }
      }

   }

   public String getName(Object module, Object setting) {
      if (this.settings.containsKey(module)) {
         Iterator var3 = this.settings.get(module).iterator();

         while(var3.hasNext()) {
            Field field = (Field)var3.next();

            try {
               if (field.get(module).equals(setting)) {
                  return ((Setting)field.getAnnotation(Setting.class)).name();
               }
            } catch (IllegalAccessException var6) {
               var6.printStackTrace();
            }
         }
      }

      return null;
   }

   public String getDescription(Object module, Object setting) {
      if (this.settings.containsKey(module)) {
         Iterator var3 = this.settings.get(module).iterator();

         while(var3.hasNext()) {
            Field field = (Field)var3.next();

            try {
               if (field.get(module).equals(setting)) {
                  return ((Setting)field.getAnnotation(Setting.class)).description();
               }
            } catch (IllegalAccessException var6) {
               var6.printStackTrace();
            }
         }
      }

      return null;
   }

   public Object getSetting(Object module, String name) {
      Iterator var3 = this.getSettings(module).iterator();

      Object setting;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         setting = var3.next();
      } while(!this.getName(module, setting).equalsIgnoreCase(name));

      return setting;
   }

   public ArrayList<Object> getSettings(Object module) {
      if (this.settings.containsKey(module)) {
         ArrayList<Object> setting = new ArrayList();
         this.settings.get(module).forEach((field) -> {
            try {
               setting.add(field.get(module));
            } catch (IllegalAccessException var4) {
               var4.printStackTrace();
            }

         });
         return setting;
      } else {
         return null;
      }
   }
}

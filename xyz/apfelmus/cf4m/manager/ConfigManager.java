package xyz.apfelmus.cf4m.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.config.Config;
import xyz.apfelmus.cf4m.annotation.config.Load;
import xyz.apfelmus.cf4m.annotation.config.Save;

public class ConfigManager {
   private HashMap<String, Object> configs = Maps.newHashMap();

   public ConfigManager() {
      (new File(CF4M.INSTANCE.dir)).mkdir();
      (new File(CF4M.INSTANCE.dir, "configs")).mkdir();

      try {
         Iterator var1 = CF4M.INSTANCE.classManager.getClasses().iterator();

         while(var1.hasNext()) {
            Class<?> type = (Class)var1.next();
            if (type.isAnnotationPresent(Config.class)) {
               this.configs.put(((Config)type.getAnnotation(Config.class)).name(), type.newInstance());
            }
         }
      } catch (InstantiationException | IllegalAccessException var3) {
         var3.printStackTrace();
      }

   }

   public Object getConfig(String name) {
      return this.configs.getOrDefault(name, (Object)null);
   }

   public String getName(Object object) {
      Iterator var2 = this.configs.entrySet().iterator();

      Entry e;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         e = (Entry)var2.next();
      } while(!e.getValue().equals(object));

      return (String)e.getKey();
   }

   public String getPath(Object object) {
      return this.configs.containsValue(object) ? CF4M.INSTANCE.dir + File.separator + "configs" + File.separator + this.getName(object) + ".json" : null;
   }

   public ArrayList<String> getConfigs() {
      return Lists.newArrayList(this.configs.keySet());
   }

   public void load() {
      this.configs.values().forEach((config) -> {
         Method[] var1 = config.getClass().getMethods();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method method = var1[var3];
            method.setAccessible(true);
            if (method.isAnnotationPresent(Load.class)) {
               try {
                  method.invoke(config);
               } catch (InvocationTargetException | IllegalAccessException var6) {
                  var6.printStackTrace();
               }
            }
         }

      });
   }

   public void save() {
      if (CF4M.INSTANCE.moduleManager.isEnabled("ClickGUI")) {
         CF4M.INSTANCE.moduleManager.toggle("ClickGUI");
      }

      this.configs.values().forEach((config) -> {
         Method[] var1 = config.getClass().getMethods();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method method = var1[var3];
            method.setAccessible(true);
            if (method.isAnnotationPresent(Save.class)) {
               try {
                  method.invoke(config);
               } catch (InvocationTargetException | IllegalAccessException var6) {
                  var6.printStackTrace();
               }
            }
         }

      });
   }
}

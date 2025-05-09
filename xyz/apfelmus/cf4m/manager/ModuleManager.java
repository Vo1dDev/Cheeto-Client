package xyz.apfelmus.cf4m.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.annotation.module.Disable;
import xyz.apfelmus.cf4m.annotation.module.Enable;
import xyz.apfelmus.cf4m.annotation.module.Module;
import xyz.apfelmus.cf4m.annotation.module.extend.Extend;
import xyz.apfelmus.cf4m.annotation.module.extend.Name;
import xyz.apfelmus.cf4m.event.events.KeyboardEvent;
import xyz.apfelmus.cf4m.module.Category;
import xyz.apfelmus.cf4m.module.ValueBean;

public class ModuleManager {
   private final LinkedHashMap<Object, LinkedHashSet<ValueBean>> modules = Maps.newLinkedHashMap();

   public ModuleManager() {
      CF4M.INSTANCE.eventManager.register(this);

      try {
         Class<?> extend = null;
         HashMap<String, Field> findFields = Maps.newHashMap();
         Iterator var3 = CF4M.INSTANCE.classManager.getClasses().iterator();

         while(true) {
            Class type;
            do {
               if (!var3.hasNext()) {
                  var3 = CF4M.INSTANCE.classManager.getClasses().iterator();

                  while(true) {
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        type = (Class)var3.next();
                     } while(!type.isAnnotationPresent(Module.class));

                     Object extendObject = extend != null ? extend.newInstance() : null;
                     Object moduleObject = type.newInstance();
                     LinkedHashSet<ValueBean> valueBeans = Sets.newLinkedHashSet();
                     Iterator var14 = findFields.entrySet().iterator();

                     while(var14.hasNext()) {
                        Entry<String, Field> entry = (Entry)var14.next();
                        valueBeans.add(new ValueBean((String)entry.getKey(), (Field)entry.getValue(), extendObject));
                     }

                     this.modules.put(moduleObject, valueBeans);
                  }
               }

               type = (Class)var3.next();
            } while(!type.isAnnotationPresent(Extend.class));

            extend = type;
            Field[] var5 = type.getDeclaredFields();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Field field = var5[var7];
               field.setAccessible(true);
               if (field.isAnnotationPresent(Name.class)) {
                  Name name = (Name)field.getAnnotation(Name.class);
                  findFields.put(name.name(), field);
               }
            }
         }
      } catch (InstantiationException | IllegalAccessException var10) {
         var10.printStackTrace();
      }
   }

   public String getName(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).name() : null;
   }

   public long getActivatedTime(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).time() : -1L;
   }

   public void setActivatedTime(Object module, long value) {
      if (this.modules.containsKey(module)) {
         try {
            this.TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "time", value);
         } catch (IllegalAccessException | NoSuchFieldException var5) {
            var5.printStackTrace();
         }
      }

   }

   public boolean isEnabled(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).enable() : false;
   }

   public boolean isEnabled(String module) {
      Object mod = CF4M.INSTANCE.moduleManager.getModule(module);
      return this.isEnabled(mod);
   }

   private void setEnable(Object module, boolean value) {
      if (this.modules.containsKey(module)) {
         if (value) {
            this.setActivatedTime(module, System.currentTimeMillis());
         }

         try {
            this.TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "enable", value);
         } catch (IllegalAccessException | NoSuchFieldException var4) {
            var4.printStackTrace();
         }
      }

   }

   public int getKey(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).key() : 0;
   }

   public void setKey(Object module, int value) {
      if (this.modules.containsKey(module)) {
         try {
            this.TypeAnnotation(Proxy.getInvocationHandler(module.getClass().getAnnotation(Module.class)), "key", value);
         } catch (IllegalAccessException | NoSuchFieldException var4) {
            var4.printStackTrace();
         }
      }

   }

   public Category getCategory(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).category() : Category.NONE;
   }

   public String getDescription(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).description() : "";
   }

   public boolean isSilent(Object module) {
      return this.modules.containsKey(module) ? ((Module)module.getClass().getAnnotation(Module.class)).silent() : false;
   }

   public <T> T getValue(Object module, String name) {
      try {
         if (this.modules.containsKey(module)) {
            Iterator var3 = ((LinkedHashSet)this.modules.get(module)).iterator();

            while(var3.hasNext()) {
               ValueBean valueBean = (ValueBean)var3.next();
               if (valueBean.getName().equals(name)) {
                  return valueBean.getField().get(valueBean.getObject());
               }
            }
         }
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return null;
   }

   public <T> void setValue(Object module, String name, T value) {
      try {
         if (this.modules.containsKey(module)) {
            Iterator var4 = ((LinkedHashSet)this.modules.get(module)).iterator();

            while(var4.hasNext()) {
               ValueBean valueBean = (ValueBean)var4.next();
               if (valueBean.getName().equals(name)) {
                  valueBean.getField().set(valueBean.getObject(), value);
               }
            }
         }
      } catch (IllegalAccessException var6) {
         var6.printStackTrace();
      }

   }

   public void setEnabled(Object module, boolean enabled) {
      if (this.modules.containsKey(module)) {
         Class<?> type = module.getClass();
         if ((!enabled || !this.isEnabled(module)) && (enabled || this.isEnabled(module))) {
            this.setEnable(module, enabled);
            if (this.isEnabled(module)) {
               CF4M.INSTANCE.configuration.enable(module);
               CF4M.INSTANCE.eventManager.register(module);
            } else {
               CF4M.INSTANCE.configuration.disable(module);
               CF4M.INSTANCE.eventManager.unregister(module);
            }

            Method[] var4 = type.getDeclaredMethods();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Method method = var4[var6];
               method.setAccessible(true);

               try {
                  if (this.isEnabled(module)) {
                     if (method.isAnnotationPresent(Enable.class)) {
                        method.invoke(module);
                     }
                  } else if (method.isAnnotationPresent(Disable.class)) {
                     method.invoke(module);
                  }
               } catch (InvocationTargetException | IllegalAccessException var9) {
                  var9.printStackTrace();
               }
            }

         }
      }
   }

   public void setEnabled(String module, boolean enabled) {
      this.setEnabled(this.getModule(module), enabled);
   }

   public void toggle(Object module) {
      if (this.modules.containsKey(module)) {
         Class<?> type = module.getClass();
         this.setEnable(module, !this.isEnabled(module));
         if (this.isEnabled(module)) {
            CF4M.INSTANCE.configuration.enable(module);
            CF4M.INSTANCE.eventManager.register(module);
         } else {
            CF4M.INSTANCE.configuration.disable(module);
            CF4M.INSTANCE.eventManager.unregister(module);
         }

         Method[] var3 = type.getDeclaredMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            method.setAccessible(true);

            try {
               if (this.isEnabled(module)) {
                  if (method.isAnnotationPresent(Enable.class)) {
                     method.invoke(module);
                  }
               } else if (method.isAnnotationPresent(Disable.class)) {
                  method.invoke(module);
               }
            } catch (InvocationTargetException | IllegalAccessException var8) {
               var8.printStackTrace();
            }
         }
      }

   }

   public void toggle(String module) {
      this.toggle(this.getModule(module));
   }

   @Event
   private void onKey(KeyboardEvent e) {
      Iterator var2 = this.getModules().iterator();

      while(var2.hasNext()) {
         Object module = var2.next();
         if (this.getKey(module) == e.getKey()) {
            this.toggle(module);
            CF4M.INSTANCE.configManager.save();
         }
      }

   }

   private void TypeAnnotation(InvocationHandler invocationHandler, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
      Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
      memberValues.setAccessible(true);
      Map<String, Object> map = (Map)memberValues.get(invocationHandler);
      map.put(name, value);
   }

   public ArrayList<Object> getModules() {
      return Lists.newArrayList(this.modules.keySet());
   }

   public ArrayList<Object> getModules(Category category) {
      return (ArrayList)this.getModules().stream().filter((module) -> {
         return this.getCategory(module).equals(category);
      }).collect(Collectors.toCollection(Lists::newArrayList));
   }

   public Object getModule(String name) {
      Iterator var2 = this.getModules().iterator();

      Object module;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         module = var2.next();
      } while(!this.getName(module).equalsIgnoreCase(name));

      return module;
   }
}

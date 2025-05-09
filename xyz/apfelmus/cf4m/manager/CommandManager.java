package xyz.apfelmus.cf4m.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.command.Command;
import xyz.apfelmus.cf4m.annotation.command.Exec;
import xyz.apfelmus.cf4m.annotation.command.Param;

public class CommandManager {
   private final HashMap<Object, String[]> commands = Maps.newHashMap();
   private final String prefix;

   public CommandManager() {
      this.prefix = CF4M.INSTANCE.configuration.prefix();

      try {
         Iterator var1 = CF4M.INSTANCE.classManager.getClasses().iterator();

         while(var1.hasNext()) {
            Class<?> type = (Class)var1.next();
            if (type.isAnnotationPresent(Command.class)) {
               this.commands.put(type.newInstance(), ((Command)type.getAnnotation(Command.class)).name());
            }
         }
      } catch (IllegalAccessException | InstantiationException var3) {
         var3.printStackTrace();
      }

   }

   public boolean isCommand(String rawMessage) {
      if (!rawMessage.startsWith(this.prefix)) {
         return false;
      } else {
         boolean safe = rawMessage.split(this.prefix).length > 1;
         if (safe) {
            String beheaded = rawMessage.split(this.prefix)[1];
            List<String> args = Lists.newArrayList(beheaded.split(" "));
            String key = (String)args.get(0);
            args.remove(key);
            Object command = this.getCommand(key);
            if (command != null) {
               if (!this.execCommand(command, args)) {
                  Method[] var7 = command.getClass().getDeclaredMethods();
                  int var8 = var7.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     Method method = var7[var9];
                     if (method.isAnnotationPresent(Exec.class)) {
                        Parameter[] parameters = method.getParameters();
                        List<String> params = Lists.newArrayList();
                        Parameter[] var13 = parameters;
                        int var14 = parameters.length;

                        for(int var15 = 0; var15 < var14; ++var15) {
                           Parameter parameter = var13[var15];
                           params.add("<" + (parameter.isAnnotationPresent(Param.class) ? ((Param)parameter.getAnnotation(Param.class)).value() : "NULL") + "|" + parameter.getType().getSimpleName() + ">");
                        }

                        CF4M.INSTANCE.configuration.message(key + " " + params);
                     }
                  }
               }
            } else {
               this.help();
            }
         } else {
            this.help();
         }

         return true;
      }
   }

   private boolean execCommand(Object command, List<String> args) {
      Method[] var3 = command.getClass().getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         method.setAccessible(true);
         if (method.getParameterTypes().length == args.size() && method.isAnnotationPresent(Exec.class)) {
            List<Object> params = Lists.newArrayList();

            for(int i = 0; i < method.getParameterTypes().length; ++i) {
               String arg = (String)args.get(i);
               Class paramType = method.getParameterTypes()[i];

               try {
                  if (paramType.equals(Boolean.class)) {
                     params.add(Boolean.parseBoolean(arg));
                  } else if (paramType.equals(Integer.class)) {
                     params.add(Integer.parseInt(arg));
                  } else if (paramType.equals(Float.class)) {
                     params.add(Float.parseFloat(arg));
                  } else if (paramType.equals(Double.class)) {
                     params.add(Double.parseDouble(arg));
                  } else if (paramType.equals(Long.class)) {
                     params.add(Long.parseLong(arg));
                  } else if (paramType.equals(Short.class)) {
                     params.add(Short.parseShort(arg));
                  } else if (paramType.equals(Byte.class)) {
                     params.add(Byte.parseByte(arg));
                  } else if (paramType.equals(String.class)) {
                     params.add(String.valueOf(arg));
                  }
               } catch (Exception var12) {
                  CF4M.INSTANCE.configuration.message(var12.getMessage());
                  var12.printStackTrace();
               }
            }

            try {
               if (params.size() == 0) {
                  method.invoke(command);
               } else {
                  method.invoke(command, params.toArray());
               }

               return true;
            } catch (InvocationTargetException | IllegalAccessException var13) {
               var13.printStackTrace();
            }
         }
      }

      return false;
   }

   private void help() {
      Iterator var1 = this.commands.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<Object, String[]> entry = (Entry)var1.next();
         CF4M.INSTANCE.configuration.message(Arrays.toString((Object[])entry.getValue()) + " - " + this.getDescription(entry.getKey()));
      }

   }

   public String getDescription(Object object) {
      return this.commands.containsKey(object) ? ((Command)object.getClass().getAnnotation(Command.class)).description() : null;
   }

   public String[] getKey(Object object) {
      return (String[])this.commands.get(object);
   }

   private Object getCommand(String key) {
      Iterator var2 = this.commands.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<Object, String[]> entry = (Entry)var2.next();
         String[] var4 = (String[])entry.getValue();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String s = var4[var6];
            if (s.equalsIgnoreCase(key)) {
               return entry.getKey();
            }
         }
      }

      return null;
   }
}

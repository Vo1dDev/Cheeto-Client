package xyz.apfelmus.cheeto.client.configs;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.config.Config;
import xyz.apfelmus.cf4m.annotation.config.Load;
import xyz.apfelmus.cf4m.annotation.config.Save;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.FloatSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.settings.ModeSetting;
import xyz.apfelmus.cheeto.client.settings.StringSetting;

@Config(
   name = "Client"
)
public class ClientConfig {
   public static Map<String, JsonArray> configs = Maps.newHashMap();
   private static String activeConfig = "Default";
   public static boolean swapping = false;

   @Load
   public void load() {
      JsonObject fullCfg = new JsonObject();
      if (!Files.exists(Paths.get(CF4M.INSTANCE.configManager.getPath(this)), new LinkOption[0])) {
         this.save();
      }

      try {
         fullCfg = (JsonObject)(new Gson()).fromJson(this.read(CF4M.INSTANCE.configManager.getPath(this)), JsonObject.class);
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      activeConfig = fullCfg.get("activeConfig").getAsString();
      if (activeConfig == null) {
         System.out.println("Nah legit not possible");
      } else {
         JsonArray configsArray = fullCfg.get("configs").getAsJsonArray();
         Iterator var3 = configsArray.iterator();

         while(var3.hasNext()) {
            JsonElement config = (JsonElement)var3.next();
            JsonObject a = config.getAsJsonObject();
            configs.put(a.get("name").getAsString(), a.get("config").getAsJsonArray());
         }

         activate(activeConfig);
      }
   }

   @Save
   public void save() {
      JsonObject fullCfg = new JsonObject();
      if (configs.isEmpty()) {
         configs.put("Default", this.generateModuleJson());
      }

      fullCfg.addProperty("activeConfig", activeConfig);
      JsonArray configsArray = new JsonArray();

      JsonObject config;
      for(Iterator var3 = configs.entrySet().iterator(); var3.hasNext(); configsArray.add(config)) {
         Entry<String, JsonArray> e = (Entry)var3.next();
         config = new JsonObject();
         config.addProperty("name", (String)e.getKey());
         if (((String)e.getKey()).equals(activeConfig)) {
            config.add("config", this.generateModuleJson());
         } else {
            config.add("config", (JsonElement)e.getValue());
         }
      }

      fullCfg.add("configs", configsArray);

      try {
         this.write(CF4M.INSTANCE.configManager.getPath(this), (new Gson()).toJson(fullCfg));
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      this.load();
   }

   private JsonArray generateModuleJson() {
      JsonArray modules = new JsonArray();

      JsonObject jsonObject;
      for(Iterator var2 = CF4M.INSTANCE.moduleManager.getModules().iterator(); var2.hasNext(); modules.add(jsonObject)) {
         Object module = var2.next();
         jsonObject = new JsonObject();
         jsonObject.addProperty("name", CF4M.INSTANCE.moduleManager.getName(module));
         jsonObject.addProperty("enable", CF4M.INSTANCE.moduleManager.isEnabled(module));
         jsonObject.addProperty("key", CF4M.INSTANCE.moduleManager.getKey(module));
         List<Object> settings = CF4M.INSTANCE.settingManager.getSettings(module);
         if (settings != null && settings.size() > 0) {
            JsonObject sets = this.getModuleSettings(module, settings);
            jsonObject.add("settings", sets);
         }
      }

      return modules;
   }

   private JsonObject getModuleSettings(Object module, List<Object> settings) {
      JsonObject sets = new JsonObject();
      Iterator var4 = settings.iterator();

      while(var4.hasNext()) {
         Object set = var4.next();
         String setName = CF4M.INSTANCE.settingManager.getName(module, set);
         if (set instanceof BooleanSetting) {
            sets.addProperty(setName, ((BooleanSetting)set).isEnabled());
         } else if (set instanceof FloatSetting) {
            sets.addProperty(setName, ((FloatSetting)set).getCurrent());
         } else if (set instanceof IntegerSetting) {
            sets.addProperty(setName, ((IntegerSetting)set).getCurrent());
         } else if (set instanceof ModeSetting) {
            sets.addProperty(setName, ((ModeSetting)set).getCurrent());
         } else if (set instanceof StringSetting) {
            sets.addProperty(setName, ((StringSetting)set).getCurrent());
         }
      }

      return sets;
   }

   public static boolean renameConfig(String configName) {
      JsonArray cfg = (JsonArray)configs.get(activeConfig);
      configs.remove(activeConfig);
      configs.put(configName, cfg);
      activeConfig = configName;
      CF4M.INSTANCE.configManager.save();
      return true;
   }

   public static boolean removeConfig(String configName) {
      if (configs.size() > 1) {
         Iterator var1 = configs.keySet().iterator();

         while(var1.hasNext()) {
            String s = (String)var1.next();
            if (s.equalsIgnoreCase(configName)) {
               configName = s;
            }
         }

         JsonArray cfg = (JsonArray)configs.get(activeConfig);
         configs.remove(configName, cfg);
         if (activeConfig.equalsIgnoreCase(configName)) {
            Entry<String, JsonArray> nextCfg = (Entry)configs.entrySet().stream().findFirst().orElse((Object)null);
            if (nextCfg == null) {
               return false;
            }

            activeConfig = (String)nextCfg.getKey();
         }

         if (!activate(activeConfig)) {
            return false;
         } else {
            CF4M.INSTANCE.configManager.save();
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean createConfig(String configName) {
      Iterator var1 = configs.keySet().iterator();

      String s;
      do {
         if (!var1.hasNext()) {
            JsonArray cfg = (JsonArray)configs.get(activeConfig);
            configs.put(configName, cfg);
            activeConfig = configName;
            CF4M.INSTANCE.configManager.save();
            return true;
         }

         s = (String)var1.next();
      } while(!s.equalsIgnoreCase(configName));

      return false;
   }

   private static boolean activate(String config) {
      if (!configs.containsKey(config)) {
         return false;
      } else {
         swapping = true;
         JsonArray jsonArray = (JsonArray)configs.get(config);
         Iterator var2 = CF4M.INSTANCE.moduleManager.getModules().iterator();

         label63:
         while(var2.hasNext()) {
            Object module = var2.next();
            Iterator var4 = jsonArray.iterator();

            while(true) {
               JsonObject jsonObject;
               do {
                  if (!var4.hasNext()) {
                     continue label63;
                  }

                  JsonElement jsonElement = (JsonElement)var4.next();
                  jsonObject = jsonElement.getAsJsonObject();
               } while(!CF4M.INSTANCE.moduleManager.getName(module).equals(((JsonObject)(new Gson()).fromJson(jsonObject, JsonObject.class)).get("name").getAsString()));

               CF4M.INSTANCE.moduleManager.setEnabled(module, jsonObject.get("enable").getAsBoolean());
               if (jsonObject.has("settings")) {
                  JsonObject setObj = jsonObject.get("settings").getAsJsonObject();
                  List<Object> settings = CF4M.INSTANCE.settingManager.getSettings(module);
                  if (settings != null && settings.size() > 0) {
                     Iterator var9 = settings.iterator();

                     while(var9.hasNext()) {
                        Object set = var9.next();
                        String setName = CF4M.INSTANCE.settingManager.getName(module, set);
                        if (setObj.has(setName)) {
                           if (set instanceof BooleanSetting) {
                              ((BooleanSetting)set).setState(setObj.get(setName).getAsBoolean());
                           } else if (set instanceof FloatSetting) {
                              ((FloatSetting)set).setCurrent(setObj.get(setName).getAsFloat());
                           } else if (set instanceof IntegerSetting) {
                              ((IntegerSetting)set).setCurrent(setObj.get(setName).getAsInt());
                           } else if (set instanceof ModeSetting) {
                              ((ModeSetting)set).setCurrent(setObj.get(setName).getAsString());
                           } else if (set instanceof StringSetting) {
                              ((StringSetting)set).setCurrent(setObj.get(setName).getAsString());
                           }
                        }
                     }
                  }
               }

               CF4M.INSTANCE.moduleManager.setKey(module, jsonObject.get("key").getAsInt());
            }
         }

         swapping = false;
         return true;
      }
   }

   public static List<String> getConfigs() {
      return new ArrayList(configs.keySet());
   }

   private String read(String path) throws IOException {
      return FileUtils.readFileToString(new File(path));
   }

   private void write(String path, String string) throws IOException {
      FileUtils.writeStringToFile(new File(path), string, "UTF-8");
   }

   public static String getActiveConfig() {
      return activeConfig;
   }

   public static boolean setActiveConfig(String cfg) {
      Iterator var1 = configs.keySet().iterator();

      while(var1.hasNext()) {
         String s = (String)var1.next();
         if (s.equalsIgnoreCase(cfg)) {
            cfg = s;
         }
      }

      if (activate(cfg)) {
         activeConfig = cfg;
         return true;
      } else {
         return false;
      }
   }
}

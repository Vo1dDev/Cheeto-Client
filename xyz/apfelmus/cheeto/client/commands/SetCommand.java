package xyz.apfelmus.cheeto.client.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.command.Command;
import xyz.apfelmus.cf4m.annotation.command.Exec;
import xyz.apfelmus.cf4m.annotation.command.Param;
import xyz.apfelmus.cf4m.configuration.IConfiguration;
import xyz.apfelmus.cheeto.client.settings.BooleanSetting;
import xyz.apfelmus.cheeto.client.settings.FloatSetting;
import xyz.apfelmus.cheeto.client.settings.IntegerSetting;
import xyz.apfelmus.cheeto.client.settings.ModeSetting;

@Command(
   name = {"set", "s"},
   description = "Sets a setting of a module"
)
public class SetCommand {
   private Object currentModule;
   private ArrayList<Object> settings;
   private Object currentSetting;

   @Exec
   public void exec(@Param("Module") String moduleName) {
      this.printModule(moduleName);
      CF4M.INSTANCE.configuration.message("No setting for &l" + moduleName + "&r specified.");
      this.printSettings();
   }

   private void printModule(@Param("Module") String moduleName) {
      this.currentModule = CF4M.INSTANCE.moduleManager.getModule(moduleName);
      if (this.currentModule == null) {
         CF4M.INSTANCE.configuration.message("The module &l" + moduleName + "&r does not exist");
      } else {
         this.settings = CF4M.INSTANCE.settingManager.getSettings(this.currentModule);
         if (this.settings == null) {
            CF4M.INSTANCE.configuration.message("The module &l" + moduleName + "&r has no settings");
         }

      }
   }

   private void printSettings() {
      CF4M.INSTANCE.configuration.message("Here is the list of settings:");
      Iterator var1 = this.settings.iterator();

      while(var1.hasNext()) {
         Object s = var1.next();
         CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.settingManager.getName(this.currentModule, s) + "(" + s.getClass().getSimpleName() + ")" + CF4M.INSTANCE.settingManager.getDescription(this.currentModule, s));
         if (s instanceof ModeSetting) {
            List var10000 = ((ModeSetting)s).getModes();
            IConfiguration var10001 = CF4M.INSTANCE.configuration;
            var10000.forEach(var10001::message);
         }
      }

   }

   private void printModuleSettings(@Param("Module") String moduleName, @Param("Setting") String settingName) {
      this.printModule(moduleName);
      Object setting = CF4M.INSTANCE.settingManager.getSetting(this.currentModule, settingName);
      if (setting != null) {
         this.currentSetting = setting;
         CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.settingManager.getName(this.currentModule, this.currentSetting) + " > " + this.currentSetting.getClass().getSimpleName());
      } else {
         CF4M.INSTANCE.configuration.message("The setting &7" + settingName + "&f does not exist");
         this.printSettings();
      }

   }

   @Exec
   public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName) {
      this.printModuleSettings(moduleName, settingName);
   }

   @Exec
   public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName, @Param("SettingValue") String settingValue) {
      this.printModuleSettings(moduleName, settingName);

      try {
         if (this.currentSetting instanceof BooleanSetting) {
            ((BooleanSetting)this.currentSetting).setState(Boolean.parseBoolean(settingValue));
         } else if (this.currentSetting instanceof FloatSetting) {
            ((FloatSetting)this.currentSetting).setCurrent(Float.parseFloat(settingValue));
         } else if (this.currentSetting instanceof IntegerSetting) {
            ((IntegerSetting)this.currentSetting).setCurrent(Integer.parseInt(settingValue));
         } else if (this.currentSetting instanceof ModeSetting) {
            ((ModeSetting)this.currentSetting).setCurrent(settingValue);
         }

         CF4M.INSTANCE.configuration.message("&7" + CF4M.INSTANCE.settingManager.getName(this.currentModule, this.currentSetting) + "&f was set to &7" + settingValue);
         CF4M.INSTANCE.configManager.save();
      } catch (NumberFormatException var5) {
         CF4M.INSTANCE.configuration.message("&cAn Error occured while trying to set &l" + settingName + " &r&cto &l" + settingValue);
      }

   }
}

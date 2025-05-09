package xyz.apfelmus.cheeto.client.commands;

import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.command.Command;
import xyz.apfelmus.cf4m.annotation.command.Exec;
import xyz.apfelmus.cf4m.annotation.command.Param;
import xyz.apfelmus.cheeto.client.configs.ClientConfig;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;

@Command(
   name = {"config", "cfg"},
   description = "Command to manage configs"
)
public class ConfigCommand {
   @Exec
   public void exec(@Param("Action") String action) {
      if (!action.equalsIgnoreCase("list") && !action.equalsIgnoreCase("ls")) {
         if (!action.equalsIgnoreCase("current") && !action.equalsIgnoreCase("cur")) {
            if (!action.equalsIgnoreCase("set") && !action.equalsIgnoreCase("rename") && !action.equalsIgnoreCase("delete") && !action.equalsIgnoreCase("remove") && !action.equalsIgnoreCase("rm") && !action.equalsIgnoreCase("create") && !action.equalsIgnoreCase("new")) {
               ChatUtils.send("Not a valid action, try: &7<list/ls>, <current/cur>, <set/load>, <delete/remove/rm>, <create/new>, <rename>");
            } else {
               ChatUtils.send("Not enough arguments!");
            }
         } else {
            ChatUtils.send("Current config: &a" + ClientConfig.getActiveConfig());
         }
      } else {
         ChatUtils.send("Available configs are: &7" + String.join(", ", ClientConfig.getConfigs()));
      }

   }

   @Exec
   public void exec(@Param("Action") String action, @Param("Config") String configName) {
      if (!action.equalsIgnoreCase("set") && !action.equalsIgnoreCase("load")) {
         if (action.equalsIgnoreCase("rename")) {
            if (ClientConfig.renameConfig(configName)) {
               ChatUtils.send("Renamed current config to: &7" + ClientConfig.getActiveConfig());
            } else {
               ChatUtils.send("Something went wrong! ¯\\_(ツ)_/¯");
            }
         } else if (!action.equalsIgnoreCase("delete") && !action.equalsIgnoreCase("remove") && !action.equalsIgnoreCase("rm")) {
            if (action.equalsIgnoreCase("create") || action.equalsIgnoreCase("new")) {
               if (ClientConfig.createConfig(configName)) {
                  ChatUtils.send("Created new config: &a" + ClientConfig.getActiveConfig());
               } else {
                  ChatUtils.send("Config &7" + configName + "&f already exists!");
               }
            }
         } else if (ClientConfig.removeConfig(configName)) {
            ChatUtils.send("Removed current config, now using: &a" + ClientConfig.getActiveConfig());
         } else {
            ChatUtils.send("Something went wrong! ¯\\_(ツ)_/¯");
         }
      } else {
         boolean set = ClientConfig.setActiveConfig(configName);
         if (set) {
            ChatUtils.send("Switched to config: &a" + ClientConfig.getActiveConfig());
            CF4M.INSTANCE.configManager.save();
         } else {
            ChatUtils.send("Config &7" + configName + "&f doesn't exist");
            this.exec("ls");
         }
      }

   }
}

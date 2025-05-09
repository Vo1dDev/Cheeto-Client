package xyz.apfelmus.cheeto.client.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import xyz.apfelmus.cheeto.client.utils.client.ChatUtils;

public class CheetoCommand extends CommandBase {
   public static final String COMMAND_NAME = "cheeto";
   public static final String COMMAND_USAGE = "/cheeto";

   public String func_71517_b() {
      return "cheeto";
   }

   public String func_71518_a(ICommandSender sender) {
      return "/cheeto";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      ChatUtils.send("================================");
      ChatUtils.send("There's no command such as /cheeto you retard");
      ChatUtils.send("To bring up the GUI, type \",t ClickGUI\" in chat, or press the Right Control Key on your keyboard");
      ChatUtils.send("The Command prefix is \",\" and you can type \",help\" to get a list of commands");
      ChatUtils.send("Yeah, that's a fucking comma you dumbass bitch");
      ChatUtils.send("================================");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }
}

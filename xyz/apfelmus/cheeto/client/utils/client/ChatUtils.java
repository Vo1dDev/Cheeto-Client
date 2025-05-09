package xyz.apfelmus.cheeto.client.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
   private static final String PREFIX = ChatColor.format("&6[&eCheeto&6]&f ");

   public static void send(String text, String... args) {
      if (Minecraft.func_71410_x().field_71439_g != null) {
         text = String.format(text, (Object[])args);
         StringBuilder messageBuilder = new StringBuilder();
         String[] var3 = text.split(" ");
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String word = var3[var5];
            word = ChatColor.format(ChatColor.getLastColors(text) + word);
            messageBuilder.append(word).append(" ");
         }

         Minecraft.func_71410_x().field_71439_g.func_145747_a(new ChatComponentText(PREFIX + ChatColor.format(messageBuilder.toString().trim())));
      }
   }
}

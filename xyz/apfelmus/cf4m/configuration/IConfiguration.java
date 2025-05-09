package xyz.apfelmus.cf4m.configuration;

public interface IConfiguration {
   default void message(String message) {
      System.out.println(message);
   }

   default void enable(Object module) {
   }

   default void disable(Object module) {
   }

   default String prefix() {
      return ",";
   }

   default boolean config() {
      return true;
   }
}

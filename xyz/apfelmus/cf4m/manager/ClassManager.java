package xyz.apfelmus.cf4m.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import java.io.IOException;
import java.util.ArrayList;
import xyz.apfelmus.cf4m.CF4M;
import xyz.apfelmus.cf4m.annotation.Configuration;
import xyz.apfelmus.cf4m.configuration.IConfiguration;

public class ClassManager {
   private final ArrayList<Class<?>> classes = Lists.newArrayList();

   public ClassManager(ClassLoader classLoader) {
      try {
         UnmodifiableIterator var2 = ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses().iterator();

         while(var2.hasNext()) {
            ClassInfo info = (ClassInfo)var2.next();
            if (info.getName().startsWith(CF4M.INSTANCE.packName) && !info.getName().contains("injection")) {
               Class<?> type = classLoader.loadClass(info.getName());
               if (type.isAnnotationPresent(Configuration.class)) {
                  CF4M.INSTANCE.configuration = (IConfiguration)type.newInstance();
               }

               this.classes.add(type);
            }
         }
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException var5) {
         var5.printStackTrace();
      }

   }

   public ArrayList<Class<?>> getClasses() {
      return this.classes;
   }
}

package xyz.apfelmus.cf4m.manager;

import com.google.common.collect.Maps;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import xyz.apfelmus.cf4m.annotation.Event;
import xyz.apfelmus.cf4m.event.EventBean;
import xyz.apfelmus.cf4m.event.Listener;

public class EventManager {
   private final LinkedHashMap<Class<? extends Listener>, CopyOnWriteArrayList<EventBean>> events = Maps.newLinkedHashMap();

   public void register(Object o) {
      Class<?> type = o.getClass();
      Method[] var3 = type.getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         if (method.getParameterTypes().length == 1 && method.isAnnotationPresent(Event.class)) {
            method.setAccessible(true);
            Class<? extends Listener> listener = method.getParameterTypes()[0];
            EventBean eventBean = new EventBean(o, method, ((Event)method.getAnnotation(Event.class)).priority());
            if (this.events.containsKey(listener)) {
               if (!((CopyOnWriteArrayList)this.events.get(listener)).contains(eventBean)) {
                  ((CopyOnWriteArrayList)this.events.get(listener)).add(eventBean);
               }
            } else {
               this.events.put(listener, new CopyOnWriteArrayList(Collections.singletonList(eventBean)));
            }

            this.events.values().forEach((methodBeans) -> {
               methodBeans.sort(Comparator.comparingInt(EventBean::getPriority));
            });
         }
      }

   }

   public void unregister(Object o) {
      this.events.values().forEach((methodBeans) -> {
         methodBeans.removeIf((methodEventBean) -> {
            return methodEventBean.getObject().equals(o);
         });
      });
      this.events.entrySet().removeIf((event) -> {
         return ((CopyOnWriteArrayList)event.getValue()).isEmpty();
      });
   }

   public CopyOnWriteArrayList<EventBean> getEvent(Class<? extends Listener> type) {
      return (CopyOnWriteArrayList)this.events.get(type);
   }
}

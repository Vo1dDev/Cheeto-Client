package xyz.apfelmus.cf4m.event;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import xyz.apfelmus.cf4m.CF4M;

public class Listener {
   private final Listener.At at;
   private boolean cancel;

   public Listener(Listener.At at) {
      this.at = at;
      this.cancel = false;
   }

   public void call() {
      this.cancel = false;
      if (CF4M.INSTANCE.eventManager != null) {
         CopyOnWriteArrayList<EventBean> eventBeans = CF4M.INSTANCE.eventManager.getEvent(this.getClass());
         if (eventBeans != null) {
            Iterator var2 = eventBeans.iterator();

            while(var2.hasNext()) {
               EventBean event = (EventBean)var2.next();

               try {
                  event.getMethod().invoke(event.getObject(), this);
               } catch (InvocationTargetException | IllegalAccessException var5) {
                  var5.printStackTrace();
               }
            }

         }
      }
   }

   public boolean getCancel() {
      return this.cancel;
   }

   public void setCancelled(boolean cancel) {
      this.cancel = cancel;
   }

   public Listener.At getAt() {
      return this.at;
   }

   public static enum At {
      HEAD,
      TAIL,
      NONE;
   }
}

package xyz.apfelmus.cheeto.client.utils.fishing;

import java.util.List;
import xyz.apfelmus.cheeto.client.utils.client.Rotation;

public class Location {
   public String name;
   public List<PathPoint> path;
   public Rotation rotation;

   public String toString() {
      return this.name;
   }
}

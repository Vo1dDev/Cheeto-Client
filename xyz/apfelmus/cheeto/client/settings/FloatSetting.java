package xyz.apfelmus.cheeto.client.settings;

public class FloatSetting {
   private Float current;
   private Float min;
   private Float max;

   public FloatSetting(Float current, Float min, Float max) {
      this.current = current;
      this.min = min;
      this.max = max;
   }

   public Float getCurrent() {
      return this.current;
   }

   public void setCurrent(Float current) {
      this.current = current < this.min ? this.min : (current > this.max ? this.max : current);
   }

   public Float getMin() {
      return this.min;
   }

   public void setMin(Float min) {
      this.min = min;
   }

   public Float getMax() {
      return this.max;
   }

   public void setMax(Float max) {
      this.max = max;
   }
}

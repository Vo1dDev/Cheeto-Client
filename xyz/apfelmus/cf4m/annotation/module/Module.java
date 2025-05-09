package xyz.apfelmus.cf4m.annotation.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import xyz.apfelmus.cf4m.module.Category;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
   String name();

   boolean enable() default false;

   int key() default 0;

   Category category() default Category.NONE;

   String description() default "";

   long time() default -1L;

   boolean silent() default false;
}

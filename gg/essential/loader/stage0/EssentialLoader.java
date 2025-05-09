package gg.essential.loader.stage0;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class EssentialLoader {
   static final String STAGE1_RESOURCE = "gg/essential/loader/stage0/stage1.jar";
   static final String STAGE1_PKG = "gg.essential.loader.stage1.";
   static final String STAGE1_PKG_PATH = "gg.essential.loader.stage1.".replace('.', '/');
   static final Logger LOGGER = LogManager.getLogger(EssentialLoader.class);
   private final String variant;

   public EssentialLoader(String variant) {
      this.variant = variant;
   }

   public Path loadStage1File(Path gameDir) throws Exception {
      Path dataDir = gameDir.resolve("essential").resolve("loader").resolve("stage0").resolve(this.variant);
      Path stage1UpdateFile = dataDir.resolve("stage1.update.jar");
      Path stage1File = dataDir.resolve("stage1.jar");
      URL stage1Url = stage1File.toUri().toURL();
      if (!Files.exists(dataDir, new LinkOption[0])) {
         Files.createDirectories(dataDir);
      }

      if (Files.exists(stage1UpdateFile, new LinkOption[0])) {
         LOGGER.info("Found update for stage1.");
         Files.deleteIfExists(stage1File);
         Files.move(stage1UpdateFile, stage1File);
      }

      URL latestUrl = null;
      int latestVersion = -1;
      if (Files.exists(stage1File, new LinkOption[0])) {
         latestVersion = getVersion(stage1Url);
         LOGGER.debug("Found stage1 version {}: {}", new Object[]{latestVersion, stage1Url});
      }

      Enumeration<URL> resources = EssentialLoader.class.getClassLoader().getResources("gg/essential/loader/stage0/stage1.jar");
      if (!resources.hasMoreElements()) {
         LOGGER.warn("Found no embedded stage1 jar files.");
      }

      while(resources.hasMoreElements()) {
         URL url = (URL)resources.nextElement();
         int version = getVersion(url);
         LOGGER.debug("Found stage1 version {}: {}", new Object[]{version, url});
         if (version > latestVersion) {
            latestVersion = version;
            latestUrl = url;
         }
      }

      if (latestUrl != null) {
         LOGGER.info("Updating stage1 to version {} from {}", new Object[]{latestVersion, latestUrl});
         InputStream in = latestUrl.openStream();
         Throwable var22 = null;

         try {
            Files.deleteIfExists(stage1File);
            Files.copy(in, stage1File, new CopyOption[0]);
         } catch (Throwable var19) {
            var22 = var19;
            throw var19;
         } finally {
            if (in != null) {
               if (var22 != null) {
                  try {
                     in.close();
                  } catch (Throwable var18) {
                     var22.addSuppressed(var18);
                  }
               } else {
                  in.close();
               }
            }

         }
      }

      return stage1File;
   }

   private static int getVersion(URL file) {
      try {
         JarInputStream in = new JarInputStream(file.openStream(), false);
         Throwable var2 = null;

         byte var5;
         try {
            Manifest manifest = in.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            if (STAGE1_PKG_PATH.equals(attributes.getValue("Name"))) {
               int var19 = Integer.parseInt(attributes.getValue("Implementation-Version"));
               return var19;
            }

            var5 = -1;
         } catch (Throwable var16) {
            var2 = var16;
            throw var16;
         } finally {
            if (in != null) {
               if (var2 != null) {
                  try {
                     in.close();
                  } catch (Throwable var15) {
                     var2.addSuppressed(var15);
                  }
               } else {
                  in.close();
               }
            }

         }

         return var5;
      } catch (Exception var18) {
         LOGGER.warn("Failed to read version from " + file, var18);
         return -1;
      }
   }
}

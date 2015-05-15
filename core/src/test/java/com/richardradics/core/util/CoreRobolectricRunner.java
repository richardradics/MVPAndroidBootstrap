package com.richardradics.core.util;


import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;
import org.robolectric.res.FsFile;

/**
 * Created by radicsrichard on 15. 05. 15..
 */
public class CoreRobolectricRunner extends RobolectricGradleTestRunner {

    public CoreRobolectricRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

    }
    protected AndroidManifest getAppManifest(Config config) {
        AndroidManifest appManifest = super.getAppManifest(config);
        FsFile androidManifestFile = appManifest.getAndroidManifestFile();

        // Workaround also wrong paths for res and assets.
        // This will be fixed with next robolectric release https://github.com/robolectric/robolectric/issues/1709
        if (androidManifestFile.exists()) {
            FsFile resDirectory = FileFsFile.from(appManifest.getAndroidManifestFile().getPath().replace("AndroidManifest.xml", "res"));
            FsFile assetsDirectory = FileFsFile.from(appManifest.getAndroidManifestFile().getPath().replace("AndroidManifest.xml", "assets"));
            return new AndroidManifest(androidManifestFile, resDirectory, assetsDirectory);
        } else {
            String moduleRoot = getModuleRootPath(config);
            androidManifestFile = FileFsFile.from(moduleRoot, appManifest.getAndroidManifestFile().getPath());
            FsFile resDirectory = FileFsFile.from(moduleRoot, appManifest.getAndroidManifestFile().getPath().replace("AndroidManifest.xml", "res"));
            FsFile assetsDirectory = FileFsFile.from(moduleRoot, appManifest.getAndroidManifestFile().getPath().replace("AndroidManifest.xml", "assets"));
            return new AndroidManifest(androidManifestFile, resDirectory, assetsDirectory);
        }
    }

    private String getModuleRootPath(Config config) {
        String moduleRoot = config.constants().getResource("").toString().replace("file:", "").replace("jar:", "");
        return moduleRoot.substring(0, moduleRoot.indexOf("/build"));
    }
}



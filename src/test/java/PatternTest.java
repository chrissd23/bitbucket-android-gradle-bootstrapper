import de.bootstrap.Utils;
import org.junit.Assert;
import org.junit.Test;

public class PatternTest {

    private static final String REG_COMPILED_SDK_VERSION = "compileSdkVersion\\s+\\d+";
    private static final String REG_BUILD_TOOLS_VERSION = "buildToolsVersion\\s+\\W\\d+\\.\\d+\\.[\\d+|\\W]\\W";
    private static final String REG_MIN_SDK_VERSION = "minSdkVersion\\s+\\d+";
    private static final String REG_TARGET_SDK_VERSION = "targetSdkVersion\\s+\\d+";
    private static final String REG_GRADLE_VERSION = "build:gradle\\W\\d+\\.\\d+\\.[\\d+|\\W]";

    private static final String ANDROID_BUILD_GRADLE_TEST_STRING = "android {\n" +
            "    compileSdkVersion 21\n" +
            "    buildToolsVersion '21.1.2'\n" +
            "\n" +
            "    defaultConfig {\n" +
            "        versionCode 53\n" +
            "        versionName \"3.1.3\"\n" +
            "        minSdkVersion 10\n" +
            "        targetSdkVersion 21\n" +
            "    }";

    @Test
    public void patternTestForCompiledSdkVersion() {
        String string = Utils.getPatternString(REG_COMPILED_SDK_VERSION, ANDROID_BUILD_GRADLE_TEST_STRING);
        Assert.assertEquals("compileSdkVersion 21", string);
    }

    @Test
    public void patternTestForBuildToolsVersion() {
        String string = Utils.getPatternString(REG_BUILD_TOOLS_VERSION, ANDROID_BUILD_GRADLE_TEST_STRING);
        Assert.assertEquals("buildToolsVersion '21.1.2'", string);

        String s = "buildToolsVersion '21.1.+'";
        string = Utils.getPatternString(REG_BUILD_TOOLS_VERSION, s);
        Assert.assertEquals("buildToolsVersion '21.1.+'", string);

    }


    @Test
    public void patternTestForMinSdkVersion() {
        String string = Utils.getPatternString(REG_MIN_SDK_VERSION, ANDROID_BUILD_GRADLE_TEST_STRING);
        Assert.assertEquals("minSdkVersion 10", string);
    }


    @Test
    public void patternTestForTargetSdkVersion() {
        String string = Utils.getPatternString(REG_TARGET_SDK_VERSION, ANDROID_BUILD_GRADLE_TEST_STRING);
        Assert.assertEquals("targetSdkVersion 21", string);
    }

    @Test
    public void patternTestForGradleVersion() {

        String s = "com.android.tools.build:gradle:1.0.0";
        String string = Utils.getPatternString(REG_GRADLE_VERSION, s);
        Assert.assertEquals("build:gradle:1.0.0", string);

        s = "com.android.tools.build:gradle:1.0.+";
        string = Utils.getPatternString(REG_GRADLE_VERSION, s);
        Assert.assertEquals("build:gradle:1.0.+", string);

        s = "com.crashlytics.tools.gradle:crashlytics-gradle:1.14.3";
        string = Utils.getPatternString(REG_GRADLE_VERSION, s);
        Assert.assertNull(string);

    }

}

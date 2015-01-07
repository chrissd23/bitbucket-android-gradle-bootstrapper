package de.bootstrap;


import com.squareup.okhttp.*;
import de.bootstrap.Utils;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class BootstrapTask {

    private static final String REG_COMPILED_SDK_VERSION = "compileSdkVersion\\s+\\d+";
    private static final String REG_BUILD_TOOLS_VERSION = "buildToolsVersion\\s+\\W\\d+\\.\\d+\\.[\\d+|\\W]\\W";
    private static final String REG_MIN_SDK_VERSION = "minSdkVersion\\s+\\d+";
    private static final String REG_TARGET_SDK_VERSION = "targetSdkVersion\\s+\\d+";
    private static final String REG_GRADLE_VERSION = "build:gradle\\W\\d+\\.\\d+\\.[\\d+|\\W]";

    private static String repositoryName;

    private static ArrayList<String> informations = new ArrayList<String>();

    private static boolean gradleUrlIsEmpty;

    private static boolean androidUrlIsEmpty;

    public static void bootstrap(String username, String password, String filePath, String outputPath) {

        File configuration = new File(filePath);

        JSONParser parser = new JSONParser();
        try {
            JSONArray configArray = (JSONArray) parser.parse(new FileReader(configuration));

            for (Object object : configArray) {
                JSONObject configObject = (JSONObject) object;

                repositoryName = (String) configObject.get("name");
                String androidInformationUrl = (String) configObject.get("androidInformationUrl");
                String gradleInformationUrl = (String) configObject.get("gradleInformationUrl");

                androidUrlIsEmpty = androidInformationUrl.isEmpty();
                gradleUrlIsEmpty = gradleInformationUrl.isEmpty();

                if (!androidUrlIsEmpty) {
                    makeAndroidRequest(username, password, androidInformationUrl);
                }

                if (!gradleUrlIsEmpty) {
                    makeGradleRequest(username, password, gradleInformationUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.writeLines(new File(outputPath + "output.txt"), informations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeGradleRequest(String username, String password, String url) {
        Response response = getResponse(username, password, url);

        if (!gradleUrlIsEmpty) {
            String gradleVersion = null;
            try {
                if (response != null) {
                    gradleVersion = Utils.getPatternString(REG_GRADLE_VERSION, response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            informations.add(gradleVersion);
        }

    }

    private static void makeAndroidRequest(final String username, final String password, String url) {
        Response response = getResponse(username, password, url);
        String responseString = null;
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!androidUrlIsEmpty) {
            if (responseString != null) {
                parseAndroidResponse(responseString);
                if (gradleUrlIsEmpty) {
                    String gradleVersion = Utils.getPatternString(REG_GRADLE_VERSION, responseString);
                    informations.add(gradleVersion);
                }
            }
        }
    }

    private static void parseAndroidResponse(String responseBody) {
        String compiledSdkVersion = Utils.getPatternString(REG_COMPILED_SDK_VERSION, responseBody);
        String buildToolsVersion = Utils.getPatternString(REG_BUILD_TOOLS_VERSION, responseBody);
        String minSdkVersion = Utils.getPatternString(REG_MIN_SDK_VERSION, responseBody);
        String targetSdkVersion = Utils.getPatternString(REG_TARGET_SDK_VERSION, responseBody);

        informations.add("");
        informations.add(repositoryName);
        informations.add(compiledSdkVersion);
        informations.add(buildToolsVersion);
        informations.add(minSdkVersion);
        informations.add(targetSdkVersion);

    }

    private static Response getResponse(String username, String password, String url) {

        OkHttpClient client = new OkHttpClient();
        String credentials = Credentials.basic(username, password);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credentials)
                .build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}

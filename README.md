bitbucket-android-gradle-bootstrapper
===========================

Download Android Metadata from every build.gradle file in your Bitbucket Repository.

Prerequisites
-------------

You need a special configuration file to give the tool the informations about your repositories.

```json
[
    {"name":"repositotyName",
     "androidInformationUrl":"https://bitbucket.org/repositotyName/raw/master/.../build.gradle",
     "gradleInformationUrl":"https://bitbucket.org/repositotyName/raw/master/.../build.gradle"},

      {"name":"repositotyName",
     "androidInformationUrl":"https://bitbucket.org/repositotyName/raw/master/.../build.gradle",
     "gradleInformationUrl":"https://bitbucket.org/repositotyName/raw/master/.../build.gradle"},

  ...
]
```

* **name**: The name of your repository
* **androidInformationUrl**: The url to you build.gradle for android informations
* **gradleInformationUrl**: The url to you build.gradle for gradle informations

If you have only on build.gradle file, set free gradleInformationUrl field



Usage
-----

Download the sources and compile the project with gradle
```
./gradlew assemble
```

Run
```
java -jar ./build/libs/bitbucket-android-gradle-bootstrapper-1.0.jar
```

The tool will ask you for the following information

* **Username**: The username of your bitbucket account
* **Password**: The Password of your account
* **Path to configuration file**:
* **Destination Path**: The destination you want to download the metadata to.

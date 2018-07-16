# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Programming\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


 -keep class com.squareup.picasso.** { *; }
 -keepclasseswithmembers class * {
     @com.squareup.picasso.** *;
 }
 -keepclassmembers class * {
     @com.squareup.picasso.** *;
 }
 -keepclassmembers class * {
     @com.squareup.picasso.Utils.** *;
 }
 -keepattributes SourceFile,LineNumberTable
 -keep class com.parse.*{ *; }
 -dontwarn com.parse.**
 -dontwarn com.squareup.picasso.**
 -dontwarn com.squareup.okhttp.internal.**
 -dontwarn com.demach.konotor.service.**
 -keep class javax.naming.** { *; }
 -keepclasseswithmembers class * {
     @javax.naming.** *;
 }
 -dontwarn javax.naming.**
 -keep class junit.** { *; }
 -keepclasseswithmembers class * {
     @junit.** *;
 }
 -dontwarn junit.**
 -dontwarn java.lang.management.**


 -keep public class * extends java.lang.Exception
 -keep public class * extends android.support.v7.app.ActionBarActivity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgent
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.support.v4.app.Fragment
 -keep public class * extends android.support.v4.app.DialogFragment
 -keep public class * extends android.app.Fragment
 -keep public class com.android.vending.licensing.ILicensingService


 -ignorewarnings
 -keep class * {
     public private *;
     }

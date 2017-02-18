# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/dingpengwei/Applications/dev/android/android-sdk-macosx/tools/proguard/proguard-android.txt
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
################### region for xUtils
-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keep public class * extends org.xutils.http.BaseParams
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}
#################### end region

-dontwarn android.support.**

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}
-keep class * implements java.io.Serializable { *; }
-keep public class * extends org.maxwe.tao.android.account.agent.AgentEntity
-keep public class * extends org.maxwe.tao.android.response.IResponse
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.database.sqlite.SQLiteOpenHelper{*;}

-keepattributes *Annotation*
-keepattributes Signature

-keepattributes SourceFile,LineNumberTable

-ignorewarning
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# 保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

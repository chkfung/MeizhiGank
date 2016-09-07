# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Shared/AndroidSDK/tools/proguard/proguard-android.txt
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

-dontobfuscate

# 代码混淆的压缩比例(0-7) , 默认为5 , 一般不需要改
#-optimizationpasses 5
#
## 混淆后类名都小写 (windows最后加上 , 因为windows大小写敏感)
#-dontusemixedcaseclassnames
#
## 指定不去忽略非公共的库的类(即混淆第三方, 第三方库可能自己混淆了 , 可在后面配置某些第三方库不混淆)
## 默认跳过，有些情况下编写的代码与类库中的类在同一个包下，并且持有包中内容的引用，此时就需要加入此条声明
#-dontskipnonpubliclibraryclasses
#
## 指定不去忽略非公共的库的类的成员
#-dontskipnonpubliclibraryclassmembers
#
## 不做预检验，preverify是proguard的四个步骤之一
## Android不需要preverify，去掉这一步可以加快混淆速度
#-dontpreverify
#
## 有了verbose这句话，混淆后就会生成映射文件
## 包含有类名->混淆后类名的映射关系
## 然后使用printmapping指定映射文件的名称
#-verbose
#-printmapping proguardMapping.txt
#
## 指定混淆时采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不改变
#-optimizations !code/simplification/cast,!field/*,!class/merging/*
#
## 保护代码中的Annotation不被混淆
## 这在JSON实体映射时非常重要，比如fastJson
#-keepattributes *Annotation*,InnerClasses
#
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
#
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#
#-keep public class me.chkfung.meizhigank.ui.widget.**
#
#-keepclassmembers class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator CREATOR;
#}
## 避免混淆泛型
## 这在JSON实体映射时非常重要，比如fastJson
#-keepattributes Signature
#
##抛出异常时保留源文件和代码行号
#-keepattributes SourceFile,LineNumberTable
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#
## The support library contains references to newer platform versions.
## Don't warn about those in case this app is linking against an older
## platform version.  We know about them, and they are safe.
#-dontwarn android.support.**
#
## Understand the @Keep support annotation.
#-keep class android.support.annotation.Keep
#
#-keep @android.support.annotation.Keep class * {*;}
#
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <methods>;
#}
#
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <fields>;
#}
#
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <init>(...);
#}
#
## Okio
#-keep class sun.misc.Unsafe { *; }
#-dontwarn java.nio.file.*
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-dontwarn okio.**
#
## Retrofit 2.X
### https://square.github.io/retrofit/ ##
#
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
#
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#}
#
## rxAndroid
#-dontwarn sun.misc.**
#
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#   long producerIndex;
#   long consumerIndex;
#}
#
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_ru_iu3_fclient_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "My first project";
    return env->NewStringUTF(hello.c_str());
}
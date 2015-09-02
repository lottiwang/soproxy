/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_forcetech_android_ForceTV */
#include <stdlib.h>
#include <fcntl.h>
#include <android/log.h>
#include <stdio.h>
#include <stdarg.h>
#include <dlfcn.h>
#ifndef _Included_com_forcetech_android_ForceTV
#define _Included_com_forcetech_android_ForceTV
#ifdef __cplusplus
extern "C" {
#endif
#undef com_forcetech_android_ForceTV_tcpport
#define com_forcetech_android_ForceTV_tcpport 9906L
#undef com_forcetech_android_ForceTV_pool20M
#define com_forcetech_android_ForceTV_pool20M 20971520L



void *filehandle = NULL;
jint (*startFunc)(JNIEnv *,jobject,jint,jint) = NULL;
jint (*stopFunc)(JNIEnv *, jobject) = NULL;
/*
 * Class:     com_forcetech_android_ForceTV
 * Method:    startnew
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_forcetech_android_ForceTV_startold
  (JNIEnv *env, jobject obj, jint a, jint b);

/*
 * Class:     com_forcetech_android_ForceTV
 * Method:    stopnew
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_forcetech_android_ForceTV_stopold
  (JNIEnv *, jobject);



JNIEXPORT jint JNICALL Java_com_forcetech_android_ForceTV_startold
  (JNIEnv *env, jobject obj, jint a, jint b)
{
	 filehandle = dlopen("libforcetvold.so", RTLD_LAZY);
	    if(filehandle)
	    {
	    	startFunc = (jint (*)(JNIEnv *,jobject,jint,jint))dlsym(filehandle, "Java_com_forcetech_android_ForceTV_start");
	        if(startFunc)
	        {
	        	return startFunc(env,obj,a,b);
	        }
	    }
}

JNIEXPORT jint JNICALL Java_com_forcetech_android_ForceTV_stopold
  (JNIEnv * env, jobject obj)
{
	 if(filehandle)
     {
		 jint ret=0;
		 stopFunc = (jint (*)(JNIEnv *,jobject))dlsym(filehandle, "Java_com_forcetech_android_ForceTV_stop");
		 if(stopFunc)
		 {
			 ret = stopFunc(env,obj);
		 }
		 dlclose(filehandle);
		 return ret;
     }
}













#ifdef __cplusplus
}
#endif
#endif

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := SOProxy
LOCAL_SRC_FILES := SOProxy.cpp

include $(BUILD_SHARED_LIBRARY)

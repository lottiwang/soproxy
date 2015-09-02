LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)


LOCAL_MODULE    := SOProxy
LOCAL_SRC_FILES := SOProxy.cpp
include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE := libforcetv
LOCAL_SRC_FILES := prebuilt/libforcetv.so
include  $(PREBUILT_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE := libforcetvold
LOCAL_SRC_FILES := prebuilt/libforcetvold.so
include  $(PREBUILT_SHARED_LIBRARY)
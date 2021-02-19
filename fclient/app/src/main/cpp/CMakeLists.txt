# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("fclient")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )

# add spdlog and mbedtls
add_definitions(-DSPDLOG_COMPILED_LIB)
add_library(spdlog STATIC IMPORTED)
add_library(mbedcrypto SHARED IMPORTED)

target_include_directories(
        native-lib PUBLIC
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/spdlog/spdlog/include
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/mbedtls/mbedtls/include
)

set_target_properties(
        spdlog PROPERTIES IMPORTED_LOCATION /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/spdlog/build/${ANDROID_ABI}/libspdlog.a
)

set_target_properties(
#        mbedcrypto PROPERTIES IMPORTED_LOCATION /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/fclient/app/src/main/cmakeLibs/${ANDROID_ABI}/libmbedcrypto.so
        mbedcrypto PROPERTIES IMPORTED_LOCATION /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/mbedtls/build/${ANDROID_ABI}/library/libmbedcrypto.so
)

configure_file(
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/mbedtls/build/${ANDROID_ABI}/library/libmbedcrypto.so
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/fclient/app/src/main/cmakeLibs/${ANDROID_ABI}/libmbedcrypto.so
        COPYONLY
)

configure_file(
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/mbedtls/build/x86/library/libmbedcrypto.so
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/fclient/app/src/main/cmakeLibs/x86/libmbedcrypto.so
        COPYONLY
)
configure_file(
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/libs/mbedtls/build/x86_64/library/libmbedcrypto.so
        /Users/vlad_voropaev/Yandex.Disk.localized/bmstu/sem_6/software_development/projects/fclient/app/src/main/cmakeLibs/x86_64/libmbedcrypto.so
        COPYONLY
)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        native-lib
        spdlog
        mbedcrypto
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} )

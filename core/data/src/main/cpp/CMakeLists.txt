cmake_minimum_required(VERSION 3.22)
project("sudokucpp")

# build static library from all the .cpp files
add_library(sudokucpp STATIC
    GameCell.cpp
    GameField.cpp
    GameFieldGenerator.cpp
    GameHandler.cpp
)

# build shared library of JNI-classes
add_library(sudoku_jni SHARED
    sudoku_jni.cpp
)

target_link_libraries(sudoku_jni
    sudokucpp
    log
)

include_directories(${CMAKE_CURRENT_SOURCE_DIR})

#include <jni.h>
#include "GameHandler.hpp"
#include "exceptions.hpp"

// this class - JNI wrapper for CPP class GameHandler
// all the functions use reinterpret_cast<> to convert jlong type
// to GameHandler* type

// FAQ about this code:
// 1. what is extern "C"
// extern "C" construction says to compiler to use C-style linking approach (not CPP-style)
//
// 2. what is jlong
// jlong - type from jni.h library, which is the same long data type in java (8 bytes long int)
// jlong is used to pass pointers from C++ to Java
//
// 3. what is reinterpret_cast<>
// reinterpret_cast<> converts bits of one object in memory to bits of another object
// without any strict checking
//
// 4. what is JNIEXPORT and JNICALL
// JNIEXPORT and JNICALL are macros, which mark function as JNI-converter from C++ to Java

// helper-function to re-throw CPP-exceptions to Java-exceptions
void throwJavaException(JNIEnv *env, const std::exception &e) {
    jclass javaClass = env->FindClass("java/lang/RuntimeException");
    if (javaClass) {
        env->ThrowNew(javaClass, e.what());
    }
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_terabyte_sudokucppgame_SudokuNative_createGame(JNIEnv *env, jobject thiz, jint difficulty) {
    try {
        GameHandler *handler = new GameHandler(static_cast<GameDifficulty>(difficulty));
        return reinterpret_cast<jlong>(handler);
    }
    catch (const std::exception &e) {
        throwJavaException(env, e);
        return 0;
    }
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_terabyte_sudokucppgame_SudokuNative_makeTurn(JNIEnv *env, jobject thiz, jlong ptr, jint row,
                                                  jint column, jint value) {
    GameHandler* handler = reinterpret_cast<GameHandler*>(ptr);
    try {
        bool result = handler->makeTurn(row, column, value);
        return result ? JNI_TRUE : JNI_FALSE;
    }
    catch (const std::exception& e) {
        throwJavaException(env, e);
        return JNI_FALSE;
    }
}


extern "C" JNIEXPORT jint JNICALL
Java_com_terabyte_sudokucppgame_SudokuNative_getMistakes(JNIEnv* env, jobject thiz, jlong ptr) {
    GameHandler* handler = reinterpret_cast<GameHandler*>(ptr);
    return handler->getAmountMistakes();
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_terabyte_sudokucppgame_SudokuNative_isVictory(JNIEnv* env, jobject thiz, jlong ptr) {
    GameHandler* handler = reinterpret_cast<GameHandler*>(ptr);
    return handler->isVictory() ? JNI_TRUE : JNI_FALSE;
}

extern "C" JNIEXPORT jintArray JNICALL
Java_com_terabyte_sudokucppgame_SudokuNative_getPuzzleField(JNIEnv* env, jobject thiz, jlong ptr) {
    GameHandler* handler = reinterpret_cast<GameHandler*>(ptr);
    GameField gameField = handler->getGameFieldPuzzle();
    jintArray arrayResult = env->NewIntArray(81);
    jint fill[81];
    for (int row = 0; row < 9; ++row) {
        for (int column = 0; column < 9; ++column) {
            fill[row*9 + column] = gameField.getCellNumber(row, column);
        }
    }
    env->SetIntArrayRegion(arrayResult, 0, 81, fill);
    return arrayResult;
}

extern "C" JNIEXPORT void JNICALL
Java_com_terabyte_sudokucppgame_SudokuNative_deleteGame(JNIEnv* env, jobject thiz, jlong ptr) {
    GameHandler* handler = reinterpret_cast<GameHandler*>(ptr);
    delete handler;
}
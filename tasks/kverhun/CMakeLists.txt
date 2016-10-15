cmake_minimum_required(VERSION 2.8.9)

project(catch_builder CXX)
include(ExternalProject)
find_package(Git REQUIRED)

ExternalProject_Add(
    catch
    PREFIX ${CMAKE_BINARY_DIR}/catch
    GIT_REPOSITORY https://github.com/philsquared/Catch.git
    TIMEOUT 10
    UPDATE_COMMAND ${GIT_EXECUTABLE} pull
    CONFIGURE_COMMAND ""
    BUILD_COMMAND ""
    INSTALL_COMMAND ""
    LOG_DOWNLOAD ON
   )

# Expose required variable (CATCH_INCLUDE_DIR) to parent scope
ExternalProject_Get_Property(catch source_dir)
set(CATCH_INCLUDE_DIR ${source_dir}/include CACHE INTERNAL "./catch")

project(Algorithms)
# Includes Catch in the project:

add_executable(Words 02-Words/impl/main.cpp)
add_executable(TextJustification 03-TextJustification/impl/main.cpp)

set(LCS_SOURCE_DIR 04-LCS/impl)
add_library(LCS SHARED ${LCS_SOURCE_DIR}/lcs.cpp ${LCS_SOURCE_DIR}/_Library.h ${LCS_SOURCE_DIR}/lcs.h)
target_compile_definitions(LCS PRIVATE LCS_LIBRARY)

add_executable(LCS_Tests 04-LCS/unit_tests/lcs_test.cpp)
target_link_libraries(LCS_Tests PUBLIC LCS)

add_executable(WordsAlignment 05-WordsAlignment/impl/main.cpp)

include_directories(${CATCH_INCLUDE_DIR} ${COMMON_INCLUDES})
enable_testing(true)  # Enables unit-testing.

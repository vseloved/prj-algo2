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

####################################################
# Longest Common Subsequence
####################################################
set(LCS_SOURCE_DIR 04-LCS/impl)
add_library(LCS SHARED ${LCS_SOURCE_DIR}/lcs.cpp ${LCS_SOURCE_DIR}/_Library.h ${LCS_SOURCE_DIR}/lcs.h)
target_compile_definitions(LCS PRIVATE LCS_LIBRARY)

add_executable(LCS_Tests 04-LCS/unit_tests/lcs_test.cpp)
target_link_libraries(LCS_Tests PUBLIC LCS)
add_dependencies(LCS_Tests catch)
####################################################

####################################################
# Words Alignment
####################################################
set(WordsAlignment_SOURCE_DIR 05-WordsAlignment/impl)
set(WordsAlignment_TEST_DIR 05-WordsAlignment/unit_tests)

add_library(WordsAlignment SHARED 
    ${WordsAlignment_SOURCE_DIR}/_Library.h
    ${WordsAlignment_SOURCE_DIR}/WordsAlignment.h
    ${WordsAlignment_SOURCE_DIR}/WordsAlignment.cpp)
target_compile_definitions(WordsAlignment PRIVATE WORDS_ALIGNMENT_LIBRARY)

add_executable(WordsAlignment_Tests ${WordsAlignment_TEST_DIR}/words_alignment_tests.cpp)
target_link_libraries(WordsAlignment_Tests PUBLIC WordsAlignment)
add_dependencies(WordsAlignment_Tests catch)
####################################################

####################################################
# TSP
####################################################
set(TSP_SOURCE_DIR 08-TSP/impl)
set(TSP_TEST_DIR 08-TSP/unit_tests) 

add_library(TSP SHARED
    ${TSP_SOURCE_DIR}/_Library.h
    ${TSP_SOURCE_DIR}/TSP.h
    ${TSP_SOURCE_DIR}/TSP.cpp
    ${TSP_SOURCE_DIR}/GeneticAlgo.h
    ${TSP_SOURCE_DIR}/GeneticAlgo.cpp
)
target_compile_definitions(TSP PRIVATE TSP_LIBRARY)

add_executable(TSP_Tests 
    ${TSP_TEST_DIR}/tsp_tests.cpp
    ${TSP_TEST_DIR}/tsp_genetic_tests.cpp
)
target_link_libraries(TSP_Tests PUBLIC TSP)
add_dependencies(TSP_Tests TSP)

####################################################

include_directories(${CATCH_INCLUDE_DIR} ${COMMON_INCLUDES})
enable_testing(true)  # Enables unit-testing.
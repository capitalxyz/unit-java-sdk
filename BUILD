load("@rules_java//java:defs.bzl", "java_library")
load("//jvm/tools/java:defs.bzl", "java_junit5_test", "java_lint")

java_library(
    name = "unit_sdk",
    srcs = glob(["src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//jvm/auto_value",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_fasterxml_jackson_datatype_jackson_datatype_jdk8",
        "@maven//:com_google_code_findbugs_jsr305",
        "@maven//:com_google_guava_guava",
    ],
)

java_lint(name = "lint")

java_junit5_test(
    name = "tests",
    srcs = glob(["src/test/java/**/*.java"]),
    resources = glob(["src/test/resources/**/*"]),
    test_package = "partyround.unit",
    deps = [
        ":unit_sdk",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
    ],
)

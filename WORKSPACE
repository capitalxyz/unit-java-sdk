workspace(
    name = "unit_sdk",
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file", "http_jar")

################################################################################
# skylib setup
################################################################################

http_archive(
    name = "bazel_skylib",
    sha256 = "af87959afe497dc8dfd4c6cb66e1279cb98ccc84284619ebfec27d9c09a903de",
    urls = [
        "https://github.com/bazelbuild/bazel-skylib/releases/download/1.2.0/bazel-skylib-1.2.0.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-skylib/releases/download/1.2.0/bazel-skylib-1.2.0.tar.gz",
    ],
)

load("@bazel_skylib//:workspace.bzl", "bazel_skylib_workspace")

bazel_skylib_workspace()

################################################################################
# java setup
################################################################################

http_archive(
    name = "rules_java",
    sha256 = "ddc9e11f4836265fea905d2845ac1d04ebad12a255f791ef7fd648d1d2215a5b",
    strip_prefix = "rules_java-5.0.0",
    url = "https://github.com/bazelbuild/rules_java/archive/refs/tags/5.0.0.tar.gz",
)

load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")

rules_java_dependencies()

rules_java_toolchains()

################################################################################
# jvm setup
################################################################################

RULES_JVM_EXTERNAL_VERSION = "4.2"

RULES_JVM_EXTERNAL_SHA = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_VERSION,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/refs/tags/%s.zip" % RULES_JVM_EXTERNAL_VERSION,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("//:dependencies.bzl", "JVM_DEPENDENCIES")

maven_install(
    artifacts = JVM_DEPENDENCIES,
    fetch_sources = True,
    maven_install_json = "//:maven_install.json",
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://maven.google.com",
    ],
    version_conflict_policy = "pinned",
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

################################################################################
# google-java-format setup
################################################################################

http_file(
    name = "com_google_googlejavaformat",
    downloaded_file_path = "googlejavaformat.jar",
    executable = True,
    sha256 = "a356bb0236b29c57a3ab678f17a7b027aad603b0960c183a18f1fe322e4f38ea",
    urls = ["https://github.com/google/google-java-format/releases/download/v1.15.0/google-java-format-1.15.0-all-deps.jar"],
)

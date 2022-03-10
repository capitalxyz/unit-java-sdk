load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

package(default_visibility = ["//visibility:public"])

AUTO_VALUE_DEPS = [
    "@maven//:com_google_auto_value_auto_value",
    "@maven//:com_google_auto_value_auto_value_annotations",
]

java_plugin(
    name = "auto_value_plugin",
    generates_api = True,
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
    deps = AUTO_VALUE_DEPS,
)

java_library(
    name = "auto_value",
    exported_plugins = [":auto_value_plugin"],
    exports = AUTO_VALUE_DEPS,
)

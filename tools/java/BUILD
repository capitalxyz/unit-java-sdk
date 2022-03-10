load("@rules_java//java:defs.bzl", "java_binary", "java_import")

java_import(
    name = "googlejavaformat_jar",
    jars = ["@com_google_googlejavaformat//file"],
)

java_binary(
    name = "googlejavaformat",
    jvm_flags = [
        "--add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
    ],
    main_class = "com.google.googlejavaformat.java.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [":googlejavaformat_jar"],
)

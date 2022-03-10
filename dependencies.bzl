"""
This module contains all of the dependencies installed via rules_jvm_external
"""

AUTO_VALUE_VERSION = "1.9"
GRPC_VERSION = "1.44.0"
JACKSON_VERSION = "2.13.1"
JUNIT_JUPITER_VERSION = "5.8.2"
JUNIT_PLATFORM_VERSION = "1.8.2"
RESILIENCE4J_VERSION = "1.7.1"

JVM_DEPENDENCIES = [
    "args4j:args4j:2.33",
    "app.cash.barber:barber:0.3.3",
    "commons-io:commons-io:2.11.0",
    "com.charleskorn.kaml:kaml-jvm:0.40.0",
    "com.eatthepath:pushy:0.15.0",
    "com.fasterxml.jackson.core:jackson-core:%s" % JACKSON_VERSION,
    "com.fasterxml.jackson.core:jackson-databind:%s" % JACKSON_VERSION,
    "com.fasterxml.jackson.dataformat:jackson-dataformats-text:%s" % JACKSON_VERSION,
    "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:%s" % JACKSON_VERSION,
    "com.github.chrisvest:stormpot:3.1",
    "com.github.dikhan:pagerduty-client:3.1.0",
    "com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0",
    "com.google.auto.value:auto-value:%s" % AUTO_VALUE_VERSION,
    "com.google.auto.value:auto-value-annotations:%s" % AUTO_VALUE_VERSION,
    "com.google.cloud:google-cloud-core:2.5.0",
    "com.google.cloud:google-cloud-monitoring:3.2.2",
    "com.google.cloud:google-cloud-pubsub:1.115.2",
    "com.google.cloud:google-cloud-storage:2.4.0",
    "com.google.cloud:google-cloud-trace:2.1.2",
    "com.google.cloud.opentelemetry:exporter-trace:0.20.0",
    "com.google.code.findbugs:jsr305:3.0.2",
    "com.google.guava:guava:30.1.1-jre",
    "com.google.protobuf:protobuf-java:3.19.1",
    "com.plaid:plaid-java:9.9.0",
    "com.nimbusds:nimbus-jose-jwt:9.19",
    "com.squareup.retrofit2:retrofit:2.9.0",
    "com.zaxxer:HikariCP:5.0.1",
    "dev.turingcomplete:kotlin-onetimepassword:2.1.0",
    "io.github.java-diff-utils:java-diff-utils:4.11",
    "io.github.resilience4j:resilience4j-bulkhead:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-cache:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-circuitbreaker:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-kotlin:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-micrometer:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-micronaut:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-ratelimiter:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-reactor:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-retry:%s" % RESILIENCE4J_VERSION,
    "io.github.resilience4j:resilience4j-timelimiter:%s" % RESILIENCE4J_VERSION,
    "io.mockk:mockk:1.12.1",
    "jakarta.inject:jakarta.inject-api:2.0.1",
    "jakarta.validation:jakarta.validation-api:3.0.0",
    "net.pwall.json:json-kotlin-schema:0.30",
    "org.apiguardian:apiguardian-api:1.1.2",
    "org.hibernate:hibernate-core:5.5.8.Final",
    "org.junit.jupiter:junit-jupiter-api:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.jupiter:junit-jupiter-engine:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.jupiter:junit-jupiter-params:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.platform:junit-platform-commons:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-console:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-engine:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-launcher:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-suite-api:%s" % JUNIT_PLATFORM_VERSION,
    "org.opentest4j:opentest4j:1.2.0",
    "org.skyscreamer:jsonassert:1.5.0",
]

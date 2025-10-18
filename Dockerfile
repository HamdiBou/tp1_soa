FROM eclipse-temurin:17-jdk

# Set working directory early
WORKDIR /workspace

# Install Maven (doesn't require apt-get)
ARG MAVEN_VERSION=3.9.5
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -o maven.tar.gz && \
    tar -xzf maven.tar.gz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    rm maven.tar.gz

ENV MAVEN_HOME=/opt/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# Install OpenAPI Generator CLI (doesn't require apt-get)
ARG OPENAPI_VERSION=7.7.0
RUN curl -fsSL https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/${OPENAPI_VERSION}/openapi-generator-cli-${OPENAPI_VERSION}.jar \
    -o /usr/local/bin/openapi-generator-cli.jar

# Create convenient command alias
RUN echo '#!/bin/sh\njava -jar /usr/local/bin/openapi-generator-cli.jar "$@"' > /usr/local/bin/openapi-generator && \
    chmod +x /usr/local/bin/openapi-generator

# Expose Spring Boot default port
EXPOSE 8080

# Keep container running
CMD ["/bin/bash"]
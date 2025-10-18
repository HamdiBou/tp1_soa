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

# Install Node.js 20.x and npm
ARG NODE_VERSION=20.11.0
RUN curl -fsSL https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-x64.tar.gz -o node.tar.gz && \
    tar -xzf node.tar.gz -C /opt && \
    ln -s /opt/node-v${NODE_VERSION}-linux-x64 /opt/node && \
    rm node.tar.gz

ENV NODE_HOME=/opt/node
ENV PATH="${NODE_HOME}/bin:${PATH}"

# Install Angular CLI globally
RUN npm install -g @angular/cli@17

# Expose Spring Boot default port and Angular dev server port
EXPOSE 8086 4200

# Keep container running
CMD ["/bin/bash"]
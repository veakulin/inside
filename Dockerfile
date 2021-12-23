FROM adoptopenjdk/openjdk11
COPY inside.db /testapp/inside.db
COPY maven/ /testapp/maven/
COPY pom.xml /testapp/pom.xml
COPY src/ /testapp/src/
WORKDIR /testapp
ENTRYPOINT ["/testapp/maven/bin/mvn", "clean", "spring-boot:run"]

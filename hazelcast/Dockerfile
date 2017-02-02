FROM hazelcast/hazelcast

ADD build/libs /application
WORKDIR /application

CMD ["java", "-jar", "hazelcast-app-1.0.jar"]
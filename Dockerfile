#open jdk java11 버전의 환경 구성
FROM adoptopenjdk/openjdk11

#build가 되는 시점에 JAR_FILE 이라는 변수명에 아래 표현식을 선언
ARG JAR_FILE=./p-application/build/libs/*.jar

#위에 선언한 JAR_FILE 을 p-application.jar 로 복사.
COPY ${JAR_FILE} p-application.jar

#jar 파일을 실행하는 명령어(java -jar jar파일)
ENTRYPOINT ["java","-jar","/p-application.jar"]

#ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app.jar"]
#운영 및 개발에서 사용되는 환경 설정을 분리해서 사용할 경우 ENTRYPOINT 설정
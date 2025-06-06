# 베이스 이미지 설정
FROM ubuntu:22.04

# 기본 패키지 설치
RUN apt-get -y update
RUN apt-get -y upgrade

# 시간대 설정 (Asia/Seoul 시간대로)
RUN DEBIAN_FRONTEND=noninteractive TZ=Asia/Seoul apt-get -y install tzdata
RUN ln -fs /usr/share/zoneinfo/Asia/Seoul /etc/localtime && dpkg-reconfigure -f noninteractive tzdata

# OpenJDK 설치
RUN apt install -y openjdk-17-jdk

# 프로젝트 jar 파일 복사
COPY ../../../build/libs/template_manage-0.0.1-SNAPSHOT.jar /app/

# 작업 디렉토리 생성 및 이동
WORKDIR /app

# Spring Boot 애플리케이션 실행
CMD ["java", "-jar", "-Dspring.profiles.active=dev8080", "/app/template_manage-0.0.1-SNAPSHOT.jar"]
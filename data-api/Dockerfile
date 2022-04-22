# 该镜像需要依赖的基础镜像
FROM openjdk:8-jdk-slim
# 构建参数
ARG JAR_FILE
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/${JAR_FILE} /${JAR_FILE}
# 声明服务运行在8081端口
EXPOSE 7070
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/${JAR_FILE}","--spring.profiles.active=prod"]
# 指定维护者的名字
MAINTAINER kirk
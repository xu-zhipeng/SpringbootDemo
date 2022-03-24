# 该镜像需要依赖的基础镜像
FROM submission:test4
COPY server-init.sh /tongyu/docker-images/server-init.sh
RUN chmod +x /tongyu/docker-images/server-init.sh
WORKDIR /tongyu/docker-images
ENTRYPOINT /tongyu/docker-images/server-init.sh && tail -f /dev/null

# 指定维护者的名字
MAINTAINER kirk
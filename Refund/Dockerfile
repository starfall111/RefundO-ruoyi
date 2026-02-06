# 使用 CentOS 7 作为基础镜像
FROM ubuntu:20.04

# 添加 JDK 到镜像中
COPY jdk-8u202-linux-x64.tar.gz /usr/local/
RUN tar -xzf /usr/local/jdk-8u202-linux-x64.tar.gz -C /usr/local/ &&  rm /usr/local/jdk-8u202-linux-x64.tar.gz

# 设置环境变量
ENV JAVA_HOME=/usr/local/jdk1.8.0_202
ENV PATH=$JAVA_HOME/bin:$PATH

# 阿里云OSS环境变量
ENV OSS_ACCESS_KEY_ID=LTAI5tLtBiWSUg4RPwitqUnQ
ENV OSS_ACCESS_KEY_SECRET=pdPHMZ85NnyIkf1Y8o0JtGeFtfev0u

#统一编码
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

# 创建应用目录
RUN mkdir -p /refundo-ruoyi
RUN mkdir -p /apk
WORKDIR /refundo-ruoyi

# 复制应用 JAR 文件到容器
COPY  RefundO-admin.jar  RefundO-admin.jar

# 暴露端口
EXPOSE 8080

# 运行命令
ENTRYPOINT ["java","-jar","/refundo-ruoyi/RefundO-admin.jar"]
FROM ubuntu
RUN apt-get update && \
    apt-get install -y abiword
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk
RUN apt-get update && \
    apt-get install -y vim
    #Thiết lập thư mục hiện tại
WORKDIR /RFSserver/out/production/RFSserver
# Copy tất cả các file trong thư mục hiện tại (.)  vào WORKDIR
ADD . /RFSserver
COPY ./out /production/RFSserver/RFSserver.jar
COPY default.policy /etc/java-11-openjdk/security
CMD abiword
CMD ["java","-jar","RFSserver.jar"]
# CMD ["java","-jar","RFSserver.jar"]
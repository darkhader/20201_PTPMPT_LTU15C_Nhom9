
FROM ubuntu
RUN apt-get update && \
    apt-get install -y abiword
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk
    #Thiết lập thư mục hiện tại
WORKDIR /RFSclient/out/production/RFSclient
# Copy tất cả các file trong thư mục hiện tại (.)  vào WORKDIR
ADD . /RFSclient
COPY ./out /production/RFSclient/RFSclient.jar
CMD abiword
# CMD ["java","-jar","RFSclient.jar"]
CMD ["java","-jar","RFSclient.jar"]

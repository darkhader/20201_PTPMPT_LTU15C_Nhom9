# Object
  GUI App quản lý files từ xa ứng dụng Docker, RMI
# Member
   * Đỗ Thị Anh Thư
   * Nguyễn Hoàng Hiệp
   * Nguyễn Đức Đạt
   * Nguyễn Hữu Vinh
# Details
  * Basic functionalities only, as nothing more complex was needed
  * Sever + Client sử dụng RMI để giao tiếp với nhau
  * Client triển khai trên 1 container
  * Server triển khai trên 1 container
  * Chạy XWindow màn ảo cho Docker chạy GUI App
  ## Build Docker
  ```python
docker build -t client .
docker build -t server .
```
  ## Run 
  ```python
docker run -e DISPLAY=192.168.2.109:0  client 
docker run -e DISPLAY=192.168.2.109:0  server
```
  ## Set up Jar  
  ```python
java -jar RFSserver.jar 
java -jar RFSclient.jar 
```
## EVN Docker  
  ```python
openjdk-11-jdk
abiword
```
## Play  
* run server then check ip on your docker 172.17.0.3
  ```python
  hostname --ip-address
```
```
* run client on XServer, enter server ip
# Having Fun

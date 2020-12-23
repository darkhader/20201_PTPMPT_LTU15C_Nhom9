# Object
  GUI App quản lý files từ xa ứng dụng Docker, RMI.
# Member
   * Đỗ Thị Anh Thư.
   * Nguyễn Hoàng Hiệp.
   * Nguyễn Đức Đạt.
   * Nguyễn Hữu Vinh.
# Details
  * Phần mềm thêm sửa xóa files, folders.
  * Sever + Client sử dụng RMI để giao tiếp với nhau.
  * Client triển khai trên 1 container.
  * Server triển khai trên 1 container.
  * Chạy XCVSrv màn ảo cho Docker chạy GUI App.
# Requirement
  * XCVSrv -- An X Server for Windows -- https://sourceforge.net/projects/vcxsrv/
  * Docker Desktop https://www.docker.com/products/docker-desktop
# Run
 * Bước 1: 
   - Chạy Docker Desktop.
   - Vào folder RFSclient/RFSserver chạy lệnh lần lượt các lệnh sau để build images Docker = DockerFile.
  ```python
docker build -t client .
docker build -t server .
```
* Bước 2: 
  - Chạy XCVSrv following setup here: https://www.youtube.com/watch?v=YbXDJJE5zsc&t=372s&ab_channel=WintellectNOW
  - Vào cmd gõ ipconfig lấy địa chỉ ip máy.
* Bước 3: 
  - Chạy image server.
   ```python
    docker run server
    ```
  - Lấy địa chỉ ip của server để giao tiếp vs client qua RMI.
  ```python
    hostname --ip-address
    ```
 * Bước 4: 
   - Chạy image client trên XWindow.
   ```python
    docker run -e DISPLAY=<your_ip>:0  client 
   ```
   - Hiển thị cửa sổ GUI và điền địa chỉ ip của server đã lấy ở trên.
   - Thêm sửa xóa files, folders trên giao diện kết nối thành công.
   - Folder lưu trữ trên server: root/RFS
  



# Tính Năng Chính 
- [x] Login/Sign up
- [x] Tạo một shop của chính mình.
- [x] Chỉnh sửa thông tin của một shop/ chỉnh sửa thông tin của một sản phẩm
- Thông tin một shop:
  - [x] Ảnh đại diện của shop
  - [x] Tên shop 
  - [x] Giờ đóng cửa/mở cửa
  - [x] Địa chỉ
  - [x] Địa điểm (location)
  - [x] Danh sách các sản phẩm: tên, hình ảnh, giá

* [x] Tìm kiếm shop, sản phẩm:
  * [x] Tìm kiếm cơ bản: text
  * [ ] Tìm kiếm bằng hình ảnh

- [ ] Chọn một item vào danh sách yêu thích 
- [x] Chỉ đường đến shop
- [x] Chọn mua
- [ ] Thông báo tới chủ shop khi có người đặt mua đó
- [ ] Chat giữa người mua và chủ shop (nếu muốn =]] )
- [ ] Shopping planner: lên kế hoạch đi shopping (Để sau)
# Các màn hình chính:
1. [x] Màn hình sign in / sign up
2. Màn hình trang chủ:
- [x] Thanh search 
- [x] Top các shop
- [x] Top các sản phẩm
- [x] Danh mục sản phẩm 
3. Profile: 
- [x] Avatar
- Hình ảnh (???)
- [ ] Danh sách sản phẩm đã mua
- [x] Danh sách shop của mình
4. [x] Màn hình tạo shop: gồm các thông tin cần thiết 
5. Màn hình chi tiết shop
6. Màn hình chi tiết sản phẩm 
7. Màn hình tất cả các sản phẩm (có filter)

# Database 
1. User
- uid
- name
2. Shop
- owner: uid
- shopName
- address: chuỗi
- location: lat lng
- open_hour
- close_hour
3. Item:
- iid
- sid
- name:
- description
- category:
- price:
- quantity:
- buys
- variation:
    + color 
    + size

4. Cart
- uid
- items: dict<iid, {immediate_price, quantity}>

5. Order
- oid
- uid
- items<CartItem>
- payment_method
- address
- order_time

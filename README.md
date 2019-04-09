# TestSample
Learn about Test in Android

# Overview

* Android Studio được thiết kế để tạo ra testing một cách dễ dàng. Với một vài click, bạn đã có thể cài đặt JUnit và chạy trên JVM hoặc trên một thiết bị cụ thể.
* Tất nhiên bạn cũng có thể mở rộng khả năng testing như tích hợp [Mockito](https://github.com/mockito/mockito) để test API, [Espresso](https://developer.android.com/training/testing/#Espresso) hoặc [UI Automator](https://developer.android.com/training/testing/#UIAutomator) để test tương tác người dùng. Bạn có thể tạo ra Espresso một cách tự động sử dụng [Espresso Test Recorder](https://developer.android.com/studio/test/espresso-test-recorder.html).

## Test types and location

Vị trí của code test phụ thuộc vào loại test mà bạn đang viết. Android Studio cung cấp các thư mục source code cho 2 loại test:

* `Local unit tests`: 

    * Nằm trong thư mục **module-name/src/test/java/**
    * Là các test chạy trên JVM(Java Vitual Machine), sử dụng các test này để giảm thiểu thời gian thực hiện khi các test của bạn không phụ thuộc vào Android framework hoặc khi bạn có thể giả định phụ thuộc của Android Framework.
    * Trong khi chạy, các test này được thực hiện với phiên bản sử đổi của **android.jar**. Điều này cho phép bạn sử dụng các thư viện mocking, giống như **Mockito**.
    
* `Instrumented tests`:
    
    * Nằm trong thư mục **module-name/src/androidTest/java/**.
    * Những bài test này chạy trên thiết bị thật hoặc là máy ảo. Các bài test này có quyền truy cập vào API của thiết bị, cung cấp cho bạn quyền truy cập các thông tin như Context đang kiểm tra và kiểm soát ứng dụng được test từ code test.
    * Thực hiện các bài test này khi viết tích hợp và kiểm tra giao diện để tự động hóa tương tác của người dùng hoặc các bài test khác phụ thuộc Android mà các đối tượng giả không thể tạo ra.
    * Bởi vì các bài test được tích hợp vào APK(tách biệt với APK ứng dụng của bạn), nên chúng ta phải có tệp **AndroidMenifest.xml** của riêng nó. Tuy nhiên Gradle tự động tạo tệp này trong quá trình xây dựng để nó không hiển thị trong source code của bạn. Nếu cần bạn có thể thêm tệp manifest vào thư mục test.

## Add a new test


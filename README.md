# TestSample
Learn about Test in Android

# Overview

* Android Studio được thiết kế để tạo ra testing một cách dễ dàng. Với một vài click, bạn đã có thể cài đặt JUnit và chạy trên JVM hoặc trên một thiết bị cụ thể.
* Tất nhiên bạn cũng có thể mở rộng khả năng testing như tích hợp [Mockito](https://github.com/mockito/mockito) để test API, [Espresso](https://developer.android.com/training/testing/#Espresso) hoặc [UI Automator](https://developer.android.com/training/testing/#UIAutomator) để test tương tác người dùng. Bạn có thể tạo ra Espresso một cách tự động sử dụng [Espresso Test Recorder](https://developer.android.com/studio/test/espresso-test-recorder.html).

## Test types and location

Vị trí của code test phụ thuộc vào loại test mà bạn đang viết. Android Studio cung cấp các thư mục source code cho 2 loại test:

* `Local unit tests`

    * Nằm trong thư mục **module-name/src/test/java/**
    * Là các test chạy trên JVM(Java Vitual Machine), sử dụng các test này để giảm thiểu thời gian thực hiện khi các test của bạn không phụ thuộc vào Android framework hoặc khi bạn có thể giả định phụ thuộc của Android Framework.
    * Trong khi chạy, các test này được thực hiện với phiên bản sử đổi của **android.jar**. Điều này cho phép bạn sử dụng các thư viện mocking, giống như **Mockito**.
    
* `Instrumented tests`
    
    * Nằm trong thư mục **module-name/src/androidTest/java/**.
    * Những bài test này chạy trên thiết bị thật hoặc là máy ảo. Các bài test này có quyền truy cập vào API của thiết bị, cung cấp cho bạn quyền truy cập các thông tin như Context đang kiểm tra và kiểm soát ứng dụng được test từ code test.
    * Thực hiện các bài test này khi viết tích hợp và kiểm tra giao diện để tự động hóa tương tác của người dùng hoặc các bài test khác phụ thuộc Android mà các đối tượng giả không thể tạo ra.
    * Bởi vì các bài test được tích hợp vào APK(tách biệt với APK ứng dụng của bạn), nên chúng ta phải có tệp **AndroidMenifest.xml** của riêng nó. Tuy nhiên Gradle tự động tạo tệp này trong quá trình xây dựng để nó không hiển thị trong source code của bạn. Nếu cần bạn có thể thêm tệp manifest vào thư mục test.

## Create Local Test

* Thêm dependency của **junit** và **mockito** vào trong project để sử dụng trong khi viết test.

```
testImplementation 'junit:junit:4.12'
testImplementation 'org.mockito:mockito-core:1.10.19'
```

## JUnit Test

* Ở đây có hàm kiểm tra xem chuỗi nhập vào có phải là định dạng Email hay không, sẽ viết Unit Test cho hàm này. 

```
class EmailValidator {

    companion object {
        private val EMAIL_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )

        fun isValid(email: CharSequence?): Boolean {
            return email != null && EMAIL_PATTERN.matcher(email).matches()
        }
    }
}
```

* Trước khi viết test, phải xác định xem có những trường hợp nào của việc test trên, ở đây là check xem chuỗi nhập vào có phải là định dạng email không, sẽ có một số các trường hợp sau:

    * Nhập vào đúng sẽ là: test@gmailcom
    * Email có dạng subdomain: test@gmail.co.uk
    * Không có .com: test@gmail
    * Có nhiều ký tự: test@gmail..com
    * Không có tên người dùng: @gmail.com
    * Không có trường nhập vào
    * Giá trị bị null

* Tạo file EmailValidatorTest trong thư mục app/test/java/..., đây là thư mục chứa các test local unit test.

```
class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectInput_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValid("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValid("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid("@email.com"))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid(null))
    }
}
```

* Một số chú ý có thể bạn chưa biết về các annotation trên:

    * **@Test**: Được cung cấp bởi JUnit Framework để đánh dấu 1 phương thức là một trường hợp test.
    * **assertTrue()**: Là một phương thức để khẳng định giá trị bên trong nó là TRUE. Nếu bên trong là sai thì đây là một test case sai.
    * **assertFalse()**: Cũng tương tự như assertTrue, nếu trường hợp sai thì test đúng và ngược lại.

## Mockito Test



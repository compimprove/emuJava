package viduoverriding;

public class Subclass extends Superclass {
 
    @Override
    public void hienThi() {
        System.out.println("Đây là phương thức hiển thị của lớp con Subclass.");
    }
}


public class TestOverriding {


    public static void main(String[] args) {
        // khai báo đối tượng của lớp Superclass
        Superclass superclass = new Superclass();
         
        // khai báo đối tượng có bản chất là Superclass
        // nhưng đóng vai trò là 1 Subclass
        // vì vậy sẽ chạy những hàm của Subclass
        Superclass subclass = new Subclass();
         
        // gọi phương thức hienThi() của lớp cha
        superclass.hienThi();
         
        // gọi phương thức hienThi() của lớp con
        subclass.hienThi();
    }
     
}
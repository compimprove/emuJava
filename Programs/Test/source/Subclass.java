package viduoverriding;

public class Subclass extends Superclass {
 
    @Override
    public void hienThi() {
        System.out.println("�y l� ph��ng th�c hi�n th� c�a l�p con Subclass.");
    }
}


public class TestOverriding {


    public static void main(String[] args) {
        

        Superclass superclass = new Superclass();
         
        

        

        

        Superclass subclass = new Subclass();
         
        

        superclass.hienThi();
         
        

        subclass.hienThi();
    }
    
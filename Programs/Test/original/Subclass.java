package viduoverriding;

public class Subclass extends Superclass {
 
    @Override
    public void hienThi() {
        System.out.println("�y l� ph��ng th�c hi�n th� c�a l�p con Subclass.");
    }
}


public class TestOverriding {


    public static void main(String[] args) {
        // khai b�o �i t��ng c�a l�p Superclass
        Superclass superclass = new Superclass();
         
        // khai b�o �i t��ng c� b�n ch�t l� Superclass
        // nh�ng �ng vai tr� l� 1 Subclass
        // v� v�y s� ch�y nh�ng h�m c�a Subclass
        Superclass subclass = new Subclass();
         
        // g�i ph��ng th�c hienThi() c�a l�p cha
        superclass.hienThi();
         
        // g�i ph��ng th�c hienThi() c�a l�p con
        subclass.hienThi();
    }
     
}

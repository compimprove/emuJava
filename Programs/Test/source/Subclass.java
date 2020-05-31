package viduoverriding;

public class Subclass extends Superclass {
 
    @Override
    public void hienThi() {
        System.out.println("ây là ph°¡ng théc hiÃn thË cça lÛp con Subclass.");
    }
}


public class TestOverriding {


    public static void main(String[] args) {
        

        Superclass superclass = new Superclass();
         
        

        

        

        Superclass subclass = new Subclass();
         
        

        superclass.hienThi();
         
        

        subclass.hienThi();
    }
    
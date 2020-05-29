package viduoverriding;

public class Subclass extends Superclass {
 
    @Override
    public void hienThi() {
        System.out.println("ây là ph°¡ng théc hiÃn thË cça lÛp con Subclass.");
    }
}


public class TestOverriding {


    public static void main(String[] args) {
        // khai báo Ñi t°ãng cça lÛp Superclass
        Superclass superclass = new Superclass();
         
        // khai báo Ñi t°ãng có b£n ch¥t là Superclass
        // nh°ng óng vai trò là 1 Subclass
        // vì v­y s½ ch¡y nhïng hàm cça Subclass
        Superclass subclass = new Subclass();
         
        // gÍi ph°¡ng théc hienThi() cça lÛp cha
        superclass.hienThi();
         
        // gÍi ph°¡ng théc hienThi() cça lÛp con
        subclass.hienThi();
    }
     
}

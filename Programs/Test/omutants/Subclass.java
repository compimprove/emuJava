
package viduoverriding ; public class Subclass extends Superclass { 

   
   public void hienThi() {
      System.out.println( "�y l� ph��ng th�c hi�n th� c�a l�p con Subclass." );
   }
   
   public static void main( String [ ] args ) {
      Superclass superclass = new Superclass();
      Superclass subclass = new Subclass();
      superclass.hienThi();
      subclass.hienThi();
   }
   

}
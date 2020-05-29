
package viduoverriding ; public class Subclass extends Superclass { 

   
   public void hienThi() {
      System.out.println( "ýy lý phýýng thýc hiýn thý cýa lýp con Subclass." );
   }
   
   public static void main( String [ ] args ) {
      Superclass superclass = new Superclass();
      Superclass subclass = new Subclass();
      superclass.hienThi();
      subclass.hienThi();
   }
   

}
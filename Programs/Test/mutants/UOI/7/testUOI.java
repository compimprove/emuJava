//ElectricHeater.turnOn()

public class ElectricHeater { 

   private String traceFile;
   private int temperature;
   private int status;
   
   public ElectricHeater() {
      temperature = 0;
      status = 0;
   }
   
   public ElectricHeater( String tf ) {
      traceFile = tf;
   }
   
   public int turnOn( int t ) {
      if( status==0 ) {
         if( t<17 ) {
            status = 1;
            temperature = temperature + --t  ;            	 // Mutant with UOI ;   
}
      
}
      return temperature;   
}
   

}
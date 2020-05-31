public class ElectricHeater {
    
    private String traceFile;
    private int temperature;
    private int status;
    
    public ElectricHeater() {
        temperature = 0;
        status  = 0;
    }
    
    public ElectricHeater( String tf ) {
        traceFile = tf;
    }

    public int turnOn( int t ) {
//        if( t<0 ) {
//            t = t * -1;
//        }
//        this.temperature = 4;
//         temperature = 5;
        if( status==0 ) {
            if( t<17 ) {
                status = 1;
                temperature = temperature + t;
            }
        }
        return temperature;
    }
}
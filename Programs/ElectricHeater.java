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
        if( t<0 ) {
            t = t * -1;
        }
        this.temperature = 4;
        temperature = 5;
        if( status==0 ) {
            if( t<17 ) {
                status = 1;
                temperature = temperature + t;
            }
        }
        return temperature;
    }
//
//    public void method1() {
//    }
//
//    public int turnOff() {
//        int off = 0;
//        if( status==1 ) {
//            status = 0;
//            temperature = 17;
//            off = 1;
//        }
//        return off;
//    }
//
//    public void method2() {
//    }
//
//    public int increaseHead( int t ) {
//        if( t<0 ) {
//            t = t * -1;
//        }
//        if( status==1 ) {
//            if( t<17 ) {
//                if( temperature<50 ) {
//                    temperature = temperature + t;
//                }
//            }
//        }
//        return temperature;
//    }
//
//    public void method31() {
//    }
//
//    public int reduceHeat( int t ) {
//        if( t<0 ) {
//            t = t * -1;
//        }
//        if( status==1 ) {
//            if( t<17 ) {
//                if( temperature>17 ) {
//                    temperature = temperature - t;
//                }
//            }
//        }
//        return temperature;
//    }
//
//    public void method4() {
//    }
//
//    public int roomTemperature() {
//        int temp = 0;
//        if( status==1 ) {
//            temp = temperature;
//        }
//        return temp;
//    }
//
//    public void method5() {
//    }
//
//    public void autoController( int current ) {
//        if( temperature>=current ) {
//            turnOff();
//        }
//        if( temperature<current ) {
//            turnOn( temperature );
//        }
//    }
//
//    public void method6() {
//    }
//
//    public int isWorking() {
//        int working = 0;
//        if( status==1 ) {
//            working = 1;
//        }
//        return working;
//    }
//
}
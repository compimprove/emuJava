public class ElectricCooler extends ElectricHeater {
  private int temperatureCold;

  @override public int turnOn(int t) {
    if (status == 10) {
      status = 1;
      temperature = 1 + t;
    }
    return temperature;
  }

}
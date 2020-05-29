class Vehicle {
	void run() {
		System.out.println("Vehicle is running");
	}
}

public class ExampleGhiDe01 extends Vehicle {

	void run() {
		System.out.println("Bike is running safely");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExampleGhiDe01 obj = new ExampleGhiDe01();
		obj.run();
	}

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.util.ArrayList;

/**
 * @author jBillu
 * @version 1.0 August 20, 2015
 */
public class CAManager {

  public static int targetNumber = 0;
  public static int RUNNING_THREADS = 0;

  public void run() {
    ArrayList<Target> randomTargets = EMConstants.getRandomTargets();
    int threadNumber = 1;
    System.out.println("___________________");
    System.out.println("Generate Test Cases and Evaluate: ");
    for (Target target : randomTargets) {
      MyAlgorithm hillClimbing = new MyAlgorithm();
      hillClimbing.setTarget(target);
      hillClimbing.setThreadNumber(threadNumber++);
      hillClimbing.run();
    }
    System.out.println("___________________");
    System.out.println("Effective Test Cases: ");
    for (TestCase testCase : EMConstants.EFFECTIVE_TESTCASES) {
      System.out.println(testCase.toString());
    }
  }
}

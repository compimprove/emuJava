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
    EMConstants.TIME_START = System.currentTimeMillis();
    ArrayList<Target> randomTargets = EMConstants.getRandomTargets();
    int threadNumber = 1;
    EMConstants.TOTAL_ITERATIONS
            = EMConstants.MAX_ITERATIONS * randomTargets.size();
//        EMConstants.PROGRESS_RATE = 45 / (double) EMConstants.TOTAL_ITERATIONS;
    while (targetNumber < randomTargets.size()) {
      Target target = randomTargets.get(targetNumber);
      MyAlgorithm climbingHill = new MyAlgorithm();
      climbingHill.setTarget(target);
      climbingHill.setThreadNumber(threadNumber++);
      climbingHill.run();
      CAManager.RUNNING_THREADS++;
    }

    EMConstants.TIME_END = System.currentTimeMillis();
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    } //END try-catch BLOCK
//        ProcessProgress.getProcessProgress().titleLabel.setText("Updating GUI to display mutants");
//        EMController.create().getProjectManager().displayMutants();
//        EMController.create().getProjectManager().updateStatisticsAndResults();
//        ProcessProgress.getProcessProgress().statusLabel.setText("EMuJava GUI updated...");
//        ProcessProgress.getProcessProgress().ppBar.setValue(100);
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    } //END try-catch BLOCK
//        ProcessProgress.getProcessProgress().setVisible(false);
  } //END run() METHOD
}

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
public class CAManager extends Thread {

    public static int targetNumber = 0;
    public static int RUNNING_THREADS = 0;

    public void run() {
        EMConstants.TIME_START = System.currentTimeMillis();
        ArrayList<Target> allTargets =
                EMConstants.getRandomTargets(5);
        int threadNumber = 1;
        EMConstants.TOTAL_ITERATIONS
                = EMConstants.MAX_ITERATIONS * allTargets.size();
//        EMConstants.PROGRESS_RATE = 45 / (double) EMConstants.TOTAL_ITERATIONS;
        while (targetNumber < allTargets.size()) {
            while (CAManager.RUNNING_THREADS < EMConstants.GA_MAX_THREADS
                    && targetNumber < allTargets.size()) {
                Target target = allTargets.get(targetNumber);
                MyAlgorithm climbingHill = new MyAlgorithm();
                climbingHill.setTarget(target);
                climbingHill.setThreadNumber(threadNumber++);
                climbingHill.start();
                CAManager.RUNNING_THREADS++;
            } //END while LOOP
        } //END for LOOP

        while (CAManager.RUNNING_THREADS > 0) {
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

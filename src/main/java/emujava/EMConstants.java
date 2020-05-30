/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.io.File;
import java.util.*;

/**
 * @author jBillu
 */
public class EMConstants {
  private static Random generator = new Random();

  public static String PROJECT_NAME = "";

  public static String PROJECT_LOCATION = "";

  public static String CLASS_1 = "";

  public static String CLASS_2 = "";

  public static ArrayList<String> MUTATION_OPERATORS = new ArrayList<String>();

  public static int POPULATION_SIZE = 0;

  public static int MUTATION_RATE = 0;

  public static int MAX_ITERATIONS = 0;

  public static int TOTAL_ITERATIONS = 0;

  public static int TOTAL_MUTANTS = 0;

  public static ArrayList<Target> ALL_TARGETS = new ArrayList<Target>();

  public static ArrayList<Target> ACHIEVED_TARGETS = new ArrayList<Target>();

  public static ArrayList<TestCase> EFFECTIVE_TESTCASES = new ArrayList<TestCase>();

  public static int ITERATIONS_PERFORMED = 0;

  public static long TIME_START = 0;

  public static long TIME_END = 0;

  public static int INT_RANGE = 50;

  public static int CHAR_RANGE = 6;

  public static String GEN_TYPE = "";

//  public static String CLASSPATH = "C:/jBillu/EMuJavaTests/PNC";

  public static int GA_TIMEOUT = 100000;

  public static int GA_MAX_THREADS = 1;

  public static double PROGRESS_RATE = 0;

  public static double CURRENT_PROGRESS = 50;

  public static String CLASSPATH = " ";

  public static double OLD_POPULATION_RATE = 0.2;

  public static int METHOD_CLASS_SEQUENCE_COUNT = 20;

  public static ArrayList<Target> getRandomTargets() {
    makeRandomTargets();
    return RANDOM_TARGETS;
  }

  public static void removeTargetsInRandomTargets(Collection<Target> targetCollection) {
    RANDOM_TARGETS.removeAll(targetCollection);
  }

  private static ArrayList<Target> RANDOM_TARGETS = new ArrayList<Target>();

  private static Map<String, ArrayList<Target>> getDividedTargets() {
    Map<String, ArrayList<Target>> dividedTargets
            = new HashMap<String, ArrayList<Target>>();
    for (String operator : MUTATION_OPERATORS) {
      dividedTargets.put(operator, new ArrayList<Target>());
    }
    for (Target target : ALL_TARGETS) {
      dividedTargets.get(target.getMutationOperator()).add(target);
    }
    return dividedTargets;
  }

  private static ArrayList<Target> getRandomTargets(int maxChoiceEachOperator) {
    Map<String, ArrayList<Target>> dividedTargets = getDividedTargets();
    ArrayList<Target> randomTargets = new ArrayList<Target>();
    Set<Map.Entry<String, ArrayList<Target>>> set = dividedTargets.entrySet();

    for (Map.Entry<String, ArrayList<Target>> operator : set) {
      if (operator.getValue().size() >= maxChoiceEachOperator) {
        int targetsSizeOfThisOperator = operator.getValue().size();
        Set<Integer> randomInteger = new HashSet<Integer>();

        for (int i = 0; randomInteger.size() < maxChoiceEachOperator; i++) {
          int randomIndex = generator.nextInt(targetsSizeOfThisOperator);
          randomInteger.add(randomIndex);
        }

        for (int i : randomInteger) {
          randomTargets.add(operator.getValue().get(i));
        }

      } else if (!operator.getValue().isEmpty()) {
        randomTargets.addAll(operator.getValue());
      }
    }
    return randomTargets;
  }

  private static void makeRandomTargets() {
    if (RANDOM_TARGETS.isEmpty()) {
      EMConstants.RANDOM_TARGETS = getRandomTargets(2);
      //deleteRemainingTargets();
    }
  }

  private static void deleteRemainingTargets() {
    deleteInInstrumentDir();
    deleteInOInstrumentDir();
  }

  private static void deleteInInstrumentDir() {
    File instrumentDir = new File(EMConstants.PROJECT_LOCATION
            + EMConstants.PROJECT_NAME + "/instrument");
  }

  private static void deleteInOInstrumentDir() {
    File oInstrumentDir = new File(EMConstants.PROJECT_LOCATION
            + EMConstants.PROJECT_NAME + "/oinstrument");
    for (Target target : RANDOM_TARGETS) {

    }
  }

  public void print() {
    System.out.println("PROJECT_NAME : " + EMConstants.PROJECT_NAME);
    System.out.println("PROJECT_LOCATION : " + EMConstants.PROJECT_LOCATION);
    System.out.println("CLASS_1 : " + EMConstants.CLASS_1);
    System.out.println("CLASS_2 : " + EMConstants.CLASS_2);
    System.out.println("POPULATION_SIZE : " + EMConstants.POPULATION_SIZE);
    System.out.println("MUTATION_RATE : " + EMConstants.MUTATION_RATE);
    System.out.println("MAX_ITERATIONS : " + EMConstants.MAX_ITERATIONS);
    System.out.println("TOTAL_ITERATIONS : " + EMConstants.TOTAL_ITERATIONS);
    System.out.println("ITERATIONS_PERFORMED : " + EMConstants.ITERATIONS_PERFORMED);
    System.out.println("TIME_START : " + EMConstants.TIME_START);
    System.out.println("TIME_END : " + EMConstants.TIME_END);
    System.out.println("INT_RANGE : " + EMConstants.INT_RANGE);
    System.out.println("CHAR_RANGE : " + EMConstants.CHAR_RANGE);
    System.out.println("GEN_TYPE : " + EMConstants.GEN_TYPE);
    System.out.println("GA_TIMEOUT : " + EMConstants.GA_TIMEOUT);
    System.out.println("GA_MAX_THREADS : " + EMConstants.GA_MAX_THREADS);
    System.out.println("PROGRESS_RATE : " + EMConstants.PROGRESS_RATE);
    System.out.println("CURRENT_PROGRESS : " + EMConstants.CURRENT_PROGRESS);
    System.out.println("OLD_POPULATION_RATE : " + EMConstants.OLD_POPULATION_RATE);
    System.out.println("METHOD_CLASS_SEQUENCE_COUNT : "
            + EMConstants.METHOD_CLASS_SEQUENCE_COUNT);
  }
} //END EMConstants CLASS

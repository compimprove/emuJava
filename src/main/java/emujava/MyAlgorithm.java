/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * @author jBillu
 * @version 1.0 October 20, 2013
 */

public class MyAlgorithm {

  private ArrayList<TestCase> selectedPopulation;

  public static int TS_K = 5;     //This 'K' is for Tournament Selection

  public Target target;

  public int threadNumber;

  private final ArrayList<TestCase> population;

  private int traceCount, iterationNumber, traceNumber;

  public MyAlgorithm() {
    population = new ArrayList<>();
    selectedPopulation = new ArrayList<>();
    traceCount = 1;
    iterationNumber = 0;
  }

  public void setTarget(Target _t) {
    target = _t;
  }

  public void setThreadNumber(int _tn) {
    threadNumber = _tn;
  }

  public void run() {
    this.executeCA();
  }

  public void executeCA() {
    this.generateTestCaseFor(target);
    System.out.println(this.population.get(0).toString());
//    for (int i = 1; i <= EMConstants.MAX_ITERATIONS; i++) {
//      this.generateTestCaseFor(target);
//      this.executeTestCases(target);
//      this.evaluateTestCases(target);
//      if (target.getAchieved()) {
//        EMConstants.ACHIEVED_TARGETS.add(target);
//        EMConstants.EFFECTIVE_TESTCASES.add(target.getTestCase());
//        break;
//      }
//    }
  }


  public void generateTestCaseFor(Target target) {

    String methodUnderTest = this.retrieveMethodUnderTest(
            EMConstants.PROJECT_LOCATION +
                    EMConstants.PROJECT_NAME +
                    "/instrument/" +
                    target.getMutationOperator() +
                    "/" +
                    target.getMutantNumber()
    );
    ClassComponents cComponents = null;
    ClassComponents c2Components = null;
    try {
      String targetPath = EMConstants.PROJECT_LOCATION
              + EMConstants.PROJECT_NAME
              + "/instrument/"
              + target.getMutationOperator()
              + "/" + target.getMutantNumber();
      File file = new File(targetPath);
      String operator = target.getMutationOperator();
      if (operator.equals("ABS")
              || operator.equals("AOR")
              || operator.equals("LCR")
              || operator.equals("ROR")
              || operator.equals("UOI")
              || operator.equals("OMD")
              || operator.equals("JID")
              || operator.equals("ECC")) {
        cComponents = EMController.create().getC1Components();
      } else {
        cComponents = EMController.create().getC1Components();
        c2Components = EMController.create().getC2Components();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


    for (int p = this.population.size(); p < EMConstants.POPULATION_SIZE; p++) {
      TestCase testCase = new TestCase();
      StringBuilder solution = new StringBuilder();
      String className = cComponents.getClassName();
      String objectName = className + "1";
      testCase.setClassName(className);
      testCase.setObjectName(objectName);
      testCase.setMethod(methodUnderTest);
      if (c2Components != null) {
        String class2Name = c2Components.getClassName();
        objectName = class2Name + "1";
        testCase.setClassName2(class2Name);
        testCase.setObjectName(objectName);
        solution.append(class2Name)
                .append(" ")
                .append(objectName)
                .append(" = new ")
                .append(class2Name)
                .append("(); ");
      } else {
        solution.append(className)
                .append(" ")
                .append(objectName)
                .append(" = new ")
                .append(className)
                .append("(); ");
      }

      int methodCallSeqCount =
              (int) (Math.random() * EMConstants.METHOD_CLASS_SEQUENCE_COUNT);
      if (methodCallSeqCount <= 1) {
        methodCallSeqCount += 2;
      }
      for (int m = 1; m <= methodCallSeqCount; m++) {
        int methodNumber = (int) (Math.random() * cComponents.getMMList().size());
        MemberMethod mMethod =
                (MemberMethod) cComponents.getMMList().get(methodNumber);
        if (!mMethod.getMethodName().equals(methodUnderTest)) {
          solution.append(objectName)
                  .append(".")
                  .append(mMethod.getMethodName())
                  .append("(");
          ArrayList<Token> mTokens = mMethod.getMethodTokens();
          int mt = 0;
          Token token = null;
          do {
            token = (Token) mTokens.get(mt++);
          } while (!token.getToken().equals("("));
          token = (Token) mTokens.get(mt++);
          while (!token.getToken().equals(")")) {
            switch (token.getToken()) {
              case "byte":
              case "short": {
                int value = (int) (Math.random() * EMConstants.INT_RANGE);
                solution.append(value);
                break;
              }
              case "int":
              case "long": {
                int value = (int) (Math.random() * EMConstants.INT_RANGE);
                int signRandom = (int) (Math.random() * 100);
                if (signRandom % 5 == 0) {
                  value = value * -1;
                }
                solution.append(value);
                break;
              }
              case "float": {
                int value1 = (int) (Math.random() * EMConstants.INT_RANGE);
                int value2 = (int) (Math.random() * 10);
                solution.append(value1).append(".").append(value2).append("f");
                break;
              }
              case "double": {
                int value1 = (int) (Math.random() * EMConstants.INT_RANGE);
                int value2 = (int) (Math.random() * 10);
                int signRandom = (int) (Math.random() * 100);
                if (signRandom % 5 == 0) {
                  value1 = value1 * -1;
                } //END if STATEMENT
                solution.append(value1).append(".").append(value2);
                break;
              }
              case "char": {
                int value = (int) (Math.random() * 26);
                int tvalue = (int) (Math.random() * 2);
                if (tvalue == 0) {
                  value += 65;
                } else {
                  value += 97;
                } //END if-else STATEMENT
                char ch = (char) value;
                solution.append("\'").append(ch).append("\'");
                break;
              }
              case "String": {
                int len = (int) (Math.random() * 10);
                StringBuilder value = new StringBuilder("\"");
                for (int l = 0; l < len; l++) {
                  int ivalue = (int) (Math.random() * 26);
                  int tvalue = (int) (Math.random() * 3);
                  if (tvalue == 0) {
                    ivalue += 65;
                  } else if (tvalue == 1) {
                    ivalue += 97;
                  } else {
                    ivalue += 48;
                  }
                  char ch = (char) ivalue;
                  value.append(ch);
                }
                value.append("\"");
                solution.append(value);
                break;
              }
            }
            mt++;
            token = (Token) mTokens.get(mt++);
            if (token.getToken().equals(",")) {
              solution.append(",");
              token = (Token) mTokens.get(mt++);
            }
          }
          solution.append("); ");
        }
      }

      if (c2Components != null) {
        methodCallSeqCount = (int) (Math.random() * (EMConstants.METHOD_CLASS_SEQUENCE_COUNT / 2));
        if (methodCallSeqCount <= 1) {
          methodCallSeqCount += 2;
        }
        for (int m = 1; m <= methodCallSeqCount; m++) {
          int methodNumber = (int) (Math.random() * c2Components.getMMList().size());
          MemberMethod mMethod = (MemberMethod) c2Components.getMMList().get(methodNumber);
          solution.append(objectName)
                  .append(".")
                  .append(mMethod.getMethodName()).append("(");
          ArrayList<Token> mTokens = mMethod.getMethodTokens();
          int mt = 0;
          Token token = null;
          do {
            token = (Token) mTokens.get(mt++);
          } while (!token.getToken().equals("("));
          token = (Token) mTokens.get(mt++);
          while (!token.getToken().equals(")")) {
            switch (token.getToken()) {
              case "byte":
              case "short": {
                int value = (int) (Math.random() * EMConstants.INT_RANGE);
                solution.append(value);
                break;
              }
              case "int":
              case "long": {
                int value = (int) (Math.random() * EMConstants.INT_RANGE);
                int signRandom = (int) (Math.random() * 100);
                if (signRandom % 5 == 0) {
                  value = value * -1;
                }
                solution.append(value);
                break;
              }
              case "float": {
                int value1 = (int) (Math.random() * EMConstants.INT_RANGE);
                int value2 = (int) (Math.random() * 10);
                solution.append(value1).append(".").append(value2).append("f");
                break;
              }
              case "double": {
                int value1 = (int) (Math.random() * EMConstants.INT_RANGE);
                int value2 = (int) (Math.random() * 10);
                int signRandom = (int) (Math.random() * 100);
                if (signRandom % 5 == 0) {
                  value1 = value1 * -1;
                }
                solution.append(value1).append(".").append(value2);
                break;
              }
              case "char": {
                int value = (int) (Math.random() * 26);
                int tvalue = (int) (Math.random() * 2);
                if (tvalue == 0) {
                  value += 65;
                } else {
                  value += 97;
                }
                char ch = (char) value;
                solution.append("\'").append(ch).append("\'");
                break;
              }
              case "String": {
                int len = (int) (Math.random() * 10);
                StringBuilder value = new StringBuilder("\"");
                for (int l = 0; l < len; l++) {
                  int ivalue = (int) (Math.random() * 26);
                  int tvalue = (int) (Math.random() * 3);
                  if (tvalue == 0) {
                    ivalue += 65;
                  } else if (tvalue == 1) {
                    ivalue += 97;
                  } else {
                    ivalue += 48;
                  }
                  char ch = (char) ivalue;
                  value.append(ch);
                }
                value.append("\"");
                solution.append(value);
                break;
              }
            }
            mt++;
            token = (Token) mTokens.get(mt++);
            if (token.getToken().equals(",")) {
              solution.append(",");
              token = (Token) mTokens.get(mt++);
            }
          }
          solution.append("); ");
        }
      }

      ArrayList<MemberMethod> mmList = cComponents.getMMList();
      for (MemberMethod mMethod : mmList) {
        if (mMethod.getMethodName().equals(methodUnderTest)) {
          solution.append(objectName)
                  .append(".")
                  .append(mMethod.getMethodName()).append("(");
          ArrayList<Token> mTokens = mMethod.getMethodTokens();
          int mt = 0;
          Token token = null;
          do {
            token = (Token) mTokens.get(mt++);
          } while (!token.getToken().equals("("));
          token = (Token) mTokens.get(mt++);
          while (!token.getToken().equals(")")) {
            switch (token.getToken()) {
              case "byte":
              case "short": {
                int value = (int) (Math.random() * EMConstants.INT_RANGE);
                solution.append(value);
                break;
              }
              case "int":
              case "long": {
                int value = (int) (Math.random() * EMConstants.INT_RANGE);
                int signRandom = (int) (Math.random() * 100);
                if (signRandom % 5 == 0) {
                  value = value * -1;
                }
                solution.append(value);
                break;
              }
              case "float": {
                int value1 = (int) (Math.random() * EMConstants.INT_RANGE);
                int value2 = (int) (Math.random() * 10);
                solution.append(value1).append(".").append(value2).append("f");
                break;
              }
              case "double": {
                int value1 = (int) (Math.random() * EMConstants.INT_RANGE);
                int value2 = (int) (Math.random() * 10);
                int signRandom = (int) (Math.random() * 100);
                if (signRandom % 5 == 0) {
                  value1 = value1 * -1;
                }
                solution.append(value1).append(".").append(value2);
                break;
              }
              case "char": {
                int value = (int) (Math.random() * 26);
                int tvalue = (int) (Math.random() * 2);
                if (tvalue == 0) {
                  value += 65;
                } else {
                  value += 97;
                }
                char ch = (char) value;
                solution.append("\'").append(ch).append("\'");
                break;
              }
              case "String": {
                int len = (int) (Math.random() * 10);
                String value = "\"";
                for (int l = 0; l < len; l++) {
                  int ivalue = (int) (Math.random() * 26);
                  int tvalue = (int) (Math.random() * 3);
                  if (tvalue == 0) {
                    ivalue += 65;
                  } else if (tvalue == 1) {
                    ivalue += 97;
                  } else {
                    ivalue += 48;
                  }
                  char ch = (char) ivalue;
                  value += ch;
                }
                value += "\"";
                solution.append(value);
                break;
              }
            }
            mt++;
            token = (Token) mTokens.get(mt++);
            if (token.getToken().equals(",")) {
              solution.append(",");
              token = (Token) mTokens.get(mt++);
            }
          }
          solution.append(");");
          break;
        }
      }
      testCase.setTestCase(solution.toString());
      this.population.add(testCase);
    }
  }

  public void executeTestCases(Target target) {
    try {
      String targetPath1 = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/instrument/" + target.getMutationOperator() + "/" + target.getMutantNumber();
      String targetPath2 = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/oinstrument/" + target.getMutationOperator() + "/" + target.getMutantNumber();
      for (int t = 0; t < this.population.size(); t++) {
        TestCase testCase = (TestCase) this.population.get(t);
        String driverString = "import java.io.*;\n";
        driverString += "public class Driver" + t + " {\n";
        driverString += "public static void main( String[] args )throws Exception {\n";
        String[] testCaseStrings = this.breakTestCase(testCase.toString());
        driverString += testCaseStrings[0] + " args[0] " + testCaseStrings[1] + "\n";
        driverString += "}\n";
        driverString += "}\n";
        File driverI = new File(targetPath1 + "/Driver" + t + ".java");
        RandomAccessFile rafi = new RandomAccessFile(driverI, "rw");
        rafi.writeBytes(driverString);
        rafi.close();
        File driverO = new File(targetPath2 + "/Driver" + t + ".java");
        RandomAccessFile rafo = new RandomAccessFile(driverO, "rw");
        rafo.writeBytes(driverString);
        rafo.close();
      } //END for LOOP

      Runtime.getRuntime().exec("javac -cp " + targetPath1 + " " + targetPath1 + "/*.java");
      File d1Check = new File(targetPath1 + "/Driver0.class");
      int timeout = 1;
      while (!d1Check.exists() && timeout <= EMConstants.GA_TIMEOUT) {
        timeout++;
      } //END of while LOOP

      Runtime.getRuntime().exec("javac -cp " + targetPath2 + " " + targetPath2 + "/*.java");
      File d2Check = new File(targetPath2 + "/Driver0.class");
      timeout = 1;
      while (!d2Check.exists() && timeout <= EMConstants.GA_TIMEOUT) {
        timeout++;
      } //END of while LOOP

      for (int t = 0; t < this.population.size(); ) {
        for (int a = 1; a <= 4 && t < this.population.size(); a++, t++) {
          String ITraceFile = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + this.traceCount + "Itrace.txt";
          Runtime.getRuntime().exec("java -cp " + targetPath1 + " Driver" + t + " " + ITraceFile);

          String OTraceFile = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + this.traceCount + "Otrace.txt";
          Runtime.getRuntime().exec("java -cp " + targetPath2 + " Driver" + t + " " + OTraceFile);

          this.traceCount++;
        } //END for LOOP
      } //END for LOOP

      String TempITraceFile = EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + this.traceCount + "Itrace.txt";
      File filei = new File(TempITraceFile);
      timeout = 1;
      while (!filei.exists() && timeout <= EMConstants.GA_TIMEOUT) {
        timeout++;
      } //END while LOOP

      for (int t = 0; t < this.population.size(); t++) {
        File driverI = new File(targetPath1 + "/Driver" + t + ".java");
        File driverIC = new File(targetPath1 + "/Driver" + t + ".class");
        driverI.delete();
        driverIC.delete();
        File driverO = new File(targetPath2 + "/Driver" + t + ".java");
        File driverOC = new File(targetPath2 + "/Driver" + t + ".class");
        driverO.delete();
        driverOC.delete();
      } //END for LOOP
    } catch (Exception e) {
      e.printStackTrace();
    } //END try-catch BLOCK

//    int testCasesGen = Integer.parseInt(TestCaseAnalysis.jLabel11.getText().trim()) + this.population.size();
//    TestCaseAnalysis.jLabel11.setText("" + testCasesGen);
  }

  public String[] breakTestCase(String tc) {
    String[] tcs = new String[2];
    String temp = "";
    for (int t = 0; t < tc.length(); t++) {
      char ch = tc.charAt(t);
      if (ch != '(') {
        temp += ch;
      } else {
        temp += ch;
        tcs[0] = temp;
        tcs[1] = tc.substring(t + 1);
        break;
      } //END if-else STATEMENT
    } //END for LOOP
    return tcs;
  }

  public void evaluateTestCases(Target target) {
    for (TestCase testCase : this.population) {
      testCase.setWeight(0.0);
    } //END for LOOP

    try {
      FOR:
      for (int t = 0; t < this.population.size(); t++) {
        TestCase testCase = this.population.get(t);

        File fileI = new File(EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + (this.traceNumber + t + 1) + "Itrace.txt");
        File fileO = new File(EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/" + this.threadNumber + "-" + (this.traceNumber + t + 1) + "Otrace.txt");

        if (fileI.exists() && fileO.exists()) {
          LineNumberReader lnrI = new LineNumberReader(new FileReader(fileI));
          LineNumberReader lnrO = new LineNumberReader(new FileReader(fileO));
          String lineI = lnrI.readLine();
          String lineO = lnrO.readLine();
          if (lineI != null) {
            do {
              if (lineI.startsWith("R: ")) {
                break;
              } //END if STATEMENT
              lineI = lnrI.readLine();
            } while (lineI != null && !lineI.startsWith("R:"));
            if (lineI == null) {
//                            EMuJava.jTextArea2.append("Test Case# " + (t + 1) + "\n");
//                            EMuJava.jTextArea2.append(((TestCase) this.population.get(t)).toString());
//                            EMuJava.jTextArea2.append("\nFITNESS: Cannot be Calculated!");
              testCase.setWeight(-10.0);
//                            EMuJava.jTextArea2.append("\nWEIGHT: -10.0\n\n");
            } else if (lineI.equals("R: 0.0 0 0.0")) {
//                            EMuJava.jTextArea2.append("Test Case# " + (t + 1) + "\n");
//                            EMuJava.jTextArea2.append(((TestCase) this.population.get(t)).toString());
              testCase.setStateFitness(0.0);
              testCase.setApproachLevel(0);
              testCase.setLocalFitness(0.0);
              testCase.setWeight(4.0);
              lineI = lnrI.readLine();
              do {
                if (lineI != null && lineI.startsWith("N: ")) {
                  break;
                } //END if STATEMENT
                lineI = lnrI.readLine();
              } while (lineI != null && !lineI.startsWith("N: "));
              do {
                if (lineO != null && lineO.startsWith("N: ")) {
                  break;
                } //END if STATEMENT
                lineO = lnrO.readLine();
              } while (lineO != null && !lineO.startsWith("N: "));
              if (lineI != null && lineO != null) {
                boolean isSufficient = false;
                StringTokenizer tokenI = new StringTokenizer(lineI, " ");
                StringTokenizer tokenO = new StringTokenizer(lineO, " ");
                tokenI.nextToken();
                tokenO.nextToken();
                while (tokenI.hasMoreTokens()) {
                  String token1 = tokenI.nextToken();
                  String token2 = tokenO.nextToken();
                  if (!token1.equals(token2)) {
                    testCase.setNecessityCost("0.0");
                    testCase.setWeight(testCase.getWeight() + 1.0);
                    isSufficient = true;
                    break;
                  } //END if STATEMENT
                } //END while LOOP
                if (isSufficient) {
                  lineI = lnrI.readLine();
                  do {
                    if (lineI != null && lineI.startsWith("S: ")) {
                      break;
                    } //END if STATEMENT
                    lineI = lnrI.readLine();
                  } while (lineI != null && !lineI.startsWith("S: "));
                  ArrayList<String> listI = new ArrayList<String>();
                  while (lineI != null) {
                    if (!lineI.equals("")) {
                      listI.add(lineI);
                    } //END if STATEMENT
                    lineI = lnrI.readLine();
                  } //END while LOOP
                  lineO = lnrO.readLine();
                  DO1:
                  do {
                    if (lineO != null && lineO.startsWith("S: ")) {
                      break DO1;
                    } //END if STATEMENT
                    lineO = lnrO.readLine();
                  } while (lineO != null && !lineO.startsWith("S: "));
                  ArrayList listO = new ArrayList();
                  while (lineO != null) {
                    if (!lineO.equals("")) {
                      listO.add(lineO);
                    } //END if STATEMENT
                    lineO = lnrO.readLine();
                  } //END while LOOP
                  if (listI.size() > 0 && listO.size() > 0) {
                    testCase.setFitness(true);
                    String suffI = (String) listI.get(listI.size() - 1);
                    String suffO = (String) listO.get(listO.size() - 1);
                    if (suffI.equals(suffO)) {
                      if (listI.size() == listO.size()) {
                        boolean isAlive = true;
                        int suff_cost = 0;
                        FORA:
                        for (int x = 0; x < listI.size(); x++) {
                          String pathI = (String) listI.get(x);
                          String pathO = (String) listO.get(x);
                          if (pathI.equals(pathO)) {
                            suff_cost++;
                          } else {
                            isAlive = false;
                          }//END if STATEMENT
                        } //END for LOOP
                        testCase.setSufficiencyCost(suff_cost + "");
                        if (isAlive) {
                          testCase.setStatus("Normal");
                        } else {
                          testCase.setStatus("Suspicious");
                          testCase.setWeight(testCase.getWeight() + 1.0);
                          target.setSuspicious(true);
                        } //END if-else STATEMENT
                      } else {
                        int suff_cost = 0;
                        if (listI.size() < listO.size()) {
                          FORA:
                          for (int x = 0; x < listI.size(); x++) {
                            String pathI = (String) listI.get(x);
                            String pathO = (String) listO.get(x);
                            if (pathI.equals(pathO)) {
                              suff_cost++;
                            } //END if STATEMENT
                          } //END for LOOP
                        } else {
                          FORA:
                          for (int x = 0; x < listO.size(); x++) {
                            String pathI = (String) listI.get(x);
                            String pathO = (String) listO.get(x);
                            if (pathI.equals(pathO)) {
                              suff_cost++;
                            } //END if STATEMENT
                          } //END for LOOP
                        } //END if-else STATEMENT
                        testCase.setSufficiencyCost(suff_cost + "");
                        testCase.setStatus("Suspicious");
                        testCase.setWeight(testCase.getWeight() + 1.0);
                        target.setSuspicious(true);
                      } //END if-else STATEMENT
                      testCase.setWeight(testCase.getWeight()
                              - (testCase.getApproachLevel()
                              + testCase.getLocalFitness()
                              + testCase.getStateFitness())
                      );
//                      EMuJava.jTextArea2.append("\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; " + testCase.getNecessityCost() + "; (" + testCase.getSufficiencyCost() + ", " + testCase.getStatus() + ")]");
//                      EMuJava.jTextArea2.append("\nWEIGHT: " + testCase.getWeight() + "\n\n");
                    } else {
                      testCase.setSufficiencyCost("0");
                      testCase.setWeight(testCase.getWeight() + 1.0);
                      testCase.setStatus("Normal");
                      target.setTestCase(testCase);
                      target.setAchieved(true);
                      target.setStatus("Killed");
                      EMConstants.ACHIEVED_TARGETS.add(target);
                      EMConstants.ALL_TARGETS.remove(target);
                      EMConstants.EFFECTIVE_TESTCASES.add(testCase);
                      testCase.setWeight(testCase.getWeight()
                              - (testCase.getApproachLevel()
                              + testCase.getLocalFitness()
                              + testCase.getStateFitness())
                      );
//                      EMuJava.jTextArea3.append("TARGET: " + target.getMutationOperator() + "-" + target.getMutantNumber() + ",\tITERATION: " + (EMConstants.ITERATIONS_PERFORMED + 1) + "\n");
//                      EMuJava.jTextArea3.append(((TestCase) this.population.get(t)).toString() + "\n\n");
//                      EMuJava.jTextArea2.append("\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; " + testCase.getNecessityCost() + "; (" + testCase.getSufficiencyCost() + ", " + testCase.getStatus() + ")]");
//                      EMuJava.jTextArea2.append("\nWEIGHT: " + testCase.getWeight() + "\n\n");
//                      ProcessProgress.getProcessProgress().statusLabel.setText("Target has achieved!!!");
                      break FOR;
                    } //END if-else STATEMENT
                  } else {
//                    EMuJava.jTextArea2.append("\nFITNESS: Cannot be Calculated!");
                    testCase.setWeight(-10.0);
//                    EMuJava.jTextArea2.append("\nWEIGHT: -10.0\n\n");
                  } //END if-else STATEMENT
                } else {
                  testCase.setFitness(true);
                  lineI = lineI.substring(3);
                  lineO = lineO.substring(3);
                  if (lineI.equals("true") || lineI.equals("false")) {
                    testCase.setNecessityCost("0.5");
                  } else {
                    double weightI = 0.0;
                    double weightO = 0.0;
                    for (int i = 0; i < lineI.length(); i++) {
                      weightI += lineI.charAt(i);
                    } //END for LOOP
                    for (int o = 0; o < lineO.length(); o++) {
                      weightO += lineO.charAt(o);
                    } //END for LOOP
                    double necessity_cost = (weightI - weightO) / (weightI + weightO);
                    necessity_cost = Math.abs(necessity_cost);
                    String nc = necessity_cost + "";
                    if (nc.length() > 4) {
                      testCase.setNecessityCost(nc.substring(0, 4));
                    } else {
                      testCase.setNecessityCost(nc);
                    } //END if-else STATEMENT
                  } //END if-else STATEMENT
                  testCase.setSufficiencyCost("c");
                  testCase.setStatus("Normal");
                  testCase.setWeight(testCase.getWeight() - (testCase.getApproachLevel() + testCase.getLocalFitness() + testCase.getStateFitness()));
//                  EMuJava.jTextArea2.append("\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; " + testCase.getNecessityCost() + "; (c, Normal)]");
//                  EMuJava.jTextArea2.append("\nWEIGHT: " + testCase.getWeight() + "\n\n");
                } //END if-else STATEMENT
              } else {
                testCase.setWeight(-10.0);
//                EMuJava.jTextArea2.append("\nFITNESS: Cannot be Calculated!");
//                EMuJava.jTextArea2.append("\nWEIGHT: -10.0\n\n");
              } //END if-else STATEMENT
            } else {
              testCase.setFitness(true);
              EMuJava.jTextArea2.append("Test Case# " + (t + 1) + "\n");
              EMuJava.jTextArea2.append(((TestCase) this.population.get(t)).toString());
              StringTokenizer tokenizer = new StringTokenizer(lineI, " ");
              tokenizer.nextToken();
              String token = tokenizer.nextToken();
              testCase.setStateFitness(Double.parseDouble(token));
              if (token.equals("0.0")) {
                testCase.setWeight(testCase.getWeight() + 2.0);
              } //END if STATEMENT
              token = tokenizer.nextToken();
              testCase.setApproachLevel(Integer.parseInt(token));
              if (token.equals("0")) {
                testCase.setWeight(testCase.getWeight() + 1.0);
              } //END if STATEMENT
              token = tokenizer.nextToken();
              testCase.setLocalFitness(Double.parseDouble(token));
              if (token.equals("0.0")) {
                testCase.setWeight(testCase.getWeight() + 1.0);
              } //END if STATEMENT
              testCase.setNecessityCost("c");
              testCase.setSufficiencyCost("c");
              testCase.setStatus("Normal");
              testCase.setWeight(testCase.getWeight()
                      - (testCase.getApproachLevel()
                      + testCase.getLocalFitness()
                      + testCase.getStateFitness())
              );
//              EMuJava.jTextArea2.append("\nFITNESS: [(" + testCase.getStateFitness() + ", [" + testCase.getApproachLevel() + ", " + testCase.getLocalFitness() + "]) ; c ; (c, Normal)]");
//              EMuJava.jTextArea2.append("\nWEIGHT: " + testCase.getWeight() + "\n\n");
            } //END if-else STATEMENT
          } else {
//            EMuJava.jTextArea2.append("Test Case# " + (t + 1) + "\n");
//            EMuJava.jTextArea2.append(((TestCase) this.population.get(t)).toString());
//            EMuJava.jTextArea2.append("\nFITNESS: Cannot be Calculated!");
            testCase.setWeight(-10.0);
//            EMuJava.jTextArea2.append("\nWEIGHT: -10.0\n\n");
          } //END if-else LOOP
        } else {
//          EMuJava.jTextArea2.append("Test Case# " + (t + 1) + "\n");
//          EMuJava.jTextArea2.append(((TestCase) this.population.get(t)).toString());
//          EMuJava.jTextArea2.append("\nFITNESS: Cannot be Calculated!");
          testCase.setWeight(-10.0);
//          EMuJava.jTextArea2.append("\nWEIGHT: -10.0\n\n");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } //END try-catch BLOCK
    this.traceNumber += this.population.size();

    try {
      File traceDir = new File(EMConstants.PROJECT_LOCATION + EMConstants.PROJECT_NAME + "/traces/");
      File[] traceFiles = traceDir.listFiles();
      for (File traceFile : Objects.requireNonNull(traceFiles)) {
        if (traceFile.exists()) {
          traceFile.delete();
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public String retrieveMethodUnderTest(String target) {
    String line = "";
    try {
      LineNumberReader lnr = null;
      if (target.contains("/IOP/") || target.contains("/PNC/")) {
        lnr = new LineNumberReader(new FileReader(target + "/" + EMConstants.CLASS_2));
      } else {
        lnr = new LineNumberReader(new FileReader(target + "/" + EMConstants.CLASS_1));
      } //END if-else STATEMENT
      line = lnr.readLine();
      lnr.close();
    } catch (Exception e) {
      e.printStackTrace();
    } //END try-catch BLOCK
    String methodName = "";
    int index = 0;
    char ch = ' ';
    do {
      ch = line.charAt(index++);
    } while (ch != '.');
    do {
      ch = line.charAt(index++);
      if (ch != '(') {
        methodName += ch;
      } //END if STATEMENT
    } while (ch != '(');
    return methodName;
  }
}

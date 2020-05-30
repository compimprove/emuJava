import emujava.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class Main {

  public static final String PROJECT_NAME = "Test";
  public static final String PROJECT_LOCATION =
          "D:\\compi\\sources\\Java\\emuJava\\Location\\";
  public static final String CLASS1_NAME = "ElectricHeater.java";
  public static final String ClASS1_SOURCE =
          "D:\\compi\\sources\\Java\\emuJava\\Programs\\" + CLASS1_NAME;
  public static final String CLASS2_NAME = "ElectricHeater.java";
  public static final String ClASS2_SOURCE =
          "D:\\compi\\sources\\Java\\emuJava\\Programs\\" + CLASS2_NAME;
  public static final ArrayList<String> mutationOperators = new ArrayList<String>(
          Arrays.asList("ABS", "AOR", "LCR", "ROR", "UOI",
                  "IOP", "PNC", "OMD", "JID", "EOC")
  );
  public static final CAManager CA_MANAGER = new CAManager();

  public static void main(String[] args) throws Exception {
    createProject();
    ClassComponents class1Components = scanSourceCode(ClASS1_SOURCE);
    ClassComponents class2Components = scanSourceCode(ClASS2_SOURCE);
    generateMutants(class1Components, class2Components, 1);
    //generateTestCase();
  }

  private static ClassComponents scanSourceCode(String class_source) {
    EMScanner scan = new EMScanner(
            new File(class_source)
    );
    ArrayList<Token> class1Tokens = scan.getTokenList();
    ClassComponents c1Components = new ClassComponents();
    ClassHeader c1Header = new ClassHeader();
    ArrayList<Token> import1List = c1Header.extractImports(class1Tokens);
    ArrayList<Token> class1Header = c1Header.extractHeader(class1Tokens);
    String class1Name = c1Header.getClassName(class1Header);
    String class1Parent = c1Header.getClassParent(class1Header);

    c1Components.setImportsList(import1List);
    c1Components.setClassHeader(class1Header);
    c1Components.setClassName(class1Name);
    c1Components.setClassParent(class1Parent);
    c1Components.extractClassComponents(class1Tokens, c1Header.getIndex() + 1);

    return c1Components;
  }

  private static void generateMutants(ClassComponents class1Components,
                                      ClassComponents class2Components,
                                      int numberOfClass) {
    MySourceCode sourceCode = new MySourceCode();
    EMProjectManager projectManager = new EMProjectManager();
    EMController emController = EMController.create();
    emController.setC1Components(class1Components);
    EMConstants.PROJECT_LOCATION = PROJECT_LOCATION;
    EMConstants.PROJECT_NAME = PROJECT_NAME;
    EMConstants.CLASS_1 = CLASS1_NAME;
    emController.setC2Components(class2Components);
    EMConstants.MUTATION_OPERATORS.addAll(mutationOperators);
    for (MemberMethod memberMethod : class1Components.getMMList()) {
      memberMethod.identifyStatements();
    }

    if (numberOfClass == 2) {
      EMConstants.CLASS_2 = CLASS2_NAME;
      EMConstants.GEN_TYPE = "eMuJava";
      for (MemberMethod memberMethod : class2Components.getMMList()) {
        memberMethod.identifyStatements();
      }
      sourceCode.generateMutants(2);
    } else {
      sourceCode.generateMutants(1);
    }
    sourceCode.instrumentAndTransformCode();
    System.out.println("-------------------");
    compileProject();
    System.out.println("-------------------");
    System.out.println("DONE");
  }

  private static void compileProject() {
    try {
      ArrayList<Target> randomTargets = EMConstants.getRandomTargets();
      File instrumentDir = new File(EMConstants.PROJECT_LOCATION
              + EMConstants.PROJECT_NAME + "/instrument");
      File oInstrumentDir = new File(EMConstants.PROJECT_LOCATION
              + EMConstants.PROJECT_NAME + "/oinstrument");

      for (Target target : randomTargets) {
        Runtime.getRuntime().exec(
                "javac "
                        + instrumentDir.getAbsolutePath()
                        + "\\" + target.getMutationOperator()
                        + "\\" + target.getMutantNumber()
                        + "\\*.java");
        Thread.sleep(500);
        Runtime.getRuntime().exec(
                "javac "
                        + oInstrumentDir.getAbsolutePath()
                        + "\\" + target.getMutationOperator()
                        + "\\" + target.getMutantNumber()
                        + "\\*.java");
        Thread.sleep(500);
        System.out.println("Mutant " +
                target.getMutantNumber() +
                target.getMutationOperator() +
                " has been compiled");
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private static void generateTestCase() {
    EMConstants.MAX_ITERATIONS = 5;
    CA_MANAGER.start();
  }


  private static void createProject() throws Exception {
    File projectDir = new File(PROJECT_LOCATION + "/" + PROJECT_NAME);
    if (projectDir.exists() && projectDir.isDirectory()) {
      FileUtils.forceDelete(projectDir);
    }
    boolean result = projectDir.mkdir();
    File projectDirs = new File(projectDir.getAbsolutePath() + "/source");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/classes");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/mutants");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/omutants");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/testcases");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/original");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/instrument");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/oinstrument");
    result = projectDirs.mkdir() && result;
    projectDirs = new File(projectDir.getAbsolutePath() + "/traces");
    result = projectDirs.mkdir() && result;
//    projectDirs = new File(projectDir.getAbsolutePath() + "/temps");
//    result = projectDirs.mkdir() && result;
//    projectDirs = new File(projectDir.getAbsolutePath() + "/temps/instrument");
//    result = projectDirs.mkdir() && result;
//    projectDirs = new File(projectDir.getAbsolutePath() + "/temps/oinstrument");
//    result = projectDirs.mkdir() && result;

    if (!result) {
      throw new Exception("Create Project has errors");
    }
  }
}

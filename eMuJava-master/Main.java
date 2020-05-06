import emujava.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    
    public static final String PROJECT_NAME = "Test";
    public static final String PROJECT_LOCATION = "C:\\Users\\compi\\OneDrive\\Desktop\\Dev\\";
    public static final String CLASS_NAME = "ElectricHeater.java";
    public static final String PROJECT_SOURCE = "C:\\Users\\compi\\sources\\Java\\emuJava\\Programs\\" + CLASS_NAME;
    
    public static void main(String[] args) throws Exception {
        createProject();
        ClassComponents classComponents = scanSourceCode();
        ArrayList<String> mutationOperators = new ArrayList<String>(
                Arrays.asList("ABS", "AOR", "LCR", "ROR", "UOI",
                        "IOP", "PNC", "OMD", "JID", "EOC")
        );
        generateMutants(classComponents, mutationOperators);
        generateTestCase();
    }
    
    private static void createProject() throws Exception {
        File projectDir = new File(PROJECT_LOCATION + "/" + PROJECT_NAME);
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
        
        if (!result) {
            throw new Exception("Create Project has errors");
        }
    }
    
    private static ClassComponents scanSourceCode() {
        EMScanner scan = new EMScanner(
                new File(PROJECT_SOURCE)
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
    
    private static void generateMutants(ClassComponents classComponents,
                                        ArrayList<String> mutationOperators) {
        MySourceCode sourceCode = new MySourceCode();
        EMProjectManager projectManager = new EMProjectManager();
        EMController emController = EMController.create();
        emController.setC1Components(classComponents);
        EMConstants.PROJECT_LOCATION = PROJECT_LOCATION;
        EMConstants.PROJECT_NAME = PROJECT_NAME;
        EMConstants.CLASS_1 = CLASS_NAME;
        EMConstants.GEN_TYPE = "eMuJava";
        EMConstants.MUTATION_OPERATORS.addAll(mutationOperators);
        ArrayList<MemberMethod> method1List = classComponents.getMMList();
        for (MemberMethod memberMethod : method1List) {
            memberMethod.identifyStatements();
            memberMethod.printStatements();
        }
        
        sourceCode.generateMutants(1);
//        sourceCode.instrumentAndTransformCode();
//        projectManager.compileProject();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void generateTestCase() {
    
    }
}


package tudresden.ocl20.pivot.ocl2parser.testcasegenerator.gen.testcasegenerator.parser;

import tudresden.ocl20.pivot.ocl2parser.testcasegenerator.gen.testcasegenerator.lexer.*;
import tudresden.ocl20.pivot.ocl2parser.testcasegenerator.gen.testcasegenerator.node.*;
import tudresden.ocl20.pivot.ocl2parser.testcasegenerator.gen.testcasegenerator.analysis.*;
import java.util.*;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Parser {

    public final Analysis ignoredTokens = new AnalysisAdapter();

    protected Node node;

    private final Lexer lexer;

    private final ListIterator stack = new LinkedList().listIterator();

    private int last_shift;

    private int last_pos;

    private int last_line;

    private Token last_token;

    private final TokenIndex converter = new TokenIndex();

    private final int[] action = new int[2];

    private static final int SHIFT = 0;

    private static final int REDUCE = 1;

    private static final int ACCEPT = 2;

    private static final int ERROR = 3;

    protected void filter() throws ParserException, LexerException, IOException {
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        if (actionTable == null) {
            try {
                DataInputStream s = new DataInputStream(new BufferedInputStream(Parser.class.getResourceAsStream("parser.dat")));
                int length = s.readInt();
                actionTable = new int[length][][];
                for (int i = 0; i < actionTable.length; i++) {
                    length = s.readInt();
                    actionTable[i] = new int[length][3];
                    for (int j = 0; j < actionTable[i].length; j++) {
                        for (int k = 0; k < 3; k++) {
                            actionTable[i][j][k] = s.readInt();
                        }
                    }
                }
                length = s.readInt();
                gotoTable = new int[length][][];
                for (int i = 0; i < gotoTable.length; i++) {
                    length = s.readInt();
                    gotoTable[i] = new int[length][2];
                    for (int j = 0; j < gotoTable[i].length; j++) {
                        for (int k = 0; k < 2; k++) {
                            gotoTable[i][j][k] = s.readInt();
                        }
                    }
                }
                length = s.readInt();
                errorMessages = new String[length];
                for (int i = 0; i < errorMessages.length; i++) {
                    length = s.readInt();
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < length; j++) {
                        buffer.append(s.readChar());
                    }
                    errorMessages[i] = buffer.toString();
                }
                length = s.readInt();
                errors = new int[length];
                for (int i = 0; i < errors.length; i++) {
                    errors[i] = s.readInt();
                }
                s.close();
            } catch (Exception e) {
                throw new RuntimeException("The file \"parser.dat\" is either missing or corrupted.");
            }
        }
    }

    private int goTo(int index) {
        int state = state();
        int low = 1;
        int high = gotoTable[index].length - 1;
        int value = gotoTable[index][0][1];
        while (low <= high) {
            int middle = (low + high) / 2;
            if (state < gotoTable[index][middle][0]) {
                high = middle - 1;
            } else if (state > gotoTable[index][middle][0]) {
                low = middle + 1;
            } else {
                value = gotoTable[index][middle][1];
                break;
            }
        }
        return value;
    }

    private void push(int state, Node node, boolean filter) throws ParserException, LexerException, IOException {
        this.node = node;
        if (filter) {
            filter();
        }
        if (!stack.hasNext()) {
            stack.add(new State(state, this.node));
            return;
        }
        State s = (State) stack.next();
        s.state = state;
        s.node = this.node;
    }

    private int state() {
        State s = (State) stack.previous();
        stack.next();
        return s.state;
    }

    private Node pop() {
        return (Node) ((State) stack.previous()).node;
    }

    private int index(Switchable token) {
        converter.index = -1;
        token.apply(converter);
        return converter.index;
    }

    public Start parse() throws ParserException, LexerException, IOException {
        push(0, null, false);
        List ign = null;
        while (true) {
            while (index(lexer.peek()) == -1) {
                if (ign == null) {
                    ign = new TypedLinkedList(NodeCast.instance);
                }
                ign.add(lexer.next());
            }
            if (ign != null) {
                ignoredTokens.setIn(lexer.peek(), ign);
                ign = null;
            }
            last_pos = lexer.peek().getPos();
            last_line = lexer.peek().getLine();
            last_token = lexer.peek();
            int index = index(lexer.peek());
            action[0] = actionTable[state()][0][1];
            action[1] = actionTable[state()][0][2];
            int low = 1;
            int high = actionTable[state()].length - 1;
            while (low <= high) {
                int middle = (low + high) / 2;
                if (index < actionTable[state()][middle][0]) {
                    high = middle - 1;
                } else if (index > actionTable[state()][middle][0]) {
                    low = middle + 1;
                } else {
                    action[0] = actionTable[state()][middle][1];
                    action[1] = actionTable[state()][middle][2];
                    break;
                }
            }
            switch(action[0]) {
                case SHIFT:
                    push(action[1], lexer.next(), true);
                    last_shift = action[1];
                    break;
                case REDUCE:
                    switch(action[1]) {
                        case 0:
                            {
                                Node node = new0();
                                push(goTo(0), node, true);
                            }
                            break;
                        case 1:
                            {
                                Node node = new1();
                                push(goTo(0), node, true);
                            }
                            break;
                        case 2:
                            {
                                Node node = new2();
                                push(goTo(1), node, true);
                            }
                            break;
                        case 3:
                            {
                                Node node = new3();
                                push(goTo(1), node, true);
                            }
                            break;
                        case 4:
                            {
                                Node node = new4();
                                push(goTo(1), node, true);
                            }
                            break;
                        case 5:
                            {
                                Node node = new5();
                                push(goTo(21), node, false);
                            }
                            break;
                        case 6:
                            {
                                Node node = new6();
                                push(goTo(21), node, false);
                            }
                            break;
                        case 7:
                            {
                                Node node = new7();
                                push(goTo(1), node, true);
                            }
                            break;
                        case 8:
                            {
                                Node node = new8();
                                push(goTo(2), node, true);
                            }
                            break;
                        case 9:
                            {
                                Node node = new9();
                                push(goTo(3), node, true);
                            }
                            break;
                        case 10:
                            {
                                Node node = new10();
                                push(goTo(4), node, true);
                            }
                            break;
                        case 11:
                            {
                                Node node = new11();
                                push(goTo(4), node, true);
                            }
                            break;
                        case 12:
                            {
                                Node node = new12();
                                push(goTo(4), node, true);
                            }
                            break;
                        case 13:
                            {
                                Node node = new13();
                                push(goTo(22), node, false);
                            }
                            break;
                        case 14:
                            {
                                Node node = new14();
                                push(goTo(22), node, false);
                            }
                            break;
                        case 15:
                            {
                                Node node = new15();
                                push(goTo(4), node, true);
                            }
                            break;
                        case 16:
                            {
                                Node node = new16();
                                push(goTo(5), node, true);
                            }
                            break;
                        case 17:
                            {
                                Node node = new17();
                                push(goTo(6), node, true);
                            }
                            break;
                        case 18:
                            {
                                Node node = new18();
                                push(goTo(7), node, true);
                            }
                            break;
                        case 19:
                            {
                                Node node = new19();
                                push(goTo(7), node, true);
                            }
                            break;
                        case 20:
                            {
                                Node node = new20();
                                push(goTo(8), node, true);
                            }
                            break;
                        case 21:
                            {
                                Node node = new21();
                                push(goTo(9), node, true);
                            }
                            break;
                        case 22:
                            {
                                Node node = new22();
                                push(goTo(10), node, true);
                            }
                            break;
                        case 23:
                            {
                                Node node = new23();
                                push(goTo(11), node, true);
                            }
                            break;
                        case 24:
                            {
                                Node node = new24();
                                push(goTo(12), node, true);
                            }
                            break;
                        case 25:
                            {
                                Node node = new25();
                                push(goTo(13), node, true);
                            }
                            break;
                        case 26:
                            {
                                Node node = new26();
                                push(goTo(13), node, true);
                            }
                            break;
                        case 27:
                            {
                                Node node = new27();
                                push(goTo(14), node, true);
                            }
                            break;
                        case 28:
                            {
                                Node node = new28();
                                push(goTo(14), node, true);
                            }
                            break;
                        case 29:
                            {
                                Node node = new29();
                                push(goTo(23), node, false);
                            }
                            break;
                        case 30:
                            {
                                Node node = new30();
                                push(goTo(23), node, false);
                            }
                            break;
                        case 31:
                            {
                                Node node = new31();
                                push(goTo(15), node, true);
                            }
                            break;
                        case 32:
                            {
                                Node node = new32();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 33:
                            {
                                Node node = new33();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 34:
                            {
                                Node node = new34();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 35:
                            {
                                Node node = new35();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 36:
                            {
                                Node node = new36();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 37:
                            {
                                Node node = new37();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 38:
                            {
                                Node node = new38();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 39:
                            {
                                Node node = new39();
                                push(goTo(16), node, true);
                            }
                            break;
                        case 40:
                            {
                                Node node = new40();
                                push(goTo(17), node, true);
                            }
                            break;
                        case 41:
                            {
                                Node node = new41();
                                push(goTo(17), node, true);
                            }
                            break;
                        case 42:
                            {
                                Node node = new42();
                                push(goTo(17), node, true);
                            }
                            break;
                        case 43:
                            {
                                Node node = new43();
                                push(goTo(17), node, true);
                            }
                            break;
                        case 44:
                            {
                                Node node = new44();
                                push(goTo(18), node, true);
                            }
                            break;
                        case 45:
                            {
                                Node node = new45();
                                push(goTo(19), node, true);
                            }
                            break;
                        case 46:
                            {
                                Node node = new46();
                                push(goTo(19), node, true);
                            }
                            break;
                        case 47:
                            {
                                Node node = new47();
                                push(goTo(20), node, true);
                            }
                            break;
                    }
                    break;
                case ACCEPT:
                    {
                        EOF node2 = (EOF) lexer.next();
                        PTestpackage node1 = (PTestpackage) pop();
                        Start node = new Start(node1, node2);
                        return node;
                    }
                case ERROR:
                    throw new ParserException(last_token, "[" + last_line + "," + last_pos + "] " + errorMessages[errors[action[1]]]);
            }
        }
    }

    Node new0() {
        PTestsuite node1 = (PTestsuite) pop();
        ASuiteTestpackage node = new ASuiteTestpackage(node1);
        return node;
    }

    Node new1() {
        PTestcasefile node1 = (PTestcasefile) pop();
        ATestcaseTestpackage node = new ATestcaseTestpackage(node1);
        return node;
    }

    Node new2() {
        XPSuiteElement node3 = null;
        PPackageDeclaration node2 = null;
        PNames node1 = (PNames) pop();
        ATestsuite node = new ATestsuite(node1, node2, node3);
        return node;
    }

    Node new3() {
        XPSuiteElement node3 = null;
        PPackageDeclaration node2 = (PPackageDeclaration) pop();
        PNames node1 = (PNames) pop();
        ATestsuite node = new ATestsuite(node1, node2, node3);
        return node;
    }

    Node new4() {
        XPSuiteElement node3 = (XPSuiteElement) pop();
        PPackageDeclaration node2 = null;
        PNames node1 = (PNames) pop();
        ATestsuite node = new ATestsuite(node1, node2, node3);
        return node;
    }

    Node new5() {
        PSuiteElement node2 = (PSuiteElement) pop();
        XPSuiteElement node1 = (XPSuiteElement) pop();
        X1PSuiteElement node = new X1PSuiteElement(node1, node2);
        return node;
    }

    Node new6() {
        PSuiteElement node1 = (PSuiteElement) pop();
        X2PSuiteElement node = new X2PSuiteElement(node1);
        return node;
    }

    Node new7() {
        XPSuiteElement node3 = (XPSuiteElement) pop();
        PPackageDeclaration node2 = (PPackageDeclaration) pop();
        PNames node1 = (PNames) pop();
        ATestsuite node = new ATestsuite(node1, node2, node3);
        return node;
    }

    Node new8() {
        TIdent node2 = (TIdent) pop();
        TSuitename node1 = (TSuitename) pop();
        ANames node = new ANames(node1, node2);
        return node;
    }

    Node new9() {
        TCloseparen node6 = (TCloseparen) pop();
        TTick node5 = (TTick) pop();
        TStringLiteral node4 = (TStringLiteral) pop();
        TTick node3 = (TTick) pop();
        TOpenparen node2 = (TOpenparen) pop();
        TInclude node1 = (TInclude) pop();
        ASuiteElement node = new ASuiteElement(node1, node2, node3, node4, node5, node6);
        return node;
    }

    Node new10() {
        XPTestcaseElement node4 = null;
        PModelDeclaration node3 = (PModelDeclaration) pop();
        PPackageDeclaration node2 = null;
        PName node1 = (PName) pop();
        ATestcasefile node = new ATestcasefile(node1, node2, node3, node4);
        return node;
    }

    Node new11() {
        XPTestcaseElement node4 = null;
        PModelDeclaration node3 = (PModelDeclaration) pop();
        PPackageDeclaration node2 = (PPackageDeclaration) pop();
        PName node1 = (PName) pop();
        ATestcasefile node = new ATestcasefile(node1, node2, node3, node4);
        return node;
    }

    Node new12() {
        XPTestcaseElement node4 = (XPTestcaseElement) pop();
        PModelDeclaration node3 = (PModelDeclaration) pop();
        PPackageDeclaration node2 = null;
        PName node1 = (PName) pop();
        ATestcasefile node = new ATestcasefile(node1, node2, node3, node4);
        return node;
    }

    Node new13() {
        PTestcaseElement node2 = (PTestcaseElement) pop();
        XPTestcaseElement node1 = (XPTestcaseElement) pop();
        X1PTestcaseElement node = new X1PTestcaseElement(node1, node2);
        return node;
    }

    Node new14() {
        PTestcaseElement node1 = (PTestcaseElement) pop();
        X2PTestcaseElement node = new X2PTestcaseElement(node1);
        return node;
    }

    Node new15() {
        XPTestcaseElement node4 = (XPTestcaseElement) pop();
        PModelDeclaration node3 = (PModelDeclaration) pop();
        PPackageDeclaration node2 = (PPackageDeclaration) pop();
        PName node1 = (PName) pop();
        ATestcasefile node = new ATestcasefile(node1, node2, node3, node4);
        return node;
    }

    Node new16() {
        TIdent node2 = (TIdent) pop();
        TTestname node1 = (TTestname) pop();
        AName node = new AName(node1, node2);
        return node;
    }

    Node new17() {
        TPackageName node2 = (TPackageName) pop();
        TPackage node1 = (TPackage) pop();
        APackageDeclaration node = new APackageDeclaration(node1, node2);
        return node;
    }

    Node new18() {
        PModelDesc node2 = (PModelDesc) pop();
        PMetamodelDesc node1 = (PMetamodelDesc) pop();
        AMetamodelModelDeclaration node = new AMetamodelModelDeclaration(node1, node2);
        return node;
    }

    Node new19() {
        PMetamodelDesc node2 = (PMetamodelDesc) pop();
        PModelDesc node1 = (PModelDesc) pop();
        AModelModelDeclaration node = new AModelModelDeclaration(node1, node2);
        return node;
    }

    Node new20() {
        TPackageName node2 = (TPackageName) pop();
        TMetamodel node1 = (TMetamodel) pop();
        AMetamodelDesc node = new AMetamodelDesc(node1, node2);
        return node;
    }

    Node new21() {
        TTick node4 = (TTick) pop();
        TStringLiteral node3 = (TStringLiteral) pop();
        TTick node2 = (TTick) pop();
        TModel node1 = (TModel) pop();
        AModelDesc node = new AModelDesc(node1, node2, node3, node4);
        return node;
    }

    Node new22() {
        TBraceClose node5 = (TBraceClose) pop();
        PResult node4 = (PResult) pop();
        POclExpression node3 = (POclExpression) pop();
        TBraceOpen node2 = (TBraceOpen) pop();
        PTestcasename node1 = (PTestcasename) pop();
        ATestcaseElement node = new ATestcaseElement(node1, node2, node3, node4, node5);
        return node;
    }

    Node new23() {
        TIdent node2 = (TIdent) pop();
        TTestcase node1 = (TTestcase) pop();
        ATestcasename node = new ATestcasename(node1, node2);
        return node;
    }

    Node new24() {
        TOclblock node2 = (TOclblock) pop();
        TOclexpression node1 = (TOclexpression) pop();
        AOclExpression node = new AOclExpression(node1, node2);
        return node;
    }

    Node new25() {
        PAbstractModel node1 = (PAbstractModel) pop();
        AAbstractmodelResult node = new AAbstractmodelResult(node1);
        return node;
    }

    Node new26() {
        TError node1 = (TError) pop();
        AErrorResult node = new AErrorResult(node1);
        return node;
    }

    Node new27() {
        TBraceClose node7 = (TBraceClose) pop();
        XPAssignment node6 = null;
        PModelExpression node5 = (PModelExpression) pop();
        TAssign node4 = (TAssign) pop();
        TFirstName node3 = (TFirstName) pop();
        TBraceOpen node2 = (TBraceOpen) pop();
        TAbstractmodel node1 = (TAbstractmodel) pop();
        AAbstractModel node = new AAbstractModel(node1, node2, node3, node4, node5, node6, node7);
        return node;
    }

    Node new28() {
        TBraceClose node7 = (TBraceClose) pop();
        XPAssignment node6 = (XPAssignment) pop();
        PModelExpression node5 = (PModelExpression) pop();
        TAssign node4 = (TAssign) pop();
        TFirstName node3 = (TFirstName) pop();
        TBraceOpen node2 = (TBraceOpen) pop();
        TAbstractmodel node1 = (TAbstractmodel) pop();
        AAbstractModel node = new AAbstractModel(node1, node2, node3, node4, node5, node6, node7);
        return node;
    }

    Node new29() {
        PAssignment node2 = (PAssignment) pop();
        XPAssignment node1 = (XPAssignment) pop();
        X1PAssignment node = new X1PAssignment(node1, node2);
        return node;
    }

    Node new30() {
        PAssignment node1 = (PAssignment) pop();
        X2PAssignment node = new X2PAssignment(node1);
        return node;
    }

    Node new31() {
        PModelExpression node3 = (PModelExpression) pop();
        TAssign node2 = (TAssign) pop();
        TIdent node1 = (TIdent) pop();
        AAssignment node = new AAssignment(node1, node2, node3);
        return node;
    }

    Node new32() {
        TAtpre node5 = null;
        TCloseparen node4 = (TCloseparen) pop();
        PParameters node3 = null;
        TOpenparen node2 = (TOpenparen) pop();
        TIdent node1 = (TIdent) pop();
        AModelelementModelExpression node = new AModelelementModelExpression(node1, node2, node3, node4, node5);
        return node;
    }

    Node new33() {
        TAtpre node5 = null;
        TCloseparen node4 = (TCloseparen) pop();
        PParameters node3 = (PParameters) pop();
        TOpenparen node2 = (TOpenparen) pop();
        TIdent node1 = (TIdent) pop();
        AModelelementModelExpression node = new AModelelementModelExpression(node1, node2, node3, node4, node5);
        return node;
    }

    Node new34() {
        TAtpre node5 = (TAtpre) pop();
        TCloseparen node4 = (TCloseparen) pop();
        PParameters node3 = null;
        TOpenparen node2 = (TOpenparen) pop();
        TIdent node1 = (TIdent) pop();
        AModelelementModelExpression node = new AModelelementModelExpression(node1, node2, node3, node4, node5);
        return node;
    }

    Node new35() {
        TAtpre node5 = (TAtpre) pop();
        TCloseparen node4 = (TCloseparen) pop();
        PParameters node3 = (PParameters) pop();
        TOpenparen node2 = (TOpenparen) pop();
        TIdent node1 = (TIdent) pop();
        AModelelementModelExpression node = new AModelelementModelExpression(node1, node2, node3, node4, node5);
        return node;
    }

    Node new36() {
        TIdent node1 = (TIdent) pop();
        AIdentModelExpression node = new AIdentModelExpression(node1);
        return node;
    }

    Node new37() {
        TNull node1 = (TNull) pop();
        ANullModelExpression node = new ANullModelExpression(node1);
        return node;
    }

    Node new38() {
        PSimpleExpression node1 = (PSimpleExpression) pop();
        ASimpleModelExpression node = new ASimpleModelExpression(node1);
        return node;
    }

    Node new39() {
        TCloseparen node5 = (TCloseparen) pop();
        PParameters node4 = (PParameters) pop();
        TOpenparen node3 = (TOpenparen) pop();
        TIdent node2 = (TIdent) pop();
        TNew node1 = (TNew) pop();
        ANewModelExpression node = new ANewModelExpression(node1, node2, node3, node4, node5);
        return node;
    }

    Node new40() {
        TRealValue node1 = (TRealValue) pop();
        ARealSimpleExpression node = new ARealSimpleExpression(node1);
        return node;
    }

    Node new41() {
        TIntegerValue node1 = (TIntegerValue) pop();
        AIntegerSimpleExpression node = new AIntegerSimpleExpression(node1);
        return node;
    }

    Node new42() {
        TTick node3 = (TTick) pop();
        TStringLiteral node2 = null;
        TTick node1 = (TTick) pop();
        AStringSimpleExpression node = new AStringSimpleExpression(node1, node2, node3);
        return node;
    }

    Node new43() {
        TTick node3 = (TTick) pop();
        TStringLiteral node2 = (TStringLiteral) pop();
        TTick node1 = (TTick) pop();
        AStringSimpleExpression node = new AStringSimpleExpression(node1, node2, node3);
        return node;
    }

    Node new44() {
        PParameter node2 = (PParameter) pop();
        TComma node1 = (TComma) pop();
        AElementParameter node = new AElementParameter(node1, node2);
        return node;
    }

    Node new45() {
        PParameter node1 = (PParameter) pop();
        ASingleParameters node = new ASingleParameters(node1);
        return node;
    }

    Node new46() {
        PParameters node3 = (PParameters) pop();
        TComma node2 = (TComma) pop();
        PParameter node1 = (PParameter) pop();
        AMultipleParameters node = new AMultipleParameters(node1, node2, node3);
        return node;
    }

    Node new47() {
        PModelExpression node1 = (PModelExpression) pop();
        AExpressionParameter node = new AExpressionParameter(node1);
        return node;
    }

    private static int[][][] actionTable;

    private static int[][][] gotoTable;

    private static String[] errorMessages;

    private static int[] errors;
}

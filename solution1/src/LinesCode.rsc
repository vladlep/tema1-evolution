module LinesCode
import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import IO;
import List;
import Set;

public void linesCode(){
//theResource = extractProject(|project://SmallSql/.|);// contains all files
methodsFile = |file:///D:/vlad/methods.txt| ;
ast = createAstsFromProject(|project://SmallSql/.|);
contor = 0;
for (AstNode aNode <- ast)
{
visit(aNode){
case methodDeclaration(_, _, _, _, methodName, _, _, _) : {contor = contor +1 ;appendToFile(methodsFile , methodName+"\n"); }

};
};
print (contor);
//res = [ info|A aNode<-ast, info = aNode@location];

//outputFile = |file:///D:/vlad/ast.txt| ;
//appendToFile(outputFile, ast);
}
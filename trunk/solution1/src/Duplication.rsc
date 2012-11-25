module Duplication

import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import  lang::java::jdt::JavaADT;
import util::Resources;
import IO;
import String;
import List;

/**
	"We calculate code duplication as the percentage of all code that occurs more 
than once in equal code blocks of at least 6 lines" [SIG]
	we ignore leading spaces
	long version of the function
*/
public void findClones(){
	allFiles = {|project://Hello/src/apackage/HelloWorld.java|,|project://Hello/src/apackage/MainClass.java|};
	strFile = [];
	totalDupLines = 0;
	for (aFile <- allFiles){
		strFile = [trim(aLine) |aLine <-readFileLines(aFile)];
		allFiles = allFiles - aFile;
		i = 0;
		while (i <size(strFile)-6){
			println(strFile[i]);
			//search same file
			theFragment = [strFile[i+k] |  k<-[0..5]];
			j =  i+5;
			while(j <size(strFile)-6){
				otherFragment = [strFile[j+k] | k<-[0..5]];
				if( theFragment == otherFragment){
					nrDupLines = 6;
					moreLines = 1;
					while(strFile[i+nrDupLines ] == strFile[j+nrDupLines ])
						nrDupLines+=1;
					j += nrDupLines;
					totalDupLines +=nrDupLines;		
				}else{ 
					j+=1;
					}
						
			}
			
			//search rest of files
			for (otherFile <-allFiles){
				break;
			}
		
		i+=1;
		}
	}
	println (totalDupLines);
}

/**
short version
*/


/**

*/
public set[loc] getAllFiles (){
	projectLoc = |project://Hello/|;
	resour = getProject(projectLoc);
	cont= 0;
	allFiles = {};
	visit (resour) { 
		case file(aFileLoc) : if(aFileLoc.extension == "java") allFiles += aFileLoc;  
	}
	return allFiles;
}

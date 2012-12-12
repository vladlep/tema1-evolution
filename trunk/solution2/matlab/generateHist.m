function generateHist( data, prjName)
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here

treatedCyc= [1;2;5;10];
for i = 1: size(treatedCyc)
    cyc = treatedCyc(i);
    loc = histForCyc(cyc,data);
    saveHistograms(loc,strcat(prjName,'-histForCyc ',num2str(treatedCyc(i))));
end

end


load results.mat;

scatter(results(:,1),results(:,2),3)
xlabel('Cyclometic complexity')
ylabel('LOC')
treatedCyc= [1;2;5;10];

for i = 1: size(treatedCyc)
    cyc = treatedCyc(i);
    loc = histForCyc(cyc,results);
    saveHistograms(loc,strcat('histForCyc ',num2str(treatedCyc(i))));
end

calcError(2.09561, 2.4646,results)
function [p]= trainFunction( trainData, prjName)
%data : 
%    column 1 = CC
%    column 2 = LOC

treatedCyc= [1;2;5;10];
for i = 1: size(treatedCyc)
    cyc = treatedCyc(i);
    loc = histForCyc(cyc,trainData);
    saveHistograms(loc,strcat(prjName,'-histForCyc ',num2str(treatedCyc(i))));
end

%--stast: plot all the points
scatter(trainData(:,1),trainData(:,2),3);
xlabel('Cyclometic complexity');
ylabel('LOC');
hold on;
p= polyfit(trainData(:,1),trainData(:,2),1)
xs = [1;max(trainData(:,1))];
ys = p(1) * xs + p(2);
plot(xs,ys,  '--rs','MarkerEdgeColor','k');
print('-dpng', strcat(prjName,'LOCvsCC'));
hold off;

end


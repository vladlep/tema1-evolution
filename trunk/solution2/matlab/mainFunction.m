function  mainFunction( data)
%data : 
%    column 1 = CC
%    column 2 = LOC

treatedCyc= [1;2;5;10];
for i = 1: size(treatedCyc)
    cyc = treatedCyc(i);
    loc = histForCyc(cyc,data);
    saveHistograms(loc,strcat('histForCyc ',num2str(treatedCyc(i))));
end

%--stast: plot all the points
scatter(data(:,1),data(:,2),3);
xlabel('Cyclometic complexity');
ylabel('LOC');
hold on;
p= polyfit(data(:,1),data(:,2),1)
xs = [1;max(data(:,1))];
ys = p(1) * xs + p(2);
plot(xs,ys,  '--rs','MarkerEdgeColor','k');
print('-dpng', 'LOCvsCC');
hold off;


calcError(p(1), p(2),data);

end


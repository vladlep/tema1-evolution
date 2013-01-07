load smallTrain.mat;
load smallTest.mat;
load xmlTrain.mat;
load xmlTest.mat;
load argoTrain.mat;
load argoTest.mat;

prjName = 'unique-small';
p = trainFunction(smallTrain,prjName );
[squareError,smallResults] = calcError(p(1), p(2),unique(smallTest,'rows'), prjName );

prjName = 'unique-hsql';
p = trainFunction( hsqlTrain,prjName );
[squareError,hsqlResults] = calcError(p(1), p(2),unique(hsqlTest,'rows'), prjName );


prjName = 'unique-argo';
p = trainFunction( argoTrain,prjName );
[squareError,argoResults] = calcError(p(1), p(2),unique(argoTest,'rows'), prjName );

prjName = 'unique-xml';
p = trainFunction( xmlTrain,prjName );
[squareError,xmlResults] = calcError(p(1), p(2),unique(xmlTest,'rows'), prjName );





plot([1:10],argoResults,'--gs','LineWidth',2,...
                'MarkerEdgeColor','g','MarkerFaceColor','g','MarkerSize',5);
text(2.25,argoResults(2),'\leftarrow Argo UML' ,'HorizontalAlignment','left','Color','g')           

hold on
plot([1:10],hsqlResults,'--k*','LineWidth',2,...
                'MarkerEdgeColor','k', 'MarkerFaceColor','k',  'MarkerSize',5);
text(4.15,hsqlResults(4),'\leftarrow Hsql ','HorizontalAlignment','left','Color','k')           


plot([1:10],xmlResults,'--r*','LineWidth',2,...
                'MarkerEdgeColor','r', 'MarkerFaceColor','r',  'MarkerSize',5);
text(3.85,xmlResults(4),'XML \rightarrow','HorizontalAlignment','right', 'Color','r')           


plot([1:10],smallResults,'--bs','LineWidth',2,...
                'MarkerEdgeColor','b', 'MarkerFaceColor','b',  'MarkerSize',5);
text(2.85,smallResults(3),'Small \rightarrow','HorizontalAlignment','right','Color','b')           


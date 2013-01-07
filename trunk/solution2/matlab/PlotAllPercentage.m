prjName = 'small';
p = trainFunction( [smallTrain;xmlTrain;hsqlTrain;argoTrain;],prjName );
% p = trainFunction( smallTrain,prjName );
[squareError,smallResults] = calcError(p(1), p(2),smallTest, prjName );


prjName = 'xml';
% p = trainFunction( xmlTrain,prjName );
[squareError,xmlResults] = calcError(p(1), p(2),xmlTest, prjName );

prjName = 'hsql';
% p = trainFunction( hsqlTrain,prjName );
[squareError,hsqlResults] = calcError(p(1), p(2),hsqlTest, prjName );


prjName = 'argo';
% p = trainFunction( argoTrain,prjName );
[squareError,argoResults] = calcError(p(1), p(2),argoTest, prjName );

prjName = 'allProjects';
% p = trainFunction( [smallTrain;xmlTrain;hsqlTrain;argoTrain;],prjName );
[squareError,allResults] = calcError(p(1), p(2),[argoTest;xmlTest;smallTest;hsqlTest], 'allFunctionTestedOnAll' );


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
text(2.85,smallResults(3)+1.5,'Small \rightarrow','HorizontalAlignment','right','Color','b')           

plot([1:10],allResults,'-mx','LineWidth',2,...
                'MarkerEdgeColor','m', 'MarkerFaceColor','m',  'MarkerSize',5);
text(2.85,allResults(3)-0.5,'All projects \rightarrow','HorizontalAlignment','right','Color','m')           

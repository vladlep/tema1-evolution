function [squareError,percentageResults] = calcError( x1,x2, data, prjName )
    error= 0;
    maxDiffLOC =10;
    percentageResults = [];
    for diffLOC =1:maxDiffLOC 
    percentCorrect =0;
     for i=1:size(data,1)
            error = error + (x1 *data(i,1) + x2 - data(i,2))^2;
                if ((x1 *data(i,1) + x2 - data(i,2))^2 <diffLOC^2)
                    percentCorrect = percentCorrect +1;
                end
        end
        squareError = error / size(data,1);
        percentCorrect = percentCorrect / size(data,1) * 100;
    percentageResults = [percentageResults ;percentCorrect];
    end
    plot([1:maxDiffLOC],percentageResults);
    percentageResults
    xlabel('Difference LOC accepted');
    ylabel('Correct percentage prediction');
    print('-dpng', strcat(prjName,'-Success percentage'));
end


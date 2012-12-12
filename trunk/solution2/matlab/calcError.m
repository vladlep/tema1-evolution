function [squareError,percentCorrect] = calcError( x1,x2, data )
    error= 0;
    percentCorrect =0;
    for diffLOC =1:7
     for i=1:size(data,1)
            error = error + (x1 *data(i,1) + x2 - data(i,2))^2;
                if ((x1 *data(i,1) + x2 - data(i,2))^2 <diffLOC^2)
                    percentCorrect = percentCorrect +1;
                end
        end
        squareError = error / size(data,1);
        percentCorrect = percentCorrect / size(data,1) * 100
    scatter(diffLOC,percentCorrect,3);
    hold on;
    end
    xlabel('Difference LOC accepted');
    ylabel('Correct percentage prediction');
    hold off;

end


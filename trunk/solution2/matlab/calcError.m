function [squareError,percentCorrect] = calcError( x1,x2, data )
    error= 0;
    percentCorrect =0;
    for i=1:size(data,1)
            error = error + (x1 *data(i,1) + x2 - data(i,2))^2;
            if ((x1 *data(i,1) + x2 - data(i,2))^2 <25)
                percentCorrect = percentCorrect +1;
            end
    end
    squareError = error / size(data,1);
    percentCorrect = percentCorrect / size(data,1) * 100
end


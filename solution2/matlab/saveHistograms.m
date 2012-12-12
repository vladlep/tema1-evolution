% Saves a histogram plot for each dimension of the data. 
% data:  the n x m matrix of data, for which m number of plots will be obtained 
% name: the name of the images. The dimension number will be added at the
% end

function  saveHistograms( data, name)
    hist(data(:),100);
    xlabel('LOC')
    ylabel('Number of methods')
    str  = strcat(name);
    print('-dpng', str)
   
end


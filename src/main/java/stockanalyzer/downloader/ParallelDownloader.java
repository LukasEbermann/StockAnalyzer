package stockanalyzer.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {


    @Override
    public int process(List<String> tickers) {
        ExecutorService executor = Executors.newFixedThreadPool(100);


        List<Future<String>> myList = new ArrayList<>();

        for (String ticker : tickers) {
            Callable<String> request = () -> saveJson2File(ticker);

            Future<String> fileName = executor.submit(request);

            myList.add(fileName);
        }

        for (Future<String> f : myList) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return myList.size();


    }
}
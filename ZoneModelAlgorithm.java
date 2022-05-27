import java.util.ArrayList;
import java.util.List;

public class ZoneModelAlgorithm extends PausingAlgorithms {
    private int sumWSS;

    public ZoneModelAlgorithm(List<Process> processList, int totalFrameNumber, double lowThreshold, double highThreshold, int timeWindow, int c) {
        super(processList, totalFrameNumber, lowThreshold, highThreshold, timeWindow, c);
    }

    @Override
    public void singleDynamicalAllocation() {
        //liczymy wspolczynniki
        countWSS();
        //patrzymy na wzpolczynniki, dodajemy, zabieramy ramki lub pauzujemy
        framesReduction();

        serviengPaused();
    }

    public void framesReduction() {
        for (Process process : processList) {
            if (!process.isPaused()) {
                if (process.getFrameNumber() > 1 && process.getWssi() < lowThreshold) {
                    //find wich remove
                    int delta = process.getFrameNumber() - process.getWssi();
                    removeProcesssesFrames(process, delta);
                }
            }
        }
        for (Process process : processList) {
            if (!process.isPaused()) {
                if (process.getFrameNumber() > 1 && process.getWssi() > highThreshold) {
                    int delta =  process.getWssi() - process.getFrameNumber();
                    //find wich remove
                    if (delta <= freeFrames) {
                        process.setFrameNumber(process.getFrameNumber() + delta);
                        freeFrames--;
                    } else {
                        chooseAndPauseProcess(delta, process);
                        //pause
                        //dodaj wiadomosc jaka byl liczba ramek przed pauzowaniem, zeby obudzic jak bedzie taka sama wolna liczba
                    }
                }
            }
        }
    }
    public void chooseAndPauseProcess(int delta, Process process){
        //bez sensu pauzowac 1 proces
        if(processList.size()!=1) {
            Process maxWSSProcess = processList.get(0);
            for (int i = 1; i< processList.size(); i++) {
                if (!processList.get(i).isPaused() && processList.get(i) != process) {
                    if (processList.get(i).getWssi() > maxWSSProcess.getWssi()) {
                        maxWSSProcess = processList.get(i);
                    }
                }
            }
            maxWSSProcess.setPaused(true);
            maxWSSProcess.setFrameNumberBeforePausing(maxWSSProcess.getFrameNumber()+delta/2);
            frameAllocation(maxWSSProcess.getFrameNumber());
            maxWSSProcess.cleanFrames();
        }

    }
    public void serviengPaused() {
         int i = 0;
         while (i < processList.size() && freeFrames>0) {
             if (processList.get(i).isPaused() && processList.get(i).getFrameNumberBeforePausing()<=freeFrames) {
                 processList.get(i).setFrameNumber(processList.get(i).getFrameNumberBeforePausing());
                 processList.get(i).setPaused(false);
                 freeFrames -=processList.get(i).getFrameNumberBeforePausing();
             }
         }
    }

    public void removeProcesssesFrames(Process process, int numberOfFrames){
        while (numberOfFrames > 0){
            int removalIndex = process.findFrameToReplace();
            process.getFrames().remove(removalIndex);
            process.setFrameNumber(process.getFrameNumber()-1);

            numberOfFrames--;
            freeFrames++;
        }
    }

    public void countWSS() {
        sumWSS = 0;
        for (Process process : processList) {
            if (!process.isPaused()) {
                countWSSi(process);
                sumWSS += process.getWssi();
            }
        }
    }

    public void countWSSi(Process process) {
        List<Page> pages = new ArrayList<>();
        int i = process.getOldPageList().size() - 1;
        int j = 0;
        int workingSetSize = 0;

        while (j < timeWindow) {
            if (!containsPage(process.getOldPageList().get(i), pages)) {
                pages.add(process.getOldPageList().get(i));
                workingSetSize++;
            }
            j++;
            i++;
        }
        process.setWssi(workingSetSize);
    }


}

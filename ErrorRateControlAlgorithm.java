import java.util.List;

public class ErrorRateControlAlgorithm extends PausingAlgorithms {
    //nie mozemy zabierac ramek i oddawac innym, bo jak sie zabierze to bedzie generowac kla kazdej strony blad

    private int numberOfErrors;

    public ErrorRateControlAlgorithm(List<Process> processList, int totalFrameNumber, double lowThreshold, double highThreshold, int timeWindow, int c) {
        super(processList, totalFrameNumber, lowThreshold, highThreshold, timeWindow, c);
    }

    @Override
    public void singleDynamicalAllocation(){
        //liczymy wspolczynniki
        countPPFs();
        //patrzymy na wzpolczynniki, dodajemy, zabieramy ramki lub pauzujemy
        framesreduction();
        //
        serviengPaused();
    }

    public void framesreduction() {

        for (Process process : processList) {
            if (!process.isPaused()) {
                if (process.getFrameNumber() > 1 && process.getPpf() < lowThreshold) {
                    //find wich remove
                    int removalIndex = process.findFrameToReplace();
                    process.getFrames().remove(removalIndex);
                    process.setFrameNumber(process.getFrameNumber() - 1);
                    freeFrames++;
                }
            }
        }
        for (Process process : processList) {
            if (!process.isPaused()) {
                if (process.getFrameNumber() > 1 && process.getPpf() > highThreshold) {
                    //find wich remove
                    if (freeFrames > 0) {
                        process.setFrameNumber(process.getFrameNumber() + 1);
                        freeFrames--;
                    } else {
                        //pause
                        process.setPaused(true);
                    }
                }
            }
        }
    }

    public void serviengPaused() {
        if (freeFrames > 0) {
            int i = 0;
            while (i < processList.size()) {
                if (processList.get(i).isPaused()) {
                    processList.get(i).setFrameNumber(freeFrames);
                    processList.get(i).setPaused(false);
                    freeFrames = 0;
                    return;
                }
            }
        }
    }

    public void countPPFs() {
        for (Process process : processList) {
            if (!process.isPaused()) {
                countPPF(process);
            }
        }
    }

    public void countPPF(Process process) {
        int[] t = process.getErrorTable();
        int errorNumber = 0;
        int timeWindowI = timeWindow;
        int i = currentTime;
        while (timeWindowI > 0) { //nie moze byc takie male
            if (t[i] == 1) {
                errorNumber++;
            }
            i--;
            timeWindowI--;
        }
        process.setPpf((double) errorNumber / timeWindow);
    }
}

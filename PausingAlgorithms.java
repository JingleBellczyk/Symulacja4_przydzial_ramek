import java.util.List;

public class PausingAlgorithms extends Algorithm{
    protected int freeFrames;
    protected double lowThreshold;
    protected double highThreshold;
    protected int timeWindow;
    protected int c; // stała okna czasowego

    public PausingAlgorithms(List<Process> processList, int totalFrameNumber, double lowThreshold, double highThreshold,
                             int timeWindow, int c) {
        super(processList,totalFrameNumber);
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
        this.timeWindow = timeWindow;
        this.c = c;
        freeFrames = 0;
    }

    @Override// nwm czy potrzebne
    public void dynamicalFrameAllocation() {
        //patrzymy czy juz czas by sprawdzic wspolczynniki
        if (currentTime >= timeWindow && currentTime % (c * timeWindow) == 0) {
            //liczymy wspolczynniki
            singleDynamicalAllocation();
        }
    }
    public void singleDynamicalAllocation(){}

    @Override
    //takie first frame allocation
    public void frameAllocation(int frameNumber) {
        //tez proporcjonalny
        //jesli wstrzymany to nie dajemy mu ramek
        //wiec w sumie inny

        //cos zle pomyslalam - totalFrameNumberjako parametr,  i totalPagesNumber - liczoje jako parametr,
        //1 - liczymy proporcjonalnie ile dla kazdego z listy procesow z listy
        int leftFrames = frameNumber;
        int i = 0;
        while (i < processList.size()) {
            if (!processList.get(i).isPaused()) {
                int processFrameNumber = (int) (((double) processList.get(i).getActualDifferentPages()/ totalDifferentPagesNumber) * frameNumber);
                processList.get(i).setFramesSize(processFrameNumber);
                leftFrames -= processFrameNumber;
            }
            i++;
        }
        i = 0;
        while (leftFrames != 0 && i<processList.size()) {
            if (!processList.get(i).isPaused()) {
                processList.get(i).setFramesSize(processList.get(i).getFrameNumber()+1);
                leftFrames--;
            }
            i++;
        }
        if(leftFrames == 0) return;
        freeFrames = leftFrames; // nie powinno sie wydarzyc
    }
}

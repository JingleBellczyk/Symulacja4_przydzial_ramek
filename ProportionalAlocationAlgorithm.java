import java.util.List;

public class ProportionalAlocationAlgorithm extends Algorithm{

    public ProportionalAlocationAlgorithm(List<Process> processList, int totalFrameNumber) {
        super(processList,totalFrameNumber);
    }
    @Override
    public void frameAllocation(int frameNumber) {
        int leftFrames = frameNumber;
        int i = 0;
        while (i < processList.size() - 1) {
            //if(processList.get(i).getPageList().size()!=0) {
                int processFrameNumber = (int) (((double) processList.get(i).getActualDifferentPages() / totalDifferentPagesNumber) * frameNumber);
                processList.get(i).setFramesSize(processList.get(i).getFrameNumber() + processFrameNumber);
                leftFrames -= processFrameNumber;
                i++;
            //}
            System.out.println("tu");
        }
        if(processList.get(i).getFrameNumber()<0) System.out.println("mniejsze od 0");
        processList.get(i).setFramesSize(leftFrames);
    }
}
//poprawic wszedzie
//dodaj to co mial plus nowe
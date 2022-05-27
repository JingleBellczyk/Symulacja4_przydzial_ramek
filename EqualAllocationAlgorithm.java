import java.util.List;

public class EqualAllocationAlgorithm extends Algorithm {
    public EqualAllocationAlgorithm(List<Process> processList, int totalFrameNumber) {
        super(processList,totalFrameNumber);
    }

    @Override
    public void frameAllocation(int frameNumber) {
        int minProcessFrameNumber = frameNumber / processList.size();
        int restOfFrames = frameNumber % processList.size();

        for (Process process : processList) {
            //if(process.getPageList().size()!=0) {
                process.setFramesSize(process.getFrameNumber() + minProcessFrameNumber);
            //}
        }
        int i = 0;
        while(restOfFrames>0){
            processList.get(i%processList.size()).setFramesSize(processList.get(i).getFrameNumber() + 1);
               restOfFrames--;
               i++;
        }
    }
}

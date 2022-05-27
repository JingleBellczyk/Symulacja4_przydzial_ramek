import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {

    protected List<Process> processList;
    protected int totalFrameNumber;
    protected int totalDifferentPagesNumber;
    protected int currentTime;
    protected List<Process> oldProcesses;

    public Algorithm(List<Process> processList, int totalFrameNumber) {
        this.processList = processList;
        this.totalFrameNumber = totalFrameNumber;
        oldProcesses = new ArrayList<>();
    }

    public boolean listEmpty(List<Process> processList) {
        return processList.size() == 0;
    }

    public abstract void frameAllocation(int frameNumber);

    public void serveProcesses() {
        countTotalDifferentPages();
        frameAllocation(totalFrameNumber);
        currentTime = 0;
        while (!listEmpty(processList)) {
            int i = 0;
            while(i < processList.size()){
                processList.get(i).increaseFrameData();
                if (processList.get(i).getPageList().size() == 0) {
                    //oddaj ramki wolne
                    countTotalDifferentPages();
                    oldProcesses.add(processList.get(i));
                    processList.remove(processList.get(i));

                    if(processList.size()!=0) {
                        frameAllocation(oldProcesses.get(oldProcesses.size() - 1).getFrameNumber()); //to jest dla wszystkich naraz
                    }
                } else {
                    if (processList.get(i).getPageList().get(0).getAppealTime() == currentTime) {
                        processList.get(i).servePage(processList.get(i).getPageList().get(0));
                    }else{
                    }
                    i++;
                }
                dynamicalFrameAllocation();
            }
            currentTime++;
        }
        printStats();
    }

    //except paused
    public void dynamicalFrameAllocation(){}

    public int countTotalDifferentPages(){
        totalDifferentPagesNumber = 0;
        int n = 0;
        for (Process process : processList) {
            if(!process.isPaused()) {
                n = countDifferentPages(process.getPageList());
                totalDifferentPagesNumber += n;
            }
            process.setActualDifferentPages(n);
        }
        return totalDifferentPagesNumber;
    }
    public int countDifferentPages(List<Page> pageList){
        int numberOfDifferent = 0;
        List<Page>  differentPages = new ArrayList<>();
        for(int i = 0; i<pageList.size(); i++){
            if(!containsPage(pageList.get(i),differentPages)){
                numberOfDifferent++;
                differentPages.add(pageList.get(i));
            }
        }
        return numberOfDifferent;
    }

    public boolean containsPage(Page page, List<Page> pages) {
        int i = 0;
        while (i < pages.size()) {
            if (pages.get(i).getNumber() == page.getNumber()) {
                return true;
            }
            i++;
        }
        return false;
    }
    public void printStats(){
        System.out.println(this.getClass().getSimpleName() + " number of errors: ");
        for (int i = 0; i<oldProcesses.size(); i++){
            System.out.println(i + ": " + oldProcesses.get(i).getErrorsNumber());
        }
    }
}

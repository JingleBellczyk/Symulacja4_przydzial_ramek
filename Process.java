import java.util.ArrayList;
import java.util.List;

public class Process {
    private List<Page> pageList;
    private List<Page> oldPageList;
    private List<Page> frames;
    private int errorsNumber;
    private boolean paused;
    private double ppf;
    private int wssi;
    private int[] errorTable;
    private int actualDifferentPages;
    private int frameNumberBeforePausing;


    private int frameNumber;
    private int pageNumber;

    public Process(List<Page> pageList) {
        frames = new ArrayList<>();
        oldPageList = new ArrayList<>();

        this.pageList = pageList;
        pageNumber = pageList.size();
        errorsNumber = 0;
        paused = false;

        int maxOccuringTime = pageList.get(pageList.size() - 1).getAppealTime();
        errorTable = new int[maxOccuringTime + 1]; // bo od zera
    }

    public void setFramesSize(int frameNumber) {
        this.frameNumber = frameNumber;
        //frames = new Page[frameNumber];
    }

    public void changeFrameSize(int newFrameNumber) {

    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public List<Page> getOldPageList() {
        return oldPageList;
    }

    public void setOldPageList(List<Page> oldPageList) {
        this.oldPageList = oldPageList;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public boolean pageIsInMemory(Page page) {

        int i = 0;
        while (i < frames.size() && frames.get(i) != null && frames.get(i).getNumber() != page.getNumber()) {
            i++;
        }
        if (i >= frames.size()) return false;
        return frames.get(i) != null;
    }

    public boolean thereAreEmptyFrames() {
        if (frameNumber == frames.size()) return false;
        return true;
    }

    public void servePage(Page page) {
        if (pageIsInMemory(page)) {
            servePageWithoutReplacing(indexOfPageInFrames(page));
        } else {
            replacePage(page);
        }
        oldPageList.add(page);
        pageList.remove(page);
    }

    public int findFrameToReplace() {
        int maxLastUsageTime = frames.get(0).getLastUsageTime();
        int maxPageIndex = 0;
        for (int i = 1; i < frameNumber; i++) {
            if (frames.get(i).getLastUsageTime() > maxLastUsageTime) {
                maxLastUsageTime = frames.get(i).getLastUsageTime();
                maxPageIndex = i;
            }
        }
        return maxPageIndex;
    }

    public void findPageToReplace(Page page) {
        frames.set(findFrameToReplace(), page);
    }

    public int indexOfPageInFrames(Page page) {
        int i = 0;
        while (frames.get(i).getNumber() != page.getNumber()) {
            i++;
        }
        return i;
    }
//frame number to nie jest dlugosc listy!!!

    public void servePageWithoutReplacing(int index) {
        frames.get(index).setLastUsageTime(0);
    }

    public void replacePage(Page page) {
        if (thereAreEmptyFrames()) {
            insertPageInEmptyFrame(page); //zwraca pozycje w ramce jeszcze nie wiem po co
        } else {
            //tylko tu ruszamy bity pozostalych stron
            findPageToReplace(page);
        }
        errorTable[page.getAppealTime()] = 1;
        errorsNumber++;
    }

    public int insertPageInEmptyFrame(Page page) {
        frames.add(page);
        return frames.size();
    }

    public List<Page> getFrames() {
        return frames;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public double getPpf() {
        return ppf;
    }

    public void setPpf(double ppf) {
        this.ppf = ppf;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int[] getErrorTable() {
        return errorTable;
    }

    public int getWssi() {
        return wssi;
    }

    public void setWssi(int wssi) {
        this.wssi = wssi;
    }

    public int getActualDifferentPages() {
        return actualDifferentPages;
    }

    public void setActualDifferentPages(int actualDifferentPages) {
        this.actualDifferentPages = actualDifferentPages;
    }

    public void setFrames(List<Page> frames) {
        this.frames = frames;
    }

    public int getFrameNumberBeforePausing() {
        return frameNumberBeforePausing;
    }

    public void setFrameNumberBeforePausing(int frameNumberBeforePausing) {
        this.frameNumberBeforePausing = frameNumberBeforePausing;
    }

    public int getErrorsNumber() {
        return errorsNumber;
    }

    public void setErrorsNumber(int errorsNumber) {
        this.errorsNumber = errorsNumber;
    }

    public void cleanFrames() {
        List<Page> cleanFrames = new ArrayList<>();
        setFrameNumber(0);
        setFrames(cleanFrames);
    }
    public void increaseFrameData(){
        int i = 0;
        while (i < frames.size()){
            frames.get(i).incrasePage();
            i++;
        }
    }
}

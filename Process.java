import java.util.List;

public class Process {
    private List<Page> pageList;
    private List<Page> oldPageList;
    private int frameNumber;
    private final int pageNumber;

    public Process(List<Page> pageList) {
        this.pageList  = pageList;
        pageNumber = pageList.size();
    }
    public void changeFrameNumber(int newFrameNumber){

    }
}

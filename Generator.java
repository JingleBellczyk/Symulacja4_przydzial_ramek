import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public List<Page> generatePages(int stringLength, int pageRangeLow, int pageRangeHigh,
                                    int frameNumber, int processesNumber, int howOftenLocality, int howManyLocality, double probability) {
        //Page[] pages = new Page[stringLength];
        List<Page> pages = new ArrayList<>();
        int licznik = 0;
        int i = 0;
        while(pages.size()!=stringLength){
            if (ifOccuring(probability)) {
                if (licznik == howOftenLocality) {
                    i = localityGenerator(howManyLocality, pages, frameNumber/(processesNumber + processesNumber/2), stringLength); //tutaj i to appealTime
                    licznik = 0;
                } else {
                    pages.add(generatePage(pageRangeLow, pageRangeHigh,i));
                    licznik++;
                }
            }
            i++;
        }

        return pages;
    }

    public boolean ifOccuring(double probability) {
        Random random = new Random();
        int rand = random.nextInt(100);
        int bound = (int) (probability * 100);
        if (rand < bound) return false;
        return true;
    }

    public int localityGenerator(int howMany, List<Page> pages, int frameNumber, int stringLenght) {
        int i = 0;
        int currentIndex = pages.size();
        while (currentIndex < stringLenght && i < howMany) {
            pages.add(new Page(pages.get(pages.size()-1).getAppealTime()+1, localityPage(pages, currentIndex, frameNumber)));
            currentIndex++;
            i++;
        }
        return pages.get(pages.size()-1).getAppealTime();
    }

    public void printPages(int[] pages) {
        for (int i = 0; i < pages.length; i++) {
            System.out.print(pages[i] + " ");
        }
    }

    public Page generatePage(int pageRangeLow, int pageRangeHigh, int appealTiem) {
        Random random = new Random();
        int pageNumber = random.nextInt(pageRangeHigh - pageRangeLow) + pageRangeLow;
        return new Page(appealTiem,pageNumber);
    }

    public boolean isInFrame(int[] frames, int numberOfPagesInFrame, int number) {
        int i = 0;
        while (i < frames.length && i < numberOfPagesInFrame) {
            if (frames[i] == number) {
                return true;
            }
            i++;
        }
        return false;
    }

    public int localityPage(List<Page> pages, int i, int frameNumber) {
        boolean fullFrames = false;
        int[] frames = new int[frameNumber];

        int index = i - 1;
        int numberOfPagesInFrame = 0;

        while ((index >= 0) && !fullFrames) {
            if (!isInFrame(frames, numberOfPagesInFrame, pages.get(index).getNumber())) {
                frames[numberOfPagesInFrame] = pages.get(index).getNumber();
                numberOfPagesInFrame++;
            }
            if (numberOfPagesInFrame == frameNumber) {
                fullFrames = true;
            }
            index--;
        }
        Random random = new Random();
        int randIndex = random.nextInt(numberOfPagesInFrame);
        return frames[randIndex];
    }
}

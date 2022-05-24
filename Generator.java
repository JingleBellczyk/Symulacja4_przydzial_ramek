import java.util.Random;

public class Generator {
    public Page[] generatePages(int stringLength, int pageRangeLow, int pageRangeHigh,
                               int frameNumber, int howOftenLocality, int howManyLocality, int probability) {
        Page[] pages = new Page[stringLength];
        int licznik = 0;

        for (int i = 0; i < stringLength; i++) {
            if (ifOccuring(probability)) {
                if (licznik == howOftenLocality) {
                    i = localityGenerator(howManyLocality, pages, frameNumber, i) - 1; //tutaj i to appealTime
                    licznik = 0;
                } else {
                    pages[i] = generatePage(pageRangeLow, pageRangeHigh,i);
                    licznik++;
                }
            } else {
                i--;
            }
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

    public int localityGenerator(int howMany, Page[] pages, int frameNumber, int currentIndex) {
        int i = 0;
        while (currentIndex < pages.length && i < howMany) {
            pages[currentIndex] = new Page(currentIndex, localityPage(pages, currentIndex, frameNumber));
            currentIndex++;
            i++;
        }
        return currentIndex;
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

    public int localityPage(Page[] pages, int i, int frameNumber) {
        boolean fullFrames = false;
        int[] frames = new int[frameNumber];

        int index = i - 1;
        int numberOfPagesInFrame = 0;

        while ((index >= 0) && !fullFrames) {
            if (!isInFrame(frames, numberOfPagesInFrame, pages[index].getNumber())) {
                frames[numberOfPagesInFrame] = pages[index].getNumber();
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

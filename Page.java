public class Page {
    private int appealTime;
    private int number;
    private int lastUsageTime;

    public Page(int appealTime, int number) {
        this.appealTime = appealTime;
        this.number = number;
        lastUsageTime = 0;
    }


    public void resetPage(){
        lastUsageTime = 0;
    }

    public void incrasePage(){
        lastUsageTime++;
    }

    public int getAppealTime() {
        return appealTime;
    }

    public void setAppealTime(int appealTime) {
        this.appealTime = appealTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLastUsageTime() {
        return lastUsageTime;
    }

    public void setLastUsageTime(int lastUsageTime) {
        this.lastUsageTime = lastUsageTime;
    }
}

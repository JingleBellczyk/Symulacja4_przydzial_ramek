import java.util.ArrayList;
import java.util.List;

public class Symulations {
    public static void main(String[] args) {
        Symulations symulations = new Symulations();
        //symulations.symulation1();
        symulations.symulation2();
    }
    public void symulation1(){
        Generator generator = new Generator();
        List<Page> pages1 = generator.generatePages(20,0,30,
                15, 3, 5,5,0.5);

        List<Page> pages2 = generator.generatePages(20,30,60,
                15, 3, 10,5,0.3);

        List<Page> pages3 = generator.generatePages(20,0,30,
                15, 3, 7,7,0.4);

        Process p1 = new Process(pages1);
        Process p2 = new Process(pages2);
        Process p3 = new Process(pages3);
        List<Process> processList = new ArrayList<>();

        processList.add(p1);
        processList.add(p2);
        processList.add(p3);


        EqualAllocationAlgorithm eaa = new EqualAllocationAlgorithm(processList,30);
        eaa.serveProcesses();
    }
    public void symulation2(){
        Generator generator = new Generator();
        List<Page> pages1 = generator.generatePages(20,0,30,
                15, 3, 5,5,0.5);

        List<Page> pages2 = generator.generatePages(20,30,60,
                15, 3, 10,10,0.3);

        List<Page> pages3 = generator.generatePages(20,0,30,
                15, 3, 7,7,0.4);

        Process p1 = new Process(pages1);
        Process p2 = new Process(pages2);
        Process p3 = new Process(pages3);
        List<Process> processList = new ArrayList<>();

        processList.add(p1);
        processList.add(p2);
        processList.add(p3);

        ProportionalAlocationAlgorithm paa = new ProportionalAlocationAlgorithm(processList,30);
        paa.serveProcesses();
    }
}

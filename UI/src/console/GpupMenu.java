package console;

import execution.SimulationTask;
import io.Communicator;


import java.util.Scanner;

public class GpupMenu {

   private Communicator communicator;

    public GpupMenu() {

        this.communicator = new ConsloeAppUI();

    }

    public void printMenu()
    {
        System.out.println("Please choose an option:");
        System.out.println(
                         "1. Read system info from file\n" +
                        "2. Display dependency graph info\n"+
                        "3. Display specific target info\n" +
                        "4. Find path between two targets\n"+
                        "5. Run task\n" +
                        "6. Check if target is in a cycle\n" +
                        "7. Exit \n");
    }

    public void executeMenu() {
        int choice;
        printMenu();
        Scanner s = new Scanner(System.in);
        choice = s.nextInt();
        int caseCount = 0;
        while (choice != 7) {
            switch (choice) {
                case 1: // load file
                    String directory = communicator.getFileNameFromUser();
                    try {
                        communicator.LoadFromFile(directory);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println("Please try again");
                        break;
                    }

                    caseCount++;
                    break;

                case 2: // Display dependency graph info
                    if (caseCount >= 1) {
                        communicator.displayGraphInformation();
                    }
                    else System.out.println("Please load file First");
                    break;

                case 3: //Display specific target info
                    if (caseCount >=1) {
                        System.out.println("Please enter name of target, or '#' to go back");
                        String targetName = communicator.getInputFromUser();
                        if (targetName.equals("#")) break;
                        communicator.displayTargetInformation(targetName);
                        break;
                    }

                    else {
                        System.out.println("Please load file First");
                        break;
                    }


                case 4: // Find path between two targets
                    System.out.println("Please enter names of 2 targets, or # to go back");
                    String name1 = communicator.getInputFromUser();
                    if (name1.equals("#")) break;

                    String name2 = communicator.getInputFromUser();
                    if (name2.equals("#")) break;


                    System.out.println("Plese enter dependency between said targets: 'DependsOn' or 'RequiredFor' ");
                    String dependencyType = communicator.getInputFromUser();
                    if (dependencyType.equals("#")) break;


                    communicator.displayPathBetweenTwoTargets(name1,name2,dependencyType);
                    break;

                case 5: // run simulation task
                    if (caseCount >= 1)
                    communicator.runTask();

                    else{
                        System.out.println("Please load file First");
                        break;
                    }

                case 6:
                    //TODO implement cycle algorithm ravid!
                    break;


                case 7:
                    //TODO add on option to save system to file, (could be any type of file) page 19 in GPUP word file
                case 8:
                    System.out.println("Good bye!!");
                    System.exit(0);
                    break;


            }

            printMenu();
            choice = s.nextInt();
        }
    }
}

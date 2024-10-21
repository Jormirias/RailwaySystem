
/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

import java.util.Scanner;

import dataTypes.DimensionConstants;
import dataTypes.Network;
import dataStructures.DoubleList;
import dataStructures.Iterator;

/**
 * Class which holds the main function of the program.
 * Responsible for handling input/output as Strings
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Network network = getNetwork();

        // Delimiter is whitespace by default

        String option = in.next().toUpperCase();
        while (!option.equals("TA")) {
            switch (option) {
                case "IL":
                    insertLine(in, network);
                    break;
                case "RL":
                    removeLine(in, network);
                    break;
                case "CL":
                    consultLine(in, network);
                    break;
                case "CE":
                    consultStation(in, network);
                    break;
                case "IH":
                    insertSchedule(in, network);
                    break;
                case "RH":
                    removeSchedule(in, network);
                    break;
                case "CH":
                    consultSchedules(in, network);
                    break;
                case "LC":
                    consultTrains(in, network);
                    break;
                case "MH":
                    bestSchedule(in, network);
                    break;
                case "TA":
                    System.out.println("Aplicação terminada.");
                    break;
                default:
                    System.out.println("Comando desconhecido.");
                    break;

            }
            option = in.next().toUpperCase();
        }
    }

    private static void insertLine(Scanner in, Network network) {
        String lineName = in.nextLine().trim();

        DoubleList<String> stationNames = new DoubleList<>();
        String stationName = in.nextLine();
        while (!stationName.equals("")) {
            stationNames.addLast(new String(stationName));
            stationName = in.nextLine();
        }

        network.insertLine(lineName, stationNames);
    }

    private static void removeLine(Scanner in, Network network) {
        String lineName = in.nextLine().trim();
        network.removeLine(lineName);
    }

    private static void consultLine(Scanner in, Network network) {
        String lineName = in.nextLine().trim();
        Iterator<String> it = network.getStationNames(lineName);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private static void consultStation(Scanner in, Network network) {
        // TODO implement in second phase
        throw new UnsupportedOperationException("Unimplemented method 'consultStation'");
    }

    private static void insertSchedule(Scanner in, Network network) {
        String lineName = in.nextLine().trim();
        String trainNumber = in.nextLine();
        
        String stationAndTime = in.nextLine();
        DoubleList<String> stationsAndTimes = new DoubleList<>();

        while (!stationAndTime.equals("")) {
            stationsAndTimes.addLast(new String(stationAndTime));
            stationAndTime = in.next();
        }

        network.insertSchedule(lineName, trainNumber, stationsAndTimes);
    }

    private static void removeSchedule(Scanner in, Network network) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSchedule'");
    }

    private static void consultSchedules(Scanner in, Network network) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultSchedules'");
    }

    private static void consultTrains(Scanner in, Network network) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultTrains'");
    }

    private static void bestSchedule(Scanner in, Network network) {
        // TODO implement in second phase
        throw new UnsupportedOperationException("Unimplemented method 'bestSchedule'");
    }



    private static Network getNetwork() {
        return new Network();
    }
}

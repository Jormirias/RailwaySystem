import java.io.*;
import java.util.Scanner;

import dataTypes.*;
import dataTypes.interfaces.*;
import dataTypes.exceptions.*;
import dataStructures.*;

/**
 * Sistema de Suporte de uma Rede Ferroviária
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 * @version 1.0
 *
 * Class which holds the main function of the program.
 * Responsible for handling input/output.
 */
public class Main {

    /**
     * Comandos do sistema (Fase 1)
     */
    private static final String INSERT_LINE = "IL";
    private static final String REMOVE_LINE = "RL";

    private static final String CONSULT_LINE_STATIONS = "CL";
    private static final String CONSULT_STATION_LINES = "CE"; //FASE 2

    private static final String INSERT_TIMETABLE = "IH";
    private static final String REMOVE_TIMETABLE = "RH";

    private static final String CONSULT_LINE_TIMETABLES = "CH";
    private static final String CONSULT_STATION_TRAINS = "LC"; //FASE 2
    private static final String CONSULT_BEST_TIMETABLE = "MH";

    private static final String EXIT = "TA";

    /**
     * Mensagens do sistema
     */
    private static final String INSERT_LINE_OK = "Inserção de linha com sucesso.";
    private static final String INSERT_LINE_ERR = "Linha existente.";
    private static final String REMOVE_LINE_OK = "Remoção de linha com sucesso.";
    private static final String INSERT_TIMETABLE_OK = "Criação de horário com sucesso.";
    private static final String INSERT_TIMETABLE_ERR = "Horário inválido.";
    private static final String REMOVE_TIMETABLE_OK = "Remoção de horário com sucesso.";
    private static final String CONSULT_BEST_TIMETABLE_ERR = "Percurso impossível.";
    private static final String EXIT_OK = "Aplicação terminada.";

    private static final String LINE_NULL = "Linha inexistente.";
    private static final String STATION_NULL = "Estação inexistente.";
    private static final String FIRST_STATION_NULL = "Estação de partida inexistente.";
    private static final String TIMETABLE_NULL = "Horário inexistente.";

    private static final String COMMAND_NULL = "Comando inexistente.";

    /**
     * Serialização
     */
    private static final String DATA_FILE = "networkdatafiletestsave.dat";

    /**
     * Testing purposes!
     * CONSOLE_INPUT: if true, console accepts command line inputs; if false, will load TEST_FILE
     * PERSISTENT: if true, loads saved file; if false, will create a new network
     *
     */
    private static final boolean CONSOLE_INPUT = true;
    private static final boolean PERSISTENT = true;
    private static final String TEST_FILE = "./tests/test15-in.txt";

    /**
     * MAIN
     */
    public static void main(String[] args) throws Exception {

        Network network;
        if(PERSISTENT) {
            network = load();
        } else {
            network = new NetworkClass();
        }

        Scanner in;
        if(CONSOLE_INPUT) {
            in = new Scanner(System.in);
        } else {
            File file = new File(TEST_FILE);
            in = new Scanner(file);
        }

        String cmd = "";
        // Delimiter is whitespace by default

        do {
            if (in.hasNext()) {
                cmd = in.next().toUpperCase();
            } else {
                break;
            }

            switch (cmd) {
                case INSERT_LINE:
                    insertLine(in, network);
                    break;
                case REMOVE_LINE:
                    removeLine(in, network);
                    break;
                case CONSULT_LINE_STATIONS:
                    consultLine(in, network);
                    break;
                case CONSULT_STATION_LINES:
                    consultStation(in, network);
                    break;
                case INSERT_TIMETABLE:
                    insertSchedule(in, network);
                    break;
                case REMOVE_TIMETABLE:
                    removeSchedule(in, network);
                    break;
                case CONSULT_LINE_TIMETABLES:
                    consultSchedules(in, network);
                    break;
                case CONSULT_STATION_TRAINS:
                    consultTrains(in, network);
                    break;
                case CONSULT_BEST_TIMETABLE:
                    bestSchedule(in, network);
                    break;
                case EXIT:
                    System.out.println(EXIT_OK);
                    break;
                default:
                    System.out.println(COMMAND_NULL);
                    break;
            }
        } while (!cmd.equals(EXIT));

        if(PERSISTENT) {
            save(network);
        }
    }

    /**
     * Function to save the Network data.
     */
    private static void save(Network network) {
        try {
            ObjectOutputStream file = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(DATA_FILE)));
            file.writeObject(network);
            file.flush();
            file.close();
            //System.out.println
            //("Serialization file saved.");
        } catch (IOException e) {
            //System.out.println
            //("ERROR" + e.getMessage());
        }
    }

    /**
     * Function to load the Network data.
     * @return the Network that was saved.
     * If no saved data was available, create new Network.
     */
    private static Network load() {
        try {
            ObjectInputStream file = new ObjectInputStream(
                    new BufferedInputStream((new FileInputStream(DATA_FILE))));

            NetworkClass network = (NetworkClass) file.readObject();
            file.close();
            //System.out.println("Serialization file loaded.");
            return network;
        } catch (IOException e) {
            //System.out.println
            //("Non existing serialization file: Creating new Object.");
            return new NetworkClass();
        } catch (ClassNotFoundException e) {
            //System.out.println
            //("Problems with serialization: Creating new Object.");
            return new NetworkClass();
        }

    }
    
    /**
     * Insertion of a line, given a name and a non-empty list of station names. The error situation
     * occurs if a line with the same name already exists in the system. You may assume (i.e., without
     * verification) that the names of stations of a line are all different amongst themselves
     */
    private static void insertLine(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();

            ListInArray<String> stationNames = new ListInArray<>();
            String stationName = in.nextLine();
            while (!stationName.isEmpty()) {
                stationNames.addLast(stationName);
                stationName = in.nextLine();
            }

            network.insertLine(lineName, stationNames);

            System.out.println(INSERT_LINE_OK);
        } catch (LineAlreadyExistsException e) {
            //caso uma linha já exista com este nome
            System.out.println(INSERT_LINE_ERR);
        }
    }

    /**
     * Removal of a line with the given name. All stations on the line that do not belong to another
     * line will be deleted. The error situation applies when the line does not exist in the system.
     */
    private static void removeLine(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            network.removeLine(lineName);
            System.out.println(REMOVE_LINE_OK);
        } catch (NoSuchLineException e) {
            System.out.println(LINE_NULL);
        }
    }

    /**
     * Lists the stations on a given line, if the line exists. The stations must be listed in the order
     * given when inserting the line.
     */
    private static void consultLine(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            Iterator<Station> it = network.getLineStations(lineName);
            while(it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoSuchLineException e) {
            System.out.println(LINE_NULL);
        }
    }

    /**
     * Lists the lines of a given station, if it exists. The list is in lexicographic order.
     */
    private static void consultStation(Scanner in, Network network) {
        try {
            String stationName = in.nextLine().trim();
            Iterator<Entry<String, String>> it = network.getStationLines(stationName);
            while(it.hasNext()) {
                System.out.println(it.next().getValue());
            }
        } catch (NoSuchStationException e) {
            System.out.println(STATION_NULL);
        }
    }

    /**
     * When inserting a schedule, you may assume (i.e., without verification) that train numbers
     *  are unique. You may also assume that a given schedule never overflows onto the next day
     * (i.e., all schedules begin and end on the same day). To insert a schedule, the first station indi-
     * cated (station-name-1) must be one of the two terminal stations of the line in question and so
     * the direction of travel. You can therefore define schedules for either of the two directions of a
     * line. A schedule always has at least two stations. Note that a schedule need not include a stop
     * at every station of the line.
     * A schedule is invalid when its times are not strictly increasing or when overtaking (or simultaneity) is intro-
     * duced in relation to another existing schedule for that direction of the line. 
     */
    private static void insertSchedule(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            String trainNumber = in.nextLine();

            String stationAndTime = in.nextLine();
            ListInArray<String[]> stops = new ListInArray<>();
            while (!stationAndTime.isEmpty()) {
                int whiteSpaceIndex = stationAndTime.lastIndexOf(' ');
                String splitStation = stationAndTime.substring(0, whiteSpaceIndex);
                String splitTime = stationAndTime.substring(whiteSpaceIndex + 1);
                String[] stopAsString = new String[]{splitStation, splitTime};
                stops.addLast(stopAsString);
                stationAndTime = in.nextLine();
            }

            network.insertSchedule(lineName, trainNumber, stops);

            System.out.println(INSERT_TIMETABLE_OK);
        }
        catch (NoSuchLineException e) {
            System.out.println(LINE_NULL);
        }
        catch (InvalidScheduleException e) {
            System.out.println(INSERT_TIMETABLE_ERR);
        }
    }

    /**
     * Removal of a schedule, if the line and schedule exist.
     */
    private static void removeSchedule(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            String stationAndTime = in.nextLine();
            int whiteSpaceIndex = stationAndTime.lastIndexOf(' ');
            String departureStationName = stationAndTime.substring(0, whiteSpaceIndex);
            String timeAsString = stationAndTime.substring(whiteSpaceIndex + 1);
            
            network.removeSchedule(lineName, departureStationName, timeAsString);

            System.out.println(REMOVE_TIMETABLE_OK);
            }
        catch (NoSuchLineException e) {
            System.out.println(LINE_NULL);
        }
        catch (NoSuchScheduleException e) {
            System.out.println(TIMETABLE_NULL);
        }
    }

    /**
     * Lists all the schedules for a given line, if the line exists and the departure station is a terminal
     * station (determining the direction of travel). direction). The list is sorted by departure
     * time.
     */
    private static void consultSchedules(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            String departureStationName = in.nextLine();

            Iterator<Entry<Time, Schedule>> schedulesIt = network.getLineSchedules(lineName, departureStationName);
            while(schedulesIt.hasNext()) {
                Schedule schedule = schedulesIt.next().getValue();
                System.out.println(schedule.getTrainNumber());

                Iterator<Stop> stopsIt = schedule.getStops();
                while(stopsIt.hasNext()) {
                    System.out.println(stopsIt.next());
                }
           }
        }
        catch (NoSuchLineException e) {
            System.out.println(LINE_NULL);
        }
        catch (NoSuchDepartureStationException e) {
            System.out.println(FIRST_STATION_NULL);
        }
    }

    /**
     * Lists all trains that pass by the given station in increasing order of departure time. The listed
     * time is that at which the train passes by the given station. When two trains pass by
     * the station at the same time, the one with the lowest number is listed first.
     */
    private static void consultTrains(Scanner in, Network network) {
        try {
            String stationName = in.nextLine().trim();
            Iterator<Entry<TrainTime, Time>> it = network.getStationRegistrySchedules(stationName);
            if(it != null) {
                while (it.hasNext()) {
                    Entry<TrainTime, Time> entry = it.next();
                    Time stationTime = entry.getValue();
                    Integer train = entry.getKey().getTrain();
                    System.out.println("Comboio " + train + " " + stationTime);
                }
            }
        } catch (NoSuchStationException e) {
            System.out.println(STATION_NULL);
        }
    }

    /**
     * Determines, if possible, the “best” route between two stations (departure and destination)
     * on a given line. In this context, the best route is the one that, without changing trains, arrives
     * at the destination station as close to the expected arrival time as possible (but never later). If a
     * route is found, all stops from the route are printed (not just those between the departure
     * and destination stations).
     * Note that the departure and destination stations may be any two stations of the line. If the
     * destination station does not exist in the line, the schedule is deemed impossible.
     */
    private static void bestSchedule(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            String departureStationName = in.nextLine();
            String arrivalStationName = in.nextLine();
            String timeAsString = in.nextLine();

            Schedule bestSchedule = network.getBestSchedule(lineName, departureStationName, arrivalStationName, timeAsString);
            int trainNumber = bestSchedule.getTrainNumber();
            Iterator<Stop> stopsIt = bestSchedule.getStops();
            System.out.println(trainNumber);
            do {
                System.out.println(stopsIt.next());
            } while (stopsIt.hasNext());

        }
        catch (NoSuchLineException e) {
            System.out.println(LINE_NULL);
        }
        catch (NoSuchDepartureStationException e) {
            System.out.println(FIRST_STATION_NULL);
        }
        catch (ImpossibleRouteException e) {
            System.out.println(CONSULT_BEST_TIMETABLE_ERR);
        }
    }
}

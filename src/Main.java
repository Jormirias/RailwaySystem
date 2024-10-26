import java.io.*;
import java.util.Scanner;

import dataTypes.*;
import dataStructures.*;

/**
 * Sistema de Suporte de uma Rede Ferroviária
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 * @version 1.0
 */

/**
 * Class which holds the main function of the program.
 * Responsible for handling input/output as Strings
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

    private static final String DATA_FILE = "storednetwork.dat";

    // Testing purposes
    private static final boolean CONSOLE_INPUT = true;
    private static final boolean PERSISTANT = true;
    private static final String TEST_FILE = "./tests/test.txt";

    public static void main(String[] args) throws Exception {

        Network network;
        if(PERSISTANT) {
            network = load();
        } else {
            network = new Network();
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
            cmd = in.next().toUpperCase();
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

        if(PERSISTANT) {
            save(network);
        }
    }

    /**
     * Guardar o estado do sistema através de serialização
     */

    private static void save(Network network) {
        try {
            ObjectOutputStream file = new ObjectOutputStream(
                    new FileOutputStream(DATA_FILE));
            file.writeObject(network);
            file.flush();
            file.close();
            System.out.println
            ("Serialization file saved.");
        } catch (IOException e) {
            System.out.println
            ("Problem saving?");
        }
    }

    /**
     * Carregar o estado do sistema através de serialização
     */

    private static Network load() {
        try {
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream(DATA_FILE));
            System.out.println
            ("Serialization file loaded.");

            Network network = (Network) file.readObject();
            file.close();
            return network;
        } catch (IOException e) {
            System.out.println
            ("Non existing serialization file: Creating new Object.");
            return new Network();
        } catch (ClassNotFoundException e) {
            System.out.println
            ("Problems with serialization: Creating new Object.");
            return new Network();
        }

    }

    private static void insertLine(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();

            //Array é mais eficiente que List aqui
            ListInArray<Station> newStations = new ListInArray<>();
            String stationName = in.nextLine();
            while (!stationName.isEmpty()) {
                //Criar logo aqui os objetos Station poupa-nos uma iteração O(n) mais tarde
                newStations.addLast(new Station(stationName));
                stationName = in.nextLine();
            }
            network.insertLine(lineName, newStations);

            System.out.println(INSERT_LINE_OK);
        } catch (dataStructures.IllegalArgumentException e) {
            System.out.println(INSERT_LINE_ERR);
        }
    }

    private static void removeLine(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            network.removeLine(lineName);
            System.out.println(REMOVE_LINE_OK);
        } catch (NoSuchElementException e) {
            System.out.println(LINE_NULL);
        }
    }

    private static void consultLine(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            ListInArray<Station> stations = network.getStationNames(lineName);
            Iterator<Station> it = stations.iterator();
            while(it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoSuchElementException e) {
            System.out.println(LINE_NULL);
        }
    }

    private static void consultStation(Scanner in, Network network) {
        // TODO implement in second phase
        throw new UnsupportedOperationException("Unimplemented method 'consultStation'");
    }

    private static void insertSchedule(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            String trainNumber = in.nextLine();
            
            String stationAndTime = in.nextLine();
            ListInArray<ScheduleStop> stops = new ListInArray<>();
            while (!stationAndTime.isEmpty()) {
                String[] splitStationAndTime = stationAndTime.split(" ");
                ScheduleStop stop = new ScheduleStop(new Station(splitStationAndTime[0]), new Time(splitStationAndTime[1]));
                stops.addLast(stop);
                stationAndTime = in.nextLine();
            }

            network.insertSchedule(lineName, trainNumber, stops);

            System.out.println(INSERT_TIMETABLE_OK);
        }
        catch (NoSuchElementException e) {
            System.out.println(LINE_NULL);
        }
        catch (dataStructures.IllegalArgumentException | NullPointerException e) {
            System.out.println(INSERT_TIMETABLE_ERR);
        }
    }

    private static void removeSchedule(Scanner in, Network network) {
        String lineName = in.nextLine().trim();
        String departureStationName = in.next();
        String timeAsString = in.next();
        in.nextLine(); // ignore next line;

        network.removeSchedule(lineName, departureStationName, timeAsString);

        System.out.println(REMOVE_TIMETABLE_OK);
    }

    private static void consultSchedules(Scanner in, Network network) {
        String lineName = in.nextLine().trim();
        String departureStationName = in.nextLine();

        Iterator<Entry<Time, Schedule>> schedulesIt = network.getLineSchedules(lineName, departureStationName);
        while(schedulesIt.hasNext()) {
            Schedule schedule = schedulesIt.next().getValue();
            System.out.println(schedule.getTrainNumber());

            Iterator<ScheduleStop> stopsIt = schedule.getStops();
            while(stopsIt.hasNext()) {
                System.out.println(stopsIt.next());
            }
        }

    }

    private static void consultTrains(Scanner in, Network network) {
        // TODO implement in second phase
        throw new UnsupportedOperationException("Unimplemented method 'consultTrains'");
    }
    
    private static void bestSchedule(Scanner in, Network network) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bestSchedule'");
    }
}

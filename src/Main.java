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

    public static void main(String[] args) throws Exception {
        Network network = load();
        Scanner in = new Scanner(System.in);
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

        save(network);
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
            //System.out.println
            //("Serialization file saved.");
        } catch (IOException e) {
            //System.out.println
            //("Problem saving?");
        }
    }

    /**
     * Carregar o estado do sistema através de serialização
     */

    private static Network load() {
        try {
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream(DATA_FILE));
            //System.out.println
            //("Serialization file loaded.");
            Network network = (Network) file.readObject();
            file.close();
            return network;
        } catch (IOException e) {
            //System.out.println
            //("Non existing serialization file: Creating new Object.");
            return new Network();
        } catch (ClassNotFoundException e) {
            //System.out.println
            //("Problems with serialization: Creating new Object.");
            return new Network();
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
}

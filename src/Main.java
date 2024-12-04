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

    /**
     * Serialização
     */
    private static final String DATA_FILE = "storednetwork.dat";

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
        if(PERSISTENT || !PERSISTENT) {
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
            //("ERROR" + e.getMessage());
        }
    }

    /**
     * Carregar o estado do sistema através de serialização
     */
    private static Network load() {
        try {
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream(DATA_FILE));

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
     * Lida com o comando IL + line_name + station_names[]
     * Se o nome da linha não existir, cria uma nova linha com esse nome e com as restantes estações inseridas com o comando (assume-se que são sempre inseridas pelo menos 2 estações)
     * Caso o nome da linha seja repetido, imprime a mensagem de erro correspondente
     * ANÁLISE TEMPORAL DO ALGORITMO: É preciso fazer uma iteração da DoubleList de Lines para procurar o lineName;
     * Melhor caso: Complexidade temporal O(1), o primeiro elemento da DoubleList é a linha inserida e o output final é a mensagem de erro.
     * Pior caso: Complexidade temporal O(n), a DoubleList é iterada sem encontrar a linha e então a linha é inserida na DoubleList.
     * Caso esperado: Complexidade temporal O(n), a DoubleList será percorrida até metade em média, o que continua a ser O(n), como o pior caso.
     * GERAÇÃO DE OUTPUT: Complexidade temporal O(1)
     *
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
     * Lida com o comando RL + line_name
     * Se o nome da linha existe, a linha é removida do sistema
     * Se o nome da linha não existir, é apresentada uma mensagem de erro
     * ANÁLISE TEMPORAL DO ALGORITMO: É preciso fazer uma iteração da DoubleList de Lines para procurar o lineName;
     * Melhor caso: Complexidade temporal O(1), o primeiro elemento da DoubleList é a linha inserida e a linha é removida.
     * Pior caso: Complexidade temporal O(n), a DoubleList é iterada sem encontrar a linha e o output final é a mensagem de erro.
     * Caso esperado: Complexidade temporal O(n), a DoubleList será percorrida até metade em média, o que continua a ser O(n), como o pior caso.
     * GERAÇÃO DE OUTPUT: Complexidade temporal O(1)
     *
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
     * Lida com o comando CL + line_name
     * Se o nome da linha existe, a coleção de Stations da Linha é apresentada no output
     * Se o nome da linha não existir, é apresentada uma mensagem de erro
     * ANÁLISE TEMPORAL DO ALGORITMO: É preciso fazer uma iteração da DoubleList de Lines para procurar o lineName;
     * Melhor caso: Complexidade temporal O(1), o primeiro elemento da DoubleList é a linha inserida e a linha é apresentada no outpu.
     * Pior caso: Complexidade temporal O(n), a DoubleList é iterada sem encontrar a linha e o output final é a mensagem de erro.
     * Caso esperado: Complexidade temporal O(n), a DoubleList será percorrida até metade em média, o que continua a ser O(n), como o pior caso.
     * GERAÇÃO DE OUTPUT: Complexidade temporal O(n), sendo que é preciso iterar para apresentar no output todas as Stations
     *
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
     * Lida com o comando CE + station_name
     * Se o nome da estação existe, a coleção de Linhas onde está presente a estação é apresentada ALFABETICAMENTE no output
     * Se o nome da estação não existir, é apresentada uma mensagem de erro
     * ANÁLISE TEMPORAL DO ALGORITMO: É preciso fazer uma 
     * Melhor caso: 
     * Pior caso:
     * Caso esperado:
     * GERAÇÃO DE OUTPUT: 
     *
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
     * Lida com o comando IH + line_name + train_number + schedules[]
     * Se o nome da linha existe e o schedule é válido, cria um novo Schedule com o train number e com as restantes Stops inseridas com o comando (assume-se que train_number dado é sempre único e os Schedules do mesmo dia)
     * Se o nome da linha não existir, é apresentada uma mensagem de erro. (Prioritário sobre Schedule inválido)
     * Se o schedule for inválido é apresentada uma mensagem de erro. (Um horário é inválido se a 1ª estação não for uma das duas terminais; Se tiver estações fora da ordem da Linha; Se não tiver os Times ordenados estritamente crescentes)
     * ANÁLISE TEMPORAL DO ALGORITMO:
     * Melhor caso:
     * Pior caso:
     * Caso esperado:
     * GERAÇÃO DE OUTPUT: Complexidade temporal O(1)
     *
     */
    private static void insertSchedule(Scanner in, Network network) {
        try {
            String lineName = in.nextLine().trim();
            String trainNumber = in.nextLine();

            //criar logo aqui uma coleção das stops a ser inseridas no Schedule, à medida que elas são lidas do input, poupa uma iteração mais tarde
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
     * Lida com o comando RH + line_name + origin_terminal + time
     * Se o nome da linha existe, a estação de origem existe e um Schedule com a hora de partida dada existe, remove esse Schedule
     * Se o nome da linha não existir, é apresentada uma mensagem de erro. (Prioritário sobre Schedule inexistente)
     * (notar que não existir estação dá erro em branco)
     * Se o schedule não existir é apresentada uma outra mensagem de erro.
     * ANÁLISE TEMPORAL DO ALGORITMO:
     * Melhor caso:
     * Pior caso:
     * Caso esperado:
     * GERAÇÃO DE OUTPUT: Complexidade temporal O(1)
     *
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
     * Lida com o comando CH + line_name + origin_terminal
     * Se o nome da linha existe e a estação de origem existe, o output será uma listagem de vários Schedules
     * Se o nome da linha não existir, é apresentada uma mensagem de erro. (Prioritário sobre Station inexistente)
     * Se a Station não existir ou não for terminal da linha, é apresentada uma outra mensagem de erro.
     * ANÁLISE TEMPORAL DO ALGORITMO:
     * Melhor caso:
     * Pior caso:
     * Caso esperado:
     * GERAÇÃO DE OUTPUT:
     *
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
     * SEGUNDA FASE
     *
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
     * Lida com o comando MH + line_name + first_terminal + first_terminal + arriving_time
     * Se o nome da linha existe e pelo menos um Schedule existe com as Stations dadas, o output será uma listagem do melhor schedule para chegar o mais perto da hora esperada
     * Se o nome da linha não existir, é apresentada uma mensagem de erro. (Prioritário 1)
     * Se a Station de partida não existir, é apresentada uma outra mensagem de erro. (Prioritário 2)
     * É ainda apresentado um terceiro erro SE: (Proritário 3)
     *      Se a Station de chegada não existe, OU
     *      Nenhum Schedule passa na duas Stations, OU
     *      Nenhum Schedule chega à Station de chegada ANTES ou NA PRÓPRIA hora de chegada dada
     * ANÁLISE TEMPORAL DO ALGORITMO:
     * Melhor caso:
     * Pior caso:
     * Caso esperado:
     * GERAÇÃO DE OUTPUT:
     *
     */
    private static void bestSchedule(Scanner in, Network network) {
        //ERRO1-Ver se linha existe
        //ERRO2-Ver se Station de partida existe em Stations
        //ERRO3-Ver se Station de chegada existe em Stations (Ver sentidO!) && se passa na Station de partida && se essa station tem Schedules com time <= à hora pedida ( esta 3ª condião vai gaurdando o Time mais próximo
        //Se não há erros, apresentar o melhor Schedule guardado na ultima verificação
        try {
            String lineName = in.nextLine().trim();
            String departureStationName = in.nextLine();
            String arrivalStationName = in.nextLine();
            String timeAsString = in.nextLine();

            Schedule bestSchedule = network.getBestSchedule(lineName, departureStationName, arrivalStationName, timeAsString);
            int trainNumber = bestSchedule.getTrainNumber();
            Iterator<Stop> stopsIt = bestSchedule.getStops();
            System.out.println(trainNumber);
            System.out.println(stopsIt.next());
            while(stopsIt.hasNext()) {
                System.out.println(stopsIt.next());
            }

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

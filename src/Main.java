/**
 * Sistema de Suporte de uma Rede Ferroviária
 * @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
 * @author NOMEALUNO2 (NUMEROALUNO2) emailALUNO2
 * @version 1.0
 */

import java.util.Scanner;

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

    /**
     * Mensagens do sistema
     */

    private static final String INSERT_LINE_OK = "Inserção de linha com sucesso.";
    private static final String INSERT_LINE_ERR = "Linha existente.";
    private static final String REMOVE_LINE_OK = "Remoção de linha com sucesso.";
    private static final String INSERT_TIMETABLE_OK = "Criação de horário com sucesso.";
    private static final String INSERT_TIMETABLE_ERR = "Horário inválido.";
    private static final String INSERT_TIMETABLE_OK = "Remoção de horário com sucesso.";
    private static final String CONSULT_BEST_TIMETABLE_ERR = "Percurso impossível.";
    private static final String EXIT_OK = "Aplicação terminada.";

    private static final String LINE_NULL = "Linha inexistente.";
    private static final String STATION_NULL = "Estação inexistente.";
    private static final String FIRST_STATION_NULL = "Estação de partida inexistente."
    private static final String TIMETABLE_NULL = "Horário inexistente.";

    private static final String DATA_FILE = "storednetwork.dat";

    public static void main(String[] args) {
        RailwayNetwork<Integer> network = load();
        Scanner in = new Scanner(System.in);
        String cmd = "";
        do {
            cmd = in.next().toUpperCase();
            switch (cmd) {
                case INSERT_LINE :
                    insertLine(in, queue);
                default:
                    break;
            }

        } while (!cmd.equals(EXIT));
        save(queue);
        }

    private static void save(RailwayNetwork<Integer> network) {
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

    private static RailwayNetwork<Integer> load() {
        try {
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream(DATA_FILE));
            //System.out.println
            //("Serialization file loaded.");
            RailwayNetwork<Integer> network = (RailwayNetwork<Integer>) file.readObject();
            file.close();
            return network;
        } catch (IOException e) {
            //System.out.println
            //("Non existing serialization file: Creating new Object.");
            return new RailwayNetwork<Integer>();
        } catch (ClassNotFoundException e) {
            //System.out.println
            //("Problems with serialization: Creating new Object.");
            return new RailwayNetwork<Integer>();
        }
    }
}

package vms;

public class Utilities{
    public void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delay(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void designMenu(){
        System.out.println("\r\n" +
                        "  ___ __  __ __  __ _   _ _   _ ___ _____   _  _____ ___ ___  _   _   _____ ____      _    ____ _  _______ ____    __  __ _____ _   _ _   _ \r\n" +
                        " |_ _|  \\/  |  \\/  | | | | \\ | |_ _|__  /  / \\|_   _|_ _/ _ \\| \\ | | |_   _|  _ \\    / \\  / ___| |/ / ____|  _ \\  |  \\/  | ____| \\ | | | | |\r\n" +
                        "  | || |\\/| | |\\/| | | | |  \\| || |  / /  / _ \\ | |  | | | | |  \\| |   | | | |_) |  / _ \\| |   | ' /|  _| | |_) | | |\\/| |  _| |  \\| | | | |\r\n" +
                        "  | || |  | | |  | | |_| | |\\  || | / /_ / ___ \\| |  | | |_| | |\\  |   | | |  _ <  / ___ \\ |___| . \\| |___|  _ <  | |  | | |___| |\\  | |_| |\r\n" +
                        " |___|_|  |_|_|  |_|\\___/|_| \\_|___/____/_/   \\_\\_| |___\\___/|_| \\_|   |_| |_| \\_\\/_/   \\_\\____|_|\\_\\_____|_| \\_\\ |_|  |_|_____|_| \\_|\\___/ \r\n" +
                        "                                                                                                                                            \r\n" +
                        "");
    }
}
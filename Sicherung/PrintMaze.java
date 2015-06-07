/**
 * The type Print maze.
 */
public class PrintMaze {
    /**
     * Print maze.
     *
     * @param map the map
     */
    public static void printMaze(char[][] map) {
        for (char[] ca : map) {
            for (char c : ca) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        MazeGenerator gen = new RecursiveBacktracker();
        char[][] map = gen.generate(25,31);
        printMaze(map);
        System.out.println();
    }
}

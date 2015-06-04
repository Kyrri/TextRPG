/**
 * The interface Maze generator.
 */
public interface MazeGenerator {

    /**
     * The WALLCHAR.
     */
    char WALLCHAR = '#';
    /**
     * The FREECHAR.
     */
    char FREECHAR = '.';
    /**
     * The STARTCHAR.
     */
    char STARTCHAR = 'S';
    /**
     * The BATTLECHAR.
     */
    char BATTLECHAR = 'B';
    /**
     * The SMITHYCHAR.
     */
    char SMITHYCHAR = 'T';
    /**
     * The WELLCHAR.
     */
    char WELLCHAR = 'O';
    /**
     * The GOALCHAR.
     */
    char GOALCHAR = 'Z';
    /**
     * 
     */
    char MERCHANTCHAR = 'H';
    /**
     * 
     */
    char QUESTGIVERCHAR = 'Q';

    /**
     * Generate char [ ] [ ].
     *
     * @param height the height
     * @param width  the width
     * @return the char [ ] [ ]
     */
    char[][] generate(int height, int width);
}

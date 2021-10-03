import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SolverTest {
    @Test
    public void testSolver() throws IOException {
        Solver solver = new Solver(readBoard("/8puzzle.txt"));
        Assert.assertEquals(13, solver.moves());
    }

    private Board readBoard(String fileName) throws IOException {
        List<String> lines = IOUtils.readLines(getClass().getResourceAsStream(fileName), "utf-8");
        int n = Integer.parseInt(lines.get(0).trim());

        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = lines.get(i + 1);
            String[] ints = line.trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                tiles[i][j] = Integer.parseInt(ints[j].trim());
            }
        }
        return new Board(tiles);
    }

}

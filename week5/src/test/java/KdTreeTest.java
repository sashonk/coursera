import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class KdTreeTest {

    @Test
    public void testDraw() throws IOException {
        KdTree set = new KdTree();
        List<String> lines = IOUtils.readLines(getClass().getResourceAsStream("/input1K.txt"), "utf-8") ;
        for (String line : lines) {
            String[] coords = line.split("\\s");
            set.insert(new Point2D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])));
        }

        set.draw();
    }

    @Test
    public void testNearest() throws Exception {
        KdTree set = new KdTree();
        List<String> lines = IOUtils.readLines(getClass().getResourceAsStream("/input100K.txt"), "utf-8") ;
        for (String line : lines) {
            String[] coords = line.split("\\s");
            set.insert(new Point2D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])));
        }
        Assert.assertEquals(set.size(), 100000);

        Point2D expect1 = new Point2D(0.699705, 0.300202);
        Point2D expect2 = new Point2D(0.498659, 0.500257);
        Point2D expect3 = new Point2D(0.001208, 0.900224);

        Point2D actual1 = set.nearest(new Point2D(0.7, 0.3));
        Assert.assertEquals(expect1, actual1);

        Point2D actual2 = set.nearest(new Point2D(0.5, 0.5));
        Assert.assertEquals(expect2, actual2);

        Point2D actual3 = set.nearest(new Point2D(0.0, 0.9));
        Assert.assertEquals(expect3, actual3);
    }

    @Test
    public void testRange() throws Exception {
        KdTree set = new KdTree();
        List<String> lines = IOUtils.readLines(getClass().getResourceAsStream("/input100K.txt"), "utf-8") ;
        for (String line : lines) {
            String[] coords = line.split("\\s");
            set.insert(new Point2D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])));
        }
        Assert.assertEquals(set.size(), 100000);
        {
            List<Point2D> expectedRange1 = getExpectedRange("/range1.txt");
            RectHV rect1 = new RectHV(0.1, 0.45, 0.12, 0.46);
            Set<Point2D> actualList1 = new TreeSet<>((Collection<Point2D>) set.range(rect1)) ;
            Assert.assertArrayEquals(expectedRange1.toArray(), actualList1.toArray());
        }

        {
            List<Point2D> expectedRange2 = getExpectedRange("/range2.txt");
            RectHV rect2 = new RectHV(0, 0.5, 0.04, 0.52);
            Set<Point2D> actualList2 = new TreeSet<>((Collection<Point2D>) set.range(rect2)) ;
            Assert.assertArrayEquals(expectedRange2.toArray(), actualList2.toArray());
        }
    }

    @Test
    public void testContains() throws IOException {
        KdTree set = new KdTree();
        List<String> lines = IOUtils.readLines(getClass().getResourceAsStream("/input1K.txt"), "utf-8") ;
        for (String line : lines) {
            String[] coords = line.split("\\s");
            set.insert(new Point2D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])));
        }

        Assert.assertTrue(set.contains(new Point2D(0.680011, 0.970001)));
        Assert.assertFalse(set.contains(new Point2D(0.680011, 0.970002)));
    }

    private List<Point2D> getExpectedRange(String fileName) throws Exception {
        return IOUtils.readLines(getClass().getResourceAsStream(fileName), "utf-8")
                .stream()
                .map(line -> {
                    String[] coors = line.replace("(", "").replace(")", "").replace(",", "").split("\\s");
                    return new Point2D(Double.parseDouble(coors[0]), Double.parseDouble(coors[1]));
                })
                .collect(Collectors.toList());
    }
}

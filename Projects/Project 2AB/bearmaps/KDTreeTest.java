package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void testKDTree() {
        Random rd = new Random();
        List<Point> p = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            p.add(new Point(rd.nextDouble(), rd.nextDouble()));
        }

        NaivePointSet ps = new NaivePointSet(p);
        Point p0 = ps.nearest(3.0, 4.0);

        KDTree nn = new KDTree(p);
        Point p1 = nn.nearest(3.0, 4.0);

        assertEquals(p0.getX(), p1.getX(), 0.0001);
        assertEquals(p0.getY(), p1.getY(), 0.0001);
    }
}

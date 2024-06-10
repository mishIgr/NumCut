import com.mathsystem.api.graph.GraphFactory;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;


public class MinCutTest {

    @Test
    public void undirectedGraph() throws FileNotFoundException {
        MinCut minCut = new MinCut();
        var graph = GraphFactory.loadGraphFromFile(new File("src/test/resources/undirected_graph.txt"));
        Integer ans = 2;
        assertEquals(minCut.execute(graph), ans);
    }

    @Test
    public void directedGraph() throws FileNotFoundException {
        MinCut minCut = new MinCut();
        var graph = GraphFactory.loadGraphFromFile(new File("src/test/resources/directed_graph.txt"));
        Integer ans = 1;
        assertEquals(minCut.execute(graph), ans);
    }

    @Test
    public void hardUndirectedGraph() throws FileNotFoundException {
        MinCut minCut = new MinCut();
        var graph = GraphFactory.loadGraphFromFile(new File("src/test/resources/hard_undirected_graph.txt"));
        Integer ans = 4;
        assertEquals(minCut.execute(graph), ans);
    }

    @Test
    public void hardDirectedGraph() throws FileNotFoundException {
        MinCut minCut = new MinCut();
        var graph = GraphFactory.loadGraphFromFile(new File("src/test/resources/hard_directed_graph.txt"));
        Integer ans = 2;
        assertEquals(minCut.execute(graph), ans);
    }

    @Test
    public void emptyCut() throws FileNotFoundException {
        MinCut minCut = new MinCut();
        var graph = GraphFactory.loadGraphFromFile(new File("src/test/resources/empty_cut.txt"));
        Integer ans = 0;
        assertEquals(minCut.execute(graph), ans);
    }

}
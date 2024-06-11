import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.graph.repository.Color;

import java.util.*;

public class MinCut implements GraphCharacteristic {
    final Color color = Color.blue;

    private Integer countVertex(Graph graph) {
        Integer counter = 0;

        for (var vertex : graph.getVertices().entrySet())
            if (vertex.getValue().getColor().equals(color))
                counter++;

        return counter;
    }

    private Map<UUID, List<UUID>> getAdjacencyList(Graph graph) {
        Map<UUID, List<UUID>> adjacencyList = new HashMap<>();
        graph.getVertices().keySet().forEach(key -> adjacencyList.put(key, new ArrayList<>()));

        for (Edge edge : graph.getEdges()) {
            UUID key = edge.getFromV();
            UUID value = edge.getToV();
            adjacencyList.get(key).add(value);
            if (graph.getDirectType().equals(GraphType.UNDIRECTED))
                adjacencyList.get(value).add(key);
        }

        return adjacencyList;
    }

    private AbstractMap.SimpleEntry<UUID, UUID> getStartEndVertex(Graph graph) {
        boolean flag_one_vertex = false;
        UUID first_vertex = null;

        for (var vertex : graph.getVertices().entrySet())
            if (vertex.getValue().getColor().equals(color)) {
                if (flag_one_vertex)
                    return new AbstractMap.SimpleEntry<>(first_vertex, vertex.getKey());
                first_vertex = vertex.getKey();
                flag_one_vertex = true;
            }

        return null;
    }

    @Override
    public Integer execute(Graph graph) {
        if (!countVertex(graph).equals(2))
            return -1;

        Map<UUID, List<UUID>> adjacencyList = getAdjacencyList(graph);

        var tmp_simple_empty = getStartEndVertex(graph);
        UUID start = tmp_simple_empty.getKey();
        UUID end = tmp_simple_empty.getValue();
        boolean no_way = false;
        Integer counter = 0;

        while (!no_way) {
            no_way = true;

            Deque<UUID> queue = new ArrayDeque<>();
            queue.offer(start);
            Map<UUID, UUID> way = new HashMap<>();
            graph.getVertices().keySet().forEach(key -> way.put(key, null));
            Set<UUID> visitedVertex = new HashSet<>();
            visitedVertex.add(start);

            while (!queue.isEmpty()) {
                UUID vertex = queue.poll();

                if (vertex.equals(end)) {
                    no_way = false;
                    counter++;
                    break;
                }

                for (UUID neighbor : adjacencyList.get(vertex)) {
                    if (!visitedVertex.contains(neighbor)) {
                        way.put(neighbor, vertex);
                        visitedVertex.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }

            UUID tmpVertex = end;
            while (!tmpVertex.equals(start) && way.get(tmpVertex) != null) {
                adjacencyList.get(way.get(tmpVertex)).remove(tmpVertex);
                if (graph.getDirectType().equals(GraphType.UNDIRECTED))
                    adjacencyList.get(tmpVertex).remove(way.get(tmpVertex));
                tmpVertex = way.get(tmpVertex);
            }
        }

        return counter;
    }
}

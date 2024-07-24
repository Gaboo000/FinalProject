import java.util.*;

public class Algoritmos {

    public static int[][] encontrarRutaRecursiva(Laberinto laberinto, int startX, int startY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[laberinto.getRows()][laberinto.getCols()];
        if (findPathRecursively(laberinto, startX, startY, endX, endY, path, visited)) {
            return path.toArray(new int[path.size()][]);
        }
        return null;
    }

    private static boolean findPathRecursively(Laberinto laberinto, int x, int y, int endX, int endY, List<int[]> path, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= laberinto.getRows() || y >= laberinto.getCols() || laberinto.getCell(x, y) == 1 || visited[x][y]) {
            return false;
        }

        path.add(new int[]{x, y});
        visited[x][y] = true;

        if (x == endX && y == endY) {
            return true;
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            if (findPathRecursively(laberinto, x + dir[0], y + dir[1], endX, endY, path, visited)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static int[][] encontrarRutaDinamica(Laberinto laberinto, int startX, int startY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        Map<String, Boolean> cache = new HashMap<>();
        if (findPathDinamico(laberinto, startX, startY, endX, endY, path, cache)) {
            return path.toArray(new int[path.size()][]);
        }
        return null;
    }

    private static boolean findPathDinamico(Laberinto laberinto, int x, int y, int endX, int endY, List<int[]> path, Map<String, Boolean> cache) {
        if (x < 0 || y < 0 || x >= laberinto.getRows() || y >= laberinto.getCols() || laberinto.getCell(x, y) == 1) {
            return false;
        }

        String key = x + "," + y;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        path.add(new int[]{x, y});

        if (x == endX && y == endY) {
            cache.put(key, true);
            return true;
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            if (findPathDinamico(laberinto, x + dir[0], y + dir[1], endX, endY, path, cache)) {
                cache.put(key, true);
                return true;
            }
        }

        path.remove(path.size() - 1);
        cache.put(key, false);
        return false;
    }

    public static int[][] bfs(Laberinto laberinto, int startX, int startY, int endX, int endY) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        boolean[][] visited = new boolean[laberinto.getRows()][laberinto.getCols()];
        int[][] parent = new int[laberinto.getRows()][laberinto.getCols()];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                return reconstruirRuta(parent, startX, startY, endX, endY);
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (isValidMove(laberinto, newX, newY) && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    parent[newX][newY] = x * laberinto.getCols() + y;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
        return null;
    }

    private static int[][] constructPath(Map<String, int[]> parentMap, int startX, int startY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        int[] current = new int[]{endX, endY};

        while (!Arrays.equals(current, new int[]{startX, startY})) {
            path.add(current);
            current = parentMap.get(current[0] + "," + current[1]);
        }

        path.add(new int[]{startX, startY});
        Collections.reverse(path);
        return path.toArray(new int[path.size()][]);
    }

    public static int[][] dfsIterativo(Laberinto laberinto, int startX, int startY, int endX, int endY) {
        Stack<int[]> stack = new Stack<>();
        Map<String, int[]> parentMap = new HashMap<>();
        stack.push(new int[]{startX, startY});
        boolean[][] visited = new boolean[laberinto.getRows()][laberinto.getCols()];
        visited[startX][startY] = true;

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                return constructPath(parentMap, startX, startY, endX, endY);
            }

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newY >= 0 && newX < laberinto.getRows() && newY < laberinto.getCols() && laberinto.getCell(newX, newY) == 0 && !visited[newX][newY]) {
                    stack.push(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    parentMap.put(newX + "," + newY, current);
                }
            }
        }

        return null;
    }

    private static boolean isValidMove(Laberinto laberinto, int x, int y) {
        return x >= 0 && x < laberinto.getRows() && y >= 0 && y < laberinto.getCols() && laberinto.getCell(x, y) == 0;
    }
    private static int[][] reconstruirRuta(int[][] parent, int startX, int startY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        int x = endX;
        int y = endY;
        while (!(x == startX && y == startY)) {
            path.add(new int[]{x, y});
            int parentIndex = parent[x][y];
            x = parentIndex / parent[0].length;
            y = parentIndex % parent[0].length;
        }
        path.add(new int[]{startX, startY});
        Collections.reverse(path);
        return path.toArray(new int[path.size()][]);
    }
}

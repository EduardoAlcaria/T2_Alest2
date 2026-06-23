import java.io.*;
import java.util.*;

public class CavaloPerdido {

    static final int[][] KNIGHT_MOVES = {
        {-2, -1}, {-2,  1},
        {-1, -2}, {-1,  2},
        { 1, -2}, { 1,  2},
        { 2, -1}, { 2,  1}
    };

    /**
     * BFS para menor número de movimentos do cavalo de C até S.
     * Tabuleiro toroidal: posições fora dos limites são mapeadas via módulo.
     * Retorna -1 se S for inacessível.
     */
    static int bfs(char[][] board, int rows, int cols, int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec) return 0;

        boolean[][] visited = new boolean[rows][cols];
        visited[sr][sc] = true;

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sr, sc, 0});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], dist = cur[2];

            for (int[] move : KNIGHT_MOVES) {
                int nr = ((r + move[0]) % rows + rows) % rows;
                int nc = ((c + move[1]) % cols + cols) % cols;

                if (!visited[nr][nc] && board[nr][nc] != 'x') {
                    if (nr == er && nc == ec) return dist + 1;
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }

        return -1;
    }

    static char[][] parseBoard(String filepath, int[] startOut, int[] endOut) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) lines.add(line);
            }
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] board = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < Math.min(cols, lines.get(r).length()); c++) {
                board[r][c] = lines.get(r).charAt(c);
                if (board[r][c] == 'C') { startOut[0] = r; startOut[1] = c; }
                if (board[r][c] == 'S') { endOut[0]   = r; endOut[1]   = c; }
            }
        }

        return board;
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "CasosT2/";
        String[] files = {"020.txt", "060.txt", "120.txt", "200.txt", "300.txt", "400.txt", "500.txt"};

        System.out.println("============================================================");
        System.out.println("  O CAVALO PERDIDO — Resultados dos Casos de Teste");
        System.out.println("============================================================\n");

        for (String filename : files) {
            int[] start = new int[2], end = new int[2];
            char[][] board = parseBoard(baseDir + filename, start, end);
            int rows = board.length;
            int cols = board[0].length;

            System.out.printf("Tabuleiro %s (%dx%d)%n", filename, rows, cols);
            System.out.printf("  C=(%d,%d)  S=(%d,%d)%n", start[0], start[1], end[0], end[1]);

            long t0 = System.currentTimeMillis();
            int result = bfs(board, rows, cols, start[0], start[1], end[0], end[1]);
            long elapsed = System.currentTimeMillis() - t0;

            if (result == -1) {
                System.out.printf("  Resultado : Sem solucao possivel%n");
            } else {
                System.out.printf("  Resultado : %d movimentos%n", result);
            }
            System.out.printf("  Tempo BFS : %d ms%n%n", elapsed);
        }
    }
}

import java.io.*;
import java.util.*;

public class CavaloPerdido {

    // os 8 movimentos em L do cavalo (linha, coluna)
    static int[][] movimentos = {
        {-2, -1}, {-2, 1},
        {-1, -2}, {-1, 2},
        {1, -2},  {1, 2},
        {2, -1},  {2, 1}
    };

    // BFS pra achar o menor numero de pulos do C ate o S.
    // tabuleiro toroidal entao quando passa da borda volta pelo outro lado (modulo).
    // devolve -1 se nao tem como chegar.
    static int bfs(char[][] tab, int linhas, int colunas, int cr, int cc, int sr, int sc) {
        if (cr == sr && cc == sc) return 0; // ja comeca na saida

        boolean[][] visitado = new boolean[linhas][colunas];
        visitado[cr][cc] = true;

        ArrayDeque<int[]> fila = new ArrayDeque<>();
        fila.add(new int[]{cr, cc, 0});

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int r = atual[0];
            int c = atual[1];
            int dist = atual[2];

            for (int[] m : movimentos) {
                // modulo duplo pra dar conta de valor negativo (quando sai por cima/esquerda)
                int nr = ((r + m[0]) % linhas + linhas) % linhas;
                int nc = ((c + m[1]) % colunas + colunas) % colunas;

                if (!visitado[nr][nc] && tab[nr][nc] != 'x') {
                    // ja confere aqui pra nao precisar processar a fila inteira
                    if (nr == sr && nc == sc) return dist + 1;

                    visitado[nr][nc] = true;
                    fila.add(new int[]{nr, nc, dist + 1});
                }
            }
        }

        return -1; // nao achou caminho
    }

    // le o arquivo, monta a matriz e ja guarda onde estao o C e o S
    static char[][] lerTabuleiro(String caminho, int[] c, int[] s) throws IOException {
        ArrayList<String> linhas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        while ((linha = br.readLine()) != null) {
            if (linha.length() > 0)
                linhas.add(linha);
        }
        br.close();

        int nLinhas = linhas.size();
        int nColunas = linhas.get(0).length();
        char[][] tab = new char[nLinhas][nColunas];

        for (int i = 0; i < nLinhas; i++) {
            String l = linhas.get(i);
            for (int j = 0; j < l.length(); j++) {
                tab[i][j] = l.charAt(j);
                if (tab[i][j] == 'C') { c[0] = i; c[1] = j; }
                if (tab[i][j] == 'S') { s[0] = i; s[1] = j; }
            }
        }

        return tab;
    }

    public static void main(String[] args) throws IOException {
        String pasta = "CasosT2/";
        String[] arquivos = {"020.txt", "060.txt", "120.txt", "200.txt", "300.txt", "400.txt", "500.txt"};

        System.out.println("== O Cavalo Perdido - resultados ==\n");

        for (String nome : arquivos) {
            int[] c = new int[2];
            int[] s = new int[2];
            char[][] tab = lerTabuleiro(pasta + nome, c, s);
            int linhas = tab.length;
            int colunas = tab[0].length;

            long inicio = System.currentTimeMillis();
            int res = bfs(tab, linhas, colunas, c[0], c[1], s[0], s[1]);
            long tempo = System.currentTimeMillis() - inicio;

            System.out.println("Tabuleiro " + nome + " (" + linhas + "x" + colunas + ")");
            System.out.println("  C=(" + c[0] + "," + c[1] + ")  S=(" + s[0] + "," + s[1] + ")");
            if (res == -1)
                System.out.println("  sem solucao");
            else
                System.out.println("  " + res + " movimentos");
            System.out.println("  tempo: " + tempo + " ms\n");
        }
    }
}

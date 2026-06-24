from collections import deque
import time

MOVIMENTOS = [(-2,-1),(-2,1),(-1,-2),(-1,2),(1,-2),(1,2),(2,-1),(2,1)]

def resolver(tabuleiro):
    linhas = len(tabuleiro)
    colunas = len(tabuleiro[0])

    inicio = saida = None
    for r in range(linhas):
        for c in range(colunas):
            if tabuleiro[r][c] == 'C':
                inicio = (r, c)
            elif tabuleiro[r][c] == 'S':
                saida = (r, c)

    if inicio == saida:
        return 0

    visitado = [[False] * colunas for _ in range(linhas)]
    visitado[inicio[0]][inicio[1]] = True
    fila = deque([(inicio[0], inicio[1], 0)])

    while fila:
        r, c, dist = fila.popleft()
        for dr, dc in MOVIMENTOS:
            nr = (r + dr) % linhas
            nc = (c + dc) % colunas
            if visitado[nr][nc] or tabuleiro[nr][nc] == 'x':
                continue
            if (nr, nc) == saida:
                return dist + 1
            visitado[nr][nc] = True
            fila.append((nr, nc, dist + 1))

    return -1

def ler_tabuleiro(caminho):
    with open(caminho) as f:
        return [linha.rstrip('\n') for linha in f if linha.strip()]

def main():
    arquivos = ['020.txt', '060.txt', '120.txt', '200.txt', '300.txt', '400.txt', '500.txt']

    print("== O Cavalo Perdido - resultados ==\n")

    for nome in arquivos:
        tabuleiro = ler_tabuleiro(f'CasosT2/{nome}')
        linhas, colunas = len(tabuleiro), len(tabuleiro[0])

        t0 = time.perf_counter()
        resultado = resolver(tabuleiro)
        tempo = (time.perf_counter() - t0) * 1000

        print(f'Tabuleiro {nome} ({linhas}x{colunas})')
        if resultado == -1:
            print('  sem solucao')
        else:
            print(f'  {resultado} movimentos')
        print(f'  tempo: {tempo:.1f} ms\n')

if __name__ == '__main__':
    main()

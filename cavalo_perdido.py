from collections import deque
import sys
import time

MOVIMENTOS = [(-2,-1),(-2,1),(-1,-2),(-1,2),(1,-2),(1,2),(2,-1),(2,1)]

def resolver(tabuleiro):
    linhas = len(tabuleiro)
    colunas = min(len(r) for r in tabuleiro)

    inicio = saida = None
    for r in range(linhas):
        for c in range(len(tabuleiro[r])):
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
    with open(caminho, encoding='utf-8-sig') as f:
        return [linha.rstrip() for linha in f if linha.strip()]

def ler_stdin():
    linhas = []
    for linha in sys.stdin:
        linha = linha.replace('﻿', '').rstrip()
        if linha.strip():
            linhas.append(linha)
    return linhas

def rodar(tabuleiro, nome):
    linhas, colunas = len(tabuleiro), len(tabuleiro[0])
    t0 = time.perf_counter()
    resultado = resolver(tabuleiro)
    tempo = (time.perf_counter() - t0) * 1000
    print(f'Tabuleiro {nome} ({linhas}x{colunas})')
    print(f'  {"sem solucao" if resultado == -1 else str(resultado) + " movimentos"}')
    print(f'  tempo: {tempo:.1f} ms\n')

def main():
    if len(sys.argv) > 1:
        print("== O Cavalo Perdido - resultados ==\n")
        for caminho in sys.argv[1:]:
            rodar(ler_tabuleiro(caminho), caminho)
    else:
        tabuleiro = ler_stdin()
        resultado = resolver(tabuleiro)
        print(resultado if resultado != -1 else "sem solucao")

if __name__ == '__main__':
    main()

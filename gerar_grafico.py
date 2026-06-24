import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import numpy as np

tamanhos = [20, 60, 120, 200, 300, 400, 500]
tempos   = [0.1, 1.8, 2.1, 22.9, 41.6, 31.2, 154.3]
movimentos = [4, 15, 14, 51, 68, 52, 123]
rotulos  = ['020', '060', '120', '200', '300', '400', '500']

fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(10, 4))

# --- tempo de execução ---
n2 = [t**2 for t in tamanhos]
escala = tempos[-1] / n2[-1]
curva = [v * escala for v in n2]

ax1.plot(tamanhos, tempos, 'o-', color='steelblue', linewidth=2,
         markersize=6, label='Medido')
ax1.plot(tamanhos, curva, '--', color='gray', linewidth=1.2,
         label=r'Referência $O(N^2)$')
ax1.set_xlabel('Dimensão N (tabuleiro N×N)')
ax1.set_ylabel('Tempo (ms)')
ax1.set_title('Tempo de execução')
ax1.legend()
ax1.grid(True, linestyle='--', alpha=0.4)
ax1.set_xticks(tamanhos)

# --- movimentos mínimos ---
x = np.arange(len(rotulos))
bars = ax2.bar(x, movimentos, color='steelblue', width=0.55)
ax2.set_xlabel('Caso de teste')
ax2.set_ylabel('Movimentos mínimos')
ax2.set_title('Movimentos mínimos por caso')
ax2.set_xticks(x)
ax2.set_xticklabels(rotulos)
ax2.grid(True, axis='y', linestyle='--', alpha=0.4)
for bar, val in zip(bars, movimentos):
    ax2.text(bar.get_x() + bar.get_width() / 2, bar.get_height() + 1,
             str(val), ha='center', va='bottom', fontsize=9)

plt.tight_layout()
plt.savefig('performance.png', dpi=150, bbox_inches='tight')
print('performance.png gerado')

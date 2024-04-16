package org.mps.crossover;

import java.util.Random;

import org.mps.EvolutionaryAlgorithmException;

/**
 * La clase OnePointCrossover implementa el operador de cruce de un punto en
 * algoritmos evolutivos.
 * Este operador de cruce simula la recombinación genética entre dos padres para
 * producir descendientes con nuevas combinaciones de características.
 *
 * En el cruce de un punto, se selecciona aleatoriamente un punto de corte en
 * las soluciones de los padres. Luego, se intercambian los segmentos de las
 * soluciones a ambos lados del punto de corte para producir dos descendientes.
 *
 * Por ejemplo, si el punto de corte está en la posición k, los descendientes
 * tendrán los segmentos [padre1[0] ... padre1[k-1], padre2[k] ... padre2[n-1]]
 * y [padre2[0] ... padre2[k-1], padre1[k] ... padre1[n-1]], donde n es la
 * longitud de las soluciones de los padres.
 *
 * El operador de cruce de un punto es uno de los métodos más simples y comunes
 * utilizados en algoritmos evolutivos. A pesar de su simplicidad, puede ser
 * efectivo para explorar el espacio de búsqueda y generar diversidad en la
 * población de descendientes.
 */
public class OnePointCrossover implements CrossoverOperator {
    private Random random;

    public OnePointCrossover() {
        this.random = new Random();
    }

    @Override
    public int[][] crossover(int[] parent1, int[] parent2) throws EvolutionaryAlgorithmException {
        int[][] offspring = null;
        if (parent1 != null && parent2 != null && parent1.length > 0 && parent1.length == parent2.length) {
            offspring = new int[2][parent1.length];
            int crossoverPoint = random.nextInt(parent1.length - 1) + 1; // Punto de cruce aleatorio
            // Realizar el cruce en el punto seleccionado
            for (int i = 0; i < crossoverPoint; i++) {
                offspring[0][i] = parent1[i];
                offspring[1][i] = parent2[i];
            }
            for (int i = crossoverPoint; i < parent1.length; i++) {
                offspring[0][i] = parent2[i];
                offspring[1][i] = parent1[i];
            }
        } else {
            throw new EvolutionaryAlgorithmException("No se ha podido realizar el cruce");
        }
        return offspring;
    }
}

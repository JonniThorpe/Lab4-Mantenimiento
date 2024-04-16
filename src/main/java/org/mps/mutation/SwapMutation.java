package org.mps.mutation;

import java.util.Random;

import org.mps.EvolutionaryAlgorithmException;

/**
 * La clase SwapMutation implementa el operador de mutación de intercambio en
 * algoritmos evolutivos.
 * Este operador de mutación simula el proceso biológico de recombinación
 * genética, donde se intercambian aleatoriamente dos valores en una solución
 * candidata para generar una nueva solución.
 *
 * En la mutación de intercambio, se eligen aleatoriamente dos posiciones en la
 * solución y se intercambian los valores en esas posiciones. Esto introduce
 * variabilidad en la solución sin cambiar su longitud o estructura básica.
 *
 * El operador de mutación de intercambio es simple pero efectivo para explorar
 * el espacio de búsqueda y generar diversidad en la población de soluciones.
 * Ayuda a evitar la convergencia prematura hacia óptimos locales y permite la
 * exploración de nuevas regiones del espacio de búsqueda.
 *
 * La clase SwapMutation implementa el contrato de la interfaz MutationOperator
 * y proporciona una implementación concreta del operador de mutación de
 * intercambio. Puedes utilizar esta clase para introducir mutación de
 * intercambio en tus algoritmos evolutivos y personalizarla según las
 * necesidades específicas del problema que estás abordando.
 */

public class SwapMutation implements MutationOperator {
    private Random random;

    public SwapMutation() {
        this.random = new Random();
    }

    @Override
    public int[] mutate(int[] individual) throws EvolutionaryAlgorithmException {
        int[] mutatedIndividual;
        if (individual != null && individual.length > 0) {
            mutatedIndividual = individual.clone();
            int index1 = random.nextInt(individual.length);
            int index2 = random.nextInt(individual.length);
            // Intercambiar dos elementos en la posición index1 y index2
            int temp = mutatedIndividual[index1];
            mutatedIndividual[index1] = mutatedIndividual[index2];
            mutatedIndividual[index2] = temp;
        } else {
            throw new EvolutionaryAlgorithmException("No se puede realizar la mutación");
        }
        return mutatedIndividual;
    }
}
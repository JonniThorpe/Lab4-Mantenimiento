package org.mps;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;
import org.mps.mutation.MutationOperator;
import org.mps.mutation.SwapMutation;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.HashSet;

public class EvolutionaryAlgorithmTest {

    static EvolutionaryAlgorithm ev;
    static SelectionOperator SO;
    static MutationOperator MO;
    static CrossoverOperator CO;
    static int[][] matrix;

    @BeforeAll
    public static void setup() throws EvolutionaryAlgorithmException {
        SO = new TournamentSelection(5);
        MO = new SwapMutation();
        CO = new OnePointCrossover();
        ev = new EvolutionaryAlgorithm(SO, MO, CO);

        // incializa array
        matrix = new int[][] {
                { 12, 42, 17, 32, 49 },
                { 14, 50, 38, 29, 10 },
                { 46, 38, 27, 4, 11 },
                { 2, 23, 17, 18, 46 },
                { 25, 40, 14, 22, 3 },
                { 7, 21, 33, 48, 19 },
                { 35, 8, 41, 13, 16 },
                { 9, 37, 31, 6, 28 },
                { 45, 26, 44, 15, 30 },
                { 1, 47, 20, 34, 36 }
        };
    }

    @Test
    public void evolutionaryAlgorithm_withNullEverything_shouldThrowException() {
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, null, null));
    }

    @Test
    public void evolutionaryAlgorithm_withNullSelectionOperator_shouldThrowException() {
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, MO, CO));
    }

    @Test
    public void evolutionaryAlgorithm_withNullMutationOperator_shouldThrowException() {
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(SO, null, CO));
    }

    @Test
    public void evolutionaryAlgorithm_withNullCrossoverOperator_shouldThrowException() {
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(SO, MO, null));
    }

    // TODO UNICA FORMA ES COMPROBAR TAMAÑOS DEL ARRAY COMO TEST
    @Test
    public void optimize_ifAlgorithmIsEmpty_shouldThrowEvolutionaryAlgorithmException() {
        int[][] algoritmo = new int[][] {};
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> ev.optimize(algoritmo));
    }

    @Test
    public void optimize_ifAlgorithmIsNull_shouldThrowEvolutionaryAlgorithmException() {
        int[][] algoritmo = null;
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> ev.optimize(algoritmo));
    }

    @Test
    public void select_withNullPopulation_shouldThrowEvolutionaryAlgorithmException() {
        assertThrows(EvolutionaryAlgorithmException.class, () -> SO.select(null));
    }

    @Test
    public void select_withEmptyPopulation_shouldThrowEvolutionaryAlgorithmException() {
        int[] population = new int[0];
        assertThrows(EvolutionaryAlgorithmException.class, () -> SO.select(population));
    }

    @Test
    public void select_withValidPopulation_shouldSelectCorrectly() throws EvolutionaryAlgorithmException {
        int[] population = { 1, 2 };
        int[] selected = SO.select(population);
        assertEquals(2, selected.length);
        assertTrue(selected[0] == 1 || selected[0] == 2);
        assertTrue(selected[1] == 1 || selected[1] == 2);
    }

    @Test
    public void select_withValidPopulationMaximumTournamentSize_shouldSelectCorrectly() throws EvolutionaryAlgorithmException {
        int[] population = { 2, 6, 9, 4, 2 };
        int[] selected = SO.select(population);

        assertEquals(population.length, selected.length);

        for (int i = 0; i < selected.length; i++) {
            boolean found = false;
            for (int j = 0; j < population.length; j++) {
                if (selected[i] == population[j]) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    // Tests para el método crossover
    @Test
    public void crossover_withNullParents_shouldThrowEvolutionaryAlgorithmException() {
        int[] parent1 = null;
        int[] parent2 = null;

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            CO.crossover(parent1, parent2);
        });
    }

    @Test
    public void crossover_withOneNullParent_shouldThrowEvolutionaryAlgorithmException() {
        int[] parent1 = { 1, 2, 3 };
        int[] parent2 = null;

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            CO.crossover(parent1, parent2);
        });
    }

    @Test
    public void crossover_withUnequalLengthParents_shouldThrowEvolutionaryAlgorithmException() {
        int[] parent1 = { 1, 2, 3 };
        int[] parent2 = { 4, 5 };

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            CO.crossover(parent1, parent2);
        });
    }

    @Test
    public void crossover_withEmptyParents_shouldThrowEvolutionaryAlgorithmException() {
        int[] parent1 = {};
        int[] parent2 = {};

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            CO.crossover(parent1, parent2);
        });
    }

    @Test
    public void crossover_withValidParents_shouldDoCrossover() throws EvolutionaryAlgorithmException {
        int[] parent1 = { 1, 2, 3, 4, 5 };
        int[] parent2 = { 6, 7, 8, 9, 10 };

        int[][] offspring = CO.crossover(parent1, parent2);

        assertEquals(parent1.length, offspring[0].length);
        assertEquals(parent2.length, offspring[1].length);

        boolean hasSwitched = false;
        for (int i = 0; i < parent1.length - 1; i++) {
            if (offspring[0][i] != parent1[i] || offspring[1][i] != parent2[i]) {
                hasSwitched = true;
                break;
            }
        }
        assertTrue(hasSwitched);
    }

    // Tests para método mutate
    @Test
    public void mutate_withNullIndividual_shouldThrowEvolutionaryAlgorithmException() {
        int[] individual = null;
        assertThrows(EvolutionaryAlgorithmException.class, () -> MO.mutate(individual));
    }

    @Test
    public void mutate_withEmptyIndividual_shouldThrowEvolutionaryAlgorithmException() {
        int[] individual = {};
        assertThrows(EvolutionaryAlgorithmException.class, () -> MO.mutate(individual));
    }

    @Test
    public void mutate_withValidIndividual_shouldSwap() throws EvolutionaryAlgorithmException {
        int[] individual = { 1, 2, 5, 3 };

        int[] mutatedIndividual = MO.mutate(individual);

        assertEquals(individual.length, mutatedIndividual.length);

        int swapCount = 0;
        for (int i = 0; i < individual.length; i++) {
            if (individual[i] != mutatedIndividual[i]) {
                swapCount++;
            }
        }

        // Verificamos que se han cambiado de posición como mucho dos "genes"
        // (debemos tener en cuenta la posibilidad de que aleatoriamente los genes se
        // coloquen en la misma posición de nuevo)
        assertTrue(swapCount <= 2);
    }

    // Tests para método optimize
    @Test
    public void optimize_withNullPopulation_shouldThrowEvolutionaryAlgorithmException() {
        int[][] population = null;
        assertThrows(EvolutionaryAlgorithmException.class, () -> ev.optimize(population));
    }

    @Test
    public void optimize_withEmptyPopulation_shouldThrowEvolutionaryAlgorithmException() {
        int[][] population = {};
        assertThrows(EvolutionaryAlgorithmException.class, () -> ev.optimize(population));
    }

    @Test
    public void optimize_withNullIndividualsInPopulation_shouldThrowEvolutionaryAlgorithmException() {
        int[][] population = { null, { 4, 5 } };
        assertThrows(EvolutionaryAlgorithmException.class, () -> ev.optimize(population));
    }

    @Test
    public void optimize_withEmptyIndividualsInPopulation_shouldThrowEvolutionaryAlgorithmException() {
        int[][] population = { {}, { 4, 5 } };
        assertThrows(EvolutionaryAlgorithmException.class, () -> ev.optimize(population));
    }

    @Test
    public void optimize_withDifferentLengthInIndividuals_shouldThrowEvolutionaryAlgorithmException() {
        int[][] population = { { 1 }, { 4, 5 } };
        assertThrows(EvolutionaryAlgorithmException.class, () -> ev.optimize(population));
    }

    @Test
    public void optimize_withBetterOffspring_shouldReplacePopulation() throws EvolutionaryAlgorithmException {
        int[][] population = { { 12, 42, 17, 32, 49 },
                { 14, 50, 38, 29, 10 } };
        int[][] population2 = { { 12, 42, 17, 32, 49 },
                { 14, 50, 38, 29, 10 } };
        int[][] optimizedPopulation = ev.optimize(population);

        assertNotEquals(population2, optimizedPopulation);
        System.out.println("Array sin optimize");
        System.out.println("");
        printMatrix(population2);
        System.out.println("");
        System.out.println("Array optimizado");
        System.out.println("");
        printMatrix(optimizedPopulation);
        System.out.println("");

        population = matrix;
        population2 = new int[][] {
                { 12, 42, 17, 32, 49 },
                { 14, 50, 38, 29, 10 },
                { 46, 38, 27, 4, 11 },
                { 2, 23, 17, 18, 46 },
                { 25, 40, 14, 22, 3 },
                { 7, 21, 33, 48, 19 },
                { 35, 8, 41, 13, 16 },
                { 9, 37, 31, 6, 28 },
                { 45, 26, 44, 15, 30 },
                { 1, 47, 20, 34, 36 }
        };
        optimizedPopulation = ev.optimize(population);

        assertNotEquals(population2, optimizedPopulation);
        System.out.println("Array sin optimize");
        System.out.println("");
        printMatrix(population2);
        System.out.println("");
        System.out.println("Array optimizado");
        System.out.println("");
        printMatrix(optimizedPopulation);
        System.out.println("");
    }

    @Test
    public void optimize_withWorseOrSameOffspring_shouldNotReplacePopulation() throws EvolutionaryAlgorithmException {
        int[][] population = { { 5, 5, 5 },
                { 5, 5, 5 },
                { 5, 5, 5 },
                { 5, 5, 5 } };
        int[][] population2 = { { 5, 5, 5 },
                { 5, 5, 5 },
                { 5, 5, 5 },
                { 5, 5, 5 } };
        int[][] optimizedPopulation = ev.optimize(population);

        for(int i=0; i<population2.length; i++){
            assertArrayEquals(population2[i], optimizedPopulation[i]);
        }
    }

    @Test
    public void select_ifSelectionIsEmpty_shouldThrowEvolutionaryAlgorithmException()
            throws EvolutionaryAlgorithmException {
        assertThrows(EvolutionaryAlgorithmException.class, () -> SO.select(new int[] {}));
    }

    @Test
    public void tournamentSelection_ifSelectionIsEmpty_shouldThrowEvolutionaryAlgorithmException() {
        assertThrows(EvolutionaryAlgorithmException.class, () -> SO = new TournamentSelection(0));
    }

    @Test
    public void mutate_ifMutateIsEmpty_shouldThrowEvolutionaryAlgorithmException() {
        assertThrows(EvolutionaryAlgorithmException.class, () -> MO.mutate(new int[] {}));
    }

    @Test
    public void crossover_ifCrossoverIsEmpty_shouldThrowEvolutionaryAlgorithmException() {
        assertThrows(EvolutionaryAlgorithmException.class, () -> CO.crossover(new int[] {}, new int[] {}));
    }

    @Test
    public void setSelectionOperator_withValidInput_shouldSetSelectionOperator() {
        ev.setSelectionOperator(SO);
        assertEquals(ev.getSelectionOperator(), SO);
    }

    @Test
    public void setCrossoverOperator_withValidInput_shouldSetCrossoverOperator() {
        ev.setCrossoverOperator(CO);
        assertEquals(ev.getCrossoverOperator(), CO);
    }

    @Test
    public void setMutationOperator_withValidInput_shouldSetMutationOperator() {
        ev.setMutationOperator(MO);
        assertEquals(ev.getMutationOperator(), MO);
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

}

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
import static org.mockito.Mockito.*;

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
        ev = new EvolutionaryAlgorithm(SO,MO,CO);

        //incializa array
        matrix = new int[][]{
                {12, 42, 17, 32, 49},
                {14, 50, 38, 29, 10},
                {46, 38, 27, 4, 11},
                {2, 23, 17, 18, 46},
                {25, 40, 14, 22, 3},
                {7, 21, 33, 48, 19},
                {35, 8, 41, 13, 16},
                {9, 37, 31, 6, 28},
                {45, 26, 44, 15, 30},
                {1, 47, 20, 34, 36}
        };
    }

    @Test
    public void evoAlgorithm_ShouldHaveNull_In_Constructor(){
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, null, null));
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, MO, CO));
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(SO, null, CO));
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(SO, MO, null));

    }

    //TODO UNICA FORMA ES COMPROBAR TAMAÑOS DEL ARRAY COMO TEST
    @Test
    public void Optimize_ShouldReturnException_if_Algorithm_is_Empty(){
        int [][] algoritmo = new int[][] {};
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> ev.optimize(algoritmo));

    }
    @Test
    public void Optimize_ShouldReturnException_if_Algorithm_is_Null(){
        int [][] algoritmo = null;
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> ev.optimize(algoritmo));

    }
    @Test
    public void Tournament_ShouldReturnException_if_SelectIsEmpty() throws EvolutionaryAlgorithmException {
        assertThrows(EvolutionaryAlgorithmException.class, ()->SO.select(new int[]{}));
        assertThrows(EvolutionaryAlgorithmException.class, ()->SO = new TournamentSelection(0));
        ;
    }
    @Test
    public void Swap_ShouldReturnException_if_MutateIsEmpty() {
        assertThrows(EvolutionaryAlgorithmException.class, ()->MO.mutate(new int[]{}));
    }
    @Test
    public void CrossOver_ShouldReturnException_if_CrossoverIsEmpty() {
        assertThrows(EvolutionaryAlgorithmException.class, ()->CO.crossover(new int[]{},new int[]{}));

    }

    @Test
    public void Optimize_ShouldReturn_TheSame_Matrix() throws EvolutionaryAlgorithmException {

        int [][] algoritmoMejorado = ev.optimize(matrix);
        printMatrix(matrix);
        System.out.println("");
        System.out.println("");
        printMatrix(algoritmoMejorado);
        System.out.println(algoritmoMejorado);

        assertEquals(matrix,algoritmoMejorado);

    }
    @Test
    public void getter_FromEvolutionary(){
        ev.setSelectionOperator(SO);
        assertEquals(ev.getSelectionOperator(),SO);

        ev.setCrossoverOperator(CO);
        assertEquals(ev.getCrossoverOperator(),CO);

        ev.setMutationOperator(MO);
        assertEquals(ev.getMutationOperator(),MO);
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

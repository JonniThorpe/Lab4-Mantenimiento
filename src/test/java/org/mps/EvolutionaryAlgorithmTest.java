package org.mps;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;
import org.mps.mutation.MutationOperator;
import org.mps.mutation.SwapMutation;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionaryAlgorithmTest {

    static EvolutionaryAlgorithm ev;

    @BeforeAll
    public static void setup() throws EvolutionaryAlgorithmException {
        SelectionOperator SO = new TournamentSelection(12);
        MutationOperator MO = new SwapMutation();
        CrossoverOperator CO = new OnePointCrossover();
        ev = new EvolutionaryAlgorithm(SO,MO,CO);
    }

    @Test
    public void evoAlgorithm_ShouldHaveNull_In_Constructor(){
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> new EvolutionaryAlgorithm(null, null, null));
    }

    //TODO UNICA FORMA ES COMPROBAR TAMAÑOS DEL ARRAY COMO TEST
    @Test
    public void Optimize_ShouldReturnException_if_Algorithm_is_Empty(){
        int [][] algoritmo = new int[][] {};
        assertThrows(EvolutionaryAlgorithmException.class,
                () -> ev.optimize(algoritmo));

    }

    @Test
    public void Optimize() throws EvolutionaryAlgorithmException {
        int [][] algoritmo = new int[][]{
                {100, 43},
                {99, 10},
                {98, 1},
                {97, 8},
                {96, 2},
                {95, 21}};

        int [][] algoritmoMejorado = ev.optimize(algoritmo);
        System.out.println(algoritmoMejorado);
    }



}

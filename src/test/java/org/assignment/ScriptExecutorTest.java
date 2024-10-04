package org.assignment;

import org.assignment.model.VulnerabilityScript;
import org.assignment.plan.ScriptExecutor;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ScriptExecutorTest {

    @Test
    void testEmptyInput() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        assertTrue(executionPlan.isEmpty(), "Execution plan should be empty when no scripts are provided.");
    }

    @Test
    void testSingleScriptNoDependencies() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Collections.emptyList()));

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        assertEquals(Arrays.asList(1), executionPlan, "Execution plan should contain the single script with no dependencies.");
    }

    @Test
    void testSingleScriptMultipleDependencies() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Arrays.asList(2, 3, 4)));
        scripts.add(new VulnerabilityScript(2, Collections.emptyList()));
        scripts.add(new VulnerabilityScript(3, Collections.emptyList()));
        scripts.add(new VulnerabilityScript(4, Collections.emptyList()));

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        assertEquals(Arrays.asList(2, 3, 4, 1), executionPlan, "Execution plan should process all dependencies before the main script.");
    }

    @Test
    void testMultipleIndependentScripts() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Collections.emptyList()));
        scripts.add(new VulnerabilityScript(2, Collections.emptyList()));
        scripts.add(new VulnerabilityScript(3, Collections.emptyList()));

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertTrue(executionPlan.containsAll(expected), "Execution plan should contain all independent scripts.");
    }

    @Test
    void testChainOfDependencies() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Arrays.asList(2)));
        scripts.add(new VulnerabilityScript(2, Arrays.asList(3)));
        scripts.add(new VulnerabilityScript(3, Arrays.asList(4)));
        scripts.add(new VulnerabilityScript(4, Collections.emptyList()));

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        assertEquals(Arrays.asList(4, 3, 2, 1), executionPlan, "Execution plan should follow the chain of dependencies.");
    }

    @Test
    void testComplexDependencyGraph() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Arrays.asList(2, 3)));
        scripts.add(new VulnerabilityScript(2, Arrays.asList(4)));
        scripts.add(new VulnerabilityScript(3, Arrays.asList(4)));
        scripts.add(new VulnerabilityScript(4, Collections.emptyList()));

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        assertEquals(Arrays.asList(4, 2, 3, 1), executionPlan, "Execution plan should handle complex dependencies correctly.");
    }

    @Test
    void testUnconnectedGraph() {
        ScriptExecutor executor = new ScriptExecutor();
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Collections.emptyList()));
        scripts.add(new VulnerabilityScript(2, Arrays.asList(3)));
        scripts.add(new VulnerabilityScript(3, Collections.emptyList()));
        scripts.add(new VulnerabilityScript(4, Collections.emptyList()));

        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        assertTrue(executionPlan.containsAll(Arrays.asList(1, 4, 3, 2)), "Execution plan should process independent script sets separately.");
    }
}


package org.assignment.plan;

import org.assignment.model.VulnerabilityScript;

import java.util.*;

public class ScriptExecutor {
    private final Map<Integer, VulnerabilityScript> scriptMap = new HashMap<>();
    private final Set<Integer> checked = new HashSet<>();
    private final List<Integer> executionOrder = new ArrayList<>();

    public List<Integer> generateExecutionPlan(List<VulnerabilityScript> scripts) {
        // Map each script by its ID for easy lookup
        for (VulnerabilityScript script : scripts) {
            scriptMap.put(script.getScriptId(), script);
        }

        // Perform check on each script
        for (VulnerabilityScript script : scripts) {
            if (!checked.contains(script.getScriptId())) {
                search(script.getScriptId());
            }
        }

        // Reverse the order to get the correct execution sequence
        Collections.reverse(executionOrder);
        return executionOrder;
    }

    private void search(int scriptId) {
        // Mark script as visited
        checked.add(scriptId);

        // Recursively visit dependencies
        for (int dep : scriptMap.get(scriptId).getDependencies()) {
            if (!checked.contains(dep)) {
                search(dep);
            }
        }

        // After all dependencies are processed, add this script to the execution order
        executionOrder.add(scriptId);
    }

    public static void main(String[] args) {
        List<VulnerabilityScript> scripts = new ArrayList<>();
        scripts.add(new VulnerabilityScript(1, Arrays.asList(2, 3)));
        scripts.add(new VulnerabilityScript(2, Arrays.asList(3)));
        scripts.add(new VulnerabilityScript(3, Collections.emptyList()));

        ScriptExecutor executor = new ScriptExecutor();
        List<Integer> executionPlan = executor.generateExecutionPlan(scripts);

        System.out.println("Execution plan: " + executionPlan);
    }
}
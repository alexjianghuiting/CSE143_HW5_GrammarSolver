// Artiom Ciumac (October 31, 2018)
// Solution to GrammarSolver CSE 143's assignment
// Use at your own risk

import java.util.*;

public class GrammarSolver {
    private Map<String, ArrayList<String>> rulesMap;

    // Builds the grammar solver from provided list of rules
    public GrammarSolver(List<String> rules) {
        if (rules == null) {
            throw new IllegalArgumentException("Argument is null.");
        }

        this.rulesMap = new HashMap<>();
        for (String rule: rules) {
            String[] parts = rule.split("::=");
            String nonTerminal = parts[0];

            if (rulesMap.containsKey(nonTerminal)) {
                throw new IllegalArgumentException("Rule can only be specified once.");
            }

            String[] parts1 = parts[1].split("[|]");
            rulesMap.put(nonTerminal, new ArrayList<>(Arrays.asList(parts1)));
        }
    }

    // Returns true if a given symbol is non-terminal
    public boolean contains(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        return rulesMap.containsKey(symbol);
    }

    // Returns all the available non-terminals
    public Set<String> getSymbols() {
        return rulesMap.keySet();
    }

    // Generates phrases for a given symbol recursively
    public String generate (String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Argument is null");
        }

        if (!contains(symbol)) {
            return symbol;
        } else {
            //Select one of the available rules randomly
            List<String> list = rulesMap.get(symbol);
            String rulesApplied = list.get((int) Math.floor(Math.random() * list.size())).trim();

            String[] parts = rulesApplied.split("[ \t]+");

            // Generate a phrase for each symbol
            String accumulate = "";
            for (String s : parts) {
                accumulate += generate(s) + " ";
            }
            return accumulate.substring(0, accumulate.length() - 1);
        }
    }
}

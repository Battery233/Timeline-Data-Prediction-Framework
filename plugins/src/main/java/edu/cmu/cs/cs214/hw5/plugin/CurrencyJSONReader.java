package edu.cmu.cs.cs214.hw5.plugin;

import java.util.Map;

/**
 * The helper class for JSON processing.
 */
class CurrencyJSONReader {
    static class JSONRates {
        Map<String, Map<String, Double>> rates;
        String start_at;
        String base;
        String end_at;
    }
}

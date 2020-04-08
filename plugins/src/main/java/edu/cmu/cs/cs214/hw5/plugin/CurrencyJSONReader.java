package edu.cmu.cs.cs214.hw5.plugin;

import java.util.Map;

class CurrencyJSONReader {
    static class JSONRates {
        Map<String, Map<String, Double>> rates;
        String start_at;
        String base;
        String end_at;
    }
}

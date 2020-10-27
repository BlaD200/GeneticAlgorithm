package ua.univ.vsynytsyn.timetable.csp.nextpicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public class MRVNextPicker implements CSPNextPicker {

    @Override
    public Integer pickNext(CSPGraph graph) {
        int mrvNode = 0;
        int mrv = Integer.MAX_VALUE;
        for (int i = 1; i < graph.getV(); i++) {
            int size = graph.getAvailableValues(i).size();
            if (size < mrv && !graph.hasValue(i)) {
                mrvNode = i;
                mrv = size;
            }
        }

        return mrvNode;
    }
}

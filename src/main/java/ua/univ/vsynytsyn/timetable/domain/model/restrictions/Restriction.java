package ua.univ.vsynytsyn.timetable.domain.model.restrictions;


import ua.univ.vsynytsyn.timetable.domain.model.Unit;

public interface Restriction {
    double calculatePenalty(Unit unit);
}

package ua.univ.vsynytsyn.timetable.domain.entities.restrictions;


import ua.univ.vsynytsyn.timetable.domain.data.Unit;

public interface Restriction {
    double calculatePenalty(Unit unit);
}

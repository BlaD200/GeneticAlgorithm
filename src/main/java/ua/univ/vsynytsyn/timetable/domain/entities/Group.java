package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.utils.CsvDeserializable;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
@Table(name = "grp")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupID;

    private String name;

    @CsvDeserializable
    public static Group getInstance(String[] strings) {
        Group group = new Group();

        group.setGroupID(Long.valueOf(strings[0]));
        group.setName(strings[1]);

        return group;
    }
}

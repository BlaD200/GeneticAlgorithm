package ua.univ.vsynytsyn.timetable.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.domain.data.LessonType;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.domain.model.Allele;
import ua.univ.vsynytsyn.timetable.domain.model.Unit;
import ua.univ.vsynytsyn.timetable.repositories.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class XlsxUtils {

    private final TimeSlotRepository timeSlotRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final LessonRepository lessonRepository;
    private final LectorRepository lectorRepository;
    private final GroupsRepository groupsRepository;

    @Autowired
    public XlsxUtils(TimeSlotRepository timeSlotRepository,
                     AuditoriumRepository auditoriumRepository,
                     LessonRepository lessonRepository,
                     LectorRepository lectorRepository,
                     GroupsRepository groupsRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.lessonRepository = lessonRepository;
        this.lectorRepository = lectorRepository;
        this.groupsRepository = groupsRepository;
    }

    public void createXlsx(Unit unit, String fileLocation) throws IOException {
        Workbook workbook = createXlsxWorkbook(unit);

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    public Workbook createXlsxWorkbook(Unit unit) {
        List<Allele> alleles = new ArrayList<>(unit.getAlleles());
        alleles.sort(Comparator.comparingLong(Allele::getTimeSlotID));

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Schedule");

        createHeader(workbook, sheet);

        int rowCount = 1;
        for (Allele allele : alleles) {
            Row row = sheet.createRow(rowCount++);

            createTimeSlotCell(row, allele.getTimeSlotID());
            createAuditoriumCell(row, allele.getAuditoriumID());
            createLessonCell(row, allele.getStudyBlock().getLessonID());
            createLectorCell(row, allele.getStudyBlock().getLectorID());
            createGroupCell(row, allele.getStudyBlock().getGroupID());
            createStudentsNumCell(row, allele.getStudyBlock().getStudentsCount());
            createLessonTypeCell(row, allele.getStudyBlock().getLessonType());
        }

        return workbook;
    }

    private void createLessonTypeCell(Row row, LessonType lessonType) {
        Cell lessonTypeCell = row.createCell(6);
        lessonTypeCell.setCellValue(lessonType.toString());
    }

    private void createStudentsNumCell(Row row, int studentsCount) {
        Cell studentsNumCell = row.createCell(5);
        studentsNumCell.setCellValue(studentsCount);
    }

    private void createGroupCell(Row row, Long groupId) {
        Cell groupCell = row.createCell(4);
        Optional<Group> groupOptional = groupsRepository.findById(groupId);
        String groupName;
        if (groupOptional.isPresent()) {
            groupName = groupOptional.get().getName();
        } else {
            groupName = "None";
        }
        groupCell.setCellValue(groupName);
    }

    private void createLectorCell(Row row, Long lectorId) {
        Cell lectorCell = row.createCell(3);
        Optional<Lector> lectorOptional = lectorRepository.findById(lectorId);
        String lectorName;
        if (lectorOptional.isPresent()) {
            lectorName = lectorOptional.get().getName();
        } else {
            lectorName = "None";
        }
        lectorCell.setCellValue(lectorName);
    }

    private void createLessonCell(Row row, Long lessonId) {
        Cell lessonCell = row.createCell(2);
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        String lessonName;
        if (lessonOptional.isPresent()) {
            lessonName = lessonOptional.get().getName();
        } else {
            lessonName = "None";
        }
        lessonCell.setCellValue(lessonName);
    }

    private void createAuditoriumCell(Row row, Long auditoriumId) {
        Cell auditoriumCell = row.createCell(1);
        Optional<Auditorium> auditoriumOptional = auditoriumRepository.findById(auditoriumId);
        String auditoriumName;
        if (auditoriumOptional.isPresent()) {
            Auditorium auditorium = auditoriumOptional.get();
            String nameOrNumber = auditorium.getNumber();
            auditoriumName = auditorium.getBuilding() +"; " + nameOrNumber;
        } else {
            auditoriumName = "None";
        }
        auditoriumCell.setCellValue(auditoriumName);
    }

    private void createTimeSlotCell(Row row, Long timeSlotId) {
        Cell timeSlotCell = row.createCell(0);
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlotId);
        String timeSlotName;
        if (timeSlotOptional.isPresent()) {
            TimeSlot timeSlot = timeSlotOptional.get();
            timeSlotName = timeSlot.getDay()+';'+timeSlot.getTime();
        } else {
            timeSlotName = "None";
        }
        timeSlotCell.setCellValue(timeSlotName);
    }

    private void createHeader(XSSFWorkbook workbook, Sheet sheet) {
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        sheet.setColumnWidth(6, 7000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Time Slot");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Auditorium");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Lesson");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Lector");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Group");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Students Count");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Lesson Type");
        headerCell.setCellStyle(headerStyle);
    }
}

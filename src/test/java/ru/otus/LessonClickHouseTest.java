package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.extensions.junit.UIExtensions;
import ru.otus.pages.LessonClickhousePage;

@ExtendWith(UIExtensions.class)
public class LessonClickHouseTest {

  @Inject
  private LessonClickhousePage lessonClickhousePage;

  @Test
  void testTeachersBlockFunctionality() {

    lessonClickhousePage.open();
    lessonClickhousePage.checkTeachersBlockVisibility();
    lessonClickhousePage.checkTeacherTilesVisibility();
    lessonClickhousePage.swipeTeacherTilesLeft();
    var teacher = lessonClickhousePage.getSpecificTeacherByTileNumber(5);
    var teachersName = lessonClickhousePage.getSpecificTeachersName(teacher);
    var popup = lessonClickhousePage.click(teacher);
    lessonClickhousePage.verifyTeachersPopupAndCompareName(popup, teachersName);
    lessonClickhousePage.clickNextAndCheckTeacherTileIsChanged(popup, teachersName);
    lessonClickhousePage.clickPreviousAndVerifyPreviousTeacher(popup, teachersName);
  }
}

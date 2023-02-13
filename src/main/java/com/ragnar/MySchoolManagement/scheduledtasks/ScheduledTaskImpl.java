package com.ragnar.MySchoolManagement.scheduledtasks;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ragnar.MySchoolManagement.user.teacher.TeacherRepository;
import com.ragnar.MySchoolManagement.user.teacher.TeacherSalaryHistory;

@Component
public class ScheduledTaskImpl implements ScheduledTasksService {

	private final TeacherRepository teacherRepository;

	public ScheduledTaskImpl(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0 6 28 * ?")
	public void payTeachersMonthlySalary() {
		 teacherRepository.findAll().stream().forEach(teacher -> {
		    TeacherSalaryHistory history = new TeacherSalaryHistory(teacher, teacher.getCurrentSalary());
		    teacher.getTeacherSalaryHistory().add(history);
		    teacherRepository.save(teacher);
		});
	}
}

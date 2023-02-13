package com.ragnar.MySchoolManagement.entenrollment;

import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class EnrolmentDTOmapper implements Function<Enrolment, EnrollmentDto> {

	@Override
	public EnrollmentDto apply(Enrolment enrolment) {
		return new EnrollmentDto(enrolment);

	}
}
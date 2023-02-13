package com.ragnar.MySchoolManagement.user.student;

import org.springframework.data.repository.CrudRepository;

import com.ragnar.MySchoolManagement.user.IDcard.User_ID_Card;

public interface StudentIdRepository extends CrudRepository<User_ID_Card, Long> {

}

package com.ragnar.MySchoolManagement.user;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ragnar.MySchoolManagement.auth.LoginAttemptService;
import com.ragnar.MySchoolManagement.constanst.RoleConstants;
import com.ragnar.MySchoolManagement.exceptions.DuplicateResourceException;
import com.ragnar.MySchoolManagement.exceptions.EmailAlreadyExistsException;
import com.ragnar.MySchoolManagement.exceptions.RequestValidationException;
import com.ragnar.MySchoolManagement.user.student.Student;
import com.ragnar.MySchoolManagement.user.student.StudentRepository;
import com.ragnar.MySchoolManagement.user.student.StudentStatus;
import com.ragnar.MySchoolManagement.user.teacher.Teacher;
import com.ragnar.MySchoolManagement.user.teacher.TeacherRepository;
import com.ragnar.MySchoolManagement.user.teacher.TeacherSalaryHistory;

import jakarta.persistence.EntityNotFoundException;

@Service

public class UserServiceImpl implements UserService, UserDetailsService {

	private final TeacherRepository teacherRepository;

	private final StudentRepository studentRepository;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	private final UserDTOmapper userDTOmapper;
	
	

	
	private final LoginAttemptService loginAttemptService;

	
	public UserServiceImpl(TeacherRepository teacherRepository, 
		                   StudentRepository studentRepository,
			               UserRepository userRepository,
			               PasswordEncoder passwordEncoder,
			               LoginAttemptService loginAttemptService,
			               UserDTOmapper userDTOmapper
			) {
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.userDTOmapper = userDTOmapper;
	}

	@Override
	public void registerStudent(UserRegistrationRequest userRegistrationRequest) {
		
		if (userRepository.existsByEmail(userRegistrationRequest.getEmail()))
			throw new EmailAlreadyExistsException("Email already exists");

		Student student = new Student(
				userRegistrationRequest,
				passwordEncoder
				.encode(userRegistrationRequest.getPassword()));
		student.setStudentStatus(StudentStatus.FRESHER);
		student.setRole(RoleConstants.ROLE_STUDENT.name());
		student.setAuthorities(RoleConstants.ROLE_STUDENT.getAuthorities());
		student.setActive(true);
		student.setNotlocked(true);
		studentRepository.save(student);
	}

	@Override
	public void registerTeacher(UserRegistrationRequest userRegistrationRequest) {
		
		if (userRepository.existsByEmail(userRegistrationRequest.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		Teacher teacher = new Teacher(
				userRegistrationRequest,
				passwordEncoder
				.encode(userRegistrationRequest.getPassword()));
		
		teacher.setRole(RoleConstants.ROLE_TEACHER.name());
		teacher.setAuthorities(RoleConstants.ROLE_TEACHER.getAuthorities());
		teacher.setActive(true);
		teacher.setNotlocked(true);
		TeacherSalaryHistory history = new TeacherSalaryHistory(teacher, userRegistrationRequest.getCurrentSalary());
		teacher.getTeacherSalaryHistory().add(history);
		
	    teacherRepository.save(teacher);
	}

	@Override
	public UserDTO updateUser(Long id, UserUpdateRequest updateRequest) {
		User user = userRepository.findById(id).orElseThrow();		

        boolean changes = false;

        if (updateRequest.firstname() != null && !updateRequest.firstname().equals(user.getFirstName())) {
            user.setFirstName(updateRequest.firstname());
            changes = true;
        }
       
        if (updateRequest.lastname() != null && !updateRequest.lastname().equals(user.getLastName())) {
            user.setLastName(updateRequest.lastname());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(user.getAge())) {
            user.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            user.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
           throw new RequestValidationException("no data changes found");
        }
        
        userRepository.save(user);
		return new UserDTO(user);
	}

	@Override
	public UserDTO findUserById(Long id) {
		return userRepository.findById(id)
				        .map(userDTOmapper)
			         	.orElseThrow(EntityNotFoundException::new);
	} 

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Page<UserDTO> findAllUsers() {
		PageRequest pageRequest = PageRequest.of(
			       0,
			       5, 
			       Sort.by(Sort.Direction.ASC,"firstName"));	
	return userRepository
		   .findAll(pageRequest)   
		   .map(userDTOmapper);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(username);
		if (!optionalUser.isPresent() ){

			throw new UsernameNotFoundException("user not found " + username);
		} else {

			User myUser = optionalUser.get();
			
			validateLoginAttempts(myUser);

			myUser.setLastloginDateDisplay(myUser.getLastLogin());

			myUser.setLastLogin(new Date());

			userRepository.save(myUser);

			UserPrincipal userPrincipal = new UserPrincipal(myUser);
			return userPrincipal;
		}
	}

	private void validateLoginAttempts(User user) {
		if (user.isNotlocked()) {

			if (loginAttemptService.hasExceededMaxAttempt(user.getEmail())) {
				user.setNotlocked(false);
			} else {
				user.setNotlocked(true);
			}
			return;
		}
		loginAttemptService.removeUserFromCache(user.getEmail());

	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByEmail(username);
	}
	

}

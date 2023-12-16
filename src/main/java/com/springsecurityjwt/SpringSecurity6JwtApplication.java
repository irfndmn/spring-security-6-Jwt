package com.springsecurityjwt;

import com.springsecurityjwt.entity.enums.RoleType;
import com.springsecurityjwt.payload.request.UserRequest;
import com.springsecurityjwt.service.ImageFileService;
import com.springsecurityjwt.service.UserRoleService;
import com.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringSecurity6JwtApplication implements CommandLineRunner {
	private final UserRoleService userRoleService;
	private final UserService userService;
	private final ImageFileService imageFileService;


	@Value("${root.user.password}")
	private String rootPassword;

	public SpringSecurity6JwtApplication(UserRoleService userRoleService, UserService userService, ImageFileService imageFileService) {
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.imageFileService = imageFileService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity6JwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (userRoleService.getAllUserRole().isEmpty()){

			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.CUSTOMER);
		}

		if(userService.isAdminRegistered()==0){


			UserRequest userRequest=UserRequest.builder()
					.isAdmin(true)
					.email("admin@gmail.com")
					.firstName("Irfan")
					.lastName("Duman")
					.password(rootPassword)
					.address("Karsiyaka Mah. Bornova/Izmir")
					.zipCode("6542")
					.phoneNumber("052425825825")
					.built_in(true)
					.build();
			userService.saveUser(userRequest);
		}

		if(imageFileService.countFile()==0){
			try {
				imageFileService.saveImageFromFile();
			} catch (IOException e) {
				System.out.println("An issue with save default image");
			}
		}

	}
}

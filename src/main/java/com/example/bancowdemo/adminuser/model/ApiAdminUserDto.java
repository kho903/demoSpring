package com.example.bancowdemo.adminuser.model;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.bancowdemo.adminuser.entity.AdminStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiAdminUserDto {

	private String email;

	private String username;

	@Enumerated(EnumType.STRING)
	private AdminStatus adminStatus;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

}

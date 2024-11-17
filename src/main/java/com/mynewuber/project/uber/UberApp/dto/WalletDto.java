package com.mynewuber.project.uber.UberApp.dto;

import java.util.List;

import lombok.Data;

@Data
public class WalletDto {
	
	
	private Long id;
	
	private UserDto userDto;
	
	private Double balance;
	
	private List<WalletTransactionDto> transaction;
}




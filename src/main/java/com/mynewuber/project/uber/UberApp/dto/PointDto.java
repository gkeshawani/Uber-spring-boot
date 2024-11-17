package com.mynewuber.project.uber.UberApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PointDto {
	
	
	 private double[] coordinates;
	    private String type = "Point";

	    public PointDto(double[] coordinates) {
	        this.coordinates = coordinates;
	    }


	    
	
}

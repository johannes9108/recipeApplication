package com.iths.jh.RecipeApplication.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Quantity {
	
	private double quantity;
	private String unit;
	public Quantity(double quantity, String unit) {
		super();
		this.quantity = quantity;
		this.unit = unit;
	}
	
	
	
}

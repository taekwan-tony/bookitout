package kr.co.bookItOut.centerInventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CenterInventoryBook {
	private int centerBookCount;
	private String adminName;
	private String adminAddr;
}

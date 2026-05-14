package com.zianpayne.tokenization.adapter.in.validation;

import java.util.Objects;
import java.util.regex.Pattern;

public final class AccountValidator {
	private static final Pattern DTO_PATTERN = Pattern.compile("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$");
	private static final Pattern DIGITS_PATTERN = Pattern.compile("^\\d{16}$");
	private static final long MAX_16_DIGIT = 9_999_999_999_999_999L;

	private AccountValidator() {
	}

	public static Long toDomainValue(String accountNumber) {
		Objects.requireNonNull(accountNumber, "accountNumber");
		if (!DTO_PATTERN.matcher(accountNumber).matches()) {
			throw new IllegalArgumentException("AccountNumber must match ####-####-####-####");
		}
		String digits = accountNumber.replace("-", "");
		if (!DIGITS_PATTERN.matcher(digits).matches()) {
			throw new IllegalArgumentException("AccountNumber must be 16 digits");
		}
		return Long.parseLong(digits);
	}

	public static String toRestValue(Long value) {
		Objects.requireNonNull(value, "value");
		if (value < 0 || value > MAX_16_DIGIT) {
			throw new IllegalArgumentException("AccountNumber must be 16 digits");
		}
		String digits = String.format("%016d", value);
		return digits.substring(0, 4)
				+ "-" + digits.substring(4, 8)
				+ "-" + digits.substring(8, 12)
				+ "-" + digits.substring(12, 16);
	}
}

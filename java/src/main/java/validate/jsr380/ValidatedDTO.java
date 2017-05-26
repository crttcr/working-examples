package validate.jsr380;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class ValidatedDTO
{
	@Min(1)
	private long id;

	@NotBlank
	private String name;

	@Email
	private String email;
}

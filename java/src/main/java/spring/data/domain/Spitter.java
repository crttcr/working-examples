package spring.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spitter
{
	private Long id;
	private String username;
	private String password;
	private String fullname;
}

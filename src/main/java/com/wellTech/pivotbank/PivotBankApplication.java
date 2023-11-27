package com.wellTech.pivotbank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Pivot Remittance",
				description = "Pivot Remittance REST API",
				version = "v1",
				contact = @Contact(
						name = "Clifford Agyei",
						email = "cagyei529@gmail.com",
						url = "https://github.com/cliff-ye/pivot_remittance_rest_api"
				)
		),
		externalDocs = @ExternalDocumentation(
				url = "https://github.com/cliff-ye/pivot_remittance_rest_api"
		)
)
public class PivotBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(PivotBankApplication.class, args);
	}

}

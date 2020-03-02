package com.brilliantinfotech.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.brilliantinfotech.service.KeyGenerationRestService;
import com.brilliantinfotech.utility.UniqueKeyResponse;

/**
 * 
 * @author Rahul Landge
 *
 */

@SpringBootApplication
@EnableAutoConfiguration
public class KGCMainService {

	public static void main(String[] args) {
		SpringApplication.run(KGCMainService.class, args);
	}
}

@RestController
@ComponentScan("com.brilliantinfotech.service.impl")
 class KeyGenerationRestController {

	@Autowired
	private KeyGenerationRestService keyGenerationRestService;

	@GetMapping("/getUniqueKey")
	public UniqueKeyResponse getUniqueKey() {
		UniqueKeyResponse uniqueKey = keyGenerationRestService.getUniqueKey();
		return uniqueKey;

	}

	@DeleteMapping("/deleteUniqueKey/{key}")
	public void deleteKey(@PathVariable String key) {
		keyGenerationRestService.deleteKey(key);
	}

	@GetMapping("/createKeys/{length}")
	public void createKey(@PathVariable int length) {
		keyGenerationRestService.createKeys(length);
	}

}

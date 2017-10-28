package com.spring.cloud.samples.rest.rest_microservices_store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class SimpleStoresController {

	@Autowired
	Environment environment;
	
	@Autowired
	StoreRepository repository;
	
	@Value("${config.name:}")
	private String configName;
	
	@RequestMapping("/simple/stores")
	@ResponseBody
	List<Store> getStores() {
//		Page<Store> all = repository.findAll(PageRequest.of(0, 10));
		Page<Store> all = repository.findAll(new PageRequest(0, 10));
		Store store1 = all.getContent().get(1);
		Address address1 = store1.getAddress();
		Store storeToAdd = new Store(configName, address1);
		List<Store> result = new ArrayList<>();
		result.add(storeToAdd);
		result.addAll(all.getContent());
		return result;
	}
}

package dev.buerdel.taskerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class TaskerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskerBackendApplication.class, args);
	}

	@PostMapping(path = "/insert", consumes = "application/json")
	public Map get_task(@RequestBody HashMap<String, String> requestParams) throws Exception {
		System.out.println(requestParams);
		String user = requestParams.get("user");
		String task = requestParams.get("task");
		try {
			DatabaseConnect connection = new DatabaseConnect();
			if (connection.insert_user_task(user, task))
				return requestParams;
		}
		catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		return new HashMap<>();
	}
}

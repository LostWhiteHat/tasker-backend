package dev.buerdel.taskerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class TaskerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskerBackendApplication.class, args);
	}

	@PostMapping(path = "/insert", consumes = "application/json")
	public Map insert_task(@RequestBody HashMap<String, String> requestParams) throws Exception {
		System.out.println(requestParams);
		String user = requestParams.get("user");
		String task = requestParams.get("task");
		try {
			Database connection = new Database();
			if (connection.insert_user_task(user, task))
				return requestParams;
		}
		catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		return new HashMap();
	}

	@GetMapping(path = "/tasks", produces = "application/json")
	public ArrayList get_tasks() throws Exception {
		try {
			Database connection = new Database();
			return connection.select_all_tasks();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ArrayList();
		}
	}

	@GetMapping(path = "/tasks/{user}", produces = "application/json")
	public ArrayList get_user_tasks(@PathVariable("user")String user) throws Exception {
		try {
			Database connection = new Database();
			return connection.select_user_tasks(user);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ArrayList<>();
		}
	}

	@GetMapping(path = "/task/{id}", produces = "application/json")
	public ArrayList get_task(@PathVariable("id")String id) throws Exception {
		try {
			Database connection = new Database();
			return connection.select_id_task(Integer.parseInt(id));
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ArrayList();
		}
	}

	@GetMapping(path = "/delete/{id}")
	public String delete_task(@PathVariable("id")String id) throws Exception {
		try
		{
			Database connection = new Database();
			connection.delete_task(Integer.parseInt(id));
			return "Successfully removed task: " + id;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "An error has occurred";
		}
	}

	@GetMapping(path = "/delete-user-tasks/{user}")
	public String delete_user_task(@PathVariable("user")String user) throws Exception{
		try {
			Database connection = new Database();
			connection.delete_user_task(user);
			return "Successfully removed tasks from: " + user;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "An error has occurred";
		}
	}
}

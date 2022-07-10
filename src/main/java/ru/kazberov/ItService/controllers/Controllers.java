package ru.kazberov.ItService.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.kazberov.ItService.models.Task;
import ru.kazberov.ItService.models.TaskMagicSquare;
import ru.kazberov.ItService.models.TaskTwoArrays;
import ru.kazberov.ItService.repo.AUpload;
import ru.kazberov.ItService.repo.AUploadRepository;

@Controller
public class Controllers {
	
	@Autowired
	private AUploadRepository aUploadRepository;
	
	@GetMapping("/")
    public String mainPage(Model model){
		model.addAttribute("task", "taskTwoArrays");
		return "main";		
    }
	
	@PostMapping("/")
	public String mainPage(HttpServletRequest request,
							RedirectAttributes attributes, Model model){
		// we get the data entered into the form
		String inputTask = request.getParameter("inputTask");
		String inputButton = request.getParameter("inputButton");
		String input1 = request.getParameter("input1");
		String input2 = request.getParameter("input2");
		
		// clearing the output fields	
		model.addAttribute("output", "");
		
		// if the request is to execute, and not just to change the task
		if (inputButton != null) {
			
			// check the entered task
			Task task;
			if (inputTask.equals("taskMagicSquare")) {
				task = new TaskMagicSquare();
			} else {
				task = new TaskTwoArrays();
				model.addAttribute("output2", input2);
			}
			model.addAttribute("output1", input1);
			
			switch (inputButton) {
				case "Calc":
					task.write(input1, input2);
					task.calculate();
					model.addAttribute("output", task.getAnswer());
					model.addAttribute("input1", input1);
					model.addAttribute("input2", input2);
					break;
				case "Export":
					attributes.addFlashAttribute("inputTask", inputTask);
					attributes.addFlashAttribute("input1", input1);
					attributes.addFlashAttribute("input2", input2);
					return "redirect:/save.ser";
				case "Save":
					AUpload aUpload2 = new AUpload (inputTask, input1, input2);
					aUploadRepository.save(aUpload2);
					model.addAttribute("info", "Successful saving!");
					break;
			}
		} else { // clearing the input field if there was just a switch
			model.addAttribute("input1", "");
			model.addAttribute("input2", "");
		}
		
		model.addAttribute("task", inputTask);
		return "main";	
	}
	
	@GetMapping("/upload/{task}")
    public String uploadingPage(@PathVariable(value = "task") String task, Model model){
		List<AUpload> uploads;
		switch (task) {
			case "taskMagicSquare":
				uploads = aUploadRepository.getUploadsWithTask("taskMagicSquare");
				model.addAttribute("title", "Task magic square");
				break;
			case "taskTwoArrays":
				uploads = aUploadRepository.getUploadsWithTask("taskTwoArrays");
				model.addAttribute("title", "Task two arrays");
				break;
			default:
				return "redirect:/";
		}
		model.addAttribute("uploads",uploads);
		model.addAttribute("task", task);
		return "upload";
    }
	
	@GetMapping("/import")
    public String importPage(Model model){
		return "import";
    }
	
	@PostMapping("/import")
    public String importPage(@RequestParam("file") MultipartFile file,
    							Model model){
		// getting our file
		AUpload aUpload = null;
		try {
			InputStream inputStream = file.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		    aUpload = (AUpload) objectInputStream.readObject();
		    objectInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// checking if the file was received successfully
		if (aUpload == null) { 
			model.addAttribute("info", "Invalid file!!");
			return "import";
		} else {
			model.addAttribute("task", aUpload.getTask());
			model.addAttribute("input1", aUpload.getInput1());
			model.addAttribute("input2", aUpload.getInput2());
			return "importSend";
		}
    }
	
	@GetMapping(value = "/save.ser", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] getFile(HttpServletRequest request,
										@ModelAttribute("inputTask") String inputTask,
										@ModelAttribute("input1") String input1,
										@ModelAttribute("input2") String input2)
												throws IOException {
		
		AUpload aUpload = new AUpload(inputTask, input1, input2);
		// creating a path
		String filePath = "save.ser";
		ServletContext context = request.getServletContext();
		String appPath = context.getRealPath("");
		String fullPath = appPath + filePath; 
		// creating a file
		FileOutputStream fileoutputStream = new FileOutputStream(fullPath);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileoutputStream);
		objectOutputStream.writeObject(aUpload);
		objectOutputStream.flush();
		objectOutputStream.close();
		// we transfer the file for download
	    InputStream inputStream = new FileInputStream(fullPath);
	    return IOUtils.toByteArray(inputStream);
	}
    
}
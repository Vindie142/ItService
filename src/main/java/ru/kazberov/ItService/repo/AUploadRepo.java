package ru.kazberov.ItService.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AUploadRepo extends CrudRepository<AUpload, Long> {
	
	public default List<AUpload> getUploadsWithTask(String task){
		Iterable<AUpload> source = findAll();
		
		List<AUpload> uploads = new ArrayList<>();
		source.forEach(a -> uploads.add(a));
		
		List<AUpload> rightUploads = new ArrayList<AUpload>();
		uploads.stream().filter(a -> a.getTask().equals(task)).forEach(a -> rightUploads.add(a));
		return rightUploads;
	}
	
}

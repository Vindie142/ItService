package ru.kazberov.ItService.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AUploadRepository extends CrudRepository<AUpload, Long> {
	
	public default List<AUpload> getUploadsWithTask(String task){
		Iterable<AUpload> uploads = findAll();
		List<AUpload> rightUploads = new ArrayList<AUpload>();
		
		for (AUpload aUpload : uploads) {
			if (aUpload.getTask().equals(task)) {
				rightUploads.add(aUpload);
			}
		}
		return rightUploads;
	}
	
}

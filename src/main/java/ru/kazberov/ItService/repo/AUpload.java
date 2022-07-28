package ru.kazberov.ItService.repo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="uploads")
public class AUpload implements Serializable {
	
	private static final long serialVersionUID = 67348335405068620L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String task;
	@ElementCollection(targetClass=String.class)
	private List<String> input;
	
	public AUpload () {}
	
	public AUpload (String task, String... input) {
		this.task = task;
		this.input = Arrays.stream(input).filter(s -> s != null).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	

	public List<String> getInput() {
		return input;
	}

	public void setInput(List<String> input) {
		this.input = input;
	}

	@Override
	public String toString() {
		String task = this.task == null ? "" : this.task;
		String input = this.input == null ? "" : this.input.toString();
		return  "AUpload{"+
				"task="+task+", "+
				"input="+input+"}";
	}
	
	@Override
    public boolean equals(Object obj) {
	    if (obj == this) {
	        return true;
	    }
	    if (obj == null || obj.getClass() != this.getClass()) {
	        return false;
	    }
	    AUpload mayBe = (AUpload) obj;
	    return mayBe.toString().equals(this.toString());
    }
	
	@Override
    public int hashCode() {
		int  result = 31;
	    int a1 = this.task == null ? 1 : this.task.hashCode();
	    int a2 = this.input == null ? 1 : this.input.hashCode();
	    return result * (a1 + a2);
    }
	
}

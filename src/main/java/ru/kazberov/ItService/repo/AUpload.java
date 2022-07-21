package ru.kazberov.ItService.repo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="uploads")
public class AUpload implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String task;
	private String input1;
	private String input2;
	
	public AUpload () {}
	
	public AUpload (String task, String input1, String input2) {
		this.task = task;
		this.input1 = input1;
		this.input2 = input2;
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
	

	public String getInput1() {
		return input1;
	}

	public void setInput1(String input1) {
		this.input1 = input1;
	}

	public String getInput2() {
		return input2;
	}

	public void setInput2(String input2) {
		this.input2 = input2;
	}

	@Override
	public String toString() {
		String task = this.task == null ? "" : this.task;
		String input1 = this.input1 == null ? "" : this.input1;
		String input2 = this.input2 == null ? "" : this.input2;
		return  "AUpload{"+
				"task="+task+", "+
				"input1="+input1+", "+
				"input2="+input2+"}";
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
	    String a1 = this.task == null ? " " : this.task;
		String a2 = this.input1 == null ? " " : this.input1;
		String a3 = this.input2 == null ? " " : this.input2;
	    return 31 * (a1.hashCode() + a2.hashCode() + a3.hashCode());
    }
	
}

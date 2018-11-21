package knihaJizd;

import cv08.Ancestor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private StringProperty id;
	private StringProperty password;
	private StringProperty fullName;
	private IntegerProperty authority;
	private String deffPass;

	
	public Person(String ID,String password,String fullName,int authority){
		this.id = new SimpleStringProperty(ID);
		this.password = new SimpleStringProperty(password);
		this.fullName = new SimpleStringProperty(fullName);
		this.authority = new SimpleIntegerProperty(authority);
				
	}
	
	public Person(String ID,String name,int authority){
		this.id = new SimpleStringProperty(ID);
		this.fullName = new SimpleStringProperty(name);
		this.authority = new SimpleIntegerProperty(authority);
		this.password = new SimpleStringProperty(ID);
	}
	
	public Person(){
		this.fullName.set("Default");
	}

	public String getID() {
		return id.get();
	}
	
	public StringProperty IDProperty() {
		return id;
	}


	public String getFullName() {
		return fullName.get();
	}
	
	public StringProperty fullNameProperty() {
		return fullName;
	}

	
	public StringProperty passwordProperty() {
		return password;
	}
	
	public String getPassword() {
		return password.get();
	}
	
	public IntegerProperty authorityProperty(){
		return authority;
	}
	
	public Integer getAuthority(){
		return authority.get();
	}
	
	public void setFullName(String name){
		this.fullName.set(name);
	}
	
	public void setID(String id){
		this.id.set(id);
	}
	
	public void resetPass(Person person){
		this.password.set(person.getID());
	}
	
	public void setPassword(String password){
		this.password.set(password);
	}
	
	public int compareTo(Person o) {
		return getFullName().compareTo(o.getFullName());
	}
	
	
	
	@Override
	public String toString(){
		return (getFullName());
	}

}

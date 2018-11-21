package knihaJizd;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
	
	private StringProperty spz;
	private StringProperty name;
	private IntegerProperty currentTachometer;
	private IntegerProperty buyTachometer;
	private ObjectProperty<LocalDate> insurance;
	private ObjectProperty<LocalDate> technical;
	
	public Car(String spz,String name,int buyTachometer,LocalDate insurance,LocalDate technical){
		this.spz = new SimpleStringProperty(spz);
		this.name = new SimpleStringProperty(name);
		this.currentTachometer = new SimpleIntegerProperty(buyTachometer);
		this.buyTachometer = new SimpleIntegerProperty(buyTachometer);
		this.insurance = new SimpleObjectProperty<>(insurance);
		this.technical = new SimpleObjectProperty<>(technical);
	}

	/**
	 * @return the spz
	 */
	public StringProperty SpzProperty() {
		return spz;
	}

	public String getSpz(){
		return spz.get();
	}
	/**
	 * @param spz the spz to set
	 */
	public void setSpz(String spz) {
		this.spz.set(spz);
	}

	/**
	 * @return the name
	 */
	public StringProperty NameProperty() {
		return name;
	}
	public String getName() {
		return name.get();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * @return the currentTachometer
	 */
	public IntegerProperty CurrentTachometerProperty() {
		return currentTachometer;
	}
	
	public Integer getCurrentTachometer() {
		return currentTachometer.get();
	}

	/**
	 * @param currentTachometer the currentTachometer to set
	 */
	public void setCurrentTachometer(Integer currentTachometer) {
		
		this.currentTachometer.set(currentTachometer);
	}

	/**
	 * @return the buyTachometer
	 */
	public IntegerProperty BuyTachometerProperty() {
		return buyTachometer;
	}
	public Integer getBuyTachometer() {
		return buyTachometer.get();
	}
	/**
	 * @param buyTachometer the buyTachometer to set
	 */
	public void setBuyTachometer(Integer buyTachometer) {
		this.buyTachometer.set(buyTachometer);
	}

	/**
	 * @return the insurance
	 */
	public ObjectProperty<LocalDate> InsuranceProperty() {
		return insurance;
	}
	public LocalDate getInsurance() {
		return insurance.get();
	}

	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(LocalDate insurance) {
		this.insurance.set(insurance);
	}

	/**
	 * @return the technical
	 */
	public ObjectProperty<LocalDate> TechnicalProperty() {
		return technical;
	}
	public LocalDate getTechnical() {
		return technical.get();
	}

	/**
	 * @param technical the technical to set
	 */
	public void setTechnical(LocalDate technical) {
		this.technical.set(technical);
	}
	
	@Override
	public String toString(){
		return getSpz()+" - "+getName();
	}

}

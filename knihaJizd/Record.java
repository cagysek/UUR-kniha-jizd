package knihaJizd;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {
	private ObjectProperty<Car> carType;
	private ObjectProperty<Person> driverName;
	private ObjectProperty<LocalDate> date;
	private StringProperty startingPoint;
	private StringProperty destination;
	private IntegerProperty distance;
	private IntegerProperty tachometerStatus;
	private StringProperty drivingTyp;
	
	public Record(Car carType,Person driverName,LocalDate date,String startingPoint,String destination,int distance,int tachometerStatus,String drivingTyp){
		this.carType = new SimpleObjectProperty(carType);
		this.driverName = new SimpleObjectProperty(driverName);
		this.date = new SimpleObjectProperty<>(date);
		this.startingPoint = new SimpleStringProperty(startingPoint);
		this.destination = new SimpleStringProperty(destination);
		this.distance = new SimpleIntegerProperty(distance);
		this.tachometerStatus = new SimpleIntegerProperty(tachometerStatus);
		this.drivingTyp = new SimpleStringProperty(drivingTyp);
	}

	/**
	 * @return the carType
	 */
	public ObjectProperty<Car> getCarTypeProperty() {
		return carType;
	}
	
	public Car getCarType(){
		return carType.get();
	}

	/**
	 * @return the driverName
	 */
	public ObjectProperty<Person> getDriverNameProperty() {
		return driverName;
	}
	
	public Person getDriverName(){
		return driverName.get();
	}

	/**
	 * @return the date
	 */
	public ObjectProperty<LocalDate> getDateProperty() {
		return date;
	}
	
	public LocalDate getDate(){
		return date.get();
	}

	/**
	 * @return the startingPoint
	 */
	public StringProperty getStartingPointProperty() {
		return startingPoint;
	}

	public String getStartingPoint(){
		return startingPoint.get();
	}
	/**
	 * @return the destination
	 */
	public StringProperty getDestinationProperty() {
		return destination;
	}
	
	public String getDestination(){
		return destination.get();
	}

	/**
	 * @return the distance
	 */
	public IntegerProperty getDistanceProperty() {
		return distance;
	}
	
	public int getDistance(){
		return distance.get();
	}

	/**
	 * @return the tachometerStatus
	 */
	public IntegerProperty getTachometerStatusProperty() {
		return tachometerStatus;
	}
	
	public int getTachometerStatus(){
		return tachometerStatus.get();
	}

	/**
	 * @return the drivingTyp
	 */
	public StringProperty getDrivingTypProperty() {
		return drivingTyp;
	}
	
	public String getDrivingTyp(){
		return drivingTyp.get();
	}

	/**
	 * @param carType the carType to set
	 */
	public void setCarType(Car carType) {
		this.carType.set(carType);
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(Person driverName) {
		this.driverName.set(driverName);
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	/**
	 * @param startingPoint the startingPoint to set
	 */
	public void setStartingPoint(String startingPoint) {
		this.startingPoint.set(startingPoint);
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination.set(destination);
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance.set(distance);
	}

	/**
	 * @param tachometerStatus the tachometerStatus to set
	 */
	public void setTachometerStatus(int tachometerStatus) {
		this.tachometerStatus.set(tachometerStatus);
	}

	/**
	 * @param drivingTyp the drivingTyp to set
	 */
	public void setDrivingTyp(String drivingTyp) {
		this.drivingTyp.set(drivingTyp);
	}
	
	@Override
	public String toString(){
		return "car: "+getCarType()+"Driver: "+getDriverName()+" Date: "+getDate();
	}
}


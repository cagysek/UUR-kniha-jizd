package knihaJizd;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.text.Font;

public class DatePickerCellCar extends TableCell<Car,LocalDate>{
	private DatePicker datePicker;
	
	//prepnuti do editacniho modu
	public void startEdit(){
		//reknu nadrazeny tride, ze jsem v editu
		super.startEdit();
		
		//vytvorim editor jestli ho jeste nemam
		if(datePicker == null){
			createDatePicker();
		}
		setText(null);
		//nastaveni
		setGraphic(datePicker);
		//zobrazeni kalendare
		datePicker.show();
	}
	
	private void createDatePicker(){
		datePicker = new DatePicker(getItem());
		
		//vypnu edit, protoze chci jenom mysi vybrat datum
		datePicker.setEditable(false);
		
		//rakce na vybrani datumu, vytvoreni udalosti
		datePicker.setOnAction(event ->{
			//pokud jsem vybral datum
			if(datePicker.getValue()!=null){
				commitEdit(datePicker.getValue());
				//pokud ne, tak colam cancel
			}else{
				cancelEdit();
			}
		});
	}
	//prepnutí zped do modu
	public void cancelEdit(){
		super.cancelEdit();
		//vratim text
		setText(getItem().toString());
		//odstraneni datepicker
		setGraphic(null);
	}
	
	//update hodnoty, když je zmenena
	public void updateItem(LocalDate item,boolean empty){
		//predkovi davam vedet, ze se neco stalo
		super.updateItem(item, empty);
		//nastavi font
		setFont(new Font("Arial",15));
		
		
		if(empty){
			setText(null);
			setGraphic(null);
		}else{
			//editovaci mod
			if(isEditing()){
				//meni hodnotu v editoru
				if(datePicker!=null){
					datePicker.setValue(getItem());
				}
				setText(null);
				//nastavi editor
				setGraphic(datePicker);
			}else
				//hodi hodnotu do labelu
				setText(getItem().toString());
			//vypne kalendar
				setGraphic(null);
		}
		
	}
}


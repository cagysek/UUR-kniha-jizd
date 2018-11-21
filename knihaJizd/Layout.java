package knihaJizd;

import java.awt.Desktop.Action;
import java.io.File;
import java.net.PortUnreachableException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Optional;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.bind.ParseConversionEvent;

import cv08.Ancestor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
/**
 * Program which is used for stored records from driving
 * 
 * @author Čarnogurský Jan
 *
 */
public class Layout extends Application {

	private Stage stage;
	private TableView<Record> table;
	private TableView<Car> carTable;
//	private Image shutDown = new Image(getClass().getResourceAsStream("shut.ico"));
	
	private ImageView imgBook = new ImageView(new Image("file:book.png"));
	private Image imgForRecord = new Image("file:record.jpg");
	private ImageView imgRecord = new ImageView(imgForRecord);
	private ImageView imgRecord2 = new ImageView(imgForRecord);
	private ImageView imgManage = new ImageView(new Image("file:manage.png"));
	private ImageView imgCar = new ImageView(new Image("file:car.png"));
	private ImageView imgLogOut = new ImageView(new Image("file:shutIcon.png"));
	
	/*
	private ImageView imgBook = new ImageView(new Image(getClass().getResourceAsStream("/book.png")));
	private Image imgForRecord = new Image(getClass().getResourceAsStream("/record.jpg"));
	private ImageView imgRecord = new ImageView(imgForRecord);
	private ImageView imgRecord2 = new ImageView(imgForRecord);
	private ImageView imgManage = new ImageView(new Image(getClass().getResourceAsStream("/manage.png")));
	private ImageView imgCar = new ImageView(new Image(getClass().getResourceAsStream("/car.png")));
	private ImageView imgLogOut = new ImageView(new Image(getClass().getResourceAsStream("/shutIcon.png")));
	*/
	
	//tab2-------
	private TreeView<Person> treePeople;
	private TextArea textArea;
	private TextField newName;
	
	//log window
	private TextField logNick;
	private PasswordField logPassword;
	
	//current person
	private Person loggedPerson;
	
	//Button test = new Button("test");
	
	//collections for data
	private ObservableList<Person> people;
	private ObservableList<Car> cars;
	private ObservableList<Person> drivers;
	private ObservableList<Record> addedRecords = FXCollections.observableArrayList();
	
	//add panels, global for set visible
	public VBox addPane = new VBox();
	public VBox addCarPane = new VBox();
	public HBox managePanel; //tab 2
	
	private TabPane tabs;
	private Tab knihaJizd;
	private Tab manage;
	private Tab car;
	
	//all button which we want hide to owner
	private Button knihaJizdAddRecord;
	private Button carAddNewCar;
	private Button carDeleteCar;
	
	public static void main(String[] args) {
		
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		this.stage.setScene(getSceneLog());
		//this.stage.centerOnScreen();
		this.stage.setResizable(false);
		
		//generate default data
		generatePeople();
		generateCars();
		generateDrivers();
		
		this.stage.show();
		
		
		
	}
	
	private Scene getSceneLog(){
		Scene logScene = new Scene(getLogRoot(),380,280);
		stage.setTitle("Log in");
		return logScene;
	}

	private Parent getLogRoot() {
		BorderPane root = new BorderPane();
		root.setCenter(getLogControls());
		return root;
	}
	
	/**
	 * Method to center window middle of screen
	 */
	private void centreStage(){
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	/**
	 * Get log book scene
	 * @return log book scene
	 */
	private Scene getMainScene(){
		Scene mainScene = new Scene(getMainRoot(),1050,680);
		  
		stage.setTitle("Log book");
		return mainScene;
	}
	
	/**
	 * Get log book window
	 * @return log book window
	 */
	private Parent getMainRoot(){
		BorderPane root = new BorderPane();
		root.setCenter(getMainCenterControls());
		return root;
		
	}
	
	/**
	 * Method to create layout of login window
	 * @return log window
	 */
	private Node getLogControls(){
		VBox controls = new VBox();
		Text header = new Text("Please log in");
		header.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		logNick = new TextField();
		logNick.setMaxWidth(240);
		logNick.setMinWidth(240);
		logNick.setPromptText("ID");
		logNick.setFocusTraversable(false);
		
		
		logPassword = new PasswordField();
		logPassword.setMaxWidth(240);
		logPassword.setMinWidth(240);
		logPassword.setPromptText("password");
		logPassword.setFocusTraversable(false);
		
		Button buttLog = new Button("Log in");
		buttLog.setPrefSize(80, 30);
		//buttLog.setOnAction(event->stage.setScene(getMainScene()));
		buttLog.setOnAction(event -> getLoged());
		controls.getChildren().addAll(header,logNick,logPassword,buttLog);
		
		controls.setSpacing(15);
		controls.setAlignment(Pos.CENTER);
		controls.setPadding(new Insets(10));
		
		
		return controls;
	}
	
	/**
	 * Method to check if ID and password match
	 * get value from id textField and according to id check password 
	 * if id and password match set log book layout
	 */
	public void getLoged(){
		boolean isWrong = true;
		for(Person p:people){
			if(p.getID().equals(logNick.getText())&&p.getPassword().equals(logPassword.getText())){
				isWrong = false;
				
				//set loggeed person for set rights 
				setLoggedPerson(p);
				
				//check if person is newbie or person have reset password
				checkPassword(p);
				
				//set log book scene and center it to middle of screen
				stage.setScene(getMainScene());
				centreStage();
				
				//set rights to logged person
				setRights();
				Alert info = new Alert(AlertType.INFORMATION);
				info.setTitle("Login");
				info.setHeaderText("You have successfully signed up!");
				//alert.setContentText("Please provide a sex of a new offspring.");
				info.showAndWait();
			}
		}
		if(isWrong==true){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Login Error");
		alert.setHeaderText("Wrong ID or password");
		alert.setContentText("Your ID and password combination was wrong. Try it again. To help contact admin");
		
		logPassword.setText("");
		
		alert.showAndWait();
		}
		
	}
	/**
	 * Method to set rights of logged person
	 */
	private void setRights(){
		switch(loggedPerson.getAuthority()){
		case 2:
			tabs.getTabs().remove(manage);
			tabs.getTabs().remove(car);
			knihaJizdAddRecord.setVisible(true);

			break;
		case 1:
			managePanel.setVisible(false);
			knihaJizdAddRecord.setVisible(false);
			carAddNewCar.setVisible(false);
			carDeleteCar.setVisible(false);
			carTable.setEditable(false);

			break;
		
		case 0:
			managePanel.setVisible(true);
			knihaJizdAddRecord.setVisible(true);
			carAddNewCar.setVisible(true);
			carDeleteCar.setVisible(true);
			carTable.setEditable(true);
		
			break;
			
		}
	}
	
	/**
	 * Method to check password of person
	 * if id and password is same value, method create dialog to change password
	 * Basically if I added person to list of employees or reset person password, I set password same like ID
	 * @param p person which responds to input ID
	 */
	private void checkPassword(Person p){
		if(p.getID().equals(p.getPassword())){
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("Change password");
			dialog.setHeaderText("Please set new password");
			
			ButtonType applyButtonType = new ButtonType("OK", ButtonData.OK_DONE);
			;
			
			dialog.getDialogPane().getButtonTypes().addAll(applyButtonType);

			
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			PasswordField password = new PasswordField();
			PasswordField passwordCheck = new PasswordField();

			grid.add(new Label("New password:"), 0, 0);
			grid.add(password, 1, 0);
			grid.add(new Label("Repeat your new password:"), 0, 1);
			grid.add(passwordCheck, 1, 1);
			
			dialog.getDialogPane().setContent(grid);
			
			Optional<Pair<String, String>> result = dialog.showAndWait();
			if(password.getText().equals(passwordCheck.getText())){
				p.setPassword(password.getText());
			}else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Passwort dont match");
				alert.setHeaderText("Passwords aren't same ");
				alert.setContentText("Please try again!");
				alert.showAndWait();
				checkPassword(p);
			}
			
			
		}
			
	}
	/**
	 * Create log book window
	 * it's one tab with sub-tabs
	 * tab contains 3 sub-tabs
	 * all window is stacked with anchorPane
	 * @return log book window
	 */
	private Node getMainCenterControls(){
		AnchorPane allScene = new AnchorPane();
		//----------------------------------
		//Other elements
		//label which shows logged person
		Label id = new Label();
		Label name = new Label();
		
		if(!loggedPerson.equals(null)){
			id.setText("ID: "+loggedPerson.getID());
			name.setText("Name: "+loggedPerson.getFullName());
		
		}
		
		//log out button
		Button end = new Button();
		end.setGraphic(imgLogOut);
		end.setPadding(Insets.EMPTY);
		end.setOnAction(event -> closeWindow());
		end.setPrefSize(70, 73);
		
		
		//----------------------------------
		tabs = new TabPane();
		
		//tab 1 - log book
		knihaJizd = new Tab();
		knihaJizd.setContent(getKnihaJizd());
		knihaJizd.setGraphic(imgBook);
		//-----------------------------------------------------------------
		
		//tab 2 - manage person
		manage = new Tab();
		manage.setGraphic(imgManage);
		manage.setContent(getManage());
		//-----------------------------------------
		
		//tab 3 - car manage
		car = new Tab();
		car.setContent(getCarTable());
		car.setGraphic(imgCar);
		//--------------------------------------
		
		tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabs.getTabs().addAll(knihaJizd,manage,car);

		tabs.setTabMinHeight(70);
		tabs.setTabMinWidth(50);
		tabs.setTabMaxWidth(50);
		tabs.setTabMaxHeight(70);
		
		//-------------------------------
		//composing of elements
		allScene.getChildren().addAll(tabs,id,name,end);
		
		AnchorPane.setLeftAnchor(tabs, 5.0);
		AnchorPane.setRightAnchor(tabs, 5.0);
		AnchorPane.setTopAnchor(tabs, 5.0);
		AnchorPane.setBottomAnchor(tabs, 5.0);
		
		AnchorPane.setTopAnchor(id, 25.0);
		AnchorPane.setRightAnchor(id, 200.0);
		
		AnchorPane.setTopAnchor(name, 50.0);
		AnchorPane.setRightAnchor(name, 200.0);
		
		AnchorPane.setTopAnchor(end, 11.0);
		AnchorPane.setRightAnchor(end, 10.0);

		return allScene;
		//return tabs;
	}
	
	/**
	 * Method to create data to tab 1
	 * @return window with records
	 */
	private Node getKnihaJizd(){
		BorderPane organization = new BorderPane();
		
		//create pane for filter
		GridPane filter = new GridPane();
		
		Label car = new Label("Car: ");
		Label driver = new Label("Driver: ");
		Label typeDriv = new Label("Type of driving: ");
		
		//interactive button which shows additionally elements
		knihaJizdAddRecord = new Button();
		knihaJizdAddRecord.setGraphic(imgRecord);
		knihaJizdAddRecord.setPrefSize(20, 20);
		knihaJizdAddRecord.setOnAction(event -> addToTable());
		
		
		ChoiceBox<Car> carsMenu = new ChoiceBox<>(cars);
			
		ChoiceBox<Person> driversMenu;
		
		Button showMe = new Button("Filter");
		
		//if logged person is driver he can add record just for himself
		if(loggedPerson.getAuthority()==2){
			driversMenu = new ChoiceBox<Person>(FXCollections.observableArrayList(loggedPerson));
			driversMenu.getSelectionModel().selectFirst();
		}else{//admin can add record to anyone
			driversMenu = new ChoiceBox<Person>(drivers);
		}
		
		ChoiceBox<String> typeOfDriving = new ChoiceBox<String>(
		        FXCollections.observableArrayList("Work", "Private"));
		
		//reset values at filter and refresh table
		Button reset = new Button("Reset");
		reset.setOnAction(event ->{
						carsMenu.setValue(null);
						if(loggedPerson.getAuthority()==2){
							driversMenu.setValue(loggedPerson);
						}else{
							driversMenu.setValue(null);
						}
						typeOfDriving.setValue(null);
						
						table.setItems(filtredData(carsMenu.getValue(), driversMenu.getValue(), typeOfDriving.getValue()));
					});
		
		carsMenu.setMinWidth(200);
		driversMenu.setMinWidth(200);
		typeOfDriving.setMinWidth(100);
		
		filter.add(car, 0, 0);
		filter.add(carsMenu, 0, 1);
		filter.add(driver, 1, 0);
		filter.add(driversMenu, 1, 1);
		filter.add(typeDriv, 2, 0);
		filter.add(typeOfDriving, 2, 1);
		filter.add(showMe, 3, 1);
		filter.add(reset, 4, 1);
		filter.add(knihaJizdAddRecord, 0, 2);
		filter.add(addPane, 0, 3,6,1);
		
		filter.setPadding(new Insets(5));
		filter.setHgap(10);
		filter.setVgap(10);
		
		//create table with records
		table = new TableView<Record>();
		TableColumn<Record,String> carType = new TableColumn<>("Car");
		TableColumn<Record,String> driverName = new TableColumn<>("Driver Name");
		TableColumn<Record,LocalDate> date = new TableColumn<>("Date");
		TableColumn<Record,String>startingPoint = new TableColumn<>("Starting point");
		TableColumn<Record,String>destination = new TableColumn<>("Destination");
		TableColumn<Record,Integer>distance = new TableColumn<>("Distance");
		TableColumn<Record,Integer>tachometerStatus = new TableColumn<>("Tachometer status");
		TableColumn<Record,String>drivingTyp = new TableColumn<>("Type");
		
		carType.setMinWidth(150);
		carType.setCellValueFactory(new PropertyValueFactory<Record,String>("carType"));
		
		driverName.setMinWidth(120);
		driverName.setCellValueFactory(new PropertyValueFactory<Record,String>("driverName"));
		
		date.setMinWidth(90);
		date.setMaxWidth(90);
		date.setCellValueFactory(new PropertyValueFactory<Record,LocalDate>("date"));
		
		startingPoint.setMinWidth(120);
		startingPoint.setCellValueFactory(new PropertyValueFactory<Record,String>("startingPoint"));
		
		destination.setMinWidth(120);
		destination.setCellValueFactory(new PropertyValueFactory<Record,String>("destination"));
		
		distance.setMinWidth(50);
		distance.setCellValueFactory(new PropertyValueFactory<Record,Integer>("distance"));
		
		tachometerStatus.setMinWidth(125);
		tachometerStatus.setCellValueFactory(new PropertyValueFactory<Record,Integer>("tachometerStatus"));
		
		drivingTyp.setMinWidth(30);
		drivingTyp.setCellValueFactory(new PropertyValueFactory<Record,String>("drivingTyp"));
		
		table.getColumns().addAll(carType,driverName,date,startingPoint,destination,distance,tachometerStatus,drivingTyp);
		table.setPadding(new Insets(5));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		BorderPane.setMargin(table, new Insets(3));
		
		//drivers and admins only can delete records
		if(loggedPerson.getAuthority()!=1){
			table.setOnKeyReleased(event -> tableDeleteSelected(event));
		}
		
		//fill the table
		table.setItems(filtredData(carsMenu.getValue(), driversMenu.getValue(), typeOfDriving.getValue()));
		
		//fill the table depends on data at filter
		showMe.setOnAction(event ->{
			table.setItems(filtredData(carsMenu.getValue(), driversMenu.getValue(), typeOfDriving.getValue()));
		});
		
		organization.setTop(filter);
		organization.setCenter(table);
		
		organization.setPadding(new Insets(5));
		
		return organization;
	}
	
	/**
	 * Delete selected from table
	 */
	public void tableDeleteSelected(javafx.scene.input.KeyEvent event){
		//získaní označených dat
		ObservableList<Record> selection = FXCollections.observableArrayList(table.getSelectionModel().getSelectedItems()) ;
		if(event.getCode() == KeyCode.DELETE){
			//pokud nic není vybráno
			if(selection.size()==0){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Deleting item");
				alert.setHeaderText("No item for deleting was selected!");
				alert.setContentText("Please select item for deleting.");
				alert.show();
			}else{
				// dialog okno pro potvrzení
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Deleting selection");
				alert.setHeaderText("Do you want to delete selected element?");
				// zobrazení vybraných dat
				alert.setContentText(selection.toString());
				
				// ceka na odpoved
				alert.showAndWait()
					 // pokud ok odstaním itemy
					 .filter(response -> response == ButtonType.OK)
					 .ifPresent(response -> {
						 table.getItems().removeAll(selection);
						 table.getSelectionModel().clearSelection();
						
						addedRecords.remove(selection);
					
				 });
			
			}
		}
	}
	
	/**
	 * Create or remove pane which used to add to table in first tab
	 * if pane dont have any children create it(basically first click)
	 * if pane have children remove all from pane(basically second click, when we want hide it)
	 */
	private void addToTable(){
		if(addPane.getChildren().size()==0){
			ObservableList<Person> per;
			
			//fill data to choicebox for people, driver can see just himself
			if(loggedPerson.getAuthority()==2){
				per = FXCollections.observableArrayList(loggedPerson);
			}else{
				per = FXCollections.observableArrayList(drivers);
			}
			
			HBox control = new HBox();
			
			ChoiceBox<Car> car = new ChoiceBox<>(cars);
			ChoiceBox<Person> name = new ChoiceBox<>(per);
			DatePicker date = new DatePicker();
			TextField startP = new TextField();
			TextField destP = new TextField();
			TextField distance = new TextField();
			TextField tachometer = new TextField();
			ChoiceBox<String> type = new ChoiceBox<>(FXCollections.observableArrayList("Work","Private"));
			Button add = new Button("Add");
			
			//if all data is insert we can create record
				add.setOnAction(event -> {
					if(date.getValue()==null||startP.getLength()==0||destP.getLength()==0||distance.getLength()==0||tachometer.getLength()==0){
						
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Insert ERROR");
						alert.setHeaderText("please check if you insert all columns");
						alert.showAndWait();	
					}else{
						addRecord(car.getSelectionModel().getSelectedItem(),
														name.getSelectionModel().getSelectedItem(),
														date.getValue(),
														startP.getText(),
														destP.getText(),
														Integer.parseInt(distance.getText().trim()),
														Integer.parseInt(tachometer.getText().trim()),
														type.getSelectionModel().getSelectedItem());
						addPane.getChildren().clear();
					}
				});
			
			
			car.setMaxWidth(200);
			car.getSelectionModel().selectFirst();
			
			name.setMinWidth(150);
			name.getSelectionModel().selectFirst();
			
			date.setMaxWidth(110);
			date.setPromptText("Date");
			date.setMinWidth(110);
			date.setEditable(false);
			
			startP.setPromptText("Starting point");
			
			destP.setPromptText("Destination");
			
			distance.setMaxWidth(70);
			distance.setPromptText("Distance");
			//distence is int, so  we allow input just numbers
			distance.textProperty().addListener(new ChangeListener<String>() {
			    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			        if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			            distance.setText(oldValue);
			        }
			    }
			});
			
			//tachometer is int, so  we allow input just numbers
			tachometer.setPromptText("tachometer status");
			tachometer.textProperty().addListener(new ChangeListener<String>() {
			    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			        if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			            tachometer.setText(oldValue);
			        }
			    }
			});
			type.getSelectionModel().selectFirst();
			
			control.setSpacing(3);
			control.getChildren().addAll(car,name,date,startP,destP,distance,tachometer,type);
			addPane.setSpacing(3);
			addPane.getChildren().addAll(control,add);
		}else addPane.getChildren().clear();
		
		
	}
	
	/**
	 * Method to create data for tab 3
	 * @return tab 3 elements
	 */
	private Node getCarTable(){
		VBox controls = new VBox();
		
		carAddNewCar = new Button();
		carAddNewCar.setGraphic(imgRecord2);
		carAddNewCar.setPrefSize(20, 20);
		carAddNewCar.setOnAction(event -> addToCarTable());//again interactive add as with records
		
		carDeleteCar = new Button("Remove");
		carDeleteCar.setOnAction(event->tableCarDeleteSelected());
		
		carTable = new TableView<Car>();
		TableColumn<Car,String> spz = new TableColumn<>("SPZ");
		TableColumn<Car,String> name = new TableColumn<>("Car type");
		TableColumn<Car,LocalDate> insurance = new TableColumn<>("Insurance");
		TableColumn<Car,LocalDate> technical = new TableColumn<>("Technical");
		TableColumn<Car,Integer>currTachometer = new TableColumn<>("Current tachometer");
		TableColumn<Car,Integer>buyTachometer = new TableColumn<>("Buy with tachometer");
		
		spz.setCellValueFactory(new PropertyValueFactory<Car,String>("spz"));
		name.setCellValueFactory(new PropertyValueFactory<Car,String>("name"));
		insurance.setCellValueFactory(new PropertyValueFactory<Car,LocalDate>("insurance"));
		technical.setCellValueFactory(new PropertyValueFactory<Car,LocalDate>("technical"));
		currTachometer.setCellValueFactory(new PropertyValueFactory<Car,Integer>("currentTachometer"));
		buyTachometer.setCellValueFactory(new PropertyValueFactory<Car,Integer>("buyTachometer"));
		
		insurance.setCellFactory(new Callback<TableColumn<Car,LocalDate>, TableCell<Car, LocalDate>>(){
			@Override
			public TableCell<Car, LocalDate> call(TableColumn<Car, LocalDate> arg0){
				return new DatePickerCellCar();
			}
		});
		//just editable insurance and technical
		insurance.setEditable(true);
		
		technical.setCellFactory(new Callback<TableColumn<Car,LocalDate>, TableCell<Car, LocalDate>>(){
			@Override
			public TableCell<Car, LocalDate> call(TableColumn<Car, LocalDate> arg0){
				return new DatePickerCellCar();
			}
		});
		
		//just editable technical and insurance
		technical.setEditable(true);
		
		carTable.getColumns().addAll(spz,name,insurance,technical,currTachometer,buyTachometer);
		carTable.setItems(cars);
		carTable.setMinHeight(500);
		carTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		carTable.setEditable(true);
		BorderPane.setMargin(table, new Insets(3));
		
		controls.getChildren().addAll(carAddNewCar,addCarPane,carTable,carDeleteCar);
		controls.setPadding(new Insets(10));
		controls.setSpacing(5);;
		
		
		return controls;
	}
	
	/**
	 *  Create or remove pane which used to add to table in third pane
	 * if pane dont have any children create it(basically first click)
	 * if pane have children remove all from pane(basically second click, when we want hide it)
	 */
	public void addToCarTable(){
		if(addCarPane.getChildren().size()==0){
			HBox control = new HBox();
			
			TextField addSpz = new TextField();
			TextField addNameCar = new TextField();
			DatePicker addInsurance = new DatePicker();
			DatePicker addTechnical = new DatePicker();
			TextField addBuyT = new TextField();
			
			Button add = new Button("Add");
			
			add.setOnAction(event -> {
				if(addSpz.getLength()==0||addNameCar.getLength()==0||addInsurance.getValue()==null||addTechnical.getValue()==null||addBuyT.getLength()==0){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Insert ERROR");
					alert.setHeaderText("please check if you insert all columns");
					alert.showAndWait();
				}else{
					addCar(addSpz.getText(),
							addNameCar.getText(),
							Integer.parseInt(addBuyT.getText().trim()),
							addInsurance.getValue(),
							addTechnical.getValue());
							
					addCarPane.getChildren().clear();
				}
			});
			
			
			addSpz.setMinWidth(200);
			addSpz.setPromptText("SPZ");
			addNameCar.setMinWidth(150);
			addNameCar.setPromptText("Car name");
			addInsurance.setMaxWidth(160);
			addInsurance.setPromptText("Insurance ends");
			addInsurance.setMinWidth(160);
			addInsurance.setEditable(false);
			addTechnical.setMaxWidth(160);
			addTechnical.setPromptText("Technical ends");
			addTechnical.setMinWidth(160);
			addTechnical.setEditable(false);
			addBuyT.setPromptText("Tachometer status");
			addBuyT.textProperty().addListener(new ChangeListener<String>() {
			    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			        if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			            addBuyT.setText(oldValue);
			        }
			    }
			});
			
			control.setSpacing(5);
			control.getChildren().addAll(addSpz,addNameCar,addInsurance,addTechnical,addBuyT);
			addCarPane.setSpacing(5);
			addCarPane.getChildren().addAll(control,add);
			addCarPane.setPadding(new Insets(5));
		}else addCarPane.getChildren().clear();
		
	}
	
	/**
	 * Delete selected item in table for cars
	 */
	public void tableCarDeleteSelected(){
		//získaní označených dat
		ObservableList<Car> selection = FXCollections.observableArrayList(carTable.getSelectionModel().getSelectedItems()) ;
			//pokud nic není vybráno
			if(selection.size()==0){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Deleting items");
				alert.setHeaderText("No items for deleting were selected!");
				alert.setContentText("Please select items for deleting.");
				alert.show();
			}else{
				// dialog okno pro potvrzení
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Deleting selection");
				alert.setHeaderText("Do you want to delete selected elements?");
				// zobrazení vybraných dat
				alert.setContentText(selection.toString());
				// ceka na odpoved
				alert.showAndWait()
					 // pokud ok odstaním itemy
					 .filter(response -> response == ButtonType.OK)
					 .ifPresent(response -> {
						 carTable.getItems().removeAll(selection);
						 carTable.getSelectionModel().clearSelection();
						
				 });
			
			}
		}
	
	
	/**
	 * Method to create instance of record
	 * @param car car name
	 * @param person driver name
	 * @param date date of driving
	 * @param startP source point
	 * @param destP destination point
	 * @param distance distance
	 * @param tachometer tachometer after drive
	 * @param type type of driving
	 */
	private void addRecord(Car car, Person person, LocalDate date, String startP, String destP, int distance, int tachometer, String type){
		
		
			if(car.getCurrentTachometer()<tachometer){
				Record record = new Record(car,person,date,startP,destP,distance,tachometer,type);
				table.getItems().add(record);
				car.setCurrentTachometer(tachometer);
				carTable.refresh();
				addedRecords.add(record);
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Current tachometer ERROR");
				alert.setHeaderText("Input tachometer is smaller than current tachometr.Please check tachometer again");
				alert.setContentText("Last input tachometer is "+car.getCurrentTachometer());
				alert.showAndWait();	
			}
	}
	
	/**
	 * Method to create instance if car
	 * @param spz spz of car
	 * @param name name of car 
	 * @param buyTa 
	 * @param insurance
	 * @param technical
	 */
	private void addCar(String spz,String name,int buyTa,LocalDate insurance,LocalDate technical){
		Car car = new Car(spz,name,buyTa,insurance,technical);
		carTable.getItems().add(car);
		
	}
	
	private Node getManage(){
		GridPane organization = new GridPane();
		
		//------------------tree
		treePeople = new TreeView<>();
		organization.add(treePeople, 0, 0);
		TreeItem rootP = new TreeItem("People");
		treePeople.setRoot(rootP);
		treePeople.setShowRoot(false);
		
	
		TreeItem TIadmin = new TreeItem("Admins");
		TreeItem TIowner = new TreeItem("Owners");
		TreeItem TIdriver = new TreeItem("Drivers");
		
		
		//rootP.getChildren().addAll(TIpeople,TIcars);
		rootP.getChildren().addAll(TIadmin,TIowner,TIdriver);	
		//TIadmin.getChildren().sort(Comparator.comparing(t->((<T) t).getValue().length()));
		
		
		//TIdriver.getChildren().addAll(getPeople());
		for(int i = 0;i<people.size();i++){
			if(people.get(i).getAuthority()==2){
				TIdriver.getChildren().add(new TreeItem<Person>(people.get(i)));
			}else if(people.get(i).getAuthority()==1){
				TIowner.getChildren().add(new TreeItem<Person>(people.get(i)));
			}else if(people.get(i).getAuthority()==0){
				TIadmin.getChildren().add(new TreeItem<Person>(people.get(i)));
			}
		}
		BorderPane.setMargin(treePeople, new Insets(5));
		
		
		treePeople.setVisible(true);
		treePeople.setMaxWidth(300);
		treePeople.setMinWidth(300);
		
		treePeople.setOnEditCommit(event ->{
			//pokud se něco změní, tak to seřadí
			//sortChildren(event.getTreeItem().getParent());
			
			//vytvořím text do tabulky vpravo
			textArea.setText(createDescriptionPeople());
		});
		
		treePeople.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends TreeItem<Person>> change) -> {
			
			textArea.setText(createDescriptionPeople());
		});
		
		//root.add(TIcars);
		//-------textarea
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefColumnCount(20);
		textArea.setMaxWidth(300);
		BorderPane.setMargin(textArea, new Insets(5));
		organization.add(textArea,2,0);
		
		organization.setPadding(new Insets(5));
		organization.setHgap(10);
		organization.setVgap(10);
		
		//-------------------buttons
		managePanel = new HBox();
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button reset = new Button("Reset password");
		newName = new TextField();
		
		add.setMinWidth(100);
		reset.setMinWidth(100);
		remove.setMinWidth(100);
		remove.setOnAction(event->deletePersonTree());
		
		
		newName.setMinWidth(200);
		newName.setPromptText("Input name of newbie");
		
		add.setOnAction(event ->addToTree());
		reset.setOnAction(event ->resetPassword());
		
		managePanel.getChildren().addAll(newName,add,remove,reset);
		managePanel.setSpacing(10);
		managePanel.setAlignment(Pos.CENTER);
		organization.add(managePanel, 0, 1, 4, 1);
		organization.setAlignment(Pos.CENTER);
		
		
		return organization;
	}
	
	private void addToTree(){
		TreeItem<Person> selected = treePeople.getSelectionModel().getSelectedItem();
		if(selected==null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nothing group is selected");
			alert.setHeaderText("No group is selected for person!");
			alert.setContentText("Please select group.");
			alert.show();
		}else if(newName.getText().equals("")){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nothing name input");
			alert.setHeaderText("Missing input name of person!");
			alert.setContentText("Please insert people's name.");
			alert.show();
			
		}else{
		
			int newID = Integer.parseInt(people.get(people.size()-1).getID())+1;
			String stringNewID = String.valueOf(newID);
			if(stringNewID.length()==1){
				stringNewID = "000"+stringNewID;
			}else if(stringNewID.length()==2){
				stringNewID = "00"+stringNewID;
			}else if(stringNewID.length()==3){
				stringNewID = "0"+stringNewID;
			}
			int authority=0;
			if(selected.toString().contains("Drivers")||selected.getParent().toString().contains("Drivers")){
				authority = 2;
			}else if(selected.toString().contains("Owners")||selected.getParent().toString().contains("Owners")){
				authority = 1;
			}
			
			Person newBie = new Person(stringNewID, newName.getText(),authority);
			
			if(!selected.isLeaf()){
			selected.getChildren().add(new TreeItem<Person>(newBie));
			}else{
				selected.getParent().getChildren().add(new TreeItem<Person>(newBie));
			}
			people.add(newBie);
			
			if(newBie.getAuthority() ==2){
				drivers.add(newBie);
			}
			newName.setText("");
			
			addPane.getChildren().clear();
			
			sortChildren(selected);
			
		}
	}
	
	private String createDescriptionPeople(){
		TreeItem test = treePeople.getSelectionModel().getSelectedItem();
		if(test!=null){
			if((!test.getValue().equals("Admins"))&&(!test.getValue().equals("Drivers"))&&(!test.getValue().equals("Owners"))){
			
				TreeItem<Person> selected = treePeople.getSelectionModel().getSelectedItem();
				if(selected !=null){
					String parent;
					if(selected.getValue().getAuthority()==0){
						parent = "Admin";
					}else if(selected.getValue().getAuthority()==1){
						parent = "Owner";
					}else{
						parent = "Driver";
					}
					String ID = selected.getValue().getID();
					String name = selected.getValue().getFullName();
					return "Group: "+parent+"\nID: "+ID+"\nName: "+name+"\n";
				}
			}
		}
		return "";
	}
	
	private void deletePersonTree(){
			//získaní označených dat
		TreeItem<Person> treeSelection = treePeople.getSelectionModel().getSelectedItem() ;
		if(treeSelection.isLeaf())	{
		//pokud nic není vybráno
			if(treeSelection==null){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Delete item");
				alert.setHeaderText("No item for deleting was selected!");
				alert.setContentText("Please select item for deleting.");
				alert.show();
			}else{
				Alert alert = new Alert(AlertType.CONFIRMATION); 
				alert.setTitle("Deleting");
				alert.setHeaderText("Do you want to delete selected person ?");				
				alert.setContentText("Selected: " + treeSelection.getValue().getID()+" - "+treeSelection.getValue().getFullName());
				alert.showAndWait()
				 .filter(response -> response == ButtonType.OK)
				 .ifPresent(response -> {
					 //mažu rodiči jeho potomka
					 treeSelection.getParent().getChildren().remove(treeSelection);
					 
					 people.remove(treeSelection.getValue());
					if(treeSelection.getValue().getAuthority()==2){
						drivers.remove(treeSelection.getValue());
					}
					 //vyčistím selected 
					 treePeople.getSelectionModel().clearSelection();
					 textArea.setText("");
			 });				
			}
		}
	}
	
	private void resetPassword(){
		TreeItem<Person> treeSelection = treePeople.getSelectionModel().getSelectedItem() ;
		if(treeSelection !=null){
			if(!treeSelection.isLeaf())	{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Select people");
				alert.setHeaderText("No people for reset password was selected!");
				alert.setContentText("Please select people for reset password.");
				alert.show();
			}else{
				treeSelection.getValue().resetPass(treeSelection.getValue());
			}
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Select people");
			alert.setHeaderText("No people for reset password was selected!");
			alert.setContentText("Please select people for reset password.");
			alert.show();
		}
	}

	private void closeWindow(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Log out");
		alert.setHeaderText("Do you want to log out?");
		alert.showAndWait()
			 .filter(response -> response == ButtonType.OK)
			 .ifPresent(response -> {
				 addPane.getChildren().clear();
				 stage.setScene(getSceneLog());
				 centreStage();
		 });
	
	}
	
	private ObservableList<Record> filtredData(Car car,Person person,String type){
		ObservableList<Record> data = getInitData();
		ObservableList<Record> filtredData = FXCollections.observableArrayList();
		data.addAll(addedRecords);
		if(car == null&&person==null&&type == null){
			
			return data;
		}
		if(car != null&&person==null&&type == null){
		
			for(Record r:data){
				if(r.getCarType().equals(car)){

					filtredData.add(r);
				}
			}
			return filtredData;
		}
		
		if(car == null&&person!=null&&type == null){
			for(Record r:data){
				if(r.getDriverName().equals(person)){
					filtredData.add(r);
				}
			}
			return filtredData;
		}
		if(car == null&&person==null&&type != null){
			for(Record r:data){
				if(r.getDrivingTyp().equals(type)){
					filtredData.add(r);
				}
			}
			return filtredData;
		}
		if(car != null&&person==null&&type != null){
			for(Record r:data){
				if(r.getCarType().equals(car)&&r.getDrivingTyp().equals(type)){
					filtredData.add(r);
				}
			}
			return filtredData;
		}
		if(car != null&&person!=null&&type == null){
			for(Record r:data){
				if(r.getCarType().equals(car)&&r.getDriverName().equals(person)){
					filtredData.add(r);
				}
				
			}
			return filtredData;
		}
		if(car == null&&person!=null&&type != null){
			for(Record r:data){
				if(r.getDriverName().equals(person)&&r.getDrivingTyp().equals(type)){
					filtredData.add(r);
				}
			}
			return filtredData;
		}
		if(car != null&&person!=null&&type != null){
			for(Record r:data){
				if(r.getCarType().equals(car)&&r.getDriverName().equals(person)&&r.getDrivingTyp().equals(type)){
					filtredData.add(r);
				}
			}
			return filtredData;
		}	
		return null;
	}
	
	
	private ObservableList<Record> getInitData() {
		ObservableList<Record> RecordData = FXCollections.observableArrayList();
		
		
		RecordData.add(new Record(cars.get(0), drivers.get(3), LocalDate.of(2000, 1, 12),"Radonice","Plzeň",87,127490,"Private"));
		RecordData.add(new Record(cars.get(1), drivers.get(0), LocalDate.of(2017, 5, 5),"Plzeň","Praha",98,127578,"Work"));
		RecordData.add(new Record(cars.get(3), drivers.get(3), LocalDate.of(2014, 3, 28),"Plzeň","Chomutov",123,107032,"Work"));
		RecordData.add(new Record(cars.get(2), drivers.get(1), LocalDate.of(2010, 4, 19),"Plzeň","Ostrava",300,157022,"Work"));
		RecordData.add(new Record(cars.get(0), drivers.get(1), LocalDate.of(2011, 4, 3),"Ostrava","Plzeň",300,107032,"Work"));
		RecordData.add(new Record(cars.get(0), drivers.get(2), LocalDate.of(2015, 3, 15),"Litvínov","Plzeň",121,57032,"Work"));
		RecordData.add(new Record(cars.get(2), drivers.get(1), LocalDate.of(2013, 6, 7),"Praha","Olomouc",200,100032,"Work"));
		RecordData.add(new Record(cars.get(1), drivers.get(2), LocalDate.of(2016, 5, 27),"Brno","Kadaň",450,111022,"Work"));
		RecordData.add(new Record(cars.get(3), drivers.get(0), LocalDate.of(2017, 9, 4),"Most","Litvínov",20,293120,"Work"));
	
		return RecordData;
	}
	
	private void generatePeople(){
		people = FXCollections.observableArrayList();
		
		people.add(new Person("0000", "0001", "admin00", 0));
		people.add(new Person("0002", "0002", "admin01", 0));
		people.add(new Person("0003", "0004", "Owner01", 1));
		people.add(new Person("0004", "0004", "Čarnogurský Jan", 1));
		people.add(new Person("0005", "0005", "Vaněk Kuba", 2));
		people.add(new Person("0006", "0007", "Danišík Vojtěch", 2));
		people.add(new Person("0007", "0007", "Kraus Petr", 2));
		people.add(new Person("0008", "0008", "Pizur Jan", 2));
	}
	
	private void generateCars(){
		cars = FXCollections.observableArrayList();
		
		cars.add(new Car("1A3 456", "Citroen Berlingo", 100000, LocalDate.of(2018, 1, 1), LocalDate.of(2019, 8, 4)));
		cars.add(new Car("2U4 755", "Mercedes Sprinter", 50123, LocalDate.of(2017, 12, 1), LocalDate.of(2020, 1, 1)));
		cars.add(new Car("8P5 470", "Škoda Octavia", 17000, LocalDate.of(2019, 12, 12), LocalDate.of(2019, 12, 12)));
		cars.add(new Car("6O3 989", "VW Golf", 1, LocalDate.of(2021, 1, 1), LocalDate.of(2018, 3, 4)));
		

		
	}
	private void generateDrivers(){
		drivers = FXCollections.observableArrayList();
		
		for(Person p:people){
			if(p.getAuthority()==2){
				drivers.add(p);
			}
		}
	}
	private void sortChildren(TreeItem<Person> parent) {
		//pokud máme rodiče
		if (parent != null) {
			// řazení potomků
			parent.getChildren().sort((item1, item2) -> {
				//porovnávání dvou
				return item1.getValue().compareTo(item2.getValue());
			});
		}
	}
	
	private void setLoggedPerson(Person loggedPerson){
		this.loggedPerson = loggedPerson;
	}
	
	protected ObservableList<Person> getPeople(){
		return people;
	}
}


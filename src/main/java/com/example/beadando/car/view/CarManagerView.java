package com.example.beadando.car.view;

import com.example.beadando.car.entity.CarEntity;
import com.example.beadando.car.service.CarService;
import com.example.beadando.core.component.MenuComponent;
import com.example.beadando.manufacturer.entity.ManufacturerEntity;
import com.example.beadando.manufacturer.service.ManufacturerService;
import com.sun.xml.bind.v2.runtime.property.Property;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

//http://localhost:8082/carmanager
@Route
public class CarManagerView extends VerticalLayout {
    private CarEntity selectedCarEntity;
    private VerticalLayout form;
    private TextField type;
    private ComboBox<ManufacturerEntity> manufacturer;
    private final TextField doorNumber = new TextField();
    private final TextField yearOfManufacturer = new TextField();


    private Binder<CarEntity> binder;
    @Autowired
    private CarService service;
    @Autowired
    private ManufacturerService manufacturerService;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        Grid<CarEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(CarEntity::getId).setHeader("ID");
        grid.addColumn(CarEntity::getType).setHeader("Type");
        grid.addColumn(carEntity -> {
            if (carEntity.getManufacturer() != null) {
                return carEntity.getManufacturer().getName();
            }
            return "";
        }).setHeader("Manufacturer");
        grid.addColumn(CarEntity::getDoor_number).setHeader("door_number");
        grid.addColumn(CarEntity::getYearOfManufacturer).setHeader("year_of_manufacturer");

        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<CarEntity> grid) {

        form = new VerticalLayout();
        binder = new Binder<>(CarEntity.class);
        type = new TextField();
        form.add(new Text("Type"), type);
        manufacturer = new ComboBox<>();

        binder.forField(doorNumber).withConverter(new StringToIntegerConverter("Must be a number!"))
                .asRequired("The number of doors are required")
                .withValidator(door -> door >= 0,"Must be larger then 1")
                .withValidator(door -> door <= 6,"Must be lower then 7")
                .bind(CarEntity::getDoor_number, CarEntity::setDoor_number);
        binder.forField(yearOfManufacturer).withConverter(new StringToIntegerConverter("Must be a number!"))
                .asRequired("Year of manufacture is required")
                .withValidator(year -> year >= 1900, "Must be larger then 1900")
                .withValidator(year -> year <= 2100,"Must be lower then 2100")
                .bind(CarEntity::getYearOfManufacturer, CarEntity::setYearOfManufacturer);
        manufacturer.setItems(manufacturerService.findAll());
        manufacturer.setItemLabelGenerator(manufacturerEntity -> manufacturerEntity.getName());
        form.add(new Text("Manufacturer"), manufacturer);
        form.add(new Text("door_number"), doorNumber);
        form.add(new Text("year_of_manufacturer"), yearOfManufacturer);


        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addClickListener(buttonClickEvent -> {
            if (selectedCarEntity.getId() != null) {
                service.update(selectedCarEntity);
            } else {
                service.create(selectedCarEntity);
            }
            grid.setItems(service.findAll());
            form.setVisible(false);
            Notification.show("Succesfully Saved!");
        });
        form.add(saveBtn);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);

    }

    private void addButtonBar(Grid<CarEntity> grid) {
        HorizontalLayout buttonBar = new HorizontalLayout();

        Button deleteBTN = new Button();
        deleteBTN.setEnabled(false);
        deleteBTN.setText("Delete");
        deleteBTN.setIcon(VaadinIcon.TRASH.create());
        deleteBTN.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedCarEntity.getId());
            grid.setItems(service.findAll());
            selectedCarEntity = null;
            deleteBTN.setEnabled(false);
            form.setVisible(false);
            Notification.show("Succesfully Deleted");
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedCarEntity = event.getValue();
            deleteBTN.setEnabled(selectedCarEntity != null);
            form.setVisible(selectedCarEntity != null);
            binder.setBean(selectedCarEntity);


        });

        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.addClickListener(buttonClickEvent -> {
            selectedCarEntity = new CarEntity();
            binder.setBean(selectedCarEntity);
            form.setVisible(true);
        });
        addBtn.setIcon(VaadinIcon.PLUS.create());
        buttonBar.add(deleteBTN, addBtn);
        add(buttonBar);

    }
}

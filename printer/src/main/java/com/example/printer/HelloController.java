package com.example.printer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Label selection;

    @FXML
    private Button closeButton;

    @FXML
    private ListView<CheckBox> listView;

    @FXML
    private Button print;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(getPrinterNames());
        print();
    }

    @FXML
    void onCloseButtonClick(ActionEvent event) {
        System.exit(0);
    }


    private static String getPrinterNames() {
        String content = "";
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        content += "Number of print services: " + services.length;
        content += "\n";

        if (services.length != 0 || services != null) {
            int i = 1;
            for (PrintService service : services) {
                String name = service.getName();

                content += "Printer " + i + " name: " + name;
                content += "\n";
                i++;
            }
        }

        System.out.println(content);
        return content;
    }

    public void print() {
        if(l.isEmpty()){
            print.setDisable(true);
        }
        for (CheckBox ll : l) {
            if (ll.isSelected()) {
                System.out.println(ll.getText());
            }
        }
        String printerName = "Microsoft XPS Document Writer";


        // Get the specified printer
        PrintService printerService = getPrintServiceByName(printerName);

        if (printerService != null) {
            // Create a simple document with your content
            String content = "This is the content that will be printed.";
            byte[] contentBytes = content.getBytes();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            SimpleDoc doc = new SimpleDoc(contentBytes, flavor, null);

            // Create a print request attribute set
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            // Print the document to the specified printer
            try {
                System.out.println(printerName);
                printerService.createPrintJob().print(doc, attributeSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Printer not found: " + printerName);
        }
    }

    private static PrintService getPrintServiceByName(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : printServices) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }

    public String[] listFilesForFolder(final File folder) {
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry.getName());
            }
        }
        String[] s = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            s[i] = files.get(i);
        }
        return s;
    }

    ObservableList<CheckBox> l;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Java");
        list.add("python");
        l = FXCollections.observableArrayList();
        for (int i = 0; i < 100; i++) {
            var cb = new CheckBox("hi " + i);
            l.add(cb);
        }
        listView.getItems().setAll(l);
//        selection.setText(l.get(0).isSelected()?"Nothing selected":l.get(0).getText());
////        listView.getSelectionModel().setSelectionMode(MULTIPLE);
//        listView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
    }

//    private void selectionChanged(ObservableValue<? extends CheckBox> Observable, CheckBox oldVal, CheckBox newVal) {
//        ObservableList<CheckBox> selectedItems = listView.getSelectionModel().getSelectedItems();
//        String getSelectedItem = (selectedItems.isEmpty()) ? "No Selected Item" : selectedItems.toString();
//        selection.setText(getSelectedItem);
//    }
}
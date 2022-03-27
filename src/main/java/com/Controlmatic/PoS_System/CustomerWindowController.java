package com.Controlmatic.PoS_System;

import com.Controlmatic.PoS_System.api.HTTPRequest;
import com.Controlmatic.PoS_System.dao.ProductCatalog;
import com.Controlmatic.PoS_System.dao.SaleRepository;
import com.Controlmatic.PoS_System.model.*;
import com.Controlmatic.PoS_System.model.XML.ObjectToXML;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static com.Controlmatic.PoS_System.model.ChangeCalculator.calculateChange;
import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;
import static javafx.collections.FXCollections.observableArrayList;


/**
 * Disgusting Gud-klass, får se om vi hinner refactora eller nä
 */
@Component
public class CustomerWindowController {

    //region Cashiers stuff
    public TextField totalSumCashier;
    public Button barcodeScanner;
    public Button removeProduct;
    public TextField barcodeField;
    public TextField nameField;
    public TextField discountField;
    public ListView<String> cashierProductsList;
    //endregion

    //region Customers stuff
    public TextField totalSumCustomer;
    public Button enablePayment;
    public Button card;
    public Button cash;
    public Button split;
    public Button receiptButton;
    public ListView<String> customerProductsList;
    public TextField[] retrievalFields;
    //endregion

    public ObservableList<Product> backendProductsList;
    public TextArea DialogTextArea;
    private final LinkedHashMap<String, Double> cashMap = populateCashMap();
    private final ProductCatalog catalog;
    private Sale sale;
    private int listIdx;
    @FXML
    private AnchorPane ap;

    /**
     *
     * @return simulerad plånbok
     */
    private LinkedHashMap<String, Double> populateCashMap() {
        LinkedHashMap<String, Double> cashMap = new LinkedHashMap<>();
        for(Cash.Note note : Cash.Note.values())
            cashMap.put(note.name(), note.getValue());
        for(Cash.Coin coin : Cash.Coin.values())
            cashMap.put(coin.name(), coin.getValue());
        return cashMap;
    }

    final
    SaleRepository repository;

    @Autowired
    public CustomerWindowController(@Qualifier("product") ProductCatalog catalog, SaleRepository repository) {
        this.catalog = catalog;
        this.repository = repository;
    }


    public void initialize() {
        sale = new Sale(catalog);
        listIdx = -1;
        backendProductsList = observableArrayList();
        retrievalFields = new TextField[] {barcodeField, nameField, discountField/*, keywordsField*/};
    }

    public void printOutReceipt() throws IOException {
        Receipt receipt = new Receipt(sale);
        receipt.printOutReceipt();
    }

    private void setProductsListItems(List<Product> products) {
        ObservableList<String> strings = observableArrayList();
        formatOutput(products, strings);

        customerProductsList.setItems(strings);
        cashierProductsList.setItems(strings);
        displayTotalSum();
    }

    /*
    Should ideally be initialised from som sort of .cfg type file but heh time heh
     */
    private void formatOutput(List<Product> products, ObservableList<String> strings) {
        for(Product p : products) {
            if(p.getDiscount() > 0) {
                strings.add(p.getName() + " : " + p.getPrice() + "\n" + p.getDiscount() + "%");
            } else {
                strings.add(p.getName() + " : " + p.getPrice());
            }

        }
    }

    private void resetProductsListFrontAndBack() {
        sale = new Sale(catalog);
        setProductsListItems(sale.getProducts());
        displayTotalSum();
    }

    public void CreatePaymentRequest(ActionEvent event) throws IOException {
        //source is always button, cba
        Button source = (Button)event.getSource();
        if(source.getId().equals("cash")) {
            CreateCashPaymentDialog();
        } else if (source.getId().equals("card")) {
            CreateCardPaymentRequest();
        } else {
            CreateSplitPaymentRequest();
        }

    }

    private void CreateSplitPaymentRequest() throws IOException {

        GridPane grid = ConfigureGridTransformAndContent(cashMap);
        Optional<String> result = configureCashPaymentDialog(grid).showAndWait();
        double cashPaymentSubTotal = 0;

        if(result.isPresent()) {

           cashPaymentSubTotal = getCashPaymentSubTotal(grid);
        }

        String saleXml = ObjectToXML.marshal(Sale.class, sale);
        HTTPRequest.makePostRequest("http://localhost:8080/api/payment/split?cash=" + cashPaymentSubTotal
                                    + "&card=" + (sale.getTotalSum()-cashPaymentSubTotal), saleXml);

        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/reset");

        getReceiptDialog();
        resetProductsListFrontAndBack();
    }



    private void CreateCardPaymentRequest() throws IOException {
        String saleXml = ObjectToXML.marshal(Sale.class, sale);
        HTTPRequest.makePostRequest("http://localhost:8080/api/payment/card", saleXml);
        getReceiptDialog();
        resetProductsListFrontAndBack();
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/reset");

    }

    private void CreateCashPaymentDialog() throws IOException {
        GridPane grid = ConfigureGridTransformAndContent(cashMap);
        Dialog<String> cashPayment = configureCashPaymentDialog(grid);
        Optional<String> result = cashPayment.showAndWait();
        double total = parseDouble(DialogTextArea.getText());

        HTTPRequest.makePostRequest("http://localhost:9001/cashbox/open");
        if(total <= 0) {
            if(result.isPresent()) {
                String xmlString = ObjectToXML.marshal(Sale.class, sale);
                HTTPRequest.makePostRequest("http://localhost:8080/api/payment/cash", xmlString);
                showChangeDialog(total);
                getReceiptDialog();

            }
        }
        else {
            System.out.println("Not enough money");
        }
    }

    @NotNull
    private Dialog<String> configureCashPaymentDialog(GridPane grid) {
        Dialog<String> cashPayment = new Dialog<>();

        cashPayment.setHeaderText("To pay with cash");
        cashPayment.getDialogPane().setContent(grid);
        cashPayment.getDialogPane().getButtonTypes().add(ButtonType.OK);
        return cashPayment;
    }

    private void showChangeDialog(double change) {
        if(change >= 0) return;
        Alert changeAlert = new Alert(Alert.AlertType.CONFIRMATION);
        changeAlert.setTitle("Change");

        //w/e
        changeAlert.setContentText("Change: " + -calculateChange(sale.getTotalSum(),
                //more icky wicky wacky woo fö ja trodde ja va smart men sen va ja dum å orka int ändra
                sale.getTotalSum()-parseDouble(DialogTextArea.getText())));

        changeAlert.showAndWait();

    }

    private double getCashPaymentSubTotal(GridPane grid) {
        double cashPaymentSubTotal = 0;
        for(Node n : grid.getChildren())
            if(n instanceof TextField curr) //try not to be sick
                if(!curr.isEditable()) //icky wicky :/
                    cashPaymentSubTotal += parseDouble(curr.getText());
        return cashPaymentSubTotal;
    }

    public void getReceiptDialog() throws IOException {
        Alert receiptAlert = new Alert(Alert.AlertType.NONE);
        receiptAlert.getButtonTypes().add(ButtonType.YES);
        receiptAlert.getButtonTypes().add(ButtonType.NO);
        receiptAlert.setTitle("Receipt?");
        receiptAlert.setContentText("Would you like a receipt?");
        Optional<ButtonType> result = receiptAlert.showAndWait();

        if(result.isPresent())
            if(result.get() == ButtonType.YES) {
                printOutReceipt();
            }
        resetProductsListFrontAndBack();
    }


    private GridPane ConfigureGridTransformAndContent(LinkedHashMap<String, Double> hashMap) {

        GridPane grid = new GridPane();
        DialogTextArea = new TextArea();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        DialogTextArea.setPrefRowCount(1);
        DialogTextArea.setText(valueOf(viaBigDecimal(sale.getTotalSum())));

        int i = 0;
        for(String s : hashMap.keySet()) {

            TextField tf = new TextField();
            TextField totalRow = new TextField();

            tf.setPromptText(s);
            totalRow.setText(valueOf(0.0));
            totalRow.setEditable(false);
            SetTextFieldOnAction(hashMap, tf, totalRow, new CashPaymentHandler());

            grid.addRow(i, tf, totalRow);
            i++;
        }
        grid.addRow(i, DialogTextArea);

        return grid;
    }

    private void SetTextFieldOnAction(LinkedHashMap<String, Double> hashMap, TextField tf, TextField totalRow, CashPaymentHandler cph) {
        tf.setOnAction(observable -> {

            double value = getTotalValueOfCashField(hashMap, tf);
            totalRow.setText(tf.getText().isEmpty() ? valueOf(viaBigDecimal(0)) : valueOf(viaBigDecimal(value)));

            double current = Objects.equals(DialogTextArea.getText(), "") ? 0 : parseDouble(DialogTextArea.getText());
            cph.setCurrentValue(value);

            DialogTextArea.setText((valueOf(viaBigDecimal(current - cph.getCurrentValue()))));
        });
    }

    private double viaBigDecimal(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    private double getTotalValueOfCashField(LinkedHashMap<String, Double> hashMap, TextField tf) {
        int shit = Integer.parseInt(Objects.equals(tf.getText(), "") ? "0" : tf.getText());
        return hashMap.get(tf.getPromptText()) * shit;
    }

    public void displayTotalSum(){ //skall uppdatera och visa totalsum varje gång en product addas eller tas bort
        double totalAmount = sale != null ? sale.getTotalSum() : 0;
        totalSumCustomer.setText(String.valueOf(totalAmount));
        totalSumCashier.setText(String.valueOf(totalAmount));
        //skall visas i båda total sum fields
    }

    public void addProduct(ActionEvent event) throws IOException{ //när add knappen trycks skall producten läggas till
        if(sale == null)
            sale = new Sale(catalog);

        if(!barcodeField.getText().equals("")) {
            //vi ogillar icke-tal...
            try {
                Integer.parseInt(barcodeField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Not a barcode! Try again!");
                return;
            }
            // Checks if the product has a discount and adds it, buuut will probs go BOOM if someone has written text/symbols in discountField
            sale.addToSaleByBarcode(barcodeField.getText(), discountField.getText());
        }

        else if(!nameField.getText().equals("")) {
            sale.addToSaleByName(nameField.getText(), discountField.getText());
        }

        //någå sånhärant
        setProductsListItems(sale.asObservable());

        for(TextField tf : retrievalFields)
            tf.setText("");

    }

    public void removeProduct(ActionEvent event) {
        if(sale == null) {
            return;
        }
        //när remove knappen trycks så ska en produkt tas bort
        if(listIdx != -1) //ifall listIdx är noll är ingen produkt vald
            sale.removeFromSale(listIdx);
        setProductsListItems(backendProductsList);
        // måst vi adda mera exceptions??

    }

    public void shelfSale(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Sales"));
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Sales object (*.sale)", "*.sale");
        fileChooser.getExtensionFilters().add(ext);
        File file = fileChooser.showSaveDialog(ap.getScene().getWindow());

        try {
            Sale.save(file.getPath(), sale);
        }
        catch (NullPointerException ignored) {

        }
        resetProductsListFrontAndBack();
    }

    public void unShelfSale(ActionEvent event) {
        Open((Stage)ap.getScene().getWindow());
    }

    private void Open(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Sales"));
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Sales object (*.sale)", "*.sale");
        fileChooser.getExtensionFilters().add(ext);
        File file = fileChooser.showOpenDialog(stage);
        sale = null;

        if (file != null) {
            sale = Sale.load(file.getPath());
            if (sale != null) {
                sale.setCatalog(catalog);
                backendProductsList.clear();
                backendProductsList.addAll(sale.getProducts());
                setProductsListItems(backendProductsList);
            }
        }
    }

    public Sale unShelfMostRecent(ActionEvent event) {
        return null;
    }

    private void finishSale() {

    }

    @FXML
    public void selectListItem(MouseEvent event) {
        customerProductsList.setOnMouseClicked(new ListViewHandler() {
            @Override
            public void handle(MouseEvent event) {
                setListIndex(customerProductsList.getSelectionModel().getSelectedIndex());
            }
        });
        cashierProductsList.setOnMouseClicked(new ListViewHandler() {
            @Override
            public void handle(MouseEvent event) {
                setListIndex(cashierProductsList.getSelectionModel().getSelectedIndex());
            }
        });
    }

    public void setListIndex(int index) {
        listIdx = index;
    }

}

class ListViewHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {

    }

}

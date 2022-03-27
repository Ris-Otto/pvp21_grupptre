package com.Controlmatic.PoS_System.model;


import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class Receipt {

    private final double totalSum;
    private final Sale sale;
    private final Date paymentDate;
    private static int counter = 0;


    public Receipt(Sale sale) {
        this.totalSum = sale.getTotalSum();
        this.sale = sale;
        this.paymentDate = new Date();
    }

    public boolean printOutReceipt() throws IOException {
        String toPrint = configureReceipt();
        boolean toReturn = !toPrint.isEmpty();
        if(toReturn)
            makePng(toPrint);
        return toReturn;
    }

    private void makePng(String receiptString) throws IOException {
        BufferedImage img = ImageIO.read(new File("Receipts/EmptyReceipt.png"));
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        Font font = new Font("Serif", Font.PLAIN, 20);
        graphics2D.setColor(new Color(0,0,0));
        graphics2D.setFont(font);
        graphics2D.drawImage(img, 0, 0, null);
        drawStringWithNewLine(graphics2D, receiptString, 20, 30);
        graphics2D.dispose();
        File file = new File("Receipts/Sale"+ counter +".png");
        ImageIO.write(bufferedImage, "png", file);
    }

    private void drawStringWithNewLine(Graphics2D graphics2D, @NotNull String text, int x, int y) {
        for(String line : text.split("\n")) {
            graphics2D.drawString(line, x, y += graphics2D.getFontMetrics().getHeight());
        }
    }

    private @NotNull String configureReceipt() { //basicest basic kvitto, enjoy
        ++counter;
        if(sale.getProducts().isEmpty()) return "";
        StringBuilder toReturn = new StringBuilder();
        for(Product p : sale.getProducts()) {
            toReturn.append("\n").append(p.getName());
            if(p.getDiscount() > 0)
                toReturn.append(" - ").append(p.getDiscount()).append(" %");
            toReturn.append("     ").append(p.getPrice());
        }

        /*
        * Nu e de ju sen så att den här dumma maskinen int ännu vet hur många paymentTypes en försäljning har
        * eftersom det först deklareras i PaymentDataAccess :DDDDDDD onpas hauskaa!
        * Dehä orkar ja int me så de får bli! Unlucky! tick tock
        * */
        /*for (Payment payment : sale.getPayment())
            toReturn.append("\n").append(payment.paymentType).append(": ").append(payment.getSubtotal());*/
        toReturn.append("\n").append("Total: ").append(totalSum);
        toReturn.append("\nTransactionID: ").append(counter).append("\nTransaction Date: ").append(paymentDate);
        return toReturn.toString();
    }

}

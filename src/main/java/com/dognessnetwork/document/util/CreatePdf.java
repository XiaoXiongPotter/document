package com.dognessnetwork.document.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf {
	public	static	 void addImageAbsolu(String	resourcePng,String	resourcePdf,String	content){
        try
        {
            //String resourcePng = "C:/Users/Dogness/Pictures/0000100020.png";//
            String RESULT = resourcePdf;
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(RESULT));
            document.open();
            //PDPage page = (PDPage)doc.getDocumentCatalog().getAllPages().get( 0 );
            Image img = Image.getInstance(resourcePng);
            img.setAbsolutePosition(
                    (PageSize.POSTCARD.getWidth() - img.getScaledWidth()) / 2,
                    (PageSize.POSTCARD.getHeight() - img.getScaledHeight()) / 2);
            
           img.setAbsolutePosition(100, 380);
           writer.setCompressionLevel(0);
           Paragraph	paragraph	=	new Paragraph(content);
           paragraph.setIndentationLeft(100f);
           com.itextpdf.text.Font	font	=	new	com.itextpdf.text.Font();
           font.setSize(16f);
           
           paragraph.setFont(font);
           document.add(paragraph);
           document.add(img);
           document.close();
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
}

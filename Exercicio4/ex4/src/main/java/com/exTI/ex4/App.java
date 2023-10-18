package com.exTI.ex4;

import java.net.URL;
import java.util.EnumSet;
import java.util.Scanner;

import com.azure.ai.vision.common.*;
import com.azure.ai.vision.imageanalysis.*;

public class App {

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Digite o nome do arquivo para ser analisado (Se deseja parar digite \"pare\"): ");
    	String linha = sc.nextLine();
    	while(!linha.equals("pare")) {
	        try (
	            VisionServiceOptions serviceOptions = new VisionServiceOptions(
	                new URL("https://ex4tii.cognitiveservices.azure.com/"), "fa86637d0e0b43a6a3e49fab2d2cb619");
	
	        	VisionSource imageSource = VisionSource.fromFile("sample.png");
	
	            ImageAnalysisOptions analysisOptions = new ImageAnalysisOptions()) {
	
	            analysisOptions.setFeatures(EnumSet.of(ImageAnalysisFeature.CAPTION, ImageAnalysisFeature.TEXT));
	
	            analysisOptions.setLanguage("en");
	
	
	            try (
	                ImageAnalyzer analyzer = new ImageAnalyzer(serviceOptions, imageSource, analysisOptions);
	
	                ImageAnalysisResult result = analyzer.analyze()) {
	
	                if (result.getReason() == ImageAnalysisResultReason.ANALYZED) {
	
	                    if (result.getCaption() != null) {
	                        System.out.println(" Descrição:");
	                        System.out.println(result.getCaption().getContent());
	                    }
	
	                    if (result.getText() != null) {
	                        System.out.println(" Textos:");
	                        for (DetectedTextLine line : result.getText()) {
	                            System.out.println(
	                                    "   Linha: " + line.getContent());
	
	                            for (DetectedTextWord word : line.getWords()) {
	                                System.out.println(
	                                        "     Palavra: '" + word.getContent());
	                            }
	                        }
	                    }
	                } else{
	                    ImageAnalysisErrorDetails errorDetails = ImageAnalysisErrorDetails.fromResult(result);
	                    System.out.println(" Analysis failed.");
	                    System.out.println("   Error reason: " + errorDetails.getReason());
	                    System.out.println("   Error code: " + errorDetails.getErrorCode());
	                    System.out.println("   Error message: " + errorDetails.getMessage());
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	System.out.println("Digite o nome do arquivo para leitura (Se deseja parar digite \"pare\"): ");
	        linha = sc.nextLine();
    	}
        sc.close();
    }
}
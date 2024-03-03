package com.thepuar.bedca;

import com.cedarsoftware.util.io.JsonIo;
import com.cedarsoftware.util.io.ReadOptions;
import com.cedarsoftware.util.io.ReadOptionsBuilder;
import com.thepuar.bedca.model.Food;
import com.thepuar.bedca.model.Valores;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {


        File fileFolder = new File("/home/thepuar/bedca_foods");
        File[] fileFoods = fileFolder.listFiles();
        //ReadOptions readOptions = new ReadOptionsBuilder().returnAsMaps().build();
        ReadOptions readOptions = new ReadOptionsBuilder().withLogicalPrimitive(Food.class).build();
        List<Food> lfood = new ArrayList<>();
        for (File food : fileFoods) {
            String text = new String(Files.readAllBytes(food.toPath()), StandardCharsets.UTF_8);
            //Map foodMap = JsonIo.toObjects(text,readOptions, Map.class);
            Food foodClass = JsonIo.toObjects(text,readOptions, Food.class);
            lfood.add(foodClass);

        }

        //Create excel
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("food");
        Row header = sheet.createRow(0);
        Cell id = header.createCell(0);
        id.setCellValue("f_id");
        Cell foodName = header.createCell(1);
        foodName.setCellValue("nombre");

        int contadorRow = 1;
        int contadorCelda = 0;
        for(Food food : lfood){
            Row fila = sheet.createRow(contadorRow++);
            Cell celda = fila.createCell(contadorCelda++);
            celda.setCellValue(food.getF_id());
            celda = fila.createCell(contadorCelda++);
            celda.setCellValue(food.getF_ori_name());

/**            for (Valores valores : food.getValores()){
                celda = fila.createCell(contadorCelda++);
                celda.setCellValue(valores.getC_id());
                celda = fila.createCell(contadorCelda++);
                celda.setCellValue(valores.getC_ori_name());
                celda = fila.createCell(contadorCelda++);
                celda.setCellValue(valores.getEur_name());
                celda = fila.createCell(contadorCelda++);
                celda.setCellValue(valores.getBest_location());
                celda = fila.createCell(contadorCelda++);
                celda.setCellValue(valores.getV_unit());
                celda = fila.createCell(contadorCelda++);
                celda.setCellValue(valores.getMu_description());
            } **/
            contadorCelda =0;
        }

        File excel = new File("/home/thepuar/bedca_foods/food.xlsx");
        FileOutputStream fos = new FileOutputStream(excel);
        workbook.write(fos);
        workbook.close();


    }
}

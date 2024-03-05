package com.thepuar.bedca;

import com.google.gson.Gson;
import com.thepuar.bedca.model.Food;
import com.thepuar.bedca.model.Valores;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static int contadorRow = 1;
    public static int contadorCelda = 0;

    public static void main(String[] args) throws IOException {



        File fileFolder = new File("/home/thepuar/bedca_foods");
        File[] fileFoods = fileFolder.listFiles();
      //  ReadOptions readOptions = new ReadOptionsBuilder().withLogicalPrimitive(Food.class).build();
        List<Food> lfood = new ArrayList<>();
        Gson gson = new Gson();
        for (File food : fileFoods) {
            System.out.println("Leyendo "+food.getName());
            String text = new String(Files.readAllBytes(food.toPath()), StandardCharsets.UTF_8);
            text = text.replaceAll("\\{\\}","\"\"");
            //Map foodMap = JsonIo.toObjects(text,readOptions, Map.class);
            //Food foodClass = JsonIo.toObjects(text,readOptions, Food.class);
       //     Food foodClass = JsonReader.toObjects(text,readOptions,Food.class);
            Food foodClass = gson.fromJson(text,Food.class);
            for(Valores value: foodClass.getFoodvalue()){
                System.out.println(value);
            }
            lfood.add(foodClass);


        }

        //Create excel
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("food");
        Row header = sheet.createRow(0);
        Cell id = header.createCell(1);
        id.setCellValue("f_id");
        Cell foodName = header.createCell(2);
        foodName.setCellValue("nombre");



        for(Food food : lfood){
            Row row = sheet.createRow(contadorRow++);
            row.createCell(contadorCelda++);
            addCell(row,food.getF_id());
            addCell(row,food.getF_ori_name());
            for(Valores valores : food.getFoodvalue()){
                addCell(row,valores.getC_id());
                addCell(row,valores.getC_ori_name());
                addCell(row,valores.getEur_name());
                addCell(row,valores.getBest_location()+" "+valores.getV_unit());
            }


            contadorCelda =0;
        }

        File excel = new File("/home/thepuar/bedca_foods/food.xlsx");
        FileOutputStream fos = new FileOutputStream(excel);
        workbook.write(fos);
        workbook.close();
    }
    public static void addCell(Row row, String value){

        Cell celda = row.createCell(App.contadorCelda++);
        celda.setCellValue(value);

    }
}

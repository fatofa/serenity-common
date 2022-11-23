package convertExcelToJson;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileInputStream;

public class ExcelToJsonConvertor {
    private JSONObject readExcelFileAsJsonObject_RowWise(String filePath) {
        DataFormatter dataFormatter = new DataFormatter(); // Create a DataFormatter to format and get each cell's value as String
        JSONObject workbookJson = new JSONObject();
        JSONArray sheetJson;
        JSONObject rowJson;
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0); // có thể dùng vòng for để duyệt sheet
            sheetJson = new JSONArray();
            int lastRowNum = sheet.getLastRowNum();
            int lastColumnNum = sheet.getRow(0).getLastCellNum();
            System.out.println(lastRowNum);
            System.out.println(lastColumnNum);
            Row firstRowAsKeys = sheet.getRow(0); // first row as a json keys
            for (int i = 1; i <= lastRowNum; i++) {
                rowJson = new JSONObject();
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < lastColumnNum; j++) {
                        rowJson.put(firstRowAsKeys.getCell(j).getStringCellValue(),
                                dataFormatter.formatCellValue(row.getCell(j)));

                    }
//                    rowJson.put(firstRowAsKeys.getCell(0).getStringCellValue(), dataFormatter.formatCellValue(row.getCell(0)));
//                    rowJson.put(firstRowAsKeys.getCell(1).getStringCellValue(), dataFormatter.formatCellValue(row.getCell(1)));
//                    rowJson.put(firstRowAsKeys.getCell(2).getStringCellValue(), dataFormatter.formatCellValue(row.getCell(2)));
//                    rowJson.put(firstRowAsKeys.getCell(3).getStringCellValue(), dataFormatter.formatCellValue(row.getCell(3)));
//                    rowJson.put(firstRowAsKeys.getCell(4).getStringCellValue(), dataFormatter.formatCellValue(row.getCell(4)));
//                    rowJson.put(firstRowAsKeys.getCell(5).getStringCellValue(), dataFormatter.formatCellValue(row.getCell(5)));


                    sheetJson.add(rowJson);
                }
            }
            workbookJson.put(sheet.getSheetName(), sheetJson);

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return workbookJson;
    }

    public static void main(String arg[]) {
        ExcelToJsonConvertor excelConvertor = new ExcelToJsonConvertor();
        String filePath = "D:\\example.xlsx";
        JSONObject data = excelConvertor.readExcelFileAsJsonObject_RowWise(filePath);
        System.out.println(data);
    }

}

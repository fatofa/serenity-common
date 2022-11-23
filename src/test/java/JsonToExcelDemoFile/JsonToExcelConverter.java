package JsonToExcelDemoFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//Workbook: đại diện cho một file Excel. Nó được triển khai dưới hai class là: HSSFWorkbook và XSSFWorkbook tương ứng cho định dạng .xls và .xlsx .
//Sheet: đại diện cho một bảng tính Excel (một file Excel có thể có nhiều Sheet). Nó có 2 class là HSSFSheet và XSSFSheet.
//Row: đại diện cho một hàng trong một bảng tính (Sheet). Nó có 2 class là HSSFRow và XSSFRow.
//Cell: đại diện cho một ô trong một hàng (Row). Tương tự nó cũng có 2 class là HSSFCell and XSSFCell.
public class JsonToExcelConverter {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Method to convert json file to excel file
     * @param srcFile
     * @param targetFileExtension
     * @return file
     */
    public File jsonFileToExcelFile(File srcFile, String targetFileExtension) {
        try {
            if (!srcFile.getName().endsWith(".json")) {
                throw new IllegalArgumentException("The source file should be .json file only");
            } else {
                Workbook workbook = null;

                //tạo workbook dựa trên định dạng tệp
                if (targetFileExtension.equals(".xls")) {
                    workbook = new HSSFWorkbook();
                } else if (targetFileExtension.equals(".xlsx")) {
                    workbook = new XSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The target file extension should be .xls or .xlsx only");
                }

                //Đọc tệp json thông qua readTree của mapper
                ObjectNode jsonData = (ObjectNode) mapper.readTree(srcFile);
                //Lặp lại từng sheets
                Iterator<String> sheetItr = jsonData.fieldNames();
                while (sheetItr.hasNext()) {

                    // create the workbook sheet
                    String sheetName = sheetItr.next();
                    Sheet sheet = workbook.createSheet(sheetName);

                    ArrayNode sheetData = (ArrayNode) jsonData.get(sheetName);
                    ArrayList<String> headers = new ArrayList<String>();

                    //Creating cell style for header to make it bold
                    CellStyle headerStyle = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    headerStyle.setFont(font);

                    //creating the header into the sheet
                    Row header = sheet.createRow(0);
                    Iterator<String> it = sheetData.get(0).fieldNames();
                    int headerIdx = 0;
                    while (it.hasNext()) {
                        String headerName = it.next();
                        headers.add(headerName);
                        Cell cell=header.createCell(headerIdx++);
                        cell.setCellValue(headerName);
                        //apply the bold style to headers
                        cell.setCellStyle(headerStyle);
                    }

                    //Iterating over the each row data and writing into the sheet
                    for (int i = 0; i < sheetData.size(); i++) {
                        ObjectNode rowData = (ObjectNode) sheetData.get(i);
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < headers.size(); j++) {
                            String value = rowData.get(headers.get(j)).asText();
                            row.createCell(j).setCellValue(value);
                        }
                    }
                    for (int i = 0; i < headers.size(); i++) {
                        sheet.autoSizeColumn(i);
                    }
                }
                //creating a target file
                String filename = srcFile.getName();
                filename = filename.substring(0, filename.lastIndexOf(".json")) + targetFileExtension;
                File targetFile = new File(srcFile.getParent(), filename);

                // write the workbook into target file
                FileOutputStream fos = new FileOutputStream(targetFile);
                workbook.write(fos);

                //close the workbook and fos
                workbook.close();
                fos.close();
                return targetFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Main method to test this converter
     *
     * @param args
     */
    public static void main(String[] args) throws IOException, ParseException {
        JsonToExcelConverter converter = new JsonToExcelConverter();
        File srcFile = new File("C:\\demoo\\serenity-common\\src\\test\\java\\JsonToExcelDemoFile\\DemoJson2.json");
        File xlsxFile = converter.jsonFileToExcelFile(srcFile, ".xlsx");
        System.out.println("Sucessfully converted JSON to Excel file at =" + xlsxFile.getAbsolutePath());
    }
}

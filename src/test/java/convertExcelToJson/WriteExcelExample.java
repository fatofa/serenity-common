package convertExcelToJson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelExample {
    public static final int CELL1 = 0;
    public static final int CELL2 = 1;
    public static final int CELL3 = 2;
    public static final int CELL4 = 3;
    public static final int CELL5 = 4;
    private static CellStyle cellStyleFormatNumber = null;

    public static void writeExcel(List<DemoTest> datas, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Demo"); // Create sheet with sheet name

        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);

        // Write data
        rowIndex++;
        for (DemoTest data : datas) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(data, row);
            rowIndex++;
        }

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
        System.out.println("Success!!!!!!!");
    }

    // Create demo data
    private static List<DemoTest> getDatas() {
        List<DemoTest> listBook = new ArrayList<>();
        DemoTest book1 = new DemoTest(1, "http://demo.guru99.com/v4", "Huy", "123456", "Pass");
        DemoTest book2 = new DemoTest(2, "http://demo.guru99.com/v4", "a", "222222", "Fail");
        DemoTest book3 = new DemoTest(3, "http://demo.guru99.com/v4", "b", "444444", "Pass");
        DemoTest book4 = new DemoTest(4, "http://demo.guru99.com/v4", "c", "666666", "Fail");
        DemoTest book5 = new DemoTest(5, "http://demo.guru99.com/v4", "d", "888888", "Pass");
        listBook.add(book1);
        listBook.add(book2);
        listBook.add(book3);
        listBook.add(book4);
        listBook.add(book5);

        return listBook;
    }

    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(CELL1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TCID");

        cell = row.createCell(CELL2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("URL");

        cell = row.createCell(CELL3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Username");

        cell = row.createCell(CELL4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Password");

        cell = row.createCell(CELL5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Pass/Fail");
    }

    // Write data
    private static void writeBook(DemoTest data, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(CELL1);
        cell.setCellValue(data.getTcid());

        cell = row.createCell(CELL2);
        cell.setCellValue(data.getUrl());

        cell = row.createCell(CELL3);
        cell.setCellValue(data.getUsername());


        cell = row.createCell(CELL4);
        cell.setCellValue(data.getPass());

        cell = row.createCell(CELL5);
        cell.setCellValue(data.getPassfail());

    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }


    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

    public static void main(String[] args) throws IOException {
        final List<DemoTest> books = getDatas();
        final String excelFilePath = "D:\\example.xlsx";
        writeExcel(books, excelFilePath);
    }

}

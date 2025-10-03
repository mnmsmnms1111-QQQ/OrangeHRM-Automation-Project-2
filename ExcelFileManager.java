package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileManager {

    XSSFWorkbook workbook;
    Sheet sheet;
    private int columnsNumber;


    public ExcelFileManager(String filePath, String sheetName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            columnsNumber = sheet.getRow(0).getPhysicalNumberOfCells();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getSpecificDataValue(int row, int col) {
        try {
            Cell cell = sheet.getRow(row).getCell(col);
            if (cell == null) {
                return ""; // return empty if cell does not exist
            }

            // Use DataFormatter to safely convert any type (numeric, boolean, string, blank) to text
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String[][] addDataToTwoDArray() {
        List<String[]> validRows = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // start after header
            if (sheet.getRow(i) == null) continue; // skip null rows

            String[] rowData = new String[columnsNumber];
            boolean isEmpty = true;

            for (int j = 0; j < columnsNumber; j++) {
                String value = getSpecificDataValue(i, j);
                rowData[j] = value;
                if (value != null && !value.trim().isEmpty()) {
                    isEmpty = false;
                }
            }

            if (!isEmpty) { // only add non-empty rows
                validRows.add(rowData);
            }
        }
        return validRows.toArray(new String[0][0]);
    }

}
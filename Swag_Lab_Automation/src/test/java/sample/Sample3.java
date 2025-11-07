package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

public class Sample3
{
	static WebDriver driver;
	static String path= System.getProperty("user.dir");

	@DataProvider(name="testData")
	public static String[][] getData(Method m) throws EncryptedDocumentException, IOException
	{
		String sheetName= m.getName();
		File file= new File(path+"/src/test/resources/Test Data/Testdata.xlsx");
		FileInputStream f= new FileInputStream(file);
		Workbook wb= WorkbookFactory.create(f);
		Sheet sheet= wb.getSheet(sheetName);

		int totalRow= sheet.getLastRowNum();
		System.out.println(totalRow);
		Row rowCells= sheet.getRow(0);
		int total_column= rowCells.getLastCellNum();
		System.out.println(total_column);

		DataFormatter format= new DataFormatter();
		String testData[][]= new String[totalRow][total_column];

		for(int i=1; i<= totalRow; i++)
		{
			for(int j=0; j<total_column; j++)
			{
				testData[i-1][j]= format.formatCellValue(sheet.getRow(i).getCell(j));
				System.out.println(testData[i-1][j]);
			}
		}

		return testData;
	}

}
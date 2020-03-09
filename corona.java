/**
 * 
 */
package corona.report.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * @author Nhu.Le
 *
 */
public final class JsoupUtil {
	
	public static String matches(String value, String regex) {
	      Pattern r = Pattern.compile(regex);
	      Matcher m = r.matcher(value);
	      if (m.find( )) {
	         return  m.group(0);
	      }else {
	         return "";
	      }
	}

	public static void parse() throws IOException {
		// String url = "https://corona.kompa.ai/";
		// Document doc = Jsoup.connect(url).get();

		File input = new File(System.getProperty("user.dir") + "/html/test.html");
		Document doc = Jsoup.parse(input, "UTF-8");

		Elements elements = doc.getAllElements();

		Elements list = doc.select(
				"#root > div.container > div.row:nth-child(2) > div.row-mob.col-md-2:nth-child(4) > div.box > div.tabs-container > div");

		for (Element element : elements) {

			// System.out.println(element.text());

			String cssSelector = element.cssSelector();
			String text = element.text();
			if (cssSelector.indexOf("list-group-item:") > -1 && cssSelector.indexOf("> span.badge.") == -1
					&& text.length() < 20 && cssSelector.indexOf("> img") == -1 && cssSelector.indexOf("> br") == -1
					&& cssSelector.indexOf("> small") == -1) {

				//System.out.println(element.cssSelector());
				//System.out.println(element.text());
				
				String country = matches(text, "^\\D+");
				String total = matches(text, "[0-9,.]+");
				
				System.out.println(country + "\t" + total);
			}
		}

	}

	public static void selenium(String url) {
		System.setProperty("phantomjs.binary.path", System.getProperty("user.dir") + "/phantom/phantomjs");
		WebDriver driver = new PhantomJSDriver();
		driver.get(url);

		String pageSource = driver.getPageSource();

		System.out.println("aaa:" + pageSource);

		// WebElement element =
		// driver.findElement(By.className("ul[class=\"list-group\"]"));

		// WebElement element =
		// driver.findElement(By.xpath("//ul[@class='list-group']"));

		// WebElement element = driver.findElement(By.cssSelector("#root > div >
		// div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > ul"));

		// Should see: "cheese! - Google Search"
		// System.out.println("Page title is: " + element.getText());

		// Close the browser
		driver.quit();
	}

	public static void main(String[] args) {

		try {
			parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

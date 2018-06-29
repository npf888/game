package com.tools.finder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 * 把作弊者名单生成指定的文件类型的生成器
 * @author Thinker
 *
 */
public class FuckerListGenerator
{

	public <T extends IFucker> void toHtmlFile(String dir,
			String dateString, List<T> fuckers) throws FileNotFoundException,UnsupportedEncodingException
	{
		String fileName = dir + dateString + "作弊者名单.html";
		PrintWriter out = new PrintWriter(new PrintStream(new FileOutputStream(fileName), true, "UTF-8"));
		printHeader(out);
		printBody(out, fuckers);
		printFooter(out);
		out.flush();
		out.close();
	}

	private <T extends IFucker> void printFooter(PrintWriter out)
	{
		out.print("</html>");
	}

	private <T extends IFucker> void printBody(PrintWriter out,List<T> fuckers)
	{
		out.print("<body>");
		out.print("<table border = '2'>");
		for (IFucker fucker : fuckers) 
		{
			out.print("<tr>");
			out.print("<td>");
			out.print(fucker.toString());
			out.print("</td>");
			out.print("</tr>");
		}
		out.print("</table>");
		out.print("</body>");
	}

	private void printHeader(PrintWriter out)
	{
		out.print("<html content = 'text/html' charset = 'UTF-8'>");
		out.print("<head>");
		out.print("疑似作弊者数据");
		out.print("</head>");
	}
}
